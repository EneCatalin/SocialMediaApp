package com.socialmedia.chat_service.util.mapper;

import com.socialmedia.chat_service.dto.UserDto;
import com.socialmedia.chat_service.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Mapping from User entity to UserDto
    UserDto userToUserDto(User user);

    // Mapping from UserDto to User entity
    User userDtoToUser(UserDto userDto);
}