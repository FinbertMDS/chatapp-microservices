import { useNavigation } from '@react-navigation/native';
import React, { useLayoutEffect, useState } from 'react';
import { Alert, ImageBackground, View } from 'react-native';
import { TouchableWithoutFeedback } from 'react-native-gesture-handler';
import { TextInput } from 'react-native-paper';
import ChatRoomAPI from '../apis/ChatRoomAPI';
import BG from '../assets/images/BG.png';
import { Text } from '../components/Themed';
import StackScreenName from '../constants/StackScreenName';

export default function CreateRoomScreen() {
  const [roomName, setRoomName] = useState("");
  const navigation = useNavigation();

  useLayoutEffect(() => {
    navigation.setOptions({
      headerRight: () => (
        <View style={{
          marginRight: 10,
        }}>
          <TouchableWithoutFeedback disabled={roomName === ""} onPress={createRoom}>
            <Text style={{ color: roomName === "" ? "#609D9E" : "white" }}>Create</Text>
          </TouchableWithoutFeedback>
        </View>
      )
    });
  }, [navigation, roomName]);

  const createRoom = () => {
    if (roomName !== "") {
      let roomData = {
        "name": roomName
      };
      ChatRoomAPI.createRoom(roomData)
        .then(() => {
          navigation.navigate(StackScreenName.Chats);
        })
        .catch(error => Alert.alert("Error", error.message));
    }
  }
  return (
    <>
      <ImageBackground style={{ width: '100%', height: '100%' }} source={BG}>
        <TextInput
          label="Room name"
          returnKeyType="next"
          value={roomName}
          onChangeText={text => setRoomName(text)}
          autoCapitalize="none"
        />
      </ImageBackground>
    </>
  );
}
