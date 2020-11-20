import { useNavigation } from '@react-navigation/native';
import React, { memo, useState } from 'react';
import { Alert, StyleSheet, Text, TouchableOpacity, View } from 'react-native';
import { useStateValue } from '../../StateProvider';
import SecurityAPI from '../apis/SecurityAPI';
import BackButton from '../components/BackButton';
import Background from '../components/Background';
import Button from '../components/Button';
import Header from '../components/Header';
import Logo from '../components/Logo';
import TextInput from '../components/TextInput';
import StackScreenName from '../constants/StackScreenName';
import { theme } from '../core/theme';
import {
  emailValidator,
  nameValidator, passwordValidator
} from '../core/utils';

const SignUpScreen = () => {
  const [{}, dispatch] = useStateValue();
  const navigation = useNavigation();
  const [username, setUsername] = useState({ value: 'user123', error: '' });
  const [email, setEmail] = useState({ value: 'user123@gmail.com', error: '' });
  const [password, setPassword] = useState({ value: 'user123', error: '' });

  const _onSignUpPressed = () => {
    const nameError = nameValidator(username.value);
    const emailError = emailValidator(email.value);
    const passwordError = passwordValidator(password.value);

    if (emailError || passwordError || nameError) {
      setUsername({ ...username, error: nameError });
      setEmail({ ...email, error: emailError });
      setPassword({ ...password, error: passwordError });
      return;
    }
    const signUpData = {
      "username": username.value,
      "email": email.value,
      "password": password.value
    };
    SecurityAPI.signUp(signUpData)
      .then(result => {
        let loginMessage = {"message": "User registered successfully!"}
        Alert.alert("Signup", loginMessage.message);
        navigation.navigate(StackScreenName.SignIn);
      })
      .catch(error => {
        Alert.alert("User registered failure", error.message)
      });
  };

  return (
    <Background>
      <BackButton goBack={() => navigation.navigate(StackScreenName.SignIn)} />

      <Logo />

      <Header>Create Account</Header>

      <TextInput
        label="Name"
        returnKeyType="next"
        value={username.value}
        onChangeText={text => setUsername({ value: text, error: '' })}
        error={!!username.error}
        errorText={username.error}
      />

      <TextInput
        label="Email"
        returnKeyType="next"
        value={email.value}
        onChangeText={text => setEmail({ value: text, error: '' })}
        error={!!email.error}
        errorText={email.error}
        autoCapitalize="none"
        autoCompleteType="email"
        textContentType="emailAddress"
        keyboardType="email-address"
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

      <Button mode="contained" onPress={_onSignUpPressed} style={styles.button}>
        Sign Up
      </Button>

      <View style={styles.row}>
        <Text style={styles.label}>Already have an account? </Text>
        <TouchableOpacity onPress={() => navigation.navigate(StackScreenName.SignIn)}>
          <Text style={styles.link}>Login</Text>
        </TouchableOpacity>
      </View>
    </Background>
  );
};

const styles = StyleSheet.create({
  label: {
    color: theme.colors.secondary,
  },
  button: {
    marginTop: 24,
  },
  row: {
    flexDirection: 'row',
    marginTop: 4,
  },
  link: {
    fontWeight: 'bold',
    color: theme.colors.primary,
  },
});

export default memo(SignUpScreen);