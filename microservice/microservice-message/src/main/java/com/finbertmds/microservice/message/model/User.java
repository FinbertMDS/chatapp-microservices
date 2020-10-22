package com.finbertmds.microservice.message.model;

import java.util.Set;
import java.util.UUID;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table("users")
public class User {
  
  @PrimaryKey(value = "user_id")
  private UUID id;

  private String login;

  private String bio;

  private String email;

  @Column("chat_rooms")
  private Set<String> chatRooms;

  private String lastname;
  
  private String firstname;
  
  @Column("pass")
  private String password;

  public User() {

  }

  public User(UUID id, String login, String bio, String email, Set<String> chatRooms, String lastname, String firstname, String password) {
    this.id = id;
    this.login = login;
    this.bio = bio;
    this.email = email;
    this.chatRooms = chatRooms;
    this.lastname = lastname;
    this.firstname = firstname;
    this.password = password;
  }

  public UUID getId() {
    return this.id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getLogin() {
    return this.login;
  }

  public void setLogin(String login) {
    this.login = login;
  }

  public String getBio() {
    return this.bio;
  }

  public void setBio(String bio) {
    this.bio = bio;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Set<String> getChatRooms() {
    return this.chatRooms;
  }

  public void setChatRooms(Set<String> chatRooms) {
    this.chatRooms = chatRooms;
  }

  public String getLastname() {
    return this.lastname;
  }

  public void setLastname(String lastname) {
    this.lastname = lastname;
  }

  public String getFirstname() {
    return this.firstname;
  }

  public void setFirstname(String firstname) {
    this.firstname = firstname;
  }

  public String getPassword() {
    return this.password;
  }

  public void setPassword(String password) {
    this.password = password;
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
