// import {
//   API,
//   Auth,
//   graphqlOperation,
// } from 'aws-amplify';
// import {
//   createMessage,
//   updateChatRoom,
// } from '../../src/graphql/mutations';
import React, { useState } from 'react';
import { KeyboardAvoidingView, Platform, TextInput, TouchableOpacity, View } from "react-native";
import Entypo from "react-native-vector-icons/Entypo";
import FontAwesome5 from "react-native-vector-icons/FontAwesome5";
import Fontisto from "react-native-vector-icons/Fontisto";
import MaterialCommunityIcons from "react-native-vector-icons/MaterialCommunityIcons";
import MaterialIcons from "react-native-vector-icons/MaterialIcons";
import styles from './styles';


const InputBox = (props) => {

  const { chatRoomID } = props;

  const [message, setMessage] = useState('');

  const onMicrophonePress = () => {
    console.warn('Microphone')
  }

  const onPress = async () => {
    if (!message) {
      onMicrophonePress();
    } else {
      await props.onSendPress(message);
    }
    setMessage('');
  }

  return (
    <KeyboardAvoidingView
      behavior={Platform.OS == "ios" ? "padding" : "height"}
      keyboardVerticalOffset={100}
      style={{width: '100%'}}
    >
      <View style={styles.container}>
      <View style={styles.mainContainer}>
        <FontAwesome5 name="laugh-beam" size={24} color="grey" />
        <TextInput
          placeholder={"Type a message"}
          style={styles.textInput}
          multiline
          value={message}
          onChangeText={setMessage}
        />
        <Entypo name="attachment" size={24} color="grey" style={styles.icon} />
        {!message && <Fontisto name="camera" size={24} color="grey" style={styles.icon} />}
      </View>
      <TouchableOpacity onPress={onPress}>
        <View style={styles.buttonContainer}>
          {!message
            ? <MaterialCommunityIcons name="microphone" size={28} color="white" />
            : <MaterialIcons name="send" size={28} color="white" />}
        </View>
      </TouchableOpacity>
      </View>
    </KeyboardAvoidingView>
  )
}

export default InputBox;
