import { useNavigation } from "@react-navigation/native";
import React, { useEffect, useState } from "react";
import { Alert, FlatList, ImageBackground } from 'react-native';
import ChatRoomAPI from '../apis/ChatRoomAPI';
import BG from '../assets/images/BG.png';
import ChatListItem from "../components/ChatListItem";
import NewMessageButton from "../components/NewMessageButton";
import StackScreenName from "../constants/StackScreenName";

export default function ChatsScreen() {
  const [isLoading, setIsLoading] = useState(false);
  const [chatRooms, setChatRooms] = useState([]);

  const fetchChatRooms = async () => {
    ChatRoomAPI.getAll()
      .then(result => {
        setChatRooms(result);
      })
      .catch(error => Alert.alert(error.message));
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

  return (
    <ImageBackground style={{ width: '100%', height: '100%' }} source={BG}>
      <FlatList
        style={{ width: '100%' }}
        data={chatRooms}
        renderItem={({ item }) => <ChatListItem chatRoom={item} />}
        keyExtractor={(item) => item.id}
      />
      <NewMessageButton onPress={handlePress} />
    </ImageBackground>
  );
}
