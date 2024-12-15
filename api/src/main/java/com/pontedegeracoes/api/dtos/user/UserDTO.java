package com.pontedegeracoes.api.dtos.user;

import java.util.Set;

import com.pontedegeracoes.api.entitys.Meeting;
import com.pontedegeracoes.api.entitys.Necessity;

public record UserDTO(
                Long id,
                String name,
                int age,
                String type,
                String photo,
                String email,
                String meetingPreference,
                String town,
                String state,
                Set<Necessity> necessities,
                Set<Meeting> meetings) {
}