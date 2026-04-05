package com.vromanyu.reactive.users.dto;

public record CreatedReactiveUserResponse(
        String uuid,
        String firstName,
        String lastName,
        String email) {
}
