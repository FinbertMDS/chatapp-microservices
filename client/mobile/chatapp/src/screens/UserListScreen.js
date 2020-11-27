import { useNavigation, useRoute } from '@react-navigation/native';
import React, { useEffect, useState } from 'react';
import { FlatList, ImageBackground, Text, View } from 'react-native';
import { TouchableWithoutFeedback } from 'react-native-gesture-handler';
import ContactAPI from '../apis/ContactAPI';
import BG from '../assets/images/BG.png';
import ContactListItem from '../components/ContactListItem';
import Const from '../constants/Const';

function UserListScreen() {
  const route = useRoute();
  const navigation = useNavigation();
  let data = route.params.data, regList = route.params.regList,
    type = route.params.type, onSave = route.params.onSave;
  const [userList, setUserList] = useState(data);
  const canSearchUser = Const.USERLIST_TYPE[type].canSearchUser;
  const canSearchOnline = Const.USERLIST_TYPE[type].canSearchOnline;
  const showCheckbox = Const.USERLIST_TYPE[type].showCheckbox;
  const hasSaveAction = onSave && Const.USERLIST_TYPE[type].hasSaveAction;
  const [checked, setChecked] = useState([]);

  useEffect(() => {
    updateUserListData(userList, regList)
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, [])

  useEffect(() => {
    navigation.setOptions({ 
      title: Const.USERLIST_TYPE[type].title,
    })
  }, [])

  useEffect(() => {
    navigation.setOptions({ 
      headerRight: () => (
        <View style={{
          marginRight: 10,
        }}>
          <TouchableWithoutFeedback disabled={getStatusSaveButton()} onPress={() => onSave(userList, checked)}>
            <Text style={{ color: getStatusSaveButton() ? "#609D9E" : "white" }}>Save</Text>
          </TouchableWithoutFeedback>
        </View>
      ),
    })
  }, [userList, checked])

  const getStatusSaveButton = () => {
    return !hasSaveAction || userList.length === 0 || checked.length === 0;
  }

  const updateUserListData = (data, regList) => {
    if (data && data.length > 0) {
      if (regList && regList.length > 0) {
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

  const handleToggle = (value) => {
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
      <ImageBackground style={{ width: '100%', height: '100%' }} source={BG}>
        <FlatList
          style={{ width: '100%' }}
          data={userList}
          renderItem={({ item, index }) =>
            <ContactListItem user={item} onClickItem={()=> handleToggle(index)} showCheckbox={showCheckbox} checked={checked.indexOf(index) !== -1} />
          }
          keyExtractor={(item) => item.username}
        />
      </ImageBackground>
    </>
  );
}
export default UserListScreen;