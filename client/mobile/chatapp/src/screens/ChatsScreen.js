import { useNavigation } from "@react-navigation/native";
import React, { useEffect, useState } from "react";
import { Alert, AppState, FlatList, ImageBackground } from 'react-native';
import PushNotification from "react-native-push-notification";
import SockJsClient from "react-stomp";
import { useStateValue } from "../../StateProvider";
import ChatRoomAPI from '../apis/ChatRoomAPI';
import MessageAPI from "../apis/MessageAPI";
import BG from '../assets/images/BG.png';
import ChatListItem from "../components/ChatListItem";
import NewMessageButton from "../components/NewMessageButton";
import Config from "../constants/Config";
import StackScreenName from "../constants/StackScreenName";
import ObjectHelper from "../helpers/ObjectHelper";

export default function ChatsScreen() {
  const [isLoading, setIsLoading] = useState(false);
  const [chatRooms, setChatRooms] = useState([]);
  const [{ user, currentRoomId }] = useStateValue();

  const fetchChatRooms = async () => {
    ChatRoomAPI.getAll()
      .then(result => {
        setChatRooms(result);
      })
      .catch(error => Alert.alert("Error", error.message));
  }

  useEffect(() => {
    if (isLoading) {
      fetchChatRooms();
      setIsLoading(false);
    }
  }, [isLoading]);

  const navigation = useNavigation();
  const handlePress = () => {
    navigation.navigate(StackScreenName.CreateRoom)
  }

  useEffect(() => {
    const unsubscribe = navigation.addListener('focus', () => {
      setIsLoading(true);
    });

    return () => {
      unsubscribe;
    };
  }, [navigation]);

  let customHeaders = {};
  if (user && user.accessToken) {
    customHeaders = {
      Authorization: "Bearer " + user.accessToken
    };
  }
  const replyUserStr = MessageAPI.getReplyMessageTopicUrl();
  const onReplyReceive = (message, topic) => {
    if (AppState.currentState.match(/inactive|background/)) {
      return;
    }
    ObjectHelper.clean(message);
    if (currentRoomId && currentRoomId === message.chatRoomId) {
      return;
    }
    PushNotification.localNotification({
      channelId: Config.ANDROID_CHANNEL_ID, // (required for Android)
      title: getTitleNotification(message), // (optional)
      message: message.text, // (required)
      userInfo: message,
    });
  };

  const getTitleNotification = (message) => {
    return `Local: [${message.chatRoomName}] ${message.fromUser}`
  }

  return (
    <>
      <ImageBackground style={{ width: '100%', height: '100%' }} source={BG}>
        <FlatList
          style={{ width: '100%' }}
          data={chatRooms}
          renderItem={({ item }) => <ChatListItem chatRoom={item} />}
          keyExtractor={(item) => item.id}
        />
        <NewMessageButton onPress={handlePress} />
        <SockJsClient
          url={MessageAPI.wsSourceUrl}
          topics={[replyUserStr]}
          headers={customHeaders}
          onMessage={onReplyReceive}
          debug={false} />
      </ImageBackground>
    </>
  );
}
