import { Avatar, IconButton, makeStyles, Menu, MenuItem, withStyles } from '@material-ui/core';
import ChatIcon from '@material-ui/icons/Chat';
import DonutLargeIcon from '@material-ui/icons/DonutLarge';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import SearchIcon from '@material-ui/icons/Search';
import React, { useEffect, useRef, useState } from 'react';
import { Link, useHistory } from 'react-router-dom';
import ChatRoomAPI from '../apis/ChatRoomAPI';
import { actionTypes } from '../reducer';
import { useStateValue } from '../StateProvider';
import './Sidebar.css';
import SidebarChat from './SidebarChat';

const StyledMenu = withStyles({
  paper: {
    border: '1px solid #d3d4d5',
  },
})((props) => (
  <Menu
    elevation={0}
    getContentAnchorEl={null}
    anchorOrigin={{
      vertical: 'bottom',
      horizontal: 'center',
    }}
    transformOrigin={{
      vertical: 'top',
      horizontal: 'center',
    }}
    {...props}
  />
));

const useStyles = makeStyles((theme) => ({
  triangleBottom: {
    position: "relative",
    "&::before,&::after": {
      content: "''",
      position: "absolute",
      bottom: 0,
      top: '32px',
      borderColor: "transparent",
      borderStyle: "solid"
    },
    "&::before": {
      borderWidth: "8px",
      // borderLeftColor: "#000000",
      borderBottomColor: "#000000"
    },
    "&::after": {
      borderRadius: "3px",
      borderWidth: "5px",
      borderLeftColor: "#fffff" /* color of the triangle */,
      borderBottomColor: "#fffff" /* color of the triangle */
    }
  }
}));

function Sidebar() {
  const [rooms, setRooms] = useState([]);
  const [{ user }, dispatch] = useStateValue();

  useEffect(() => {
    ChatRoomAPI.getAll()
      .then(result => {
        setRooms(result);
      })
      .catch(error => alert(error.message));
  }, []);

  const [isOpenMoreButton, setIsOpenMoreButton] = useState(false);
  const moreButtonRef = useRef(null)
  const handleClick = (event) => {
    setIsOpenMoreButton(true);
  };

  const handleClose = () => {
    setIsOpenMoreButton(false);
  };

  const history = useHistory();

  const handleLogout = () => {
    setIsOpenMoreButton(false);
    localStorage.removeItem("userInfo");
    history.push("/");
    dispatch({
      type: actionTypes.SET_USER,
      user: null
    })
  }

  const [input, setInput] = useState('');
  const searchRoom = (e) => {
    e.preventDefault();

    alert(input)
    setInput('')
  }

  const handleCreateChat = (roomName) => {
    let roomData = {
      "name": roomName
    };
    ChatRoomAPI.createRoom(roomData)
      .then(result => {
        setRooms([
          ...rooms,
          result,
        ]);
        // console.log(result);
      })
      .catch(error => alert(error.message));
  }

  const classes = useStyles();

  return (
    <div className='sidebar'>
      <div className='sidebar__header'>
        <Link to={`/`}>
          <Avatar src={user?.photoURL} />
        </Link>
        <div className='sidebar__headerRight'>
          <IconButton>
            <DonutLargeIcon />
          </IconButton>
          <IconButton>
            <ChatIcon />
          </IconButton>
          <IconButton ref={moreButtonRef} onClick={handleClick} className={isOpenMoreButton ? classes.triangleBottom : ""}>
            <MoreVertIcon />
          </IconButton>
        </div>
        <StyledMenu
          id="simple-menu"
          anchorEl={moreButtonRef.current}
          keepMounted
          open={isOpenMoreButton}
          onClose={handleClose}
        >
          <MenuItem onClick={handleLogout}>Logout</MenuItem>
        </StyledMenu>
      </div>
      <div className='sidebar__search'>
        <div className='sidebar__searchContainer'>
          <IconButton>
            <SearchIcon />
          </IconButton>
          <form>
            <input
              placeholder='Search or start new chat'
              type='text'
              value={input}
              onChange={(e) => setInput(e.target.value)}
            />
            <button onClick={searchRoom} type='submit'>Search room</button>
          </form>
        </div>
      </div>
      <div className='sidebar__chats'>
        <SidebarChat addNewChat onCreateChat={handleCreateChat}/>
        {rooms && rooms.map(room => room && (
          <SidebarChat
            key={room.id}
            roomInfo={room}
          />
        ))}

      </div>
    </div>
  )
}

export default Sidebar;
