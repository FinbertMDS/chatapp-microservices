package com.finbertmds.microservice.message.model;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("chat_rooms")
public class Room {
  
  @PrimaryKey("room_id")
  private UUID id;

  @Column("room_name")
  private String roomName;

  @Column("creation_date")
  private LocalDateTime creationDate;

  @Column("creator_login")
  private String creatorLogin;

  private Set<String> participants;

  private String banner;

  private String creator;

  public Room() {

  }

  public Room(UUID id, String roomName, LocalDateTime creationDate, String creatorLogin, Set<String> participants,
      String banner, String creator) {
    this.id = id;
    this.roomName = roomName;
    this.creationDate = creationDate;
    this.creatorLogin = creatorLogin;
    this.participants = participants;
    this.banner = banner;
    this.creator = creator;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getRoomName() {
    return this.roomName;
  }

  public void setRoomName(String roomName) {
    this.roomName = roomName;
  }

  public LocalDateTime getCreationDate() {
    return this.creationDate;
  }

  public void setCreationDate(LocalDateTime creationDate) {
    this.creationDate = creationDate;
  }

  public String getCreatorLogin() {
    return this.creatorLogin;
  }

  public void setCreatorLogin(String creatorLogin) {
    this.creatorLogin = creatorLogin;
  }

  public Set<String> getParticipants() {
    return this.participants;
  }

  public void setParticipants(Set<String> participants) {
    this.participants = participants;
  }

  public String getBanner() {
    return this.banner;
  }

  public void setBanner(String banner) {
    this.banner = banner;
  }

  public String getCreator() {
    return this.creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
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
