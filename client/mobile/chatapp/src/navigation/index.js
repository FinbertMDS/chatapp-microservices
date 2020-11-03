import AsyncStorage from '@react-native-async-storage/async-storage';
import { DarkTheme, DefaultTheme, NavigationContainer } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import React, { useEffect, useState } from 'react';
import { View } from 'react-native';
import FontAwesome5 from "react-native-vector-icons/FontAwesome5";
import MaterialCommunityIcons from "react-native-vector-icons/MaterialCommunityIcons";
import MaterialIcons from "react-native-vector-icons/MaterialIcons";
import Octicons from "react-native-vector-icons/Octicons";
import { actionTypes } from '../../reducer';
import { useStateValue } from '../../StateProvider';
import { Text } from '../components/Themed';
import Colors from "../constants/Colors";
import StackScreenName from '../constants/StackScreenName';
import ChatRoomScreen from '../screens/ChatRoomScreen';
import ContactsScreen from "../screens/ContactsScreen";
import NotFoundScreen from '../screens/NotFoundScreen';
import SettingScreen from '../screens/SettingScreen';
import SignInScreen from '../screens/SignInScreen';
import SignUpScreen from '../screens/SignUpScreen';
import SplashScreen from '../screens/SplashScreen';
import LinkingConfiguration from './LinkingConfiguration';
import MainTabNavigator from './MainTabNavigator';


// If you are not familiar with React Navigation, we recommend going through the
// "Fundamentals" guide: https://reactnavigation.org/docs/getting-started
export default function Navigation({ colorScheme }) {
  return (
    <NavigationContainer
      linking={LinkingConfiguration}
      theme={colorScheme === 'dark' ? DarkTheme : DefaultTheme}>
      <RootNavigator />
    </NavigationContainer>
  );
}

// A root stack navigator is often used for displaying modals on top of all other content
// Read more here: https://reactnavigation.org/docs/modal
const Stack = createStackNavigator();

function RootNavigator() {
  const [isLoading, setIsLoading] = useState(true);
  const [{ user }, dispatch] = useStateValue();

  useEffect(() => {
    const bootstrapAsync = async () => {
      let userInfo;
      try {
        userInfo = await AsyncStorage.getItem('userInfo');
        userInfo = JSON.parse(userInfo);
        dispatch({
          type: actionTypes.SET_USER,
          user: userInfo
        });
      } catch (e) {
        dispatch({
          type: actionTypes.SET_USER,
          user: null
        });
      }
      setIsLoading(false)
    };

    bootstrapAsync();
  }, []);

  return (
    <Stack.Navigator screenOptions={{
      headerStyle: {
        backgroundColor: Colors.light.tint,
        shadowOpacity: 0,
        elevation: 0,
      },
      headerTintColor: Colors.light.background,
      headerTitleAlign: 'left',
      headerTitleStyle: {
        fontWeight: 'bold',
      }
    }}>
      {
        isLoading ? (
          <Stack.Screen name={StackScreenName.Splash} component={SplashScreen} />
        ) : user === null ? (
          <>
            <Stack.Screen
              name={StackScreenName.SignIn}
              component={SignInScreen}
              options={{
                headerShown: false
              }}
            />
            <Stack.Screen
              name={StackScreenName.SignUp}
              component={SignUpScreen}
              options={{
                headerShown: false
              }}
            />
          </>
        ) : (
              <>
                <Stack.Screen
                  name={StackScreenName.Root}
                  component={MainTabNavigator}
                  options={{
                    title: "WhatsApp",
                    headerLeft: () => (
                      <Text>test</Text>
                    ),
                    headerRight: () => (
                      <View style={{
                        flexDirection: 'row',
                        width: 60,
                        justifyContent: 'space-between',
                        marginRight: 10,
                      }}>
                        <Octicons name="search" size={22} color={'white'} />
                        <MaterialCommunityIcons name="dots-vertical" size={22} color={'white'} />
                      </View>
                    )
                  }}
                />
                <Stack.Screen
                  name={StackScreenName.ChatRoom}
                  component={ChatRoomScreen}
                  options={({ route }) => ({
                    title: route.params.name,
                    headerRight: () => (
                      <View style={{
                        flexDirection: 'row',
                        width: 100,
                        justifyContent: 'space-between',
                        marginRight: 10,
                      }}>
                        <FontAwesome5 name="video" size={22} color={'white'} />
                        <MaterialIcons name="call" size={22} color={'white'} />
                        <MaterialCommunityIcons name="dots-vertical" size={22} color={'white'} />
                      </View>
                    )
                  })}
                />
                <Stack.Screen
                  name={StackScreenName.Contacts}
                  component={ContactsScreen}
                />
                <Stack.Screen
                  name={StackScreenName.Setting}
                  component={SettingScreen}
                />
                <Stack.Screen name="NotFound" component={NotFoundScreen} options={{ title: 'Oops!' }} />
              </>
            )
      }
    </Stack.Navigator>
  );
}
