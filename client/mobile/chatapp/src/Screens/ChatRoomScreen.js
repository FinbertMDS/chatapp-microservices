import { useRoute } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import { FlatList, ImageBackground } from 'react-native';
import BG from '../assets/images/BG.png';
import ChatMessage from "../components/ChatMessage";
import InputBox from "../components/InputBox";
// import {
//   API,
//   graphqlOperation,
//   Auth,
// } from 'aws-amplify';
// import { messagesByChatRoom } from '../src/graphql/queries';
// import { onCreateMessage } from '../src/graphql/subscriptions';
import chatsData from '../data/Chats';
import usersData from '../data/Users';

const ChatRoomScreen = () => {

  const [messages, setMessages] = useState([]);
  const [myId, setMyId] = useState(null);

  const route = useRoute();

  const fetchMessages = async () => {
    // const messagesData = await API.graphql(
    //   graphqlOperation(
    //     messagesByChatRoom, {
    //       chatRoomID: route.params.id,
    //       sortDirection: "DESC",
    //     }
    //   )
    // )

    console.log("FETCH MESSAGES")
    // setMessages(messagesData.data.messagesByChatRoom.items);
    setMessages(chatsData);
  }

  useEffect(() => {
    fetchMessages();
  }, [])

  useEffect(() => {
    const getMyId = async () => {
      // const userInfo = await Auth.currentAuthenticatedUser();
      // setMyId(userInfo.attributes.sub);
      setMyId(usersData[0].id);
    }
    getMyId();
  }, [])

  useEffect(() => {
    // const subscription = API.graphql(
    //   graphqlOperation(onCreateMessage)
    // ).subscribe({
    //   next: (data) => {
    //     const newMessage = data.value.data.onCreateMessage;

    //     if (newMessage.chatRoomID !== route.params.id) {
    //       console.log("Message is in another room!")
    //       return;
    //     }

    //     fetchMessages();
    //     // setMessages([newMessage, ...messages]);
    //   }
    // });

    // return () => subscription.unsubscribe();
  }, [])

  console.log(`messages in state: ${messages.length}`)

  return (
    <ImageBackground style={{width: '100%', height: '100%'}} source={BG}>
      <FlatList
        data={messages}
        renderItem={({ item }) => <ChatMessage myId={myId} message={item} />}
        inverted
      />

      <InputBox chatRoomID={route.params.id} />
    </ImageBackground>
  );
}

export default ChatRoomScreen;
