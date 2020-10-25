package com.finbertmds.microservice.message.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;

import java.util.List;
import java.util.UUID;

import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.finbertmds.microservice.message.model.MessageModel;
import com.finbertmds.microservice.message.resource.model.MessagePosting;
import com.finbertmds.microservice.message.service.MessageService;

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
@RequestMapping("/messages")
public class MessageResource {

    static final int DEFAULT_MESSAGES_FETCH_SIZE = 5;

    @Inject
    private MessageService service;

    @Inject
    private SimpMessagingTemplate template;

    @RequestMapping(value = "/{roomName}", method = POST, consumes = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void postNewMessage(@PathVariable String roomName, @NotNull @RequestBody @Valid MessagePosting messagePosting) throws JsonProcessingException {
        Validator.validateNotBlank(roomName, "Room name can not be blank for posting new message");
        final MessageModel messageModel = service.postNewMessage(messagePosting.getAuthor(), roomName, messagePosting.getContent());
        template.convertAndSend("/topic/messages/"+roomName, messageModel);
    }

    @RequestMapping(value = "/{roomName}", method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public List<MessageModel> fetchNextMessagesForRoom(@PathVariable String roomName, @RequestParam(required = false) UUID fromMessageId, @RequestParam(required = false) int fetchSize) {
        UUID fromLastMessage = fromMessageId == null ? Uuids.timeBased() : fromMessageId;
        final int pageSize = fetchSize <= 0 ? DEFAULT_MESSAGES_FETCH_SIZE : fetchSize;
        Validator.validateNotBlank(roomName, "Room name can not be blank for posting new message");
        return service.fetchNextMessagesForRoom(roomName, fromLastMessage, pageSize);
    }
}
