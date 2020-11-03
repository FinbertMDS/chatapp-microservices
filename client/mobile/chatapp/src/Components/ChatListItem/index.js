import { useNavigation } from "@react-navigation/native";
import React from 'react';
import { Text, TouchableWithoutFeedback, View } from 'react-native';
import { Avatar } from "react-native-paper";
import StackScreenName from "../../constants/StackScreenName";
import styles from './style';


const ChatListItem = (props) => {
  const { chatRoom } = props;
  const navigation = useNavigation();

  const onClick = () => {
    navigation.navigate(StackScreenName.ChatRoom, {
      id: chatRoom.id,
      name: chatRoom.name,
    })
  }

  return (
    <TouchableWithoutFeedback onPress={onClick}>
      <View style={styles.container}>
        <View style={styles.lefContainer}>
          <Avatar.Text size={24} label={chatRoom.name} style={styles.avatar}/>
          <View style={styles.midContainer}>
            <Text style={styles.username}>{chatRoom.name}</Text>
            {/* <Text
              numberOfLines={2}
              style={styles.lastMessage}>
              {chatRoom.lastMessage && chatRoom.lastMessage.user
                ? `${chatRoom.lastMessage.user.name}: ${chatRoom.lastMessage.content}`
                : ""}
            </Text> */}
          </View>

        </View>

        {/* <Text style={styles.time}>
          {chatRoom.lastMessage && moment(chatRoom.lastMessage.createdAt).format("DD/MM/YYYY")}
        </Text> */}
      </View>
    </TouchableWithoutFeedback>
  )
};

export default ChatListItem;
