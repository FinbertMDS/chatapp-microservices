package com.finbertmds.microservice.message.service;

import static java.lang.String.format;

import java.util.Date;
import java.util.List;
import java.util.Set;

import com.finbertmds.microservice.message.entity.ChatRoomEntity;
import com.finbertmds.microservice.message.exceptions.ChatRoomDoesNotExistException;
import com.finbertmds.microservice.message.model.ChatRoomModel;
import com.finbertmds.microservice.message.model.LightUserModel;
import com.google.common.collect.Sets;

import org.springframework.stereotype.Service;

@Service
public class ChatRoomService {

    // TODO: update

    private static final String INCORRECT_CREATOR_FOR_DELETION = "You cannot delete this chat room because you're not the creator";
    private static final String INCORRECT_PARTICIPANTS_FOR_DELETION = "Chat room deletion failed, please reload the chat room and retry";

    public static final String DELETION_MESSAGE = "The room '%s' has been removed by '%s'";

    // private final ChatRoomEntity_Manager manager;

    // private final UserEntity_Manager userManager;

    // private final MessageEntity_Manager messagesManager;

    // private final PreparedStatement updateParticipantIfExists;

    // @Inject
    // public ChatRoomService(ChatRoomEntity_Manager manager, UserEntity_Manager userManager, MessageEntity_Manager messagesManager) {
    //     this.manager = manager;
    //     this.userManager = userManager;
    //     this.messagesManager = messagesManager;
    //     updateParticipantIfExists = manager.getNativeSession().prepare("UPDATE " + KEYSPACE + "." + CHATROOMS
    //             + " SET participants = participants + :participant WHERE room_name = :room_name IF EXISTS");
    // }

    public void createChatRoom(String roomName, String banner, LightUserModel creator) {
        final ChatRoomEntity entity = new ChatRoomEntity(roomName, creator, new Date(), banner, Sets.newHashSet(creator));
        // manager
        //         .crud()
        //         .insert(entity)
        //         .ifNotExists()
        //         .withLwtResultListener(x -> {
        //             throw new ChatRoomAlreadyExistsException(format("The room '%s' already exists", roomName));
        //         })
        //         .execute();

        // Add this room name to user chat rooms set too
        // final BoundStatement updateUserRooms = userManager
        //         .dsl()
        //         .update()
        //         .fromBaseTable()
        //         .chatRooms().AddTo(roomName)
        //         .where()
        //         .login().Eq(creator.getLogin())
        //         .generateAndGetBoundStatement();

        // final BatchStatement batch = new BatchStatement(LOGGED);
        // batch.add(updateUserRooms);
        // userManager.getNativeSession().execute(batch);
    }

    public ChatRoomModel findRoomByName(String roomName) {
        // final ChatRoomEntity chatRoom = manager
        //         .crud()
        //         .findById(roomName)
        //         .get();
        final ChatRoomEntity chatRoom = null;
        if (chatRoom == null) {
            throw new ChatRoomDoesNotExistException(format("Chat room '%s' does not exists", roomName));
        }
        return chatRoom.toModel();
    }

    public List<ChatRoomModel> listChatRooms(int fetchSize) {

        return null;
        // return manager
        //         .dsl()
        //         .select()
        //         .allColumns_FromBaseTable()
        //         .without_WHERE_Clause()
        //         .limit(fetchSize)
        //         .getList()
        //         .stream()
        //         .map(ChatRoomEntity::toModel)
        //         .collect(toList());
    }

