import { Avatar } from '@material-ui/core';
import React, { useEffect, useState } from 'react';
import { Link } from 'react-router-dom';
import ChatRoomAPI from '../apis/ChatRoomAPI';
import { useStateValue } from '../StateProvider';
import './SidebarChat.css';

function SidebarChat({ addNewChat, id, name }) {
  const [seed, setSeed] = useState('');
  const [messages] = useState('')
  const [{ currentRoomId }] = useStateValue();

  useEffect(() => {
    setSeed(Math.floor(Math.random() * 5000));

    // if (id) {
    //   MessageAPI.getAllMessageInRoom(id, user.username)
    //     .then(result => {
    //       setMessages(result);
    //     })
    //     .catch(error => alert(error.message));
    // }
  }, [id]);

  const createChat = () => {
    const roomName = prompt('please enter name for the chat room');
    if (roomName) {
      let roomData = {
        "name": roomName
      };
      ChatRoomAPI.createRoom(roomData)
        .then(result => {
          console.log(result);
        })
        .catch(error => alert(error.message));
    }
  }
  return !addNewChat ? (
    <Link to={`/rooms/${id}`}>
      <div className={`sidebarChat ${id === currentRoomId && "current"}`}>
        <Avatar src={`https://avatars.dicebear.com/api/human/${seed}.svg`} />
        <div className='sidebarChat__info'>
          <h2>{name}</h2>
          <p>{messages[0]?.text}</p>
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
