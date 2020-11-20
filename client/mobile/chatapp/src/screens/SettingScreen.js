import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation, useRoute } from '@react-navigation/native';
import React, { useEffect } from 'react';
import { ImageBackground, TouchableOpacity } from 'react-native';
import { Divider, Drawer } from 'react-native-paper';
import { actionTypes } from '../../reducer';
import { useStateValue } from '../../StateProvider';
import BG from '../assets/images/BG.png';

function SettingScreen() {
  const [{ }, dispatch] = useStateValue();
  const navigation = useNavigation();
  const route = useRoute();

  useEffect(() => {
    if (route.params && route.params.signOut) {
      handleSignOut();
    }
  }, [route.params]);

  const handleSignOut = async () => {
    await AsyncStorage.removeItem("userInfo");
    dispatch({
      type: actionTypes.SET_USER,
      user: null
    })
  }

  return (
    <>
      <ImageBackground style={{ width: '100%', height: '100%' }} source={BG}>
        <TouchableOpacity onPress={handleSignOut}>
          <Drawer.Item
            icon="logout"
            label="Sign out"
          />
        </TouchableOpacity>
        <Divider />
      </ImageBackground>
    </>
  );
}

export default SettingScreen;