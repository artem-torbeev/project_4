package com.application_server.server.restController;

import com.application_server.server.model.User;
import com.application_server.server.model.UserDto;
import com.application_server.server.service.UserFactory;
import com.application_server.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/admin")
public class AdminRestController {

    private final UserService userService;

    private final UserFactory userFactory;

    @Autowired
    public AdminRestController(UserService userService, UserFactory userFactory) {
        this.userService = userService;
        this.userFactory = userFactory;
    }

    @GetMapping("/all")
    public ResponseEntity<List<UserDto>> showAllUser() {
        List<UserDto> listUserDto = userService.findAll().stream()
                .map(userFactory::create).collect(Collectors.toList());
        if (listUserDto.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(listUserDto, HttpStatus.OK);
    }


    @PostMapping("/create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User newUser = userService.addUser(user);
        return new ResponseEntity<>(userFactory.create(newUser), HttpStatus.CREATED);
    }

    @PutMapping("/edit")
    public ResponseEntity<UserDto> editUser(@RequestBody UserDto user) {
        if (user == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long id = user.getId();
        User updateUser = userService.updateUserById(id, user);
        return new ResponseEntity<>(userFactory.create(updateUser), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long id) {
        if (userService.findUserById(id) == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        User user = userService.findUserById(id);
        return new ResponseEntity<>(userFactory.create(user), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        if (id == null) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        userService.deleteUserById(id);
        return new ResponseEntity<>("User was deleted ...", HttpStatus.NO_CONTENT);
    }

}

