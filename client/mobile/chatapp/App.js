import React, { useEffect } from "react";
import { StatusBar, useColorScheme } from "react-native";
import { SafeAreaProvider } from 'react-native-safe-area-context';
import useCachedResources from "./src/hooks/useCachedResources";
import Navigation from "./src/navigation";

function App() {
  const isLoadingComplete = useCachedResources();
  const colorScheme = useColorScheme();
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
      <Navigation colorScheme={colorScheme} />
      <StatusBar />
    </SafeAreaProvider>
  );
}

export default App;
