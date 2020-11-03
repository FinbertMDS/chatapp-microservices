import { API_BASE_URL } from "@env";
import axios from "axios";
import { AppData } from "../core/appData";

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

export default instance;