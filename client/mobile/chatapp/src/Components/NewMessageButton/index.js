import { useNavigation } from '@react-navigation/native';
import React from 'react';
import { TouchableOpacity, View } from "react-native";
import MaterialCommunityIcons from "react-native-vector-icons/MaterialCommunityIcons";
import styles from "./styles";

const NewMessageButton = () => {

  const navigation = useNavigation();

  const onPress = () => {
    navigation.navigate('Contacts');
  }

  return (  
    <View style={styles.container}>
      <TouchableOpacity onPress={onPress}>
        <MaterialCommunityIcons
          name="message-reply-text"
          size={28}
          color="white"
        />
      </TouchableOpacity>
    </View>
  )
}

export default NewMessageButton;
