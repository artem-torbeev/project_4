package com.application_server.server.restController;

import com.application_server.server.model.Role;
import com.application_server.server.model.UserJwt;
import com.application_server.server.security.JwtTokenProvider;
import com.application_server.server.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toList;
import static org.springframework.http.ResponseEntity.ok;

@RestController
@RequestMapping
public class LoginRestController {

    private final AuthenticationManager authenticationManager;

    private final JwtTokenProvider jwtTokenProvider;

    private final UserService userService;

    public LoginRestController(AuthenticationManager authenticationManager,
                               JwtTokenProvider jwtTokenProvider,
                               UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<UserJwt> login(@RequestBody UserJwt user) {

        try {
//            TODO Enter email instead of password json => "email":"admin@com"
            String email = user.getEmail();
            String password= user.getPassword();
            Set<Role> roleSet = userService.findUserByEmail(email).getRole();

            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
            String token = jwtTokenProvider.createToken(email, roleSet);

            Map<String, Object> model = new HashMap<>();
            model.put("token", token);
            model.put("role", roleSet.stream().map(Role::getRole).collect(toList()));

            return ResponseEntity.ok(new UserJwt(email, model));

        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username/password supplied");
        }
    }

     //TODO test Security
    @GetMapping("/me")
    public ResponseEntity<Map<String, Object>> currentUser(@AuthenticationPrincipal UserDetails userDetails) {
        Map<String, Object> model = new HashMap<>();
        model.put("username", userDetails.getUsername());
        model.put("roles", userDetails.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(toList())
        );
        return ok(model);
    }

}
