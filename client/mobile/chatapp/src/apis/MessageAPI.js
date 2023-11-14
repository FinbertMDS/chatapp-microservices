import { SOCKET_BASE_URL, SOCKET_BASE_URL_ANDROID } from "@env";
import Config from "../constants/Config";
import axios from "./axios";
import { Platform } from 'react-native';

const getAllMessageInRoomUrl = "/message/api/messages";
const sendMessageUrl = "/message/api/messages";

const sockeBaseURL = Platform.OS === "android" ? SOCKET_BASE_URL_ANDROID : SOCKET_BASE_URL

const MessageAPI = {
  wsSourceUrl: sockeBaseURL,

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