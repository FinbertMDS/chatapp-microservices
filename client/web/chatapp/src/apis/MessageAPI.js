import axios from "./axios";

const getAllMessageInRoomUrl = "./message/api/messages";
const sendMessageUrl = "./message/api/messages";

const MessageAPI = {
  getAllMessageInRoom: function (roomId, forUser) {
    let url = getAllMessageInRoomUrl + "/" + roomId + "?forUser=" + forUser;
    return axios.get(url)
      .then(response => response.data);
  },
  sendPublicMessage: function (roomId, data) {
    let url = sendMessageUrl + "/" + roomId;
    return axios.post(url, data)
      .then(response => response.data);
  }
};

export default MessageAPI;