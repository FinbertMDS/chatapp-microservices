package com.finbertmds.microservice.message.resource.model;

import java.util.Set;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChatRoomDeletionModel {

    @NotNull
    private Set<String> participants;

    public ChatRoomDeletionModel() {
    }

    public ChatRoomDeletionModel(Set<String> participants) {
        this.participants = participants;
    }

    public Set<String> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<String> participants) {
        this.participants = participants;
    }
}
