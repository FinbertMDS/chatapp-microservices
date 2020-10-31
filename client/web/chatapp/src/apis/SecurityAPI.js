import axios from "./axios";

const signInUrl = "./security/api/auth/signin";
const signUpUrl = "./security/api/auth/signup";

const SecurityAPI = {
  signIn: function (data) {
    return axios.post(signInUrl, data)
      .then(response => response.data);
  },
  signUp: function (data) {
    return axios.post(signUpUrl, data)
      .then(response => response.data);
  }
};

export default SecurityAPI;