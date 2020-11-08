package com.finbertmds.microservice.contact.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(	name = "contacts")
public class Contact {
	@Id
	private Long id;

	@NotBlank
	@Size(max = 20)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

  private String firstName;
  
	private String lastName;
  
  private String phone;
  
  private Date createdAt;

  public Contact() {
    this.createdAt = new Date();
  }

  public Contact(Long id, String username, String email, String firstName, String lastName, String phone, Date createdAt) {
    this.id = id;
    this.username = username;
    this.email = email;
    this.firstName = firstName;
    this.lastName = lastName;
    this.phone = phone;
    this.createdAt = createdAt;
  }

  public Contact(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.firstName = user.getFirstName();
    this.lastName = user.getLastName();
    this.phone = user.getPhone();
    this.createdAt = new Date();
  }

  public Long getId() {
    return this.id;
  }

  public void setId(Long id) {
    this.id = id;
  }

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

  public Date getCreatedAt() {
    return this.createdAt;
  }

  public void setCreatedAt(Date createdAt) {
    this.createdAt = createdAt;
  }
	
	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public int hashCode() {
    HashCodeBuilder builder = new HashCodeBuilder();
    builder.append(getEmail());
    builder.append(getUsername());
    return builder.hashCode();
		// return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
    if (!(obj instanceof Contact)) {
        return false;
    }
    Contact other = (Contact) obj;
    EqualsBuilder builder = new EqualsBuilder();
    builder.append(email, other.email);
    builder.append(username, other.username);
    return builder.isEquals();
		// return EqualsBuilder.reflectionEquals(this, obj);
	}
}
