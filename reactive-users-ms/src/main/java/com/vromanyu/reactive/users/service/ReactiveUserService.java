package com.vromanyu.reactive.users.service;

import com.vromanyu.reactive.users.dto.CreateReactiveUserRequest;
import com.vromanyu.reactive.users.dto.CreatedReactiveUserResponse;
import reactor.core.publisher.Mono;

public interface ReactiveUserService {
    Mono<CreatedReactiveUserResponse> createReactiveUser(Mono<CreateReactiveUserRequest> createReactiveUserRequest);
}
