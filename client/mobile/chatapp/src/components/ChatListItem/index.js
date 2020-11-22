import { useNavigation } from "@react-navigation/native";
import moment from "moment";
import React from 'react';
import { Text, TouchableWithoutFeedback, View } from 'react-native';
import { Avatar } from "react-native-paper";
import { useStateValue } from "../../../StateProvider";
import StackScreenName from "../../constants/StackScreenName";
import styles from './style';


const ChatListItem = (props) => {
  const { chatRoom } = props;
  const navigation = useNavigation();
  const [{ user }] = useStateValue();

  const onClick = () => {
    navigation.navigate(StackScreenName.ChatRoom, {
      id: chatRoom.id,
      name: chatRoom.name,
    })
  }

  const displayLastMessageDate = (date) => {
    if (date) {
      if (moment(date).isSame(moment(), 'day')) {
        return moment(date).format('HH:mm');
      } else {
        return moment(date).format('dddd');
      }
    }
    return '';
  }

  const displayLastMessageFromUser = (fromUser) => {
    if (fromUser && user) {
      if (fromUser === user.username) {
        return 'Me';
      } else {
        return fromUser;
      }
    }
    return '';
  }

  return (
    <TouchableWithoutFeedback onPress={onClick}>
      <View style={styles.container}>
        <View style={styles.lefContainer}>
          <Avatar.Text size={24} label={chatRoom.name} style={styles.avatar}/>
          <View style={styles.midContainer}>
            <Text style={styles.username}>{chatRoom.name}</Text>
            <Text
              numberOfLines={2}
              style={styles.lastMessage}>
              {chatRoom.lastMessage
                ? `${displayLastMessageFromUser(chatRoom.lastMessage?.fromUser)}: ${chatRoom.lastMessage?.text}`
                : ""}
            </Text>
          </View>

        </View>

        <Text style={styles.time}>
          {displayLastMessageDate(chatRoom.lastMessage?.createdAt)}
        </Text>
      </View>
    </TouchableWithoutFeedback>
  )
};

export default ChatListItem;
