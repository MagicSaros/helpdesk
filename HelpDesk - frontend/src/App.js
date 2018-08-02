import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './components/login';
import Logout from './components/logout';
import './App.css';

const accessToken = {
	header: 'Authentication token header',
	string: 'Authentication token string'
};

class App extends Component {

	render() {
		return (
			<Router>
				<div className="App">
					<Switch>
						<Route path="/login" render={() => <Login accessToken={accessToken} />} />
						<Route path="/logout" render={props => <Logout accessToken={accessToken} {...props} />} />
					</Switch>
				</div>
			</Router>
		);
	}
}

export default App;
