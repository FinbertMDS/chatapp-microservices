import Config from "../constants/Config";
import axios from "./axios";

const getAllMessageInRoomUrl = "./message/api/messages";
const sendMessageUrl = "./message/api/messages";

const MessageAPI = {
  wsSourceUrl: process.env.REACT_APP_SOCKET_BASE_URL,

  updateUrl: function () {
    let urlOld;
    try {
      urlOld = localStorage.getItem('url');
      if (urlOld != null) {
        this.wsSourceUrl = `${urlOld}:8079/ws`
      }
    } catch (e) {
    }
  },

  getPublicMessageTopicUrl: function (roomId) {
    return "/topic/" + roomId + ".public.messages"
  },

  getPrivateMessageTopicUrl: function (roomId) {
    return "/user/queue/" + roomId + ".private.messages"
  },

  getReplyMessageTopicUrl: function () {
    return "/user/queue/reply"
  },

  getAllMessageInRoom: function (roomId, forUser) {
    let url = getAllMessageInRoomUrl + "/" + roomId + "?forUser=" + forUser;
    return axios.get(url)
      .then(response => response.data);
  },

  getMessageInRoom: function (roomId, forUser, page = 0, size = Config.DEFAULT_PAGE_SIZE_MESSAGE) {
    let url = `${getAllMessageInRoomUrl}/${roomId}?forUser=${forUser}&page=${page}&size=${size}`;
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