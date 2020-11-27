import { useNavigation } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import { Alert, FlatList, ImageBackground, StyleSheet } from 'react-native';
import { actionTypes } from '../../reducer';
import { useStateValue } from '../../StateProvider';
import ContactAPI from '../apis/ContactAPI';
import BG from '../assets/images/BG.png';
import ContactListItem from '../components/ContactListItem';
import NewContactButton from '../components/NewContactButton';
import Const from '../constants/Const';
import StackScreenName from '../constants/StackScreenName';


export default function ContactsScreen() {
  const [{ user, contacts }, dispatch] = useStateValue();
  const navigation = useNavigation();

  useEffect(() => {
    ContactAPI.getContactForUser(user.username)
      .then(result => {
        dispatch({
          type: actionTypes.SET_CONTACTS,
          contacts: result
        });
      })
      .catch(error => Alert.alert("Error", error.message));
  }, [user.username])

  const [allContacts, setAllContacts] = useState([]);
  useEffect(() => {
    ContactAPI.getAll()
      .then(result => {
        setAllContacts(result);
      })
      .catch(error => alert(error.message));
  }, []);

  useEffect(() => {
    ContactAPI.getContactForUser(user.username)
      .then(result => {
        dispatch({
          type: actionTypes.SET_CONTACTS,
          contacts: result
        });
      })
      .catch(error => Alert.alert("Error", error.message));
  }, [user.username])
  

  const handleAddContact = async (userList, checked) => {
    if (checked.length > 0) {
      for (const index of checked) {
        let addUserContactRequest = {
          "username": userList[index].username
        };
        try {
          const result = await ContactAPI.createContactForUser(user.username, addUserContactRequest);
          dispatch({
            type: actionTypes.SET_CONTACTS,
            contacts: result
          });
          navigation.navigate(StackScreenName.Contacts);
        } catch (error) {
          Alert.alert("Error", error.message)
        }
      }
    } else {
      navigation.navigate(StackScreenName.Contacts);
    }
  };

  const handlePressAddContact = () => {
    navigation.navigate(StackScreenName.UserList, {
      data: allContacts,
      regList: [{ username: user.username }, ...contacts],
      type: Const.USERLIST_TYPE.ADD_CONTACT.key,
      onSave: handleAddContact
    })
  }

  const handleEnterContactSetting = (index) => {
    navigation.navigate(StackScreenName.ContactSetting, {
      contactInfo: contacts[index],
    })
  }

  return (
    <>
      <ImageBackground style={{ width: '100%', height: '100%' }} source={BG}>
        <FlatList
          style={{ width: '100%' }}
          data={contacts}
          renderItem={({ item, index }) => <ContactListItem user={item} onClickItem={()=> handleEnterContactSetting(index)}/>}
          keyExtractor={(item) => item.username}
        />
        <NewContactButton onPress={handlePressAddContact} />
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
