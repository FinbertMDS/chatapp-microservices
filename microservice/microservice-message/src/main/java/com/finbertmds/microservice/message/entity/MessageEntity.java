package com.finbertmds.microservice.message.entity;

import java.util.Date;
import java.util.UUID;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.datastax.oss.driver.api.core.uuid.Uuids;
import com.finbertmds.microservice.message.model.LightUserModel;
import com.finbertmds.microservice.message.model.MessageModel;

import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(Schema.CHATROOM_MESSAGES)
public class MessageEntity {

    @PrimaryKeyColumn(name = "room_name", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
    private String roomName;

    @PrimaryKeyColumn(name = "message_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
    private UUID messageId;

    @NotNull
    @Column
    @Frozen
    private LightUserModel author;

    @NotEmpty
    @Column
    private String content;

    @Column("system_message")
    private boolean systemMessage;


    public MessageEntity(String roomName, UUID messageId, LightUserModel author, String content) {
        this.roomName = roomName;
        this.messageId = messageId;
        this.author = author;
        this.content = content;
        this.systemMessage = false;
    }

    public MessageEntity(String roomName, UUID messageId, LightUserModel author, String content, boolean systemMessage) {
        this.roomName = roomName;
        this.messageId = messageId;
        this.author = author;
        this.content = content;
        this.systemMessage = systemMessage;
    }

    public MessageModel toModel() {
        MessageModel model = new MessageModel();
        model.setAuthor(this.author);
        model.setContent(this.content);
        model.setMessageId(this.getMessageId());
        model.setSystemMessage(this.systemMessage);
        model.setCreationDate(new Date(Uuids.unixTimestamp(this.getMessageId())));
        return model;
    }


















    /**
     *
     * Boring getters & setters & default constructor
     *
     */
    public MessageEntity() {
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public LightUserModel getAuthor() {
        return author;
    }

    public void setAuthor(LightUserModel author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean isSystemMessage() {
        return systemMessage;
    }

    public void setSystemMessage(boolean systemMessage) {
        this.systemMessage = systemMessage;
    }

}
