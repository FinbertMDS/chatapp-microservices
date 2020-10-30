import axios from "axios";

const signInUrl = process.env.REACT_APP_API_BASE_URL + "/security/api/auth/signin";
const signUpUrl = process.env.REACT_APP_API_BASE_URL + "/security/api/auth/signup";

const SecurityAPI = {
  signIn,
  signUp
};

function signIn(data) {
  return axios.post(signInUrl, data)
    .then(response => response.data);
};

function signUp(data) {
  return axios.post(signUpUrl, data)
    .then(response => response.data);
};

export default SecurityAPI;