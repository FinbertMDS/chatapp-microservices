import { useNavigation } from "@react-navigation/native";
import React, { useEffect, useLayoutEffect, useState } from "react";
import { FlatList, StyleSheet } from 'react-native';
import ChatListItem from '../components/ChatListItem';
import NewMessageButton from "../components/NewMessageButton";
import { Text, View } from '../components/Themed';
import chatRoomsData from '../data/ChatRooms';


export default function ChatsScreen() {

  const [chatRooms, setChatRooms] = useState([]);

  const navigation = useNavigation();
  const headerLeft = () => {
    return (
      <View style={{ marginLeft: 20 }}>
        <TouchableWithoutFeedback onPress={() => navigation.navigate(StackScreenName.Setting)}>
          <Avatar.Image size={24} source={require('../assets/logo.png')} />
        </TouchableWithoutFeedback>
      </View>
    )
  }
  useLayoutEffect(() => {
    navigation.setOptions({
      headerRight: () => (
        <Text>test234</Text>
      )
    });
  }, [navigation]);

  useEffect(() => {
    setChatRooms(chatRoomsData)
    // const fetchChatRooms = async () => {
    //   try {
    //     const userInfo = await Auth.currentAuthenticatedUser();

    //     const userData = await API.graphql(
    //       graphqlOperation(
    //         getUser, {
    //           id: userInfo.attributes.sub,
    //         }
    //       )
    //     )

    //     setChatRooms(userData.data.getUser.chatRoomUser.items)
    //   } catch (e) {
    //     console.log(e);
    //   }
    // }
    // fetchChatRooms();
  }, []);

  return (
    <View style={styles.container}>
      <FlatList
        style={{ width: '100%' }}
        data={chatRooms}
        renderItem={({ item }) => <ChatListItem chatRoom={item} />}
        keyExtractor={(item) => item.id}
      />
      <NewMessageButton />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },

});
