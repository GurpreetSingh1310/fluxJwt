package com.flux.fluxReactive.repository;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import com.flux.fluxReactive.model.User;

import reactor.core.publisher.Mono;


public interface UserRepository extends ReactiveCrudRepository<User, String> {
    Mono<User> findByEmail(String email);
}