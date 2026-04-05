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
import java.time.Duration;
import java.util.UUID;

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
        URI requestURI = request.getURI();
        return reactiveUserService.createReactiveUser(createUserRequest)
                .map(createdReactiveUserResponse -> ResponseEntity.created(UriComponentsBuilder.fromUri(requestURI).pathSegment(createdReactiveUserResponse.uuid()).build().toUri()).body(createdReactiveUserResponse));
    }

    @GetMapping(
            value = "/{uuid}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public Mono<ResponseEntity<GetReactiveUserResponse>> getReactiveUser(@PathVariable String uuid) {
        return Mono.just(ResponseEntity.ok(new GetReactiveUserResponse(uuid, "John", "Doe", "johndoe@gmail.com")));
    }

    @GetMapping(
            produces = MediaType.TEXT_EVENT_STREAM_VALUE
    )
    public ResponseEntity<Flux<GetReactiveUserResponse>> getAllReactiveUsers(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                                                             @RequestParam(value = "limit", defaultValue = "0") int limit,
                                                                             ServerHttpRequest request) {
        return ResponseEntity.ok(Flux.just(
                        new GetReactiveUserResponse(UUID.randomUUID().toString(), "John", "Doe", "johndoe@gmail.com"),
                        new GetReactiveUserResponse(UUID.randomUUID().toString(), "John", "Doe", "johndoe@gmail.com"),
                        new GetReactiveUserResponse(UUID.randomUUID().toString(), "John", "Doe", "johndoe@gmail.com"),
                        new GetReactiveUserResponse(UUID.randomUUID().toString(), "John", "Doe", "johndoe@gmail.com"),
                        new GetReactiveUserResponse(UUID.randomUUID().toString(), "John", "Doe", "johndoe@gmail.com"),
                        new GetReactiveUserResponse(UUID.randomUUID().toString(), "John", "Doe", "johndoe@gmail.com"),
                        new GetReactiveUserResponse(UUID.randomUUID().toString(), "John", "Doe", "johndoe@gmail.com"),
                        new GetReactiveUserResponse(UUID.randomUUID().toString(), "John", "Doe", "johndoe@gmail.com"),
                        new GetReactiveUserResponse(UUID.randomUUID().toString(), "John", "Doe", "johndoe@gmail.com"),
                        new GetReactiveUserResponse(UUID.randomUUID().toString(), "John", "Doe", "johndoe@gmail.com"))
                .skip(offset)
                .take(limit)
                .delayElements(Duration.ofSeconds(1)).doOnCancel(() -> reactiveLogger.info("request: {} was cancelled", request.getId())));
    }
}
