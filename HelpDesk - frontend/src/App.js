import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './components/login';
import Logout from './components/logout';
import TicketList from './components/ticket/ticketList';
import TicketCreation from './components/ticket/ticketCreation';
import TicketOverview from './components/ticket/ticketOverview';
import TicketEdition from './components/ticket/ticketEdition';
import './App.css';

const baseUrl = 'http://localhost:8080/api';

class App extends Component {

	render() {
		return (
			<Router>
				<div className="App">
					<Switch>
						<Route path="/login" render={props => <Login baseUrl={baseUrl} {...props} />} />
						<Route path="/logout" render={props => <Logout {...props} />} />
						<Route path="/tickets" render={props => <TicketList baseUrl={baseUrl} {...props} />} />
						<Route path="/create" render={props => <TicketCreation baseUrl={baseUrl} {...props} />} />
						<Route path="/overview" render={props => <TicketOverview baseUrl={baseUrl} {...props} />} />
						<Route path="/edit" render={props => <TicketEdition baseUrl={baseUrl} {...props} />} />
					</Switch>
				</div>
			</Router>
		);
	}
}

export default App;
