import { useNavigation } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import {

  TouchableWithoutFeedback, View
} from "react-native";
import styles from "./style";
// import {
//   Auth,
// } from 'aws-amplify';


const ChatListItem = (props) => {
  const { chatRoom } = props;
  const [otherUser, setOtherUser] = useState(null);

  const navigation = useNavigation();
  useEffect(() => {
    // const getOtherUser = async () => {
    //   const userInfo = await Auth.currentAuthenticatedUser();
    //   if (chatRoom.chatRoomUsers.items[0].user.id === userInfo.attributes.sub) {
    //     setOtherUser(chatRoom.chatRoomUsers.items[1].user);
    //   } else {
    //     setOtherUser(chatRoom.chatRoomUsers.items[0].user);
    //   }
    // }
    // getOtherUser();
  }, [])

  const onClick = () => {
    navigation.navigate('ChatRoom', {
      id: chatRoom.id,
      name: otherUser.name,
    })
  }

  if (!otherUser) {
    return null;
  }

  return (
    <TouchableWithoutFeedback onPress={onClick}>
      <View style={styles.container}>
        <View style={styles.lefContainer}>
          <Image source={{ uri: otherUser.imageUri }} style={styles.avatar}/>

          <View style={styles.midContainer}>
            <Text style={styles.username}>{otherUser.name}</Text>
            <Text
              numberOfLines={2}
              style={styles.lastMessage}>
              {chatRoom.lastMessage
                ? `${chatRoom.lastMessage.user.name}: ${chatRoom.lastMessage.content}`
                : ""}
            </Text>
          </View>

        </View>

        <Text style={styles.time}>
          {chatRoom.lastMessage && moment(chatRoom.lastMessage.createdAt).format("DD/MM/YYYY")}
        </Text>
      </View>
    </TouchableWithoutFeedback>
  )
};

export default ChatListItem;
