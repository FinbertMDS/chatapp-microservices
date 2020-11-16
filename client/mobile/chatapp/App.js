import PushNotificationIOS from "@react-native-community/push-notification-ios";
import messaging from '@react-native-firebase/messaging';
import React, { useEffect } from "react";
import { Platform, StatusBar, useColorScheme } from "react-native";
import { Provider as PaperProvider } from "react-native-paper";
import PushNotification from "react-native-push-notification";
import { SafeAreaProvider } from 'react-native-safe-area-context';
import reducer, { initialState } from "./reducer";
import Config from "./src/constants/Config";
import StackScreenName from "./src/constants/StackScreenName";
import { theme } from "./src/core/theme";
import ObjectHelper from "./src/helpers/ObjectHelper";
import useCachedResources from "./src/hooks/useCachedResources";
import Navigation from "./src/navigation";
import RootNavigation from "./src/navigation/RootNavigation";
import { StateProvider } from "./StateProvider";

// Must be outside of any component LifeCycle (such as `componentDidMount`).
PushNotification.configure({
  // (optional) Called when Token is generated (iOS and Android)
  onRegister: function (token) {
    console.log("TOKEN:", token);
  },

  // (required) Called when a remote is received or opened, or local notification is opened
  onNotification: function (notification) {
    console.log("NOTIFICATION:", notification);

    // process the notification
    let message = notification.data;
    ObjectHelper.clean(message);
    if (notification.userInteraction) {
      if (message.chatRoomId) {
        new Promise(resolve => setTimeout(resolve, 2000)).then(() => {
          RootNavigation.navigate(StackScreenName.ChatRoom, {
            id: message.chatRoomId,
            name: message.chatRoomName,
          });
        });
      }
    } else {
      // PushNotification.localNotification({
      //   channelId: Config.ANDROID_CHANNEL_ID, // (required for Android)
      //   title: `[${message.chatRoomName}] ${message.fromUser}`, // (optional)
      //   message: message.text, // (required)
      //   userInfo: message,
      // });
    }

    // (required) Called when a remote is received or opened, or local notification is opened
    notification.finish(PushNotificationIOS.FetchResult.NoData);
  },

  // (optional) Called when Registered Action is pressed and invokeApp is false, if true onNotification will be called (Android)
  onAction: function (notification) {
    console.log("ACTION:", notification.action);
    console.log("NOTIFICATION:", notification);

    // process the action
  },

  // (optional) Called when the user fails to register for remote notifications. Typically occurs when APNS is having issues, or the device is a simulator. (iOS)
  onRegistrationError: function (err) {
    console.error(err.message, err);
  },

  // IOS ONLY (optional): default: all - Permissions to register.
  permissions: {
    alert: true,
    badge: true,
    sound: true,
  },

  // Should the initial notification be popped automatically
  // default: true
  popInitialNotification: true,

  /**
   * (optional) default: true
   * - Specified if permissions (ios) and token (android and ios) will requested or not,
   * - if not, you must call PushNotificationsHandler.requestPermissions() later
   * - if you are not using remote notification or do not have Firebase installed, use this:
   *     requestPermissions: Platform.OS === 'ios'
   */
  requestPermissions: Platform.OS === 'ios',
});

if (Platform.OS === 'android') {
  PushNotification.createChannel(
    {
      channelId: Config.ANDROID_CHANNEL_ID, // (required)
      channelName: "Chat App channel", // (required)
      channelDescription: "A channel to categorise your notifications", // (optional) default: undefined.
      soundName: "default", // (optional) See `soundName` parameter of `localNotification` function
      importance: 4, // (optional) default: 4. Int value of the Android notification importance
      vibrate: true, // (optional) default: true. Creates the default vibration patten if true.
    },
    (created) => console.log(`createChannel returned '${created}'`) // (optional) callback returns whether the channel was created, false means it already existed.
  );
}

async function requestUserPermission() {
  const authStatus = await messaging().requestPermission();
  const enabled =
    authStatus === messaging.AuthorizationStatus.AUTHORIZED ||
    authStatus === messaging.AuthorizationStatus.PROVISIONAL;

  if (enabled) {
    console.log('Authorization status:', authStatus);
  }
}

function App() {
  const isLoadingComplete = useCachedResources();
  const colorScheme = useColorScheme();

  useEffect(() => {
    if (Platform.OS === 'ios') {
      requestUserPermission();
    }
  }, [])

  useEffect(() => {
    const fetchUser = async () => {
      // const userInfo = await Auth.currentAuthenticatedUser({ bypassCache: true });

      // if (userInfo) {
      //   const userData = await API.graphql(
      //     graphqlOperation(
      //       getUser,
      //       { id: userInfo.attributes.sub }
      //       )
      //   )

      //   if (userData.data.getUser) {
      //     console.log("User is already registered in database");
      //     return;
      //   }

      //   const newUser = {
      //     id: userInfo.attributes.sub,
      //     name: userInfo.username,
      //     imageUri: getRandomImage(),
      //     status: 'Hey, I am using WhatsApp',
      //   }

      //   await API.graphql(
      //     graphqlOperation(
      //       createUser,
      //       { input: newUser }
      //     )
      //   )
      // }
    }

    fetchUser();
  }, [])

  return (
    <SafeAreaProvider>
      <StateProvider initialState={initialState} reducer={reducer}>
        <PaperProvider theme={theme}>
          <Navigation colorScheme={colorScheme} />
          <StatusBar />
        </PaperProvider>
      </StateProvider>
    </SafeAreaProvider>
  );
}

export default App;
