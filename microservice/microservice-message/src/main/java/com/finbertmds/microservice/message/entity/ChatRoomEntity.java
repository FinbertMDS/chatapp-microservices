package com.finbertmds.microservice.message.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotNull;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.Frozen;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

@Table(Schema.CHATROOMS)
public class ChatRoomEntity {

    @PrimaryKey
    @Column("room_name")
    private String roomName;

    @Column
    @NotNull
    @Frozen
    private LightUserModel creator;

    @Column("creator_login")
    private String creatorLogin;

    @NotNull
    @Column("creation_date")
    private Date creationDate;

    @Column
    private String banner;

    // TODO: update
    // @EmptyCollectionIfNull
    @Column
    @CassandraType(type = CassandraType.Name.UDT, userTypeName = "participants")
    private Set<@Frozen LightUserModel> participants = new HashSet<>();


    public ChatRoomEntity(String roomName, LightUserModel creator, Date creationDate, String banner, Set<LightUserModel> participants) {
        this.roomName = roomName;
        this.creator = creator;
        this.creatorLogin = creator.getLogin();
        this.creationDate = creationDate;
        this.banner = banner;
        this.participants = participants;
    }

    // public ChatRoomModel toModel() {
    //     return new ChatRoomModel(roomName, creator, creationDate, banner, participants);
    // }

    /**
     *
     * Boring getters & setters & default constructor
     *
     */
    public ChatRoomEntity() {
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getCreatorLogin() {
        return creatorLogin;
    }

    public void setCreatorLogin(String creatorLogin) {
        this.creatorLogin = creatorLogin;
    }

    public LightUserModel getCreator() {
        return creator;
    }

    public void setCreator(LightUserModel creator) {
        this.creator = creator;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public Set<LightUserModel> getParticipants() {
        return participants;
    }

    public void setParticipants(Set<LightUserModel> participants) {
        this.participants = participants;
    }
}
