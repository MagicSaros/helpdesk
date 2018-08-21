import { Component } from 'react';

import AuthorizationService from './authorizationService';

class Logout extends Component {
    render() {
        return null;
    }

    componentDidMount() {
        AuthorizationService.deleteCurrentUser();
        AuthorizationService.deleteAuthorizationToken();
        this.props.history.push('/login')
    }
}

export default Logout;