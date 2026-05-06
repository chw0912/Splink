package com.splink.domain.user.dto;

public record UserUpdateRequest(String name, String password) {

    public UserUpdateRequest {
        if (name != null && name.isBlank()) name = null;
        if (password != null && password.isBlank()) password = null;
    }
}