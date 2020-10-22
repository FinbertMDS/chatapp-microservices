package com.finbertmds.microservice.message.model;

import java.util.UUID;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.cassandra.core.cql.Ordering;
import org.springframework.data.cassandra.core.cql.PrimaryKeyType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKeyColumn;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("chat_room_messages")
public class Message {
  
  @PrimaryKeyColumn(name = "room_id", ordinal = 0, type = PrimaryKeyType.PARTITIONED)
  private UUID roomId;

  @PrimaryKeyColumn(name = "message_id", ordinal = 1, type = PrimaryKeyType.CLUSTERED, ordering = Ordering.DESCENDING)
  private UUID messageId;

  private String content;

  private String author;

  @Column("system_message")
  private boolean systemMessage;

  public Message() {

  }

  public Message(UUID roomId, UUID messageId, String content, String author, boolean systemMessage) {
    this.roomId = roomId;
    this.messageId = messageId;
    this.content = content;
    this.author = author;
    this.systemMessage = systemMessage;
  }

  public UUID getRoomId() {
    return this.roomId;
  }

  public void setRoomId(UUID roomId) {
    this.roomId = roomId;
  }

  public UUID getMessageId() {
    return this.messageId;
  }

  public void setMessageId(UUID messageId) {
    this.messageId = messageId;
  }

  public String getContent() {
    return this.content;
  }

  public void setContent(String content) {
    this.content = content;
  }

  public String getAuthor() {
    return this.author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public boolean isSystemMessage() {
    return this.systemMessage;
  }

  public boolean getSystemMessage() {
    return this.systemMessage;
  }

  public void setSystemMessage(boolean systemMessage) {
    this.systemMessage = systemMessage;
  }

  @Override
  public String toString() {
    return ToStringBuilder.reflectionToString(this);
  }

  @Override
  public int hashCode() {
    return HashCodeBuilder.reflectionHashCode(this);
  }

  @Override
  public boolean equals(Object obj) {
    return EqualsBuilder.reflectionEquals(this, obj);
  }

}
