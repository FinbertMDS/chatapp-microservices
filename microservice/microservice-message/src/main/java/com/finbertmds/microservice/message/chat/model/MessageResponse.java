package com.finbertmds.microservice.message.chat.model;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finbertmds.microservice.message.entity.InstantMessage;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessageResponse {

  private String chatRoomId;
  private String chatRoomName;
	private Date date;
  private String fromUser;
  private String toUser;
  private String text;
	private boolean isNotification;

  public MessageResponse() {
  }

  public MessageResponse(String chatRoomId, Date date, String fromUser, String toUser, String text, boolean isNotification) {
    this.chatRoomId = chatRoomId;
    this.date = date;
    this.fromUser = fromUser;
    this.toUser = toUser;
    this.text = text;
    this.isNotification = isNotification;
  }

  public MessageResponse(InstantMessage instantMessage) {
    this.chatRoomId = instantMessage.getInstantMessageKey().getChatRoomId();
    this.date = instantMessage.getInstantMessageKey().getDate();
    this.fromUser = instantMessage.getFromUser();
    this.toUser = instantMessage.getToUser();
    this.text = instantMessage.getText();
    this.isNotification = instantMessage.getIsNotification();
  }

  public String getFromUser() {
    return this.fromUser;
  }

  public void setFromUser(String fromUser) {
    this.fromUser = fromUser;
  }

  public String getToUser() {
    return this.toUser;
  }

  public void setToUser(String toUser) {
    this.toUser = toUser;
  }

  public String getText() {
    return this.text;
  }

  public void setText(String text) {
    this.text = text;
  }

  public String getChatRoomId() {
    return this.chatRoomId;
  }

  public void setChatRoomId(String chatRoomId) {
    this.chatRoomId = chatRoomId;
  }

  public String getChatRoomName() {
    return this.chatRoomName;
  }

  public void setChatRoomName(String chatRoomName) {
    this.chatRoomName = chatRoomName;
  }

  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public boolean isIsNotification() {
    return this.isNotification;
  }

  public boolean getIsNotification() {
    return this.isNotification;
  }

  public void setIsNotification(boolean isNotification) {
    this.isNotification = isNotification;
  }

}
