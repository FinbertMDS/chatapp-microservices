import { Avatar, IconButton, makeStyles, Menu, MenuItem, Modal, withStyles } from '@material-ui/core';
import ChatIcon from '@material-ui/icons/Chat';
import DonutLargeIcon from '@material-ui/icons/DonutLarge';
import MoreVertIcon from '@material-ui/icons/MoreVert';
import SearchIcon from '@material-ui/icons/Search';
import React, { useEffect, useRef, useState } from 'react';
import { Link, useHistory, useLocation } from 'react-router-dom';
import ChatRoomAPI from '../apis/ChatRoomAPI';
import ContactAPI from '../apis/ContactAPI';
import Const from '../constants/Const';
import { actionTypes } from '../reducer';
import { useStateValue } from '../StateProvider';
import './Sidebar.css';
import SidebarChat from './SidebarChat';
import UserList from './UserList';

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
  },
  paperModal: {
    position: 'absolute',
    width: '30%',
    backgroundColor: theme.palette.background.paper,
    border: '2px solid #000',
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
  },
}));

function getModalStyle() {
  const top = 50;
  const left = 50;

  return {
    top: `${top}%`,
    left: `${left}%`,
    transform: `translate(-${top}%, -${left}%)`,
  };
}

function Sidebar() {
  const [rooms, setRooms] = useState([]);
  const [{ user, contacts }, dispatch] = useStateValue();
  const classes = useStyles();

  useEffect(() => {
    ChatRoomAPI.getRoomForUser(user.username)
      .then(result => {
        setRooms(result);
      })
      .catch(error => alert(error.message));
  }, [user.username]);

  const [isOpenMoreButton, setIsOpenMoreButton] = useState(false);
  const moreButtonRef = useRef(null)
  const handleClick = (event) => {
    setIsOpenMoreButton(true);
  };

  const handleCloseMoreButton = () => {
    setIsOpenMoreButton(false);
  };

  const history = useHistory();

  const handleLogout = () => {
    handleCloseMoreButton()
    localStorage.removeItem("userInfo");
    // localStorage.removeItem("url");
    history.push("/");
    dispatch({
      type: actionTypes.SET_USER,
      user: null
    })
  }

  const handleGoToSettings = () => {
    handleCloseMoreButton();
    history.push("/settings");
  }

  const [input, setInput] = useState('');
  const searchRoom = (e) => {
    e.preventDefault();

    alert(input)
    setInput('')
  }

  const handleCreateChat = async (roomName) => {
    try {
      let roomData = {
        "name": roomName
      };
      const roomInfo = await ChatRoomAPI.createRoom(roomData);
      let participant = {
        "username": user.username
      };
      await ChatRoomAPI.addUserToChatRoom(roomInfo.id, participant);
      setRooms([
        ...rooms,
        roomInfo,
      ]);
    } catch (error) {
      alert(error.message)
    }
  }

  useEffect(() => {
    if (user.username) {
      ContactAPI.getContactForUser(user.username)
        .then(result => {
          dispatch({
            type: actionTypes.SET_CONTACTS,
            contacts: result
          });
        })
        .catch(error => alert(error.message));
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [user.username]);
  const [allContacts, setAllContacts] = useState([]);
  useEffect(() => {
    ContactAPI.getAll()
      .then(result => {
        setAllContacts(result);
      })
      .catch(error => alert(error.message));
  }, []);

  const [modalStyle] = useState(getModalStyle);
  const [isAddMember, setIsAddMember] = useState(false);
  const handleOpenModalAddMember = () => {
    handleCloseMoreButton();
    setIsAddMember(true);
  };

  const [isRemoveMember, setIsRemoveMember] = useState(false);
  const handleOpenModalRemoveMember = () => {
    handleCloseMoreButton();
    setIsRemoveMember(true);
  };

  const [isListMember, setIsListMember] = useState(false);
  const handleOpenModalListMember = () => {
    handleCloseMoreButton();
    setIsListMember(true);
  };

  const handleCloseModal = () => {
    handleCloseMoreButton();
    setIsAddMember(false);
    setIsRemoveMember(false);
    setIsListMember(false);
  };

  const handleAddContact = async (userList, checked) => {
    handleCloseMoreButton();
    if (checked.length > 0) {
      for (const index of checked) {
        let addUserContactRequest = {
          "username": userList[index].username
        };
        try {
          const result = await ContactAPI.createContactForUser(user.username, addUserContactRequest);
          dispatch({
            type: actionTypes.SET_CONTACTS,
            contacts: result
          })
        } catch (error) {
          alert(error.message)
        }
      }
    }
    handleCloseModal();
  };

  const handleRemoveContact = async (userList, checked) => {
    if (checked.length > 0) {
      for (const index of checked) {
        let addUserContactRequest = {
          "username": userList[index].username
        };
        try {
          const result = await ContactAPI.removeContactForUser(user.username, addUserContactRequest);
          dispatch({
            type: actionTypes.SET_CONTACTS,
            contacts: result
          })
        } catch (error) {
          alert(error.message)
        }
      }
    }
    handleCloseModal();
  };

  const bodyModalAddMember = (
    <div style={modalStyle} className={classes.paperModal}>
      <UserList data={allContacts}
        regList={[{ username: user.username }, ...contacts]}
        type={Const.USERLIST_TYPE.ADD_CONTACT.key}
        onCancel={handleCloseModal}
        onSave={handleAddContact}
      />
    </div>
  );

  const bodyModalRemoveMember = (
    <div style={modalStyle} className={classes.paperModal}>
      <UserList data={contacts}
        regList={[{ username: user.username }]}
        type={Const.USERLIST_TYPE.REMOVE_CONTACT.key}
        onCancel={handleCloseModal}
        onSave={handleRemoveContact}
      />
    </div>
  );

  const bodyModalListMember = (
    <div style={modalStyle} className={classes.paperModal}>
      <UserList data={contacts}
        type={Const.USERLIST_TYPE.LIST_CONTACT.key}
        onCancel={handleCloseModal}
      />
    </div>
  );

  const location = useLocation();

  if (location && location.pathname.includes("settings")) {
    return <></>
  }

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
          onClose={handleCloseMoreButton}
        >
          <MenuItem onClick={handleOpenModalAddMember}>Add contact</MenuItem>
          <MenuItem onClick={handleOpenModalRemoveMember}>Remove contact</MenuItem>
          <MenuItem onClick={handleOpenModalListMember}>List contact</MenuItem>
          <MenuItem onClick={handleGoToSettings}>Settings</MenuItem>
          <MenuItem onClick={handleLogout}>Logout</MenuItem>
        </StyledMenu>
      </div>
      <Modal
        open={isAddMember || isRemoveMember || isListMember}
        onClose={handleCloseModal}
        aria-labelledby="contact"
        aria-describedby="contact"
      >
        {
          isAddMember ? bodyModalAddMember : (
            isRemoveMember ? bodyModalRemoveMember : bodyModalListMember
          )
        }
      </Modal>
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
        <SidebarChat addNewChat onCreateChat={handleCreateChat} />
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
