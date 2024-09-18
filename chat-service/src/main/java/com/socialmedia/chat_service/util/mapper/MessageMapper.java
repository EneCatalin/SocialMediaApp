package com.socialmedia.chat_service.util.mapper;

import com.socialmedia.chat_service.dto.MessageDto;
import com.socialmedia.chat_service.entity.Message;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface MessageMapper {

    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    // Map Message entity to MessageDto
    @Mapping(source = "chat.id", target = "chatId")
    @Mapping(source = "sender.id", target = "senderId")
    @Mapping(source = "sender.username", target = "senderUsername")
    MessageDto messageToMessageDto(Message message);
}