package social.media.socialMedia.util.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import social.media.socialMedia.dto.CreateUserDto;
import social.media.socialMedia.dto.FindByUsernameDto;
import social.media.socialMedia.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    FindByUsernameDto userToUserDto(User user);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", expression = "java(java.time.LocalDateTime.now())")
    User createUserDtoToUser(CreateUserDto createUserDto);
}