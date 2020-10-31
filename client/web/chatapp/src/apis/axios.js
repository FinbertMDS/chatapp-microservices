import axios from "axios";

const instance = axios.create({
  baseURL: process.env.REACT_APP_API_BASE_URL,
});

const getAuthToken = () => {
  let userInfo = localStorage.getItem("userInfo");
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

let authToken = getAuthToken();
if (authToken) {
  instance.defaults.headers.common['Authorization'] = authToken;
}

export default instance;