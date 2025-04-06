package com.dengue_webapp.dengue_webapp.controller;

import com.dengue_webapp.dengue_webapp.dto.request.RequestChatMessageDto;
import com.dengue_webapp.dengue_webapp.model.entity.ChatMessage;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
import com.dengue_webapp.dengue_webapp.service.ChatService;
import com.dengue_webapp.dengue_webapp.util.StandardResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ChatMessageController {



    @Autowired
    private ChatService chatService;


    @MessageMapping("/chat")
    public void handleChatMessage(RequestChatMessageDto messageDto) {
        System.out.println("hello i'm in controller");
       chatService.sendMessage(messageDto);
    }


    @GetMapping("/getChatMessages/{id}")
    public ResponseEntity<StandardResponse> getAllChatMessages(@PathVariable long id) {
        //System.out.println("hello");
        List<ChatMessage> messageList =  chatService.getAllChatMessages(id);
        StandardResponse response = new StandardResponse(200, "get all  chat messages successfully", messageList );
        return ResponseEntity.ok(response);
    }

    @PostMapping("/setChatMessageStatus")
    public ResponseEntity<StandardResponse> setChatMessageStatus(@RequestBody long id) {

        String msg = chatService.setChatMessageStatus(id);
        StandardResponse response = new StandardResponse(200, "read status changed",msg );
        return ResponseEntity.ok(response);
    }

}
