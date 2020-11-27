import { useNavigation, useRoute } from '@react-navigation/native';
import React from 'react';
import { Alert, ImageBackground, StyleSheet, TouchableOpacity } from 'react-native';
import { Divider, Drawer } from 'react-native-paper';
import { actionTypes } from '../../reducer';
import { useStateValue } from '../../StateProvider';
import ContactAPI from '../apis/ContactAPI';
import BG from '../assets/images/BG.png';
import StackScreenName from '../constants/StackScreenName';


export default function ContactSettingScreen() {
  const route = useRoute();
  const [{ user }, dispatch] = useStateValue();
  const navigation = useNavigation();
  let contactInfo = route.params.contactInfo;
  if (!contactInfo) {
    return null;
  }
  const handleRemoveContact = async () => {
    let removeUserContactRequest = {
      "username": contactInfo.username
    };
    try {
      const result = await ContactAPI.removeContactForUser(user.username, removeUserContactRequest);
      dispatch({
        type: actionTypes.SET_CONTACTS,
        contacts: result
      })
      navigation.navigate(StackScreenName.Contacts)
    } catch (error) {
      Alert.alert("Error", error.message);
    }
  }

  return (
    <>
      <ImageBackground style={{ width: '100%', height: '100%' }} source={BG}>
        <Drawer.Item
          label={`Name: ${contactInfo.username}`}
        />
        <Divider />
        <TouchableOpacity onPress={handleRemoveContact}>
          <Drawer.Item
            icon="person-remove"
            label="Remove contact"
          />
        </TouchableOpacity>
        <Divider />
      </ImageBackground>
    </>
  );
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: 'center',
    justifyContent: 'center',
  },
});
