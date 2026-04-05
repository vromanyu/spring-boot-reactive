package com.vromanyu.reactive.users.service;

import com.vromanyu.reactive.users.dto.CreateReactiveUserRequest;
import com.vromanyu.reactive.users.dto.CreatedReactiveUserResponse;
import com.vromanyu.reactive.users.dto.GetReactiveUserResponse;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ReactiveUserService {
    Mono<CreatedReactiveUserResponse> createReactiveUser(Mono<CreateReactiveUserRequest> createReactiveUserRequest);

    Mono<GetReactiveUserResponse> getReactiveUser(String uuid);

    Flux<GetReactiveUserResponse> getAllReactiveUsers(int offset, int limit);
}
