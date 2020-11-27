import { createMaterialTopTabNavigator } from '@react-navigation/material-top-tabs';
import { useNavigation } from '@react-navigation/native';
import { createStackNavigator } from '@react-navigation/stack';
import React, { useLayoutEffect } from 'react';
import { TouchableWithoutFeedback } from 'react-native-gesture-handler';
import { Avatar } from 'react-native-paper';
import Fontisto from "react-native-vector-icons/Fontisto";
import Ionicons from "react-native-vector-icons/Ionicons";
import { View } from '../components/Themed';
import Colors from '../constants/Colors';
import StackScreenName from '../constants/StackScreenName';
import useColorScheme from '../hooks/useColorScheme';
import ChatsScreen from '../screens/ChatsScreen';
import ContactsScreen from '../screens/ContactsScreen';
import TabTwoScreen from '../screens/TabTwoScreen';


const MainTab = createMaterialTopTabNavigator();

export default function MainTabNavigator() {
  const colorScheme = useColorScheme();

  const navigation = useNavigation();
  useLayoutEffect(() => {
    navigation.setOptions({
      headerLeft: () => (
        <View style={{ marginLeft: 20, borderRadius: 15 }}>
          <TouchableWithoutFeedback onPress={() => navigation.navigate(StackScreenName.Setting)}>
            <Avatar.Image size={24} source={require('../assets/logo.png')} />
          </TouchableWithoutFeedback>
        </View>
      )
    });
  }, [navigation]);

  return (
    <MainTab.Navigator
      initialRouteName={StackScreenName.Chats}
      tabBarOptions={{
        activeTintColor: Colors[colorScheme].background,
        style: {
          backgroundColor: Colors[colorScheme].tint,
        },
        indicatorStyle: {
          backgroundColor: Colors[colorScheme].background,
          height: 4,
        },
        labelStyle: {
          fontWeight: 'bold'
        },
        showIcon: true,
      }}>
      <MainTab.Screen
        name={StackScreenName.Camera}
        component={TabOneNavigator}
        options={{
          tabBarIcon: ({ color }) => <Fontisto name="camera" color={color} size={18} />,
          tabBarLabel: () => null
        }}
      />
      <MainTab.Screen
        name={StackScreenName.Chats}
        component={ChatsScreen}
      />
      <MainTab.Screen
        name={StackScreenName.Contacts}
        component={ContactsScreen}
      />
      <MainTab.Screen
        name={StackScreenName.Calls}
        component={TabTwoNavigator}
      />
    </MainTab.Navigator>
  );
}

// You can explore the built-in icon families and icons on the web at:
// https://icons.expo.fyi/
function TabBarIcon(props) {
  return <Ionicons size={30} style={{ marginBottom: -3 }} {...props} />;
}

// Each tab has its own navigation stack, you can read more about this pattern here:
// https://reactnavigation.org/docs/tab-based-navigation#a-stack-navigator-for-each-tab
const TabOneStack = createStackNavigator();

function TabOneNavigator() {
  return (
    <TabOneStack.Navigator>
      <TabOneStack.Screen
        name="TabOneScreen"
        component={TabTwoScreen}
        options={{ headerTitle: 'Tab One Title' }}
      />
    </TabOneStack.Navigator>
  );
}

const TabTwoStack = createStackNavigator();

function TabTwoNavigator() {
  return (
    <TabTwoStack.Navigator>
      <TabTwoStack.Screen
        name="TabTwoScreen"
        component={TabTwoScreen}
        options={{ headerTitle: 'Tab Two Title' }}
      />
    </TabTwoStack.Navigator>
  );
}
