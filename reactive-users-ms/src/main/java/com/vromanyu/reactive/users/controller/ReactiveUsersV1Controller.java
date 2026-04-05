package com.vromanyu.reactive.users.controller;

import com.vromanyu.reactive.users.dto.CreateReactiveUserRequest;
import com.vromanyu.reactive.users.dto.CreatedReactiveUserResponse;
import com.vromanyu.reactive.users.dto.GetReactiveUserResponse;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
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
    public Mono<ResponseEntity<CreatedReactiveUserResponse>> createReactiveUser(@RequestBody @Valid Mono<CreateReactiveUserRequest> createUserRequest,
                                                                                ServerHttpRequest request) {
        return createUserRequest.log(reactiveLogger)
                .map(req -> new CreatedReactiveUserResponse(UUID.randomUUID().toString(),
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

    @GetMapping(
            value = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<GetReactiveUserResponse>> getReactiveUser(@PathVariable String uuid) {
        return Mono.just(ResponseEntity.ok(new GetReactiveUserResponse(uuid, "John", "Doe", "johndoe@gmail.com")));
    }
}
