import axios, { addAuthTokenToHeader } from "./axios";

const getAllUrl = "./message/api/chatroom";
const createRoomUrl = "./message/api/chatroom";
const getDetailUrl = "./message/api/chatroom";
const addUserToChatRoomUrl = "./message/api/chatroom/participant";
const removeUserFromChatRoomUrl = "./message/api/chatroom/participant";

const ChatRoomAPI = {
  getAll: function () {
    addAuthTokenToHeader();
    return axios.get(getAllUrl)
      .then(response => response.data);
  },
  getRoomForUser: function (username) {
    addAuthTokenToHeader();
    let url = `${getAllUrl}?forUser=${username}`
    return axios.get(url)
      .then(response => response.data);
  },
  createRoom: function (data) {
    return axios.post(createRoomUrl, data)
      .then(response => response.data);
  },
  getDetail: function (roomId) {
    let url = getDetailUrl + "/" + roomId;
    return axios.get(url)
      .then(response => response.data);
  },
  addUserToChatRoom: function (roomId, data) {
    let url = addUserToChatRoomUrl + "/" + roomId;
    return axios.put(url, data)
      .then(response => response.data);
  },
  removeUserFromChatRoom: function (roomId, data) {
    let url = removeUserFromChatRoomUrl + "/" + roomId;
    return axios.patch(url, data)
      .then(response => response.data);
  }
};

export default ChatRoomAPI;