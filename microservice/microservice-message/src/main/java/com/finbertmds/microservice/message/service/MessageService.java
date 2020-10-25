package com.finbertmds.microservice.message.service;

import static com.finbertmds.microservice.message.entity.Schema.CHATAPP_USER;
import static java.lang.String.format;

import java.util.List;
import java.util.UUID;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.finbertmds.microservice.message.entity.MessageEntity;
import com.finbertmds.microservice.message.model.LightUserModel;
import com.finbertmds.microservice.message.model.MessageModel;

import org.springframework.stereotype.Service;

@Service
public class MessageService {

    public static final String JOINING_MESSAGE = "%s joins the room";
    public static final String LEAVING_MESSAGE = "%s leaves the room";

    // TODO: update
    // private MessageEntity_Manager manager;

    // @Inject
    // public MessageService(MessageEntity_Manager manager) {
    //     this.manager = manager;
    // }

    public MessageModel postNewMessage(LightUserModel author, String roomName, String messageContent) {
        final MessageEntity entity = new MessageEntity(roomName, Uuids.timeBased(), author, messageContent);
        // TODO: update
        // manager.crud().insert(entity).executeAsync();
        return entity.toModel();
    }

    public List<MessageModel> fetchNextMessagesForRoom(String roomName, UUID fromMessageId, int pageSize) {

        return null;
        // return manager
        //         .dsl()
        //         .select()
        //         .allColumns_FromBaseTable()
        //         .where()
        //         .roomName().Eq(roomName)
        //         .messageId().Lt(fromMessageId)
        //         .limit(pageSize)
        //         .orderByMessageIdDescending()
        //         .getList()
        //         .stream()
        //         .map(MessageEntity::toModel)
        //         .collect(Collector.of(
        //                 ArrayList::new,
        //                 (List<MessageModel> l, MessageModel t) -> l.add(t),
        //                 (List<MessageModel> l, List<MessageModel> r) -> {
        //                     l.addAll(r);
        //                     return l;
        //                 },
        //                 Lists::<MessageModel>reverse));
    }

    public MessageModel createJoiningMessage(String roomName, LightUserModel participant) {
        final MessageEntity entity = new MessageEntity(roomName, Uuids.timeBased(), CHATAPP_USER.toLightModel(), format(JOINING_MESSAGE, participant.getFormattedName()), true);
        // manager.crud().insert(entity).executeAsync();
        return entity.toModel();
    }

    public MessageModel createLeavingMessage(String roomName, LightUserModel participant) {
        final MessageEntity entity = new MessageEntity(roomName, Uuids.timeBased(), CHATAPP_USER.toLightModel(), format(LEAVING_MESSAGE, participant.getFormattedName()), true);
        // manager.crud().insert(entity).executeAsync();
        return entity.toModel();
    }
}
