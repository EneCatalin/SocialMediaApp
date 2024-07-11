package social.media.socialMedia.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;
import social.media.socialMedia.dto.CreateUserDto;
import social.media.socialMedia.dto.CreateUserResponseDto;
import social.media.socialMedia.dto.FindByUsernameDto;
import social.media.socialMedia.dto.UpdateUserDto;
import social.media.socialMedia.entity.User;

@Mapper(componentModel = "spring", uses = { RoleMapper.class })
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "roles", target = "roles")
    FindByUsernameDto userToUserDto(User user);

    @Mapping(source = "roles", target = "roles")
    CreateUserResponseDto createUserToCreateUserResponseDto(User user);

    @Mapping(source = "roles", target = "roles", ignore = true) // Ignore direct mapping
    User updateUserDtoToUser(UpdateUserDto updateUserDto, @MappingTarget User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "roles", ignore = true) // We will handle setting roles separately
    User createUserDtoToUser(CreateUserDto createUserDto);
}
