package com.vromanyu.reactive.users.dto;

public record GetReactiveUserResponse(
        String uuid,
        String firstName,
        String lastName,
        String email) {
}
