import { Component } from 'react';

class Logout extends Component {
    render() {
        return null;
    }

    componentDidMount() {
        localStorage.removeItem(this.props.accessToken.header);
        localStorage.removeItem(this.props.accessToken.string);
        console.log("Successfully logged out")
        this.props.history.push('/login')
    }
}

export default Logout;