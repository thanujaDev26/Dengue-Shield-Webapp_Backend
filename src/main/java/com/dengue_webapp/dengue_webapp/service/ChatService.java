package com.dengue_webapp.dengue_webapp.service;

import com.dengue_webapp.dengue_webapp.dto.request.RequestChatMessageDto;
import com.dengue_webapp.dengue_webapp.model.entity.ChatMessage;

import java.util.List;

public interface ChatService {
    void sendMessage(RequestChatMessageDto messageDto);

    List<ChatMessage> getAllChatMessages(long id);

    String setChatMessageStatus(long id);
}
