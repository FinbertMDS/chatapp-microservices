import { useNavigation, useRoute } from '@react-navigation/native';
import React from 'react';
import { Alert, ImageBackground, TouchableOpacity } from 'react-native';
import { Divider, Drawer } from 'react-native-paper';
import { useStateValue } from '../../StateProvider';
import ChatRoomAPI from '../apis/ChatRoomAPI';
import BG from '../assets/images/BG.png';
import Const from '../constants/Const';
import StackScreenName from '../constants/StackScreenName';

function ChatRoomSettingScreen() {
  const [{ user, contacts }, dispatch] = useStateValue();
  const navigation = useNavigation();
  const route = useRoute();
  const roomDetail = route.params.roomDetail;

  if (!roomDetail) {
    return null
  }

  const handleAddMember = () => {
    navigation.navigate(StackScreenName.UserList, {
      data: contacts,
      regList: roomDetail.connectedUsers,
      type: Const.USERLIST_TYPE.ADD_MEMBER.key,
      onSave: handleAddMemberToRoomOnSave
    })
  }

  const handleAddMemberToRoomOnSave = async (userList, checked) => {
    if (checked.length > 0) {
      for (const index of checked) {
        let participant = {
          "username": userList[index].username
        };
        let roomId = roomDetail.id;
        try {
          const result = await ChatRoomAPI.addUserToChatRoom(roomId, participant);
          navigation.navigate(StackScreenName.ChatRoomSetting, {
            roomDetail: result
          })
        } catch (error) {
          Alert.alert("Error", error.message)
        }
      }
    } else {
      navigation.navigate(StackScreenName.ChatRoomSetting, {
        roomDetail: roomDetail
      })
    }
  };

  const handleRemoveMemberToRoomOnSave = async (userList, checked) => {
    if (checked.length > 0) {
      for (const index of checked) {
        let participant = {
          "username": userList[index].username
        };
        let roomId = roomDetail.id;
        try {
          const result = await ChatRoomAPI.removeUserFromChatRoom(roomId, participant);
          navigation.navigate(StackScreenName.ChatRoomSetting, {
            roomDetail: result
          })
        } catch (error) {
          Alert.alert("Error", error.message)
        }
      }
    } else {
      navigation.navigate(StackScreenName.ChatRoomSetting, {
        roomDetail: roomDetail
      })
    }
  };

  const handleRemoveMember = () => {
    navigation.navigate(StackScreenName.UserList, {
      data: roomDetail.connectedUsers,
      regList: [{ username: user.username }],
      type: Const.USERLIST_TYPE.REMOVE_MEMBER.key,
      onSave: handleRemoveMemberToRoomOnSave
    })
  }

  const handleListMember = () => {
    navigation.navigate(StackScreenName.UserList, {
      data: roomDetail.connectedUsers,
      type: Const.USERLIST_TYPE.LIST_MEMBER.key,
    })
  }

  return (
    <>
      <ImageBackground style={{ width: '100%', height: '100%' }} source={BG}>
        <Drawer.Item
          label={`Room name: ${roomDetail.name}`}
        />
        <Divider />
        <TouchableOpacity onPress={handleAddMember}>
          <Drawer.Item
            icon="person-add"
            label="Add member"
          />
        </TouchableOpacity>
        <Divider />
        <TouchableOpacity onPress={handleRemoveMember}>
          <Drawer.Item
            icon="person-remove"
            label="Remove member"
          />
        </TouchableOpacity>
        <Divider />
        <TouchableOpacity onPress={handleListMember}>
          <Drawer.Item
            icon="format-list-bulleted"
            label="List member"
          />
        </TouchableOpacity>
        <Divider />
      </ImageBackground>
    </>
  );
}

export default ChatRoomSettingScreen;