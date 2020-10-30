import { IconButton, Snackbar } from '@material-ui/core';
import CloseIcon from '@material-ui/icons/Close';
import React, { useEffect, useState } from 'react';
import { actionTypes } from '../reducer';
import { useStateValue } from '../StateProvider';

function Notification() {

  const [{notification}, dispatch] = useStateValue();
  const [open, setOpen] = useState(null);

  const handleClick = () => {
    setOpen(true);
  };

  const handleClose = (event, reason) => {
    if (reason === 'clickaway') {
      return;
    }
    dispatch({
      type: actionTypes.CLEAR_NOTIFICATION
    });
    setOpen(false);
  };

  useEffect(() => {
    if (notification && notification.message && notification.message !== "") {
      handleClick();
    }
  }, [notification]);

  return (
    <div>
      <Snackbar
        anchorOrigin={{
          vertical: 'bottom',
          horizontal: 'center',
        }}
        open={open}
        autoHideDuration={2000}
        onClose={handleClose}
        message={notification ? notification.message : ""}
        action={
          <React.Fragment>
            <IconButton size="small" aria-label="close" color="inherit" onClick={handleClose}>
              <CloseIcon fontSize="small" />
            </IconButton>
          </React.Fragment>
        }
      />
    </div>
  )
}

export default Notification

