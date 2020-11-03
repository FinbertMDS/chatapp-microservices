import moment from "moment";
import React from 'react';
import { Text, View } from 'react-native';
import styles from './styles';

const ChatMessage = (props) => {
  const { message, user } = props;

  const isMyMessage = () => {
    return message.fromUser === user.username;
  }

  return (
    <View style={styles.container}>
      <View style={[
        styles.messageBox, {
          backgroundColor: isMyMessage() ? '#dcf8c6' : 'white',
          marginLeft: isMyMessage() ? 50 : 0,
          marginRight: isMyMessage() ? 0 : 50,
        }
      ]}>
        <Text style={styles.name}>{message.fromUser}</Text>
        <Text style={styles.message}>{message.text}</Text>
        <Text style={styles.time}>{moment(message.date).fromNow()}</Text>
      </View>
    </View>
  )
}

export default ChatMessage;
