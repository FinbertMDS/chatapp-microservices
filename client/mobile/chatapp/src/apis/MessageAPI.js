import { SOCKET_BASE_URL } from "@env";
import Config from "../constants/Config";
import axios from "./axios";

const getAllMessageInRoomUrl = "/message/api/messages";
const sendMessageUrl = "/message/api/messages";

const MessageAPI = {
  wsSourceUrl: SOCKET_BASE_URL,

  getPublicMessageTopicUrl: function (roomId) {
    return "/topic/" + roomId + ".public.messages"
  },

  getPrivateMessageTopicUrl: function (roomId) {
    return "/user/queue/" + roomId + ".private.messages"
  },

  getReplyMessageTopicUrl: function () {
    return "/user/queue/reply"
  },

  getReplyMessageFCMTopicUrl: function (username) {
    return `chatapp_${username}_reply`
  },

  getAllMessageInRoom: function (roomId, forUser) {
    let url = getAllMessageInRoomUrl + "/" + roomId + "?forUser=" + forUser;
    return axios.get(url)
      .then(response => response.data);
  },

  getMessageInRoom: function (roomId, forUser, page = 0, size = Config.DEFAULT_PAGE_SIZE) {
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