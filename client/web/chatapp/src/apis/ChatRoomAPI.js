import axios from "axios";

const getAllUrl = process.env.REACT_APP_API_BASE_URL + "/message/api/chatroom";
const createRoomUrl = process.env.REACT_APP_API_BASE_URL + "/message/api/chatroom";
const getDetailUrl = process.env.REACT_APP_API_BASE_URL + "/message/api/chatroom";
const addUserToChatRoomUrl = process.env.REACT_APP_API_BASE_URL + "/message/api/chatroom/participant";
const removeUserFromChatRoomUrl = process.env.REACT_APP_API_BASE_URL + "/message/api/chatroom/participant";

const ChatRoomAPI = {
  getAll,
  createRoom,
  getDetail,
  addUserToChatRoom,
  removeUserFromChatRoom
};

function getAll() {
  return axios.get(getAllUrl)
    .then(response => response.data);
};

function createRoom(data) {
  return axios.post(createRoomUrl, data)
    .then(response => response.data);
};

function getDetail(roomId) {
  let url = getDetailUrl + "/" + roomId;
  return axios.get(url)
    .then(response => response.data);
};

function addUserToChatRoom(roomId, data) {
  let url = addUserToChatRoomUrl + "/" + roomId;
  return axios.put(url, data)
    .then(response => response.data);
};

function removeUserFromChatRoom(roomId, data) {
  let url = removeUserFromChatRoomUrl + "/" + roomId;
  return axios.patch(url, data)
    .then(response => response.data);
};

export default ChatRoomAPI;