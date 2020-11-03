import React from 'react';
import { ActivityIndicator, StatusBar, StyleSheet, View } from 'react-native';

function SplashScreen() {
  return (
    <View style={styles.container}>
      <ActivityIndicator />
      <StatusBar barStyle="default" />
    </View>
  );
}

const styles = StyleSheet.create({
  container: {
      flex: 1,
      alignItems: 'center',
      justifyContent: 'center',
  },
});
export default SplashScreen;