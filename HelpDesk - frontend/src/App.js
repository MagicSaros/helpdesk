import React, { Component } from 'react';
import { BrowserRouter as Router, Route, Switch } from 'react-router-dom';
import Login from './components/login';
import Logout from './components/logout';
import TicketList from './components/ticket/ticketList';
import TicketCreation from './components/ticket/ticketCreation';
import TicketOverview from './components/ticket/ticketOverview';
import TicketEdition from './components/ticket/ticketEdition';
import Feedback from './components/ticket/feedback';
import PrivateRoute from './components/privateRoute';
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
						<PrivateRoute exact path="/tickets" component={TicketList} baseUrl={baseUrl} />
						<PrivateRoute exact path="/tickets/create" component={TicketCreation} baseUrl={baseUrl} />
						<PrivateRoute exact path="/tickets/:ticketId" component={TicketOverview} baseUrl={baseUrl} />
						<PrivateRoute exact path="/tickets/:ticketId/edit" component={TicketEdition} baseUrl={baseUrl} />
						<PrivateRoute exact path="/tickets/:ticketId/feedback" component={Feedback} baseUrl={baseUrl} />
					</Switch>
				</div>
			</Router>
		);
	}
}

export default App;
