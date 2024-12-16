import { Grid } from '@material-ui/core';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import Container from '@material-ui/core/Container';
import CssBaseline from '@material-ui/core/CssBaseline';
import { makeStyles } from '@material-ui/core/styles';
import TextField from '@material-ui/core/TextField';
import Typography from '@material-ui/core/Typography';
import LockOutlinedIcon from '@material-ui/icons/LockOutlined';
import React, { useEffect, useState } from 'react';
import { useHistory } from 'react-router-dom';
import MessageAPI from '../apis/MessageAPI';
import { actionTypes } from '../reducer';
import { useStateValue } from '../StateProvider';

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
}));

const baseUrl = process.env.REACT_APP_API_BASE_URL ? process.env.REACT_APP_API_BASE_URL.replace(":8080", "") : ""

function Settings() {
  // eslint-disable-next-line
  const [{ }, dispatch] = useStateValue();
  const classes = useStyles();
  const history = useHistory();

  const [state, setState] = useState({
    url: baseUrl,
  });


  useEffect(() => {
    let urlOld = localStorage.getItem("url");
    if (urlOld) {
      try {
        setState({ url: urlOld });
      } catch (error) {
      }
    }
  }, [])

  const handleChange = event => {
    setState({ ...state, [event.target.name]: event.target.value });
  };

  const onChangePressed = (event) => {
    event.preventDefault();
    localStorage.setItem("url", state.url);
    MessageAPI.updateUrl();
    let changeMessage = { "message": "Data is changed!" };
    dispatch({
      type: actionTypes.SET_NOTIFICATION,
      notification: changeMessage
    });
  }

  const onBackSignIn = (event) => {
    event.preventDefault();
    history.push("/");
  }

  return (
    <Container component="main" maxWidth="xs">
      <CssBaseline />
      <div className={classes.paper}>
        <Avatar className={classes.avatar}>
          <LockOutlinedIcon />
        </Avatar>
        <Typography component="h1" variant="h5">
          Settings
        </Typography>
        <form className={classes.form} noValidate onSubmit={onChangePressed}>
          <TextField
            variant="outlined"
            margin="normal"
            required
            fullWidth
            id="url"
            label="Api Base Url"
            name="url"
            value={state.url}
            onChange={handleChange}
            autoComplete="url"
            autoFocus
          />
          <Button
            type="submit"
            onClick={onChangePressed}
            fullWidth
            variant="contained"
            color="primary"
            className={classes.submit}
          >
            Change
          </Button>
        </form>
        <Grid container>
          <Grid item xs>
            <Button
              type="button"
              onClick={onBackSignIn}
              fullWidth
              variant="contained"
              color="primary"
            >
              Back
            </Button>
          </Grid>
        </Grid>
      </div>
    </Container>
  );
}

export default Settings
