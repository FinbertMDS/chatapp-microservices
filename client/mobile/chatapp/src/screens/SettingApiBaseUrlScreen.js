import { API_BASE_URL, API_BASE_URL_ANDROID } from "@env";
import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation, useRoute } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import { Alert, ImageBackground, Platform } from 'react-native';
import { useStateValue } from '../../StateProvider';
import BG from '../assets/images/BG.png';
import { urlValidator } from '../core/utils';
import MessageAPI from '../apis/MessageAPI';
import Header from "../components/Header";
import Button from "../components/Button";
import TextInput from "../components/TextInput";

const baseURLDefault = Platform.OS === "android" ? API_BASE_URL_ANDROID : API_BASE_URL

function SettingApiBaseUrlScreen() {
  const [{ }, dispatch] = useStateValue();
  const navigation = useNavigation();
  const route = useRoute();

  const [url, setUrl] = useState({ value: baseURLDefault.replace(":8080", ""), error: '' });

  useEffect(() => {
    const bootstrapAsync = async () => {
      let urlOld;
      try {
        urlOld = await AsyncStorage.getItem('url');
        if (urlOld != null) {
          setUrl({ value: urlOld, error: '' });
        }
      } catch (e) {
      }
    };

    bootstrapAsync();
  }, [])


  const _onChangePressed = async () => {
    // const urlError = urlValidator(url.value);

    // if (urlError) {
    //   setUrl({ ...url, error: urlError });
    //   return;
    // }

    await AsyncStorage.setItem("url", `${url.value}`);
    await MessageAPI.updateUrl();
    
    Alert.alert("Setting Url", "Change Api Base Url successfully");
  }

  return (
    <>
      <ImageBackground style={{ width: '100%', height: '100%' }} source={BG}>
        <Header>Api Url</Header>

        <TextInput
          label="Api Base Url"
          returnKeyType="next"
          value={url.value}
          onChangeText={text => setUrl({ value: text, error: '' })}
          error={!!url.error}
          errorText={url.error}
          autoCapitalize="none"
        />

        <Button mode="contained" onPress={_onChangePressed}>
          Change
        </Button>
      </ImageBackground>
    </>
  );
}

export default SettingApiBaseUrlScreen;