package com.finbertmds.microservice.message.chat.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagePosting {

  private String fromUser;
  private String toUser;
  private String text;

  public MessagePosting() {
  }

  public MessagePosting(String fromUser, String toUser, String text) {
    this.fromUser = fromUser;
    this.toUser = toUser;
    this.text = text;
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
}
