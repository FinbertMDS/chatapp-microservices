package com.finbertmds.microservice.message.entity;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotEmpty;

import com.finbertmds.microservice.message.model.UserModel;
import com.finbertmds.microservice.message.security.authority.CustomUserDetails;
import com.google.common.collect.Sets;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(Schema.USERS)
public class UserEntity {

    @PrimaryKey
    private String login;

    @NotEmpty
    @Column
    private String pass;

    @Column
    private String firstname;

    @Column
    private String lastname;

    @Column
    private String email;

    @Column
    private String bio;

    // TODO: update
    // @EmptyCollectionIfNull
    @Column("chat_rooms")
    private Set<String> chatRooms = new HashSet<>();

    public UserEntity(String login, String pass, String firstname, String lastname, String email, String bio) {
        this.login = login;
        this.pass = pass;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.bio = bio;
    }

    public static UserEntity fromModel(UserModel model) {
        return new UserEntity(model.getLogin(), model.getPassword(), model.getFirstname(), model.getLastname(), model.getEmail(), model.getBio());
    }

    public UserModel toModel() {
        final UserModel model = new UserModel();
        model.setLogin(this.getLogin());
        model.setFirstname(this.getFirstname());
        model.setLastname(this.getLastname());
        model.setEmail(this.getEmail());
        model.setBio(this.getBio());
        model.setChatRooms(this.getChatRooms());
        return model;
    }

    public CustomUserDetails toUserDetails() {
        return new CustomUserDetails(Sets.newHashSet(new UserAuthority()),this.login,this.pass);
    }


    /**
     *
     * Boring getters & setters & default constructor
     *
     */
    public UserEntity() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }


    public void setEmail(String email) {
        this.email = email;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Set<String> getChatRooms() {
        return chatRooms;
    }

    public void setChatRooms(Set<String> chatRooms) {
        this.chatRooms = chatRooms;
    }
}
