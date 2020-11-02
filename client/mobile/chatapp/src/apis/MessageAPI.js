import axios from "./axios";

const getAllMessageInRoomUrl = "/message/api/messages";
const sendMessageUrl = "/message/api/messages";

const MessageAPI = {
  wsSourceUrl: "http://10.0.2.2:8079/ws"/* process.env.SOCKET_BASE_URL */,

  getPublicMessageTopicUrl: function (roomId) {
    return "/topic/" + roomId + ".public.messages"
  },

  getPrivateMessageTopicUrl: function (roomId) {
    return "/user/queue/" + roomId + ".private.messages"
  },

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