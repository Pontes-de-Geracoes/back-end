package com.pontedegeracoes.api.mappers;

import com.pontedegeracoes.api.dtos.user.UserCreateDTO;
import com.pontedegeracoes.api.dtos.user.UserDTO;
import com.pontedegeracoes.api.dtos.user.UserUpdateDTO;
import com.pontedegeracoes.api.entitys.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
  public UserDTO toDTO(User user) {
    return new UserDTO(
        user.getId(),
        user.getName(),
        user.getAge(),
        user.getType(),
        user.getPhoto(),
        user.getEmail(),
        user.getMeetingPreference(),
        user.getTown(),
        user.getState(),
        user.getNecessities(),
        user.getMeetings());
  }

  public User toEntity(UserCreateDTO dto) {
    User user = new User();
    user.setName(dto.name());
    user.setAge(dto.age());
    user.setType(dto.type());
    user.setPhoto(dto.photo());
    user.setEmail(dto.email());
    user.setPassword(dto.password());
    user.setMeetingPreference(dto.meetingPreference());
    user.setTown(dto.town());
    user.setState(dto.state());
    return user;
  }

  public void updateEntityFromDTO(UserUpdateDTO dto, User user) {
    if (dto.name() != null)
      user.setName(dto.name());
    if (dto.age() != null)
      user.setAge(dto.age());
    if (dto.type() != null)
      user.setType(dto.type());
    if (dto.photo() != null)
      user.setPhoto(dto.photo());
    if (dto.email() != null)
      user.setEmail(dto.email());
    if (dto.meetingPreference() != null)
      user.setMeetingPreference(dto.meetingPreference());
    if (dto.town() != null)
      user.setTown(dto.town());
    if (dto.state() != null)
      user.setState(dto.state());
  }
}