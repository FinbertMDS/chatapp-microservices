package com.finbertmds.microservice.message.resource.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.finbertmds.microservice.message.model.LightUserModel;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatRoomParticipantModel {

    @NotNull
    private LightUserModel participant;

    public ChatRoomParticipantModel() {
    }

    public ChatRoomParticipantModel(LightUserModel participant) {
        this.participant = participant;
    }


    public LightUserModel getParticipant() {
        return participant;
    }

    public void setParticipant(LightUserModel participant) {
        this.participant = participant;
    }

    public static enum Status {
        JOIN, LEAVE
    }

}
