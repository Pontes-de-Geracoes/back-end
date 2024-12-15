package com.pontedegeracoes.api.dtos.user;

import jakarta.validation.constraints.*;
import java.util.Set;

public record UserCreateDTO(
    @NotBlank String name,
    @NotNull int age,
    @NotBlank String type,
    String photo,
    @Email @NotBlank String email,
    @NotBlank String password,
    @Pattern(regexp = "in person|remote|hybrid") String meetingPreference,
    @NotBlank String town,
    @Size(max = 2) String state,
    Set<Long> necessities) {
}