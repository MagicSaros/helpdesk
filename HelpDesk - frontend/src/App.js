import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './components/login';
import Logout from './components/logout';
import './App.css';
import TicketList from './components/ticket/ticketList';

import TicketCreation from './components/ticket/ticketCreation';

const authenticationDataLocalStorageKeys = {
	user: 'User',
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
						<Route path="/login" render={props => <Login authenticationData={authenticationDataLocalStorageKeys} baseUrl={baseUrl} {...props} />} />
						<Route path="/logout" render={props => <Logout authenticationData={authenticationDataLocalStorageKeys} {...props} />} />
						<Route path="/tickets" render={props => <TicketList authenticationData={authenticationDataLocalStorageKeys} baseUrl={baseUrl} {...props} />} />
						<Route path="/create" render={props => <TicketCreation authenticationData={authenticationDataLocalStorageKeys} baseUrl={baseUrl} {...props} />} />
					</Switch>
				</div>
			</Router>
		);
	}
}

export default App;
