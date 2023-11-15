import { API_BASE_URL, API_BASE_URL_ANDROID } from "@env";
import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import StackScreenName from "../constants/StackScreenName";
import { AppData } from "../core/appData";
import RootNavigation from "../navigation/RootNavigation";
import { Platform } from 'react-native';

const baseURL = Platform.OS === "android" ? API_BASE_URL_ANDROID : API_BASE_URL

const instance = axios.create({
  baseURL: baseURL,
});

const getAuthToken = () => {
  let userInfo = AppData.user;
  if (userInfo) {
    try {
      return userInfo.tokenType + " " + userInfo.accessToken;
    } catch (error) {
      console.log(error.message);
    }
  }
  return null;
};

export const addAuthTokenToHeader = () => {
  let authToken = getAuthToken();
  if (authToken) {
    instance.defaults.headers.common['Authorization'] = authToken;
  }
}

addAuthTokenToHeader();

const handleRequest = () => {
  instance.interceptors.request.use(async config => {
    let urlOld;
    try {
      urlOld = await AsyncStorage.getItem('url');
      if (urlOld != null && urlOld != "") {
        config.baseURL =  `${urlOld}:8080`;
      }
    } catch (e) {
    }
    return config; 
  }, error => Promise.reject(error));
}

handleRequest();

const handleResponse = () => {
  instance.interceptors.response.use(function (response) {
    return response;
  }, function (error) {
    if (error && error.response && error.response.status === 401) {
      if (AppData.user != null) {
        AppData.user = null;
        AsyncStorage.removeItem('userInfo')
          .then(RootNavigation.navigate(StackScreenName.Setting, {
            signOut: true
          }));
      }
    }
    return Promise.reject(error)
  });
}

handleResponse();

export default instance;