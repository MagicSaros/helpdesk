import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './components/login';
import Logout from './components/logout';
import TicketList from './components/ticket/ticketList';
import TicketCreation from './components/ticket/ticketCreation';
import TicketOverview from './components/ticket/ticketOverview';
import TicketEdition from './components/ticket/ticketEdition';
import Feedback from './components/ticket/feedback';
import './App.css';

const baseUrl = 'http://localhost:8080/api';

class App extends Component {

	render() {
		return (
			<Router>
				<div className="App">
					<Switch>
						<Route exact path="/" render={props => <Login baseUrl={baseUrl} {...props} />} />
						<Route path="/login" render={props => <Login baseUrl={baseUrl} {...props} />} />
						<Route path="/logout" render={props => <Logout baseUrl={baseUrl} {...props} />} />
						<Route exact path="/tickets" render={props => <TicketList baseUrl={baseUrl} {...props} />} />
						<Route exact path="/tickets/create" render={props => <TicketCreation baseUrl={baseUrl} {...props} />} />
						<Route exact path="/tickets/:ticketId" render={props => <TicketOverview baseUrl={baseUrl} {...props} />} />
						<Route exact path="/tickets/:ticketId/edit" render={props => <TicketEdition baseUrl={baseUrl} {...props} />} />
						<Route exact path="/tickets/:ticketId/feedback" render={props => <Feedback baseUrl={baseUrl} {...props} />} />
					</Switch>
				</div>
			</Router>
		);
	}
}

export default App;
