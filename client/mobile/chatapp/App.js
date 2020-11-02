import React, { useEffect } from "react";
import { StatusBar, useColorScheme } from "react-native";
import { Provider as PaperProvider } from "react-native-paper";
import { SafeAreaProvider } from 'react-native-safe-area-context';
import reducer, { initialState } from "./reducer";
import { theme } from "./src/core/theme";
import useCachedResources from "./src/hooks/useCachedResources";
import Navigation from "./src/navigation";
import { StateProvider } from "./StateProvider";

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
