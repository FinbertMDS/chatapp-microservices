import React, { useEffect, useState } from "react";
import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import "./App.css";
import Chat from "./Components/Chat";
import Notification from "./Components/Notification";
import Sidebar from "./Components/Sidebar";
import SignIn from "./Components/SignIn";
import { actionTypes } from "./reducer";
import { useStateValue } from "./StateProvider";

function App() {
  const [{ user }, dispatch] = useStateValue();
  const [messages, /* setMessages */] = useState([]);

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
    // eslint-disable-next-line
  }, []);

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
    <div className="app">
      {!user ? (
        <SignIn />
      ) : (
          <div className="app__body">
            <Router>
              <Sidebar />
              <Switch>
                <Route path="/rooms/:roomId">
                  <Chat />
                </Route>
                <Route path="/app">
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
