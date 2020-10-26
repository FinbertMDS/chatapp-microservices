package com.finbertmds.microservice.message.resource.model;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finbertmds.microservice.message.entity.LightUserModel;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatRoomCreationModel {

    private String banner;

    @NotNull
    private LightUserModel creator;

    public ChatRoomCreationModel() {
    }

    public ChatRoomCreationModel(String banner, LightUserModel creator) {
        this.banner = banner;
        this.creator = creator;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public LightUserModel getCreator() {
        return creator;
    }

    public void setCreator(LightUserModel creator) {
        this.creator = creator;
    }
}
