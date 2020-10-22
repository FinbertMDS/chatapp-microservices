package com.finbertmds.microservice.message.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.finbertmds.microservice.message.model.Room;
import com.finbertmds.microservice.message.repository.RoomRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RoomController {

  public static final int DEFAULT_ROOMS_LIST_FETCH_SIZE = 100;

  @Autowired
  RoomRepository roomRepository;

  @GetMapping("/rooms")
  public ResponseEntity<List<Room>> getAllRooms(@RequestParam(required = false) int fetchSize) {
    try {
      List<Room> rooms = new ArrayList<Room>();

      roomRepository.findAll().forEach(rooms::add);

      if (rooms.isEmpty()) {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
      }

      return new ResponseEntity<>(rooms, HttpStatus.OK);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  @GetMapping("/rooms/{id}")
  public ResponseEntity<Room> getRoomById(@PathVariable("id") UUID id) {
    Optional<Room> messageData = roomRepository.findById(id);

    if (messageData.isPresent()) {
      return new ResponseEntity<>(messageData.get(), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  @PostMapping("/rooms")
  public ResponseEntity<Room> createRoom(@RequestBody Room message) {
    try {
      Room _message = roomRepository.save(new Room(Uuids.timeBased(), message.getRoomName(), LocalDateTime.now(),
          message.getCreatorLogin(), message.getParticipants(), message.getBanner(), message.getCreator()));
      return new ResponseEntity<>(_message, HttpStatus.CREATED);
    } catch (Exception e) {
      return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // TODO not test
  @PutMapping("/rooms/{id}")
  public ResponseEntity<Room> updateRoom(@PathVariable("id") UUID id, @RequestBody Room message) {
    Optional<Room> roomData = roomRepository.findById(id);

    if (roomData.isPresent()) {
      Room _room = roomData.get();
      _room.setRoomName(message.getRoomName());
      _room.setParticipants(message.getParticipants());
      _room.setBanner(message.getBanner());
      return new ResponseEntity<>(roomRepository.save(_room), HttpStatus.OK);
    } else {
      return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
  }

  // TODO not test
  @DeleteMapping("/rooms/{id}")
  public ResponseEntity<HttpStatus> deleteRoom(@PathVariable("id") String id) {
    try {
      roomRepository.deleteById(UUID.fromString(id));
      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    } catch (Exception e) {
      return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }
  }

  // TODO not test
  @PutMapping(value = "/participant/{roomId}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void addUserToChatRoom(@PathVariable @NotEmpty String roomId,
      @NotNull @RequestBody @Valid String participant) {
    // chatRoomService.addUserToRoom(roomId, participant);
    // final MessageModel joiningMessage =
    // messageService.createJoiningMessage(roomId, participant);
    // template.convertAndSend("/topic/participants/"+ roomId, participant,
    // ImmutableMap.<String,Object>of("status", Status.JOIN));
    // template.convertAndSend("/topic/messages/"+roomId, joiningMessage);
  }

  // TODO not test
  @PatchMapping(value = "/participant/{roomName}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void removeUserFromChatRoom(@PathVariable @NotEmpty String roomName,
      @NotNull @RequestBody @Valid String participant) {
    // chatRoomService.removeUserFromRoom(roomName, participant);
    // final MessageModel leavingMessage =
    // messageService.createLeavingMessage(roomName, participant);
    // template.convertAndSend("/topic/participants/"+ roomName, participant,
    // ImmutableMap.<String,Object>of("status", Status.LEAVE));
    // template.convertAndSend("/topic/messages/"+roomName, leavingMessage);
  }

}
