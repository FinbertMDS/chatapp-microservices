import Avatar from '@material-ui/core/Avatar';
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
import { actionTypes } from '../reducer';
import { useStateValue } from '../StateProvider';

const useStyles = makeStyles((theme) => ({
  paper: {
    marginTop: theme.spacing(1),
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
}));

function SignUp(props) {
  // eslint-disable-next-line
  const [{ }, dispatch] = useStateValue();

  const [state, setState] = useState({
    username: "user",
    email: "user@gmail.com",
    password: "user123",
  });

  const handleChange = event => {
    setState({ ...state, [event.target.name]: event.target.value });
  };

  const signUp = () => {
    const signUpData = {
      "username": state.username,
      "email": state.email,
      "password": state.password
    };
    SecurityAPI.signUp(signUpData)
      .then(result => {
        dispatch({
          type: actionTypes.SET_NOTIFICATION,
          notification: result
        });
        props.onSignIn();
      })
      .catch(error => {
        dispatch({
          type: actionTypes.SET_NOTIFICATION,
          notification: error.response ? error.response.data : error
        });
      });
  }

  const classes = useStyles();

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Sign up
        </Typography>
        <form className={classes.form} noValidate>
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
            id="email"
            label="Email"
            name="email"
            value={state.email}
            onChange={handleChange}
            autoComplete="email"
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
            // type="submit"
            onClick={signUp}
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Sign Up
          </Button>
          <Grid container>
            <Grid item>
              <Link href="#" variant="body2" onClick={props.onSignIn}>
                {"I have an account? Sign In"}
              </Link>
            </Grid>
          </Grid>
        </form>
      </div>
    </Container>
  );
}

export default SignUp
