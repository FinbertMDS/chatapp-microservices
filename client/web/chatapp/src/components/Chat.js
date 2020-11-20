import { Avatar, Button, IconButton } from '@material-ui/core';
import ArrowBackIosIcon from '@material-ui/icons/ArrowBackIos';
import AttachFileIcon from '@material-ui/icons/AttachFile';
import InsertEmoticonIcon from '@material-ui/icons/InsertEmoticon';
import MicIcon from '@material-ui/icons/Mic';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import SearchIcon from '@material-ui/icons/Search';
import React, { useEffect, useRef, useState } from 'react';
import { useHistory, useParams } from 'react-router-dom';
import SockJsClient from "react-stomp";
import ChatRoomAPI from '../apis/ChatRoomAPI';
import MessageAPI from '../apis/MessageAPI';
import useDeviceDetect from '../helpers/useDeviceDetect';
import { actionTypes } from '../reducer';
import { useStateValue } from '../StateProvider';
import './Chat.css';

function Chat() {
  const [seed, setSeed] = useState('');
  const [input, setInput] = useState('');
  const { roomId } = useParams();
  const [roomName, setRoomName] = useState('');
  const [roomDetail, setRoomDetail] = useState(null);
  const [messages, setMessages] = useState([]);
  const [{ user }, dispatch] = useStateValue();
  const { isMobile } = useDeviceDetect();
  const history = useHistory();

  useEffect(() => {
    setSeed(Math.floor(Math.random() * 5000));
    if (roomId) {
      ChatRoomAPI.getDetail(roomId)
        .then(result => {
          setRoomName(result.name);
          setRoomDetail(result);
        })
        .catch(error => alert(error.message));

      MessageAPI.getAllMessageInRoom(roomId, user.username)
        .then(result => {
          setMessages(result);
        })
        .catch(error => alert(error.message));
    }
  }, [roomId, user.username]);

  useEffect(() => {
    if (roomId && roomDetail) {
      if (roomId !== roomDetail.id) {
        return;
      }
      if (roomDetail.connectedUsers) {
        if (roomDetail.connectedUsers.filter(e => e.username === user.username).length <= 0) {
          let participant = {
            "username": user.username
          };
          ChatRoomAPI.addUserToChatRoom(roomId, participant)
            .catch(error => alert(error.message));
        }
      }
    }
  }, [roomDetail, roomId, user.username]);

  // useEffect(() => {
  //   let participant = {
  //     "username": user.username
  //   };
  //   if (roomId) {
  //     ChatRoomAPI.addUserToChatRoom(roomId, participant)
  //       .catch(error => alert(error.message));
  //   }
  //   return () => {
  //     if (roomId) {
  //       ChatRoomAPI.removeUserFromChatRoom(roomId, participant)
  //         .catch(error => alert(error.message));
  //     }
  //   };
  // }, [roomId, user.username]);

  useEffect(() => {
    dispatch({
      type: actionTypes.SET_CURRENT_ROOM_ID,
      currentRoomId: roomId
    });
    return () => {
      dispatch({
        type: actionTypes.SET_CURRENT_ROOM_ID,
        currentRoomId: null
      });
    };
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [roomId]);

  const sendMessage = (e) => {
    e.preventDefault();
    setInput('');
    let messageData = {
      "fromUser": user.username,
      "text": input
    };
    MessageAPI.sendPublicMessage(roomId, messageData)
      .catch(error => alert(error.message));
  }

  const messagesEndRef = useRef(null)

  const scrollToBottom = () => {
    messagesEndRef.current.scrollIntoView();
  }
  useEffect(scrollToBottom, [messages]);

  let customHeaders = {};
  if (user && user.accessToken) {
    customHeaders = {
      Authorization: "Bearer " + user.accessToken
    };
  }
  const publicTopicStr = MessageAPI.getPublicMessageTopicUrl(roomId);
  // const replyUserStr = MessageAPI.getReplyMessageTopicUrl();
  const [/* clientRef */, setClientRef] = useState(null);
  const [/* clientConnected */, setClientConnected] = useState(false);
  const onMessageReceive = (msg, topic) => {
    setMessages([
      ...messages,
      msg
    ])
  }
  // const onReplyReceive = (msg, topic) => {
  //   console.log(msg);
  // }

  // useEffect(() => {
  //   const onbeforeunloadFn = () => {
  //     let participant = {
  //       "username": user.username
  //     };
  //     ChatRoomAPI.removeUserFromChatRoom(roomId, participant)
  //       .catch(error => alert(error.message));
  //   }

  //   window.addEventListener('beforeunload', onbeforeunloadFn);
  //   return () => {
  //     window.removeEventListener('beforeunload', onbeforeunloadFn);
  //   }
  //   // eslint-disable-next-line react-hooks/exhaustive-deps
  // }, [])

  // const handleDisconnect = () => {
  //   let participant = {
  //     "username": user.username
  //   };
  //   ChatRoomAPI.removeUserFromChatRoom(roomId, participant)
  //     .catch(error => alert(error.message));
  // }  

  return (
    <div className='chat'>
      <div className='chat__header'>
        {
          isMobile && (
            <Button
              onClick={() => history.push("/")}
            >
              <IconButton>
                <ArrowBackIosIcon />
              </IconButton>
            </Button>
          )
        }
        <Avatar src={`https://avatars.dicebear.com/api/human/${seed}.svg`} />
        <div className='chat__headerInfo'>
          <h3>{roomName}</h3>
          {
            !isMobile && messages.length > 0 && (
              <p>
                Last seen {' '}
                {new Date(messages[messages.length - 1]?.date).toLocaleString()}
              </p>
            )
          }
        </div>
        <div className='chat__headerRight'>
          <IconButton>
            <SearchIcon />
          </IconButton>
          <IconButton>
            <AttachFileIcon />
          </IconButton>
          <IconButton>
            <MoreVertIcon />
          </IconButton>
        </div>
      </div>
      <div className='chat__body'>
        {messages && messages.map(message => {
          if (!message.isNotification) {
            return (
              <p className={`chat__message ${message.fromUser === user.username && "chat__reciever"}`} key={message.date}>
                <span className='chat__name'>{message.fromUser}</span>
                {message.text}
                <span className='chat__timestamp'>{new Date(message.date).toLocaleString()}</span>
              </p>
            );
          } else {
            return "";
          }
        })}
        <div ref={messagesEndRef} />
      </div>
      <div className='chat__footer'>
        <IconButton>
          <InsertEmoticonIcon />
        </IconButton>
        <form noValidate onSubmit={sendMessage}>
          <input
            placeholder='Type a message'
            type='text'
            value={input}
            onChange={(e) => setInput(e.target.value)}
          />
          <button onClick={sendMessage} type='submit'>Send a message</button>
        </form>
        <IconButton>
          <MicIcon />
        </IconButton>
      </div>
      <SockJsClient
        url={MessageAPI.wsSourceUrl}
        topics={[publicTopicStr]}
        headers={customHeaders}
        onMessage={onMessageReceive} ref={(client) => { setClientRef(client) }}
        onConnect={() => { setClientConnected(true) }}
        onDisconnect={() => { setClientConnected(false) }}
        debug={false} />
      {/* <SockJsClient
        url={MessageAPI.wsSourceUrl}
        topics={[replyUserStr]}
        headers={customHeaders}
        onMessage={onReplyReceive}
        debug={false} /> */}
    </div>
  )
}

export default Chat;
