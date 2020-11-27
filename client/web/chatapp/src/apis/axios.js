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
    if (error && error.response && error.response.status === 401) {
      localStorage.removeItem('userInfo');
      window.location = '/';
    }
    return Promise.reject(error)
  });
}

handleRequest();

export default instance;