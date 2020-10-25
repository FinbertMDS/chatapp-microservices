package com.finbertmds.microservice.message.entity;

import com.finbertmds.microservice.message.model.UserModel;

import org.apache.commons.lang.RandomStringUtils;

public interface Schema {
    String KEYSPACE = "db_messages";
    String USERS = "users";
    String USER_UDT = "user";
    String USER_AUTHORITY_UDT = "user_authority";
    String CHATROOMS = "chat_rooms";
    String CHATROOM_MESSAGES = "chat_room_messages";
    String PERSISTENT_TOKEN = "security_tokens";
    String CHATAPP_USER_LOGIN = "user";
    UserModel CHATAPP_USER = new UserModel(CHATAPP_USER_LOGIN, RandomStringUtils.randomAlphanumeric(10),
            CHATAPP_USER_LOGIN, CHATAPP_USER_LOGIN,
            "Administrative account", "");

}
