import { Avatar } from '@material-ui/core';
import moment from "moment";
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import { useStateValue } from '../StateProvider';
import './SidebarChat.css';

function SidebarChat({ addNewChat, onCreateChat, roomInfo }) {
  const [seed, setSeed] = useState('');
  const [{ user, currentRoomId }] = useStateValue();

  useEffect(() => {
    setSeed(Math.floor(Math.random() * 5000));
  }, []);

  const createChat = () => {
    const roomName = prompt('please enter name for the chat room');
    if (roomName) {
      onCreateChat(roomName);
    }
  }

  const displayLastMessageDate = (date) => {
    if (date) {
      if (moment(date).isSame(moment(), 'day')) {
        return moment(date).format('HH:mm');
      } else {
        return moment(date).format('dddd');
      }
    }
    return '';
  }

  const displayLastMessageFromUser = (fromUser) => {
    if (fromUser && user) {
      if (fromUser === user.username) {
        return 'You';
      } else {
        return fromUser;
      }
    }
    return '';
  }

  return !addNewChat ? (
    <Link to={`/rooms/${roomInfo.id}`}>
      <div className={`sidebarChat ${roomInfo.id === currentRoomId && "current"}`}>
        <Avatar src={`https://avatars.dicebear.com/api/human/${seed}.svg`} />
        <div className='sidebarChat__info'>
          <h2>{roomInfo.name}</h2>
          {
            roomInfo.lastMessage && (
              <div className='sidebarChat__info__lastmessage'>
                <div className="text">{displayLastMessageFromUser(roomInfo.lastMessage?.fromUser)}: <span>{roomInfo.lastMessage?.text} </span></div>
                &nbsp;<span aria-hidden="true">·</span>&nbsp;
                {`${displayLastMessageDate(roomInfo.lastMessage?.createdAt)}`}
              </div>
            )
          }
        </div>
      </div>
    </Link>
  ) : (
      <div onClick={createChat} className='sidebarChat'>
        <h2>Add new chat</h2>
      </div>
    )
}

export default SidebarChat;
