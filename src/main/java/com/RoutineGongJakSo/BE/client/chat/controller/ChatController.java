package com.RoutineGongJakSo.BE.client.chat.controller;


import com.RoutineGongJakSo.BE.client.chat.dto.ChatMessageDto;
import com.RoutineGongJakSo.BE.client.chat.model.ChatMessage;
import com.RoutineGongJakSo.BE.client.chat.service.ChatMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Controller
public class ChatController {

    private final ChatMessageService chatMessageService;

    /**
     * websocket "/pub/chat/message"로 들어오는 메시징을 처리한다.
     */
    @MessageMapping("/chat/message")
    public void message(ChatMessageDto message, @Header("Authorization") String token) {
        chatMessageService.save(message, token);
    }

    @GetMapping("/chat/message/{roomId}")
    @ResponseBody
    public List<ChatMessage> getMessages(@PathVariable String roomId) {
        return chatMessageService.getMessages(roomId);
    }

    @GetMapping("/chat/message/file/{roomId}")
    @ResponseBody
    public List<ChatMessage> getMessageFromFile(@PathVariable String roomId, @RequestParam(required = false) Long prevId){
        return chatMessageService.getMessageFromFile(roomId, prevId);
    }
}
