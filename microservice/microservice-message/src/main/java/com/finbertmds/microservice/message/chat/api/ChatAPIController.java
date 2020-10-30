package com.finbertmds.microservice.message.chat.api;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.finbertmds.microservice.message.chat.model.MessagePosting;
import com.finbertmds.microservice.message.chatroom.domain.model.InstantMessage;
import com.finbertmds.microservice.message.chatroom.domain.service.ChatRoomService;
import com.finbertmds.microservice.message.chatroom.domain.service.InstantMessageService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/messages")
public class ChatAPIController {

  @Autowired
  private ChatRoomService chatRoomService;

  @Autowired
  private InstantMessageService instantMessageService;

  @PostMapping(value = "/{chatRoomId}")
  @ResponseBody
  public void postNewMessage(@PathVariable @NotNull String chatRoomId,
      @NotNull @RequestBody @Valid MessagePosting messagePosting) throws JsonProcessingException {
    InstantMessage instantMessage = new InstantMessage();
    instantMessage.setUsername(messagePosting.getFromUser());
    instantMessage.setToUser(messagePosting.getToUser());
    instantMessage.setFromUser(messagePosting.getFromUser());
    instantMessage.setChatRoomId(chatRoomId);
    instantMessage.setText(messagePosting.getText());
    instantMessage.setIsNotification(false);
    if (instantMessage.isPublic()) {
      chatRoomService.sendPublicMessage(instantMessage);
    } else {
      chatRoomService.sendPrivateMessage(instantMessage);
    }
  }

  @GetMapping(value = "/{chatRoomId}")
  public ResponseEntity<List<InstantMessage>> fetchAllMessagesForRoom(@PathVariable String chatRoomId,
      @RequestParam(required = true) String forUser) {
    try {
      List<InstantMessage> messages = new ArrayList<InstantMessage>();

      instantMessageService.findAllInstantMessagesFor(forUser, chatRoomId).forEach(messages::add);

      if (messages.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(messages, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }
}
