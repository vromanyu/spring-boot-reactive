package com.vromanyu.reactive.users.controller;

import com.vromanyu.reactive.users.dto.CreateReactiveUserRequest;
import com.vromanyu.reactive.users.dto.CreatedReactiveUserResponse;
import com.vromanyu.reactive.users.dto.GetReactiveUserResponse;
import com.vromanyu.reactive.users.service.ReactiveUserService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.net.URI;

@RestController
@RequestMapping(value = "/api/v{version}/reactive-users")
public class ReactiveUsersV1Controller {

    private static final Logger reactiveLogger = Loggers.getLogger(ReactiveUsersV1Controller.class);
    private final ReactiveUserService reactiveUserService;

    public ReactiveUsersV1Controller(ReactiveUserService reactiveUserService) {
        this.reactiveUserService = reactiveUserService;
    }

    @PostMapping(
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<CreatedReactiveUserResponse>> createReactiveUser(@RequestBody @Valid Mono<CreateReactiveUserRequest> createUserRequest,
                                                                                ServerHttpRequest request) {
        reactiveLogger.info("Received request to create reactive user: {}", createUserRequest);
        URI requestURI = request.getURI();
        return reactiveUserService.createReactiveUser(createUserRequest)
                .map(createdReactiveUserResponse -> ResponseEntity.created(UriComponentsBuilder.fromUri(requestURI).pathSegment(createdReactiveUserResponse.uuid()).build().toUri()).body(createdReactiveUserResponse));
    }

    @GetMapping(
            value = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<GetReactiveUserResponse>> getReactiveUser(@PathVariable String uuid) {
        reactiveLogger.info("Received request to get reactive user: {}", uuid);
        return reactiveUserService.getReactiveUser(uuid).map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping(
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public ResponseEntity<Flux<GetReactiveUserResponse>> getAllReactiveUsers(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                                                             @RequestParam(value = "limit", defaultValue = "0") int limit) {
        reactiveLogger.info("Received request to get all reactive users");
        return ResponseEntity.ok(reactiveUserService.getAllReactiveUsers(offset, limit));
    }
}
