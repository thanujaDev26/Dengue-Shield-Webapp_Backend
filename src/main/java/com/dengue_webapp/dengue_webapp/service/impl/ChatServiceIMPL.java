package com.dengue_webapp.dengue_webapp.service.impl;

import com.dengue_webapp.dengue_webapp.dto.request.RequestChatMessageDto;
import com.dengue_webapp.dengue_webapp.exceptions.NoDataFoundException;
import com.dengue_webapp.dengue_webapp.model.entity.ChatMessage;
import com.dengue_webapp.dengue_webapp.model.entity.PHIOfficer;
import com.dengue_webapp.dengue_webapp.repository.ChatMessageRepo;
import com.dengue_webapp.dengue_webapp.repository.PHIRepo;
import com.dengue_webapp.dengue_webapp.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class ChatServiceIMPL implements ChatService {
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private PHIRepo phiRepo;
    @Autowired
    private ChatMessageRepo chatMessageRepo;

    @Override
    public void sendMessage(RequestChatMessageDto messageDto) {
        System.out.println("in service");
        List<Long> receiverIds = messageDto.getReceiverId();
        System.out.println(receiverIds);
        receiverIds.forEach(receiverId -> {
                    // Create a new ChatMessage for each receiver
                    ChatMessage chatMessage = new ChatMessage();
                    chatMessage.setSenderId(messageDto.getSenderId());
                    chatMessage.setReceiverId(receiverId); // Set receiverId for each message
                    chatMessage.setContent(messageDto.getContent());
                    chatMessage.setTimestamp(LocalDateTime.now());
                    chatMessage.setReadstatus(false);
                    chatMessageRepo.save(chatMessage);
                });
        // If only one receiver, send to that user
        if (receiverIds.size() == 1) {
            messagingTemplate.convertAndSendToUser(receiverIds.get(0).toString(), "/queue/messages", messageDto);

        } else {
            // If multiple receivers, send to each user
            receiverIds.forEach(receiverId -> {
                messagingTemplate.convertAndSendToUser(receiverId.toString(), "/queue/messages", messageDto);
            });
        }
    }

    @Override
    public List<ChatMessage> getAllChatMessages(long id) {
        System.out.println("in servicimpl");
//        Optional<PHIOfficer> phiOfficer = phiRepo.findById(id);
//        if(phiOfficer.isEmpty()){
//            throw new NoDataFoundException("Phi officer couldn't find");
//        }
        List<ChatMessage> chatMessages = chatMessageRepo.findAllByReceiverId(id);
        System.out.println(chatMessages);
        return chatMessages;
    }

    @Override
    public String setChatMessageStatus(long id) {
        Optional<ChatMessage> msg = chatMessageRepo.findById(id);
        if(msg.isEmpty()){
            throw new NoDataFoundException("no message found ");
        }
        msg.get().setReadstatus(true);
        chatMessageRepo.save(msg.get());
        return "message Status changed";
    }
}