    public void addUserToRoom(String roomName, LightUserModel participant) {

        // final UDTValue udtValue = manager.meta.participants.encodeSingleElement(participant, Optional.empty());
        // final BoundStatement boundStatement = updateParticipantIfExists.bind(Sets.newHashSet(udtValue), roomName);
        // final ResultSet resultSet = manager.getNativeSession().execute(boundStatement);
        // if (!resultSet.wasApplied()) {
        //     throw new ChatRoomDoesNotExistException(format("The chat room '%s' does not exist", roomName));
        // }

        // // Add this room name to participant chat rooms set too
        // final BoundStatement updateUserRooms = userManager
        //         .dsl()
        //         .update()
        //         .fromBaseTable()
        //         .chatRooms().AddTo(roomName)
        //         .where()
        //         .login().Eq(participant.getLogin())
        //         .generateAndGetBoundStatement();

        // final BatchStatement batch = new BatchStatement(LOGGED);
        // batch.add(updateUserRooms);
        // userManager.getNativeSession().execute(batch);
    }

    public void removeUserFromRoom(String roomName, LightUserModel participant) {
        // final BatchStatement batch = new BatchStatement(LOGGED);

        // final BoundStatement removeParticipant = manager
        //         .dsl()
        //         .update()
        //         .fromBaseTable()
        //         .participants().RemoveFrom(participant)
        //         .where()
        //         .roomName().Eq(roomName)
        //         .generateAndGetBoundStatement();

        // // Remove this room name from participant chat rooms set too
        // final BoundStatement updateUserRooms = userManager
        //         .dsl()
        //         .update()
        //         .fromBaseTable()
        //         .chatRooms().RemoveFrom(roomName)
        //         .where()
        //         .login().Eq(participant.getLogin())
        //         .generateAndGetBoundStatement();

        // batch.add(removeParticipant);
        // batch.add(updateUserRooms);
        // manager.getNativeSession().execute(batch);
    }

    public String deleteRoomWithParticipants(String creatorLogin, String roomName, Set<String> participants) {

        final ChatRoomEntity currentRoom = null;
        // final ChatRoomEntity currentRoom = manager.crud().findById(roomName).get();
        if (currentRoom == null) {
            throw new ChatRoomDoesNotExistException("Chat room '"+roomName+"' does not exists");
        }

        final Set<LightUserModel> currentParticipants = currentRoom.getParticipants();

        // manager
        //         .dsl()
        //         .delete()
        //         .allColumns_FromBaseTable()
        //         .where()
        //         .roomName().Eq(roomName)
        //         .if_CreatorLogin().Eq(creatorLogin)
        //         .if_Participants().Eq(currentParticipants)
        //         .withLwtResultListener(lwtResult -> {
        //             String creator = lwtResult.currentValues().getTyped("creator_login");
        //             String message = creator.equals(creatorLogin) ? INCORRECT_PARTICIPANTS_FOR_DELETION:INCORRECT_CREATOR_FOR_DELETION;
        //             throw new IncorrectRoomException(message);
        //         })
        //         .execute();

        // Delete all chat messages from room
        // final BatchStatement batch = new BatchStatement(LOGGED);
        // TODO: update
        // batch.add(messagesManager.crud().deleteByPartitionKeys(roomName).generateAndGetBoundStatement());
        // manager.getNativeSession().execute(batch);

        // Remove room name from each participant room set too
        // Create batches of 100 mutations maximum to avoid
        // killing the coordinator
        // IntStream.range(0, (currentParticipants.size()/100) + 1)
        //         .forEach(iteration -> {
        //             BatchStatement updateParticipantBatch = new BatchStatement(LOGGED);
        //             currentParticipants
        //                     .stream()
        //                     .skip(iteration * 100)
        //                     .limit(100)
        //                     .map(participant -> userManager
        //                             .dsl().update()
        //                             .fromBaseTable()
        //                             .chatRooms().RemoveFrom(roomName)
        //                             .where()
        //                             .login().Eq(participant.getLogin())
        //                             .generateAndGetBoundStatement())
        //                     .forEach(updateParticipantBatch::add);
        //             manager.getNativeSession().execute(updateParticipantBatch);
        //         });

        return String.format(DELETION_MESSAGE, roomName, creatorLogin);
    }

}
