import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation, useRoute } from '@react-navigation/native';
import React, { useEffect } from 'react';
import { ImageBackground, TouchableOpacity } from 'react-native';
import { Divider, Drawer } from 'react-native-paper';
import { actionTypes } from '../../reducer';
import { useStateValue } from '../../StateProvider';
import BG from '../assets/images/BG.png';
import StackScreenName from '../constants/StackScreenName';

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
    // await AsyncStorage.removeItem("url");
    dispatch({
      type: actionTypes.SET_USER,
      user: null
    })
  }

  const goToSettingApiBaseUrl = async () => {
    navigation.navigate(StackScreenName.SettingApiBaseUrl)
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
        <TouchableOpacity onPress={goToSettingApiBaseUrl}>
          <Drawer.Item
            icon="settings"
            label="Api Base Url"
          />
        </TouchableOpacity>
        <Divider />
      </ImageBackground>
    </>
  );
}

export default SettingScreen;