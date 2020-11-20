import { API_BASE_URL } from "@env";
import AsyncStorage from "@react-native-async-storage/async-storage";
import axios from "axios";
import StackScreenName from "../constants/StackScreenName";
import { AppData } from "../core/appData";
import RootNavigation from "../navigation/RootNavigation";

const instance = axios.create({
  baseURL: API_BASE_URL,
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
  instance.interceptors.response.use(function (response) {
    return response;
  }, function (error) {
    if (401 === error.response.status) {
      AppData.user = null;
      AsyncStorage.removeItem('userInfo')
        .then(RootNavigation.navigate(StackScreenName.Setting, {
          signOut: true
        }));
    }
    return Promise.reject(error)
  });
}

handleRequest();

export default instance;