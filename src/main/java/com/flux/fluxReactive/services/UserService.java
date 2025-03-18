package com.flux.fluxReactive.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
// import com.flux.fluxReactive.FluxReactiveApplication;
import com.flux.fluxReactive.model.User;
import com.flux.fluxReactive.repository.UserRepository;
import com.flux.fluxReactive.util.JwtUtil;

import reactor.core.publisher.Mono;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
    }

    public Mono<User> registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user)
                .map(u -> {
                    jwtUtil.generateToken(u.getEmail(), u.getRoles());
                    return u;
                });
    }

    public Mono<String> loginUser(User user) {
        return userRepository.findByEmail(user.getEmail())
                .filter(u -> passwordEncoder.matches(user.getPassword(), u.getPassword()))
                .map(u -> jwtUtil.generateToken(u.getEmail(), u.getRoles()));
    }

}
