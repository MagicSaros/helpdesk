import { Component } from 'react';

class Logout extends Component {
    render() {
        return null;
    }

    componentDidMount() {
        localStorage.removeItem(this.props.authenticationData.userId);
        localStorage.removeItem(this.props.authenticationData.header);
        localStorage.removeItem(this.props.authenticationData.string);
        this.props.history.push('/login')
    }
}

export default Logout;