package com.vromanyu.reactive.users.repository;

import com.vromanyu.reactive.users.entity.ReactiveUser;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

@Repository
public interface ReactiveUserRepository extends ReactiveCrudRepository<ReactiveUser, Integer> {
    Mono<ReactiveUser> findByUuid(String uuid);
}
