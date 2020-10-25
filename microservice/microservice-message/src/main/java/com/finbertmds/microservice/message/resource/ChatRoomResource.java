package com.finbertmds.microservice.message.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.PATCH;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import com.finbertmds.microservice.message.model.ChatRoomModel;
import com.finbertmds.microservice.message.model.ChatRoomModel.Action;
import com.finbertmds.microservice.message.model.LightUserModel;
import com.finbertmds.microservice.message.model.MessageModel;
import com.finbertmds.microservice.message.resource.model.ChatRoomCreationModel;
import com.finbertmds.microservice.message.resource.model.ChatRoomDeletionModel;
import com.finbertmds.microservice.message.resource.model.ChatRoomParticipantModel.Status;
import com.finbertmds.microservice.message.security.utils.SecurityUtils;
import com.finbertmds.microservice.message.service.ChatRoomService;
import com.finbertmds.microservice.message.service.MessageService;
import com.google.common.collect.ImmutableMap;

import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rooms")
public class ChatRoomResource {

    public static final int DEFAULT_CHAT_ROOMS_LIST_FETCH_SIZE = 100;

    @Inject
    private ChatRoomService chatRoomService;

    @Inject
    private MessageService messageService;

    @Inject
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/{roomName}", method = POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void createChatRoom(
            @PathVariable @NotEmpty @Pattern(regexp = "[a-zA-Z0-9][a-zA-Z0-9_.-]{2,30}") String roomName,
            @NotNull @RequestBody @Valid ChatRoomCreationModel model) {
        final LightUserModel creator = model.getCreator();

        chatRoomService.createChatRoom(roomName, model.getBanner(), creator);
    }

    @RequestMapping(value = "/{roomName}", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public ChatRoomModel findRoomByName(@PathVariable @NotEmpty String roomName) {
        return chatRoomService.findRoomByName(roomName);
    }

    @RequestMapping(value = "/{roomName}", method = PATCH, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRoomWithParticipants(@PathVariable @NotEmpty String roomName,
            @RequestBody @Valid ChatRoomDeletionModel model) {
        final String login = SecurityUtils.getCurrentLogin();
        final String deletionMessage = chatRoomService.deleteRoomWithParticipants(login, roomName,
                model.getParticipants());
        template.convertAndSend("/topic/action/" + roomName, deletionMessage,
                ImmutableMap.<String, Object>of("action", Action.DELETE, "room", roomName, "creator", login));
    }

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<ChatRoomModel> listChatRooms(@RequestParam(required = false) int fetchSize) {
        final int pageSize = fetchSize <= 0 ? DEFAULT_CHAT_ROOMS_LIST_FETCH_SIZE : fetchSize;
        return chatRoomService.listChatRooms(pageSize);
    }

    @RequestMapping(value = "/participant/{roomName}", method = PUT, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addUserToChatRoom(@PathVariable @NotEmpty String roomName,
            @NotNull @RequestBody @Valid LightUserModel participant) {
        chatRoomService.addUserToRoom(roomName, participant);
        final MessageModel joiningMessage = messageService.createJoiningMessage(roomName, participant);
        template.convertAndSend("/topic/participants/" + roomName, participant,
                ImmutableMap.<String, Object>of("status", Status.JOIN));
        template.convertAndSend("/topic/messages/" + roomName, joiningMessage);
    }

    @RequestMapping(value = "/participant/{roomName}", method = PATCH, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void removeUserFromChatRoom(@PathVariable @NotEmpty String roomName,
            @NotNull @RequestBody @Valid LightUserModel participant) {
        chatRoomService.removeUserFromRoom(roomName, participant);
        final MessageModel leavingMessage = messageService.createLeavingMessage(roomName, participant);
        template.convertAndSend("/topic/participants/" + roomName, participant,
                ImmutableMap.<String, Object>of("status", Status.LEAVE));
        template.convertAndSend("/topic/messages/" + roomName, leavingMessage);
    }
}
