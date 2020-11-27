package com.finbertmds.microservice.contact.payload.request;

public class AddRemoveUserContactRequest {
	private String username;

  private String email;
  
  public String getUsername() {
    return this.username;
  }

  public void setUsername(String username) {
    this.username = username;
  }

  public String getEmail() {
    return this.email;
  }

  public void setEmail(String email) {
    this.email = email;
  }
  
}
