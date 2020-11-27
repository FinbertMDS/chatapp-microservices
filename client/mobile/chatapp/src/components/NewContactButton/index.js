import React from 'react';
import { TouchableOpacity, View } from "react-native";
import AntDesign from "react-native-vector-icons/AntDesign";
import styles from "./styles";

const NewContactButton = (props) => {
  return (  
    <View style={styles.container}>
      <TouchableOpacity onPress={props.onPress}>
        <AntDesign
          name="contacts"
          size={28}
          color="white"
        />
      </TouchableOpacity>
    </View>
  )
}

export default NewContactButton;
