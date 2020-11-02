import axios from "axios";

const instance = axios.create({
  baseURL: "http://192.168.11.2:8080"/* process.env.API_BASE_URL */,
});

const getAuthToken = () => {
  let userInfo = null;
  if (userInfo) {
    try {
      userInfo = JSON.parse(userInfo);
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