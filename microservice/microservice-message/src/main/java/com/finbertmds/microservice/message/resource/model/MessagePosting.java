package com.finbertmds.microservice.message.resource.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finbertmds.microservice.message.entity.LightUserModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class MessagePosting {

    @NotNull
    private LightUserModel author;

    @NotBlank
    private String content;

    public MessagePosting() {
    }

    public MessagePosting(LightUserModel author, String content) {
        this.author = author;
        this.content = content;
    }

    public LightUserModel getAuthor() {
        return author;
    }

    public void setAuthor(LightUserModel author) {
        this.author = author;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
