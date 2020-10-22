package com.finbertmds.microservice.message.controller;

import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.finbertmds.microservice.message.model.Message;
import com.finbertmds.microservice.message.repository.MessageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MessageController {

  @Autowired
  MessageRepository messageRepository;

  @PostMapping("/messages/{roomId}")
  public ResponseEntity<Message> createMessage(@PathVariable("roomId") String roomId, @RequestBody Message message) {
    try {
      Message _message = messageRepository
          .save(new Message(UUID.fromString(roomId), Uuids.timeBased(), message.getContent(), message.getAuthor(), false));
      return new ResponseEntity<>(_message, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @DeleteMapping("/messages/{messageId}")
  public ResponseEntity<HttpStatus> deleteMessage(@PathVariable("messageId") String messageId) {
    try {
      // TODO: delete message failure
      messageRepository.deleteById(messageId);
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

}
