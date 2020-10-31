import { Modal } from '@material-ui/core';
import Avatar from '@material-ui/core/Avatar';
import Box from '@material-ui/core/Box';
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import CssBaseline from '@material-ui/core/CssBaseline';
import Grid from '@material-ui/core/Grid';
import Link from '@material-ui/core/Link';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import React, { useState } from 'react';
import SecurityAPI from '../apis/SecurityAPI';
import AsyncLocalStorage from '../helpers/AsyncLocalStorage';
import { actionTypes } from '../reducer';
import { useStateValue } from '../StateProvider';
import SignUp from "./SignUp";

function Copyright() {
  return (
    <Typography variant="body2" color="textSecondary" align="center">
      {'Copyright © '}
      <Link color="inherit" href="https://material-ui.com/">
        Chat App
        </Link>{' '}
      {new Date().getFullYear()}
      {'.'}
    </Typography>
  );
}

function getModalStyle() {
  const top = 50;
  const left = 50;

  return {
    top: `${top}%`,
    left: `${left}%`,
    transform: `translate(-${top}%, -${left}%)`,
  };
}

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  avatar: {
    margin: theme.spacing(1),
    backgroundColor: theme.palette.secondary.main,
  },
  form: {
    width: '100%', // Fix IE 11 issue.
    marginTop: theme.spacing(1),
  },
  submit: {
    margin: theme.spacing(3, 0, 2),
  },
  paperModal: {
    position: 'absolute',
    // width: 400,
    backgroundColor: theme.palette.background.paper,
    border: '2px solid #000',
    boxShadow: theme.shadows[5],
    padding: theme.spacing(2, 4, 3),
  },
}));

function SignIn() {
  // eslint-disable-next-line
  const [{ }, dispatch] = useStateValue();
  const classes = useStyles();
  const [modalStyle] = useState(getModalStyle);
  const [isSignUp, setIsSignUp] = useState(false);

  const handleOpen = () => {
    setIsSignUp(true);
  };

  const handleClose = () => {
    setIsSignUp(false);
  };

  const body = (
    <div style={modalStyle} className={classes.paperModal}>
      <SignUp onSignIn={handleClose} />
    </div>
  );

  const [state, setState] = useState({
    username: "user123",
    password: "user123"
  });

  const handleChange = event => {
    setState({ ...state, [event.target.name]: event.target.value });
  };

  const signIn = (event) => {
    event.preventDefault();
    const signInData = {
      "username": state.username,
      "password": state.password
    };
    SecurityAPI.signIn(signInData)
      .then(result => {
        AsyncLocalStorage.setItem("userInfo", JSON.stringify(result))
          .then(() => {
            let loginMessage = {"message": "User login successfully!"}
            dispatch({
              type: actionTypes.SET_NOTIFICATION,
              notification: loginMessage
            });
            dispatch({
              type: actionTypes.SET_USER,
              user: result
            });
          });
      })
      .catch(error => {
        dispatch({
          type: actionTypes.SET_NOTIFICATION,
          notification: error
        });
      });
  }

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign in
        </Typography>
        <form className={classes.form} noValidate onSubmit={signIn}>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="username"
            label="Username"
            name="username"
            value={state.username}
            onChange={handleChange}
            autoComplete="username"
            autoFocus
          />
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            name="password"
            label="Password"
            type="password"
            id="password"
            value={state.password}
            onChange={handleChange}
            autoComplete="current-password"
          />
          <Button
            type="submit"
            onClick={signIn}
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Sign In
          </Button>
          <Grid container>
            <Grid item xs>
              <Link href="#" variant="body2">
                Forgot password?
              </Link>
            </Grid>
            <Grid item>
              <Link href="#" variant="body2" onClick={handleOpen}>
                {"Don't have an account? Sign Up"}
              </Link>
            </Grid>
          </Grid>
          <Modal
            open={isSignUp}
            onClose={handleClose}
            aria-labelledby="signup"
            aria-describedby="signup"
          >
            {body}
          </Modal>
        </form>
      </div>
      <Box mt={8}>
        <Copyright />
      </Box>
    </Container>
  );
}

export default SignIn
