import { useNavigation, useRoute } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import { Alert, FlatList, ImageBackground } from 'react-native';
import SockJsClient from "react-stomp";
import { actionTypes } from '../../reducer';
import { useStateValue } from '../../StateProvider';
import ChatRoomAPI from '../apis/ChatRoomAPI';
import MessageAPI from '../apis/MessageAPI';
import BG from '../assets/images/BG.png';
import ChatMessage from "../components/ChatMessage";
import InputBox from "../components/InputBox";

const updateMessagesData = (messages) => {
  return messages.filter((message) => !message.isNotification).reverse();
}

const ChatRoomScreen = () => {

  const [roomDetail, setRoomDetail] = useState(null);
  const [messages, setMessages] = useState([]);
  const [{ user }, dispatch] = useStateValue();
  const route = useRoute();
  const navigation = useNavigation();

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
        .catch(error => Alert.alert("Error", JSON.stringify(error)));
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
            .catch(error => Alert.alert("Error", JSON.stringify(error)));
        }
      }
    }
  }, [roomDetail, route.params.id, user.username]);

  const fetchMessages = () => {
    if (route.params.id) {
      MessageAPI.getAllMessageInRoom(route.params.id, user.username)
        .then(result => {
          if (result && result.length > 0) {
            setMessages(updateMessagesData(result));
          }
        })
        .catch(error => Alert.alert("Error", JSON.stringify(error)));
    }
  }

  useEffect(() => {
    fetchMessages();
  }, [route.params.id, user.username])

  // useEffect(() => {
  //   let participant = {
  //     "username": user.username
  //   };
  //   ChatRoomAPI.addUserToChatRoom(route.params.id, participant)
  //     .catch(error => Alert.alert("Error", JSON.stringify(error)));
  //   return () => {
  //     ChatRoomAPI.removeUserFromChatRoom(route.params.id, participant)
  //       .catch(error => Alert.alert("Error", JSON.stringify(error)));
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
  const updateChatRoomLastMessage = async (messageId) => {
    try {
      // await API.graphql(
      //   graphqlOperation(
      //     updateChatRoom, {
      //       input: {
      //         id: chatRoomID,
      //         lastMessageID: messageId,
      //       }
      //     }
      //   )
      // );
    } catch (e) {
      console.log(e);
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
      Alert.alert("Error", JSON.stringify(error));
    }
  }

  const customHeaders = {
    Authorization: "Bearer " + user.accessToken,
    chatRoomId: route.params.id
  };

  return (
    <ImageBackground style={{ width: '100%', height: '100%' }} source={BG}>
      <FlatList
        data={messages}
        renderItem={({ item }) => <ChatMessage user={user} message={item} />}
        keyExtractor={(item) => item.date.toString()}
        inverted
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

export default ChatRoomScreen;
