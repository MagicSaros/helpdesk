import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './components/login';
import Logout from './components/logout';
import './App.css';
import TicketList from './components/ticket/ticketList';

const accessToken = {
	header: 'Authentication token header',
	string: 'Authentication token string'
};

const baseUrl = 'http://localhost:8080/api';

class App extends Component {

	render() {
		return (
			<Router>
				<div className="App">
					<Switch>
						<Route path="/login" render={props => <Login accessToken={accessToken} baseUrl={baseUrl} {...props} />} />
						<Route path="/logout" render={props => <Logout accessToken={accessToken} {...props} />} />
						<Route path="/tickets" render={() => <TicketList accessToken={accessToken} baseUrl={baseUrl} />} />
					</Switch>
				</div>
			</Router>
		);
	}
}

export default App;
