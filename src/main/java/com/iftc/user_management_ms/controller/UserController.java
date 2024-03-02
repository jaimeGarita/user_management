package com.iftc.user_management_ms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iftc.user_management_ms.model.User;
import com.iftc.user_management_ms.service.user.UserService;

@RestController
@CrossOrigin
@RequestMapping("/user/")
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping
    public ResponseEntity<?> getAllUsers() {
        return new ResponseEntity<>(userService.getAllUsers(), HttpStatus.OK);
    }

    @PostMapping("register/")
    public ResponseEntity<?> registerUser(@RequestBody User user) {

        if (user != null) {
            userService.saveUser(user);
        } else {
            return new ResponseEntity<>("USER_NOT_FOUND", HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(user, HttpStatus.OK);

    }

}
