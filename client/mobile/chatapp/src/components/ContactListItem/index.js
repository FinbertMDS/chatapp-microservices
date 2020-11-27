import { useNavigation } from '@react-navigation/native';
import React from 'react';
import { Text, TouchableWithoutFeedback, View } from "react-native";
import { Avatar, Checkbox } from 'react-native-paper';
import styles from "./style";


const ContactListItem = (props) => {
  const { user, showCheckbox, checked } = props;

  const navigation = useNavigation();

  const onClick = () => {
    if (props.onClickItem) {
      props.onClickItem();
    }
  }

  const getAvatarText = (text) => {
    return String(text).charAt(0).toUpperCase();
  }

  return (
    <TouchableWithoutFeedback onPress={onClick}>
      <View style={styles.container}>
        <View style={styles.lefContainer}>
          <Avatar.Text size={36} label={getAvatarText(user.username)} style={styles.avatar} />
          <View style={styles.midContainer}>
            <Text style={styles.username}>{user.username}</Text>
          </View>
          {
            showCheckbox && (
              <View style={styles.rightContainer}>
                <Checkbox.Item status={checked ? "checked" : "indeterminate"} />
              </View>
            )
          }
        </View>
      </View>
    </TouchableWithoutFeedback>
  )
};

export default ContactListItem;
