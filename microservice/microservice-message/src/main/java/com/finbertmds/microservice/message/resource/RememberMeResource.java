package com.finbertmds.microservice.message.resource;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;

import javax.inject.Inject;

import com.finbertmds.microservice.message.model.UserModel;
import com.finbertmds.microservice.message.service.UserService;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/remember-me")
public class RememberMeResource {


    @Inject
    private UserService service;

    @RequestMapping(method = GET, produces = APPLICATION_JSON_VALUE)
    @ResponseBody
    public UserModel getRememberMeUser() {
        return service.fetchRememberMeUser();
    }

}
