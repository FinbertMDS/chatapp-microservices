import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import "./App.css";
import Chat from "./Components/Chat";
import Notification from "./Components/Notification";
import Sidebar from "./Components/Sidebar";
import SignIn from "./Components/SignIn";
import useDeviceDetect from "./helpers/useDeviceDetect";
import { actionTypes } from "./reducer";
import { useStateValue } from "./StateProvider";

function App() {
  const [{ user }, dispatch] = useStateValue();
  const [messages] = useState([]);
  const { isMobile } = useDeviceDetect();
  const [{ currentRoomId }] = useStateValue();

  useEffect(() => {
    let userInfo = localStorage.getItem("userInfo");
    if (userInfo) {
      try {
        userInfo = JSON.parse(userInfo);
        dispatch({
          type: actionTypes.SET_USER,
          user: userInfo
        });
      } catch (error) {
        alert(error.message);
        localStorage.removeItem("userInfo");
      }
    }
    // eslint-disable-next-line react-hooks/exhaustive-deps
  }, []);

  const getHiddenState = () => {
    if (isMobile) {
      if (currentRoomId) {
        return 'hidden_sidebar';
      } else {
        return 'hidden_chat';
      }
    }
    return '';
  }

  useEffect(() => {
    // axios.get("./messages/sync").then((response) => {
    //   setMessages(response.data);
    // });
  }, []);

  useEffect(() => {
    // const pusher = new Pusher("d76e780f6077ae259761", {
    //   cluster: "us2",
    // });

    // const channel = pusher.subscribe("messages");
    // channel.bind("inserted", function (data) {
    //   alert(JSON.stringify(data));
    //   setMessages([...messages, data]);
    // });

    // return () => {
    //   channel.unbind_all();
    //   channel.unsubscribe();
    // };
  }, [messages]);

  // console.log(messages);
  return (
    <div className={`app ${isMobile ? 'mobile' : ''}`}>
      {!user ? (
        <SignIn />
      ) : (
          <div className={`app__body ${getHiddenState()}`}>
            <Router>
              <Sidebar />
              <Switch>
                <Route path="/rooms/:roomId">
                  <Chat />
                </Route>
              </Switch>
            </Router>
          </div>
        )}
      <Notification />
    </div>
  );
}

export default App;
