package com.flux.fluxReactive.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.flux.fluxReactive.model.User;
import com.flux.fluxReactive.services.UserService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/signup")
    public Mono<ResponseEntity<String>> registerUser(@RequestBody User user) {
        return userService.registerUser(user)
        .map(token -> ResponseEntity.ok("Bearer " + token));
    }

    @PostMapping("/login")
    public Mono<String> loginUser(@RequestBody User user) {
        return userService.loginUser(user);
    }

}
