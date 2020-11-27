/**
 * @format
 */

import React from 'react';
import { AppRegistry, LogBox } from 'react-native';
import App from './App';
import { name as appName } from './app.json';

// messaging().setBackgroundMessageHandler(async remoteMessage => {
//   console.log('Message handled in the background!', remoteMessage);
// });
LogBox.ignoreLogs([
  'Non-serializable values were found in the navigation state',
  // See: https://github.com/react-navigation/react-navigation/issues/7839
  'Sending \`onAnimatedValueUpdate\` with no listeners registered.',
]);

function HeadlessCheck({ isHeadless }) {
  if (isHeadless) {
    // App has been launched in the background by iOS, ignore
    return null;
  }

  return <App />;
}

AppRegistry.registerComponent(appName, () => HeadlessCheck);
