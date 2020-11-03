import AsyncStorage from '@react-native-async-storage/async-storage';
import { useNavigation } from '@react-navigation/native';
import React, { memo, useState } from 'react';
import { Alert, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { actionTypes } from '../../reducer';
import { useStateValue } from '../../StateProvider';
import SecurityAPI from '../apis/SecurityAPI';
import Background from '../components/Background';
import Button from '../components/Button';
import Header from '../components/Header';
import Logo from '../components/Logo';
import TextInput from '../components/TextInput';
import StackScreenName from '../constants/StackScreenName';
import { theme } from '../core/theme';
import { nameValidator, passwordValidator } from '../core/utils';

const SignInScreen = () => {
  const [{}, dispatch] = useStateValue();
  const navigation = useNavigation();
  const [username, setUsername] = useState({ value: 'user123', error: '' });
  const [password, setPassword] = useState({ value: 'user123', error: '' });


  const _onLoginPressed = () => {
    const usernameError = nameValidator(username.value);
    const passwordError = passwordValidator(password.value);

    if (usernameError || passwordError) {
      setUsername({ ...username, error: usernameError });
      setPassword({ ...password, error: passwordError });
      return;
    }
    const signInData = {
      "username": username.value,
      "password": password.value
    };
    SecurityAPI.signIn(signInData)
      .then(async (result) => {
        await AsyncStorage.setItem("userInfo", JSON.stringify(result));
        let loginMessage = {"message": "User login successfully!"}
        Alert.alert(loginMessage.message);
        dispatch({
          type: actionTypes.SET_USER,
          user: result
        });
      })
      .catch(error => {
        Alert.alert(JSON.stringify(error))
      });
  }

  return (
    <Background>
      <Logo />

      <Header>Welcome back.</Header>

      <TextInput
        label="Username"
        returnKeyType="next"
        value={username.value}
        onChangeText={text => setUsername({ value: text, error: '' })}
        error={!!username.error}
        errorText={username.error}
        autoCapitalize="none"
      />

      <TextInput
        label="Password"
        returnKeyType="done"
        value={password.value}
        onChangeText={text => setPassword({ value: text, error: '' })}
        error={!!password.error}
        errorText={password.error}
        secureTextEntry
      />

      <View style={styles.forgotPassword}>
        <TouchableOpacity
          // onPress={() => navigation.navigate('ForgotPasswordScreen')}
        >
          <Text style={styles.label}>Forgot your password?</Text>
        </TouchableOpacity>
      </View>

      <Button mode="contained" onPress={_onLoginPressed}>
        Login
      </Button>

      <View style={styles.row}>
        <Text style={styles.label}>Don’t have an account? </Text>
        <TouchableOpacity onPress={() => navigation.navigate(StackScreenName.SignUp)}>
          <Text style={styles.link}>Sign up</Text>
        </TouchableOpacity>
      </View>
    </Background>
  );
};

const styles = StyleSheet.create({
  forgotPassword: {
    width: '100%',
    alignItems: 'flex-end',
    marginBottom: 24,
  },
  row: {
    flexDirection: 'row',
    marginTop: 4,
  },
  label: {
    color: theme.colors.secondary,
  },
  link: {
    fontWeight: 'bold',
    color: theme.colors.primary,
  },
});

export default memo(SignInScreen);