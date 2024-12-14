import Config from "../constants/Config";
import axios from "./axios";

const getAllUrl = "./api/contacts";
const searchContactUrl = "./api/contacts/search";
const getContactForUserUrl = (username) => `./api/users/${username}/contacts`;
const createContactForUserUrl = (username) => `./api/users/${username}/contacts`;
const removeContactForUserUrl = (username) => `./api/users/${username}/contacts`;

const ContactAPI = {
  getAll: function () {
    return axios.get(getAllUrl)
      .then(response => response.data);
  },
  searchContact: function (query, page = 0, size = Config.DEFAULT_PAGE_SIZE_CONTACT) {
    let url = `${searchContactUrl}?query=${query}&page=${page}&size=${size}`;
    return axios.get(url)
      .then(response => response.data);
  },
  getContactForUser: function (username) {
    let url = getContactForUserUrl(username);
    return axios.get(url)
      .then(response => response.data);
  },
  createContactForUser: function (username, data) {
    let url = createContactForUserUrl(username);
    return axios.post(url, data)
      .then(response => response.data);
  },
  removeContactForUser: function (username, data) {
    let url = removeContactForUserUrl(username);
    return axios.delete(url, {data})
      .then(response => response.data);
  },
};

export default ContactAPI;