package com.application_server.server.security;

import com.application_server.server.model.Role;
import com.application_server.server.service.CustomerUserDetailsService;
import io.jsonwebtoken.*;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.util.Base64;
import java.util.Date;
import java.util.Set;

import static java.util.stream.Collectors.toList;

@Component
public class JwtTokenProvider {

    private String secret = "test";

    private final CustomerUserDetailsService userDetailsService;

    public JwtTokenProvider(CustomerUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @PostConstruct
    protected void init() {
        secret = Base64.getEncoder().encodeToString(secret.getBytes());
    }

    public String createToken(String email, Set<Role> roles) {
        //todo можно без role
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("roles", roles.stream().map(Role::getRole).collect(toList()));

        Date now = new Date();
        long validityInMillisecond = 600000;
        Date validity = new Date(now.getTime() + validityInMillisecond);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
    }

    public String getToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public boolean validateToken(String token) {
        try {
            Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);

            if (claims.getBody().getExpiration().before(new Date())) {
                return false;
            }

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            throw new InvalidJwtAuthenticationException("Expired or invalid JWT token");
        }
    }
}









/*
*
* public Authentication getAuthentication(HttpServletRequest request) {
    String token = request.getHeader(HEADER_STRING);
    if (token != null) {
        // parse the token.
        String user = getUsername(token);

        String roles = getBody(token).get("roles", String.class);
        List<GrantedAuthority> grantedAuths =
                AuthorityUtils.commaSeparatedStringToAuthorityList(roles);

        return user != null ?
                new UsernamePasswordAuthenticationToken(user, null,
                        grantedAuths) :
                null;
    }
    return null;
}*/