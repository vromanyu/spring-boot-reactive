package com.vromanyu.reactive.users.dto;

public record CreatedUserResponse(
        String uuid,
        String firstName,
        String lastName,
        String email) {
}
