import { Component } from 'react';
import axios from 'axios';

import AuthorizationService from './authorizationService';

class Logout extends Component {
    render() {
        return null;
    }

    componentDidMount() {
        this.processLogout();
        AuthorizationService.deleteCurrentUser();
        AuthorizationService.deleteAuthorizationToken();
        this.props.history.push('/login')
    }

    processLogout() {
        let url = this.props.baseUrl + '/logout';
        let authToken = AuthorizationService.getAuthorizationToken();
        let config = {
            headers: {
                [authToken.header]: authToken.string
            }
        };

        axios
            .post(url, {}, config)
            .then(response => {
                if (response.status === 200) {
                    console.log(response);
                }  
            })
            .catch(error => this.handleRequestError(error));
    }

    handleRequestError(error) {
        if (error.response) {
            console.log(error.response.data)
        } else if (error.request) {
            console.log(error.request);
        } else {
            console.log('Error', error.message);
        }
    }
}

export default Logout;