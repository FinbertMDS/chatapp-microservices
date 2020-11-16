import * as React from 'react';

export const navigationRef = React.createRef();

export default {
  navigate: (name, params) => {
    navigationRef.current?.navigate(name, params);
  }
}