package com.vromanyu.reactive.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record CreateUserRequest(
        @NotBlank(message = "firstName can't be empty") String firstName,
        @NotBlank(message = "lastName can't be empty") String lastName,
        @Email(message = "invalid email format") String email,
        @NotBlank(message = "password can't be empty") String password) {
}
