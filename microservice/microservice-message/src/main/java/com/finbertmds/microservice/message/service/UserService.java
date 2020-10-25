package com.finbertmds.microservice.message.service;

import static java.lang.String.format;

import javax.validation.Valid;

import com.finbertmds.microservice.message.entity.UserEntity;
import com.finbertmds.microservice.message.exceptions.RememberMeDoesNotExistException;
import com.finbertmds.microservice.message.exceptions.UserNotFoundException;
import com.finbertmds.microservice.message.model.UserModel;
import com.finbertmds.microservice.message.security.utils.SecurityUtils;

import org.springframework.stereotype.Service;

@Service
public class UserService {

    // TODO: update

    // private final UserEntity_Manager manager;

    // @Inject
    // public UserService(UserEntity_Manager manager) {
    //     this.manager = manager;
    // }

    public void createUser(@Valid UserModel model) {
        // final UserEntity entity = UserEntity.fromModel(model);
        // manager.crud()
        //         .insert(entity)
        //         .ifNotExists()
        //         .withLwtResultListener(lwtResult -> {
        //             throw new UserAlreadyExistsException(format("The user with the login '%s' already exists", model.getLogin()));
        //         })
        //         .execute();
    }

    public UserEntity findByLogin(String login) {
        final UserEntity entity = null;
        // final UserEntity entity = manager.crud().findById(login).get();
        if (entity == null) {
            throw new UserNotFoundException(format("Cannot find user with login '%s'", login));
        }
        return entity;
    }

    public UserModel fetchRememberMeUser() {
        final String login = SecurityUtils.getCurrentLogin();
        if ("anonymousUser".equals(login)) {
            throw new RememberMeDoesNotExistException(format("There is no remember me information for the login '%s'", login));
        }
        return findByLogin(login).toModel();
    }
}
