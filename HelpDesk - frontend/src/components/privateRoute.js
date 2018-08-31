import React from 'react';
import { Route, Redirect } from 'react-router-dom';

import AuthorizationService from './authorizationService';

let PrivateRoute = ({component: Component, baseUrl, ...rest }) => {
    let user = AuthorizationService.getCurrentUser();
    return (
        <Route {...rest} render={
            props => (
                user ?
                    <Component baseUrl={baseUrl} {...props} />
                    :
                    <Redirect to="/login" />
            )
        } />
    )
};

export default PrivateRoute;