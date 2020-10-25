package com.finbertmds.microservice.message.resource.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finbertmds.microservice.message.model.LightUserModel;

import javax.validation.constraints.NotNull;

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
