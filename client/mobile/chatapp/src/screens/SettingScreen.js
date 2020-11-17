import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation } from '@react-navigation/native';
import React from 'react';
import { TouchableOpacity } from 'react-native';
import { Divider, Drawer } from 'react-native-paper';
import { actionTypes } from '../../reducer';
import { useStateValue } from '../../StateProvider';
import { View } from '../components/Themed';

function SettingScreen() {
  const [{ }, dispatch] = useStateValue();
  const navigation = useNavigation();

  const handleSignOut = async () => {
    await AsyncStorage.removeItem("userInfo");
    dispatch({
      type: actionTypes.SET_USER,
      user: null
    })
  }

  return (
    <View>
      <TouchableOpacity onPress={handleSignOut}>
        <Drawer.Item
          icon="logout"
          label="Sign out"
        />
      </TouchableOpacity>
      <Divider />
    </View>
  );
}

export default SettingScreen;