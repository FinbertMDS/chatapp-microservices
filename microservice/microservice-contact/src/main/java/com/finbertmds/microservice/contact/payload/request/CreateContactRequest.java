package com.finbertmds.microservice.contact.payload.request;

import javax.validation.constraints.NotBlank;

public class CreateContactRequest {
	@NotBlank
	private String username;

	@NotBlank
	private String email;

  private String firstName;
  
	private String lastName;
  
  private String phone;

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

  public String getFirstName() {
    return this.firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return this.lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getPhone() {
    return this.phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }
  
}
