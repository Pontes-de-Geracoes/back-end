package com.pontedegeracoes.api.dtos.user;

import jakarta.validation.constraints.*;
import java.util.Set;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(example = """
        {
          "name": "Maria Silva",
          "age": 75,
          "type": "elderly",
          "email": "mariad@email.com",
          "password": "password123",
          "meetingPreference": "hybrid",
          "town": "São Paulo",
          "state": "SP",
          "necessities": [1, 2]
        }
        """)
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