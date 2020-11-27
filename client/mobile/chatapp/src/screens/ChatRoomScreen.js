import { useNavigation, useRoute } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import {
  ActivityIndicator, Alert, FlatList, ImageBackground,
  Platform, StyleSheet, TouchableWithoutFeedback, View
} from 'react-native';
import SockJsClient from "react-stomp";
import { actionTypes } from '../../reducer';
import { useStateValue } from '../../StateProvider';
import ChatRoomAPI from '../apis/ChatRoomAPI';
import MessageAPI from '../apis/MessageAPI';
import BG from '../assets/images/BG.png';
import ChatMessage from "../components/ChatMessage";
import InputBox from "../components/InputBox";
import { Text } from '../components/Themed';
import StackScreenName from '../constants/StackScreenName';

const updateMessagesData = (messages) => {
  return messages.filter((message) => !message.isNotification);
}

const ChatRoomScreen = () => {

  const [roomDetail, setRoomDetail] = useState(null);
  const [messages, setMessages] = useState([]);
  const [{ user }, dispatch] = useStateValue();
  const route = useRoute();
  const navigation = useNavigation();
  const [isFetching, setIsFetching] = useState(false);
  const [hasMore, setHasMore] = useState(true);
  const [currentPage, setCurrentPage] = useState(0);

  useEffect(() => {
    if (roomDetail) {
      navigation.setOptions({
        headerTitle: () => (
          <TouchableWithoutFeedback onPress={handleOpenChatRoomSetting}>
            <Text style={[styles.title]}>{roomDetail.name}</Text>
          </TouchableWithoutFeedback>
        ),
      })
    }
  }, [roomDetail])

  const handleOpenChatRoomSetting = () => {
    navigation.navigate(StackScreenName.ChatRoomSetting, {
      roomDetail: roomDetail
    })
  };

  useEffect(() => {
    dispatch({
      type: actionTypes.SET_CURRENT_ROOM_ID,
      currentRoomId: route.params.id
    });
    return () => {
      dispatch({
        type: actionTypes.SET_CURRENT_ROOM_ID,
        currentRoomId: null
      });
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [route.params.id]);

  useEffect(() => {
    if (route.params.id) {
      ChatRoomAPI.getDetail(route.params.id)
        .then(result => {
          setRoomDetail(result);
          navigation.setOptions({ title: result.name })
        })
        .catch(error => Alert.alert("Error", error.message));
    }
  }, [route.params.id, user.username]);

  useEffect(() => {
    if (route.params.id && roomDetail) {
      if (route.params.id !== roomDetail.id) {
        return;
      }
      if (roomDetail.connectedUsers) {
        if (roomDetail.connectedUsers.filter(e => e.username === user.username).length <= 0) {
          let participant = {
            "username": user.username
          };
          ChatRoomAPI.addUserToChatRoom(route.params.id, participant)
            .catch(error => Alert.alert("Error", error.message));
        }
      }
    }
  }, [roomDetail, route.params.id, user.username]);

  const fetchMessages = () => {
    MessageAPI.getMessageInRoom(route.params.id, user.username)
      .then(result => {
        if (result && result.length > 0) {
          setMessages(updateMessagesData(result));
        }
      })
      .catch(error => Alert.alert("Error", error.message));
  }

  const fetchMoreData = () => {
    if (route.params.id) {
      if (isFetching) {
        return;
      }
      if (!hasMore) {
        return;
      }
      setIsFetching(true);
      MessageAPI.getMessageInRoom(route.params.id, user.username, currentPage + 1)
        .then(result => {
          setIsFetching(false);
          if (!result) {
            setHasMore(false);
            return;
          }
          result = updateMessagesData(result);
          if (result.length <= 0) {
            setHasMore(false);
            return;
          }
          setCurrentPage(currentPage + 1);
          setMessages([
            ...messages,
            ...result,
          ]);
        })
        .catch(error => alert(error.message));
    }
  }

  useEffect(() => {
    if (route.params.id) {
      setMessages([]);
      setHasMore(true);
      setCurrentPage(0);

      fetchMessages();
    }
  }, [route.params.id, user.username])

  // useEffect(() => {
  //   let participant = {
  //     "username": user.username
  //   };
  //   ChatRoomAPI.addUserToChatRoom(route.params.id, participant)
  //     .catch(error => Alert.alert("Error", error.message));
  //   return () => {
  //     ChatRoomAPI.removeUserFromChatRoom(route.params.id, participant)
  //       .catch(error => Alert.alert("Error", error.message));
  //   };
  // }, [route.params.id, user.username]);

  const publicTopicStr = MessageAPI.getPublicMessageTopicUrl(route.params.id);
  // const privateTopicStr = MessageAPI.getPrivateMessageTopicUrl(route.params.id);
  const [/* clientRef */, setClientRef] = useState(null);
  const [/* clientConnected */, setClientConnected] = useState(false);
  const onMessageReceive = (msg, topic) => {
    if (!msg.isNotification) {
      setMessages([
        msg,
        ...messages,
      ]);
    }
  }

  const handleSendPress = async (input) => {
    try {
      let messageData = {
        "fromUser": user.username,
        "text": input
      };
      await MessageAPI.sendPublicMessage(route.params.id, messageData);

      // await updateChatRoomLastMessage(newMessageData.data.createMessage.id)
    } catch (e) {
      Alert.alert("Error", error.message);
    }
  }

  const customHeaders = {
    Authorization: "Bearer " + user.accessToken,
    chatRoomId: route.params.id
  };

  const renderFetching = (
    <View style={{ marginTop: 15, marginBottom: 15 }}>
      <ActivityIndicator />
    </View>
  )

  return (
    <ImageBackground style={{ width: '100%', height: '100%' }} source={BG}>
      {
        isFetching && renderFetching
      }
      <FlatList
        data={messages}
        renderItem={({ item }) => <ChatMessage user={user} message={item} />}
        keyExtractor={(item) => item.date.toString()}
        inverted
        onEndReachedThreshold={0}
        onEndReached={fetchMoreData}
      />
      <InputBox chatRoomID={route.params.id} onSendPress={handleSendPress} />
      <SockJsClient
        url={MessageAPI.wsSourceUrl}
        topics={[publicTopicStr]}
        headers={customHeaders}
        onMessage={onMessageReceive} ref={(client) => { setClientRef(client) }}
        onConnect={() => { setClientConnected(true) }}
        onDisconnect={() => { setClientConnected(false) }}
        debug={false} />
    </ImageBackground>
  );
}

const styles = StyleSheet.create({
  title: Platform.select({
    ios: {
      fontSize: 17,
      fontWeight: '600',
    },
    android: {
      fontSize: 20,
      fontFamily: 'sans-serif-medium',
      fontWeight: 'normal',
    },
    default: {
      fontSize: 18,
      fontWeight: '500',
    },
  }),
});


export default ChatRoomScreen;
