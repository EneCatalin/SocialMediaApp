package com.socialmedia.chat_service.dto;

import java.util.UUID;

public class IncomingMessage {
    public UUID chatId;
    public UUID senderId;
    public String content;
}