import axios from "axios";

const getAllMessageInRoomUrl = process.env.REACT_APP_API_BASE_URL + "/message/api/messages";
const sendMessageUrl = process.env.REACT_APP_API_BASE_URL + "/message/api/messages";

const MessageAPI = {
  getAllMessageInRoom,
  sendPublicMessage
};

function getAllMessageInRoom(roomId, forUser) {
  let url = getAllMessageInRoomUrl + "/" + roomId + "?forUser=" + forUser;
  return axios.get(url)
    .then(response => response.data);
};

function sendPublicMessage(roomId, data) {
  let url = sendMessageUrl + "/" + roomId;
  return axios.post(url, data)
    .then(response => response.data);
};

export default MessageAPI;