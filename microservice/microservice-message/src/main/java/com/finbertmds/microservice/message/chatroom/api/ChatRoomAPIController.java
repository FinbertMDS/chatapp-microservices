package com.finbertmds.microservice.message.chatroom.api;

import java.util.ArrayList;
import java.util.List;

import com.finbertmds.microservice.message.chatroom.domain.model.ChatRoom;
import com.finbertmds.microservice.message.chatroom.domain.model.ChatRoomUser;
import com.finbertmds.microservice.message.chatroom.domain.service.ChatRoomService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/chatroom")
public class ChatRoomAPIController {

	@Autowired
	private ChatRoomService chatRoomService;

	@GetMapping
	public ResponseEntity<List<ChatRoom>> getRooms() {
		try {
			List<ChatRoom> chatRooms = new ArrayList<ChatRoom>();

			chatRoomService.findAll().forEach(chatRooms::add);

			if (chatRooms.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}

			return new ResponseEntity<>(chatRooms, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<ChatRoom> getRoomById(@PathVariable("id") String id) {
		ChatRoom chatRoomData = chatRoomService.findById(id);

		if (chatRoomData != null) {
			return new ResponseEntity<>(chatRoomData, HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<ChatRoom> updateChatRoom(@PathVariable("id") String id, @RequestBody ChatRoom tutorial) {
		ChatRoom tutorialData = chatRoomService.findById(id);

		if (tutorialData != null) {
			tutorialData.setName(tutorial.getName());
			tutorialData.setDescription(tutorial.getDescription());
			return new ResponseEntity<>(chatRoomService.save(tutorialData), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	// @Secured("ROLE_ADMIN")
	@PostMapping
	@ResponseBody
	@ResponseStatus(code = HttpStatus.CREATED)
	public ResponseEntity<ChatRoom> createChatRoom(@RequestBody ChatRoom chatRoom) {
		try {
			ChatRoom _chatRoom = chatRoomService.save(chatRoom);
			return new ResponseEntity<>(_chatRoom, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/participant/{chatRoomId}")
	@ResponseBody
	public ResponseEntity<ChatRoom> addUserToChatRoom(@PathVariable String chatRoomId,
			@RequestBody ChatRoomUser participant) {
		try {
			ChatRoom chatRoom = chatRoomService.findById(chatRoomId);
			if (!chatRoom.getConnectedUsers().stream().filter(o -> o.getUsername().equals(participant.getUsername()))
					.findFirst().isPresent()) {
				chatRoom = chatRoomService.join(participant, chatRoom);
			}
			return new ResponseEntity<>(chatRoom, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PatchMapping(value = "/participant/{chatRoomId}")
	public ResponseEntity<ChatRoom> removeUserFromChatRoom(@PathVariable String chatRoomId,
			@RequestBody ChatRoomUser participant) {
		try {
			ChatRoom _chatRoom = chatRoomService.leave(participant, chatRoomService.findById(chatRoomId));
			return new ResponseEntity<>(_chatRoom, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
