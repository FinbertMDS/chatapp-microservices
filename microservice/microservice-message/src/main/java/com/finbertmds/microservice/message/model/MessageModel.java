package com.finbertmds.microservice.message.model;

import java.util.Date;
import java.util.Objects;
import java.util.UUID;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.finbertmds.microservice.message.entity.LightUserModel;
import com.finbertmds.microservice.message.json.JsonDateSerializer;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageModel {

    private UUID messageId;

    @NotNull
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date creationDate;

    @NotNull
    private LightUserModel author;

    @NotBlank
    private String content;

    private boolean systemMessage;

    public MessageModel(UUID messageId, Date creationDate, LightUserModel author, String content, boolean systemMessage) {
        this.messageId = messageId;
        this.creationDate = creationDate;
        this.author = author;
        this.content = content;
        this.systemMessage = systemMessage;
    }

    public MessageModel() {
    }

    public UUID getMessageId() {
        return messageId;
    }

    public void setMessageId(UUID messageId) {
        this.messageId = messageId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MessageModel that = (MessageModel) o;
        return Objects.equals(systemMessage, that.systemMessage) &&
                Objects.equals(messageId, that.messageId) &&
                Objects.equals(creationDate, that.creationDate) &&
                Objects.equals(author, that.author) &&
                Objects.equals(content, that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(messageId, creationDate, author, content, systemMessage);
    }
}
