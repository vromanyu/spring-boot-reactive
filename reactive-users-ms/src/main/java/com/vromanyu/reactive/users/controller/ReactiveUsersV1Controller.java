package com.vromanyu.reactive.users.controller;

import com.vromanyu.reactive.users.dto.CreateUserRequest;
import com.vromanyu.reactive.users.dto.CreatedUserResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping(value = "/api/v{version}/reactive-users")
public class ReactiveUsersV1Controller {

    private static final Logger reactiveLogger = Loggers.getLogger(ReactiveUsersV1Controller.class);

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CreatedUserResponse>> createReactiveUser(@RequestBody @Valid Mono<CreateUserRequest> createUserRequest,
                                                                        ServerHttpRequest request) {
        return createUserRequest.log(reactiveLogger)
                .map(req -> new CreatedUserResponse(UUID.randomUUID().toString(),
                        req.firstName(),
                        req.lastName(),
                        req.email()))
                .map(res -> {
                    URI location = UriComponentsBuilder.fromUri(request.getURI())
                            .path("/{uuid}")
                            .buildAndExpand(res.uuid())
                            .toUri();
                    return ResponseEntity.created(location).body(res);
                });
    }
}
