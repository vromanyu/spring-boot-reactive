package com.vromanyu.reactive.users.service;

import com.vromanyu.reactive.users.dto.CreateReactiveUserRequest;
import com.vromanyu.reactive.users.dto.CreatedReactiveUserResponse;
import com.vromanyu.reactive.users.dto.GetReactiveUserResponse;
import com.vromanyu.reactive.users.entity.ReactiveUser;
import com.vromanyu.reactive.users.repository.ReactiveUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.util.Logger;
import reactor.util.Loggers;

import java.util.UUID;

@Service
@Transactional
public class ReactiveUserServiceImpl implements ReactiveUserService {

    private static final Logger reactiveLogger = Loggers.getLogger(ReactiveUserServiceImpl.class);
    private final ReactiveUserRepository reactiveUserRepository;

    public ReactiveUserServiceImpl(ReactiveUserRepository reactiveUserRepository) {
        this.reactiveUserRepository = reactiveUserRepository;
    }

    @Override
    public Mono<CreatedReactiveUserResponse> createReactiveUser(Mono<CreateReactiveUserRequest> createReactiveUserRequest) {
        return createReactiveUserRequest.log(reactiveLogger)
                .map(request -> {
                    ReactiveUser reactiveUser = new ReactiveUser();
                    reactiveUser.setUuid(UUID.randomUUID().toString());
                    reactiveUser.setFirstName(request.firstName());
                    reactiveUser.setLastName(request.lastName());
                    reactiveUser.setEmail(request.email());
                    reactiveUser.setPassword(request.password());
                    return reactiveUser;
                }).flatMap(reactiveUserRepository::save)
                .map(savedEntity -> new CreatedReactiveUserResponse(savedEntity.getUuid(),
                        savedEntity.getFirstName(),
                        savedEntity.getLastName(),
                        savedEntity.getEmail()));
    }

    @Override
    public Mono<GetReactiveUserResponse> getReactiveUser(String uuid) {
        return reactiveUserRepository.findByUuid(uuid)
                .log(reactiveLogger)
                .map(reactiveUser -> new GetReactiveUserResponse(
                        reactiveUser.getUuid(),
                        reactiveUser.getFirstName(),
                        reactiveUser.getLastName(),
                        reactiveUser.getEmail()
                ));
    }

    @Override
    public Flux<GetReactiveUserResponse> getAllReactiveUsers(int offset, int limit) {
        return reactiveUserRepository.findAll()
                .log(reactiveLogger)
                .skip(offset)
                .take(limit)
                .map(reactiveUser -> new GetReactiveUserResponse(
                        reactiveUser.getUuid(),
                        reactiveUser.getFirstName(),
                        reactiveUser.getLastName(),
                        reactiveUser.getEmail()
                ));
    }
}
