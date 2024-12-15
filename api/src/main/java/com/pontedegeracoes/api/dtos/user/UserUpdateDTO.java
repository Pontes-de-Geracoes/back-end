package com.pontedegeracoes.api.dtos.user;

import java.util.Set;

public record UserUpdateDTO(
    String name,
    Integer age,
    String type,
    String photo,
    String email,
    String meetingPreference,
    String town,
    String state,
    // ! The user can't update their password through this endpoint.
    Set<Long> necessities) {
}