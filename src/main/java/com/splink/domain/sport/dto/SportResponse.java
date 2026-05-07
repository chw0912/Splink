package com.splink.domain.sport.dto;

import com.splink.domain.sport.entity.Sport;

import java.time.LocalDateTime;

public record SportResponse(Long id, String name, LocalDateTime createdAt) {

    public static SportResponse from(Sport sport) {
        return new SportResponse(sport.getId(), sport.getName(), sport.getCreatedAt());
    }
}
