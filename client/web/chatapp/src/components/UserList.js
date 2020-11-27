import { Avatar, Button, IconButton } from '@material-ui/core';
import Checkbox from '@material-ui/core/Checkbox';
import List from '@material-ui/core/List';
import ListItem from '@material-ui/core/ListItem';
import ListItemAvatar from '@material-ui/core/ListItemAvatar';
import ListItemSecondaryAction from '@material-ui/core/ListItemSecondaryAction';
import ListItemText from '@material-ui/core/ListItemText';
import { makeStyles } from '@material-ui/core/styles';
import SearchIcon from '@material-ui/icons/Search';
import React, { useEffect, useState } from 'react';
import ContactAPI from '../apis/ContactAPI';
import Const from '../constants/Const';
import './UserList.css';

const useStyles = makeStyles((theme) => ({
  root: {
    width: '100%',
    maxWidth: 360,
    backgroundColor: theme.palette.background.paper,
  },
}));

function UserList({ data = [], regList = [], type, onCancel, onSave }) {
  const [userList, setUserList] = useState(data);
  const canSearchUser = Const.USERLIST_TYPE[type].canSearchUser;
  const canSearchOnline = Const.USERLIST_TYPE[type].canSearchOnline;
  const showCheckbox = Const.USERLIST_TYPE[type].showCheckbox;
  const hasSaveAction = onSave && Const.USERLIST_TYPE[type].hasSaveAction;
  const classes = useStyles();
  const [checked, setChecked] = useState([]);

  useEffect(() => {
    updateUserListData(userList, regList)
  }, [])

  const updateUserListData = (data, regList) => {
    if (data.length > 0) {
      if (regList.length > 0) {
        let userListData = data.filter(function (objFromA) {
          return !regList.find(function (objFromB) {
            return objFromA.username === objFromB.username
          })
        })
        setUserList(userListData);
      } else {
        setUserList(data);
      }
    } else {
      setUserList([]);
    }
  }

  const handleToggle = (value) => () => {
    if (!showCheckbox) {
      return;
    }
    const currentIndex = checked.indexOf(value);
    const newChecked = [...checked];

    if (currentIndex === -1) {
      newChecked.push(value);
    } else {
      newChecked.splice(currentIndex, 1);
    }

    setChecked(newChecked);
  };

  const getAvatarText = (text) => {
    return String(text).charAt(0).toUpperCase();
  }

  const [input, setInput] = useState('');
  const searchUser = (e) => {
    e.preventDefault();
    if (canSearchOnline) {
      ContactAPI.searchContact(input)
        .then(result => {
          updateUserListData(result, regList)
        })
        .catch(error => alert(error.message));
    } else {
      searchUserFromUserList(data, input)
    }
  }

  const searchUserFromUserList = (userList = [], query) => {
    let userListData = userList.filter(function (userInfo) {
      return userInfo.username.includes(query);
    })
    updateUserListData(userListData, regList)
  }

  return (
    <>
      <h4 className='modal__header'>
        <span className='modal__header_left'>
          <Button onClick={onCancel}>Cancel</Button>
        </span>
        <div className='modal__header__content'>{Const.USERLIST_TYPE[type].title}</div>
        {
          hasSaveAction && userList.length > 0 && (
            <span className='modal__header_right'>
              <Button onClick={() => onSave(userList, checked)}>Save</Button>
            </span>
          )
        }
      </h4>
      {
        canSearchUser && (
          <div className='user__search'>
            <div className='user__searchContainer'>
              <IconButton>
                <SearchIcon />
              </IconButton>
              <form noValidate onSubmit={searchUser}>
                <input
                  placeholder='Search user'
                  type='text'
                  value={input}
                  onChange={(e) => setInput(e.target.value)}
                />
                <button onClick={searchUser} type='submit'>Search user</button>
              </form>
            </div>
          </div>
        )
      }
      {
        userList && userList.length > 0 && (
          <List dense className={classes.root}>
            {userList.map((userInfo, index) => {
              const labelId = `checkbox-list-secondary-label-${userInfo.username}`;
              return (
                <ListItem key={userInfo.username} role={undefined} dense button onClick={handleToggle(index)}>
                  <ListItemAvatar>
                    <Avatar>{getAvatarText(userInfo.username)}</Avatar>
                  </ListItemAvatar>
                  <ListItemText id={labelId} primary={userInfo.username} />
                  {
                    showCheckbox &&
                    <ListItemSecondaryAction>
                      <Checkbox
                        edge="end"
                        onChange={handleToggle(index)}
                        checked={checked.indexOf(index) !== -1}
                        inputProps={{ 'aria-labelledby': labelId }}
                      />
                    </ListItemSecondaryAction>
                  }
                </ListItem>
              );
            })}
          </List>
        )
      }
    </>
  );
}
export default UserList;