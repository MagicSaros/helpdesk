import React, { Component } from 'react';
import axios from 'axios';

import AuthorizationService from './../authorizationService';
import Util from './../util';

const newStateByAction = {
    "Submit": "NEW",
    "Approve": "APPROVED",
    "Decline": "DECLINED",
    "Cancel": "CANCELLED",
    "Assign": "IN_PROGRESS",
    "Done": "DONE"
}

class TicketItem extends Component {
    constructor(props) {
        super(props);
        this.baseUrl = 'http://localhost:8080/api';
        this.state = {
            ticket: this.props.ticket,
            actions: [],
            currentAction: ''
        }
    }

    render() {
        let name = this.state.ticket.name ? this.state.ticket.name : '-';
        let date = this.state.ticket.desiredResolutionDate ? Util.toShortDateFormat(this.state.ticket.desiredResolutionDate) : '';
        let urgency = this.state.ticket.urgency ? Util.capitalize(this.state.ticket.urgency) : '';

        return (
            <tr className="d-flex">
                <td className="p-2 col-1">
                    {this.state.ticket.id}
                </td>
                <td className="p-2 col-2">
                    <a href="" onClick={event => this.showTicketOverview(event)}>{name}</a>
                </td>
                <td className="p-2 col-2">
                    {date}
                </td>
                <td className="p-2 col-2">
                    {urgency}
                </td>
                <td className="p-2 col-2">
                    {Util.capitalize(this.state.ticket.state)}
                </td>
                <td className="p-2 col-3">
                    <div className="btn-group">
                        <button type="button" className="btn btn-success" onClick={event => this.doAction(event)}>
                            {this.state.currentAction}
                        </button>
                        <button type="button" className="btn btn-success dropdown-toggle dropdown-toggle-split" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                            <span className="sr-only">Toggle Dropdown</span>
                        </button>
                        <div className="dropdown-menu">
                            {this.state.actions.map((action, i) => (
                                <a className="dropdown-item" href="" key={i} onClick={event => { event.preventDefault(); this.setState({ currentAction: action }) }}>{action}</a>    
                            ))}
                        </div>
                    </div>
                </td>
            </tr>
        );
    }

    componentDidMount() {
        this.loadActions();
    }

    loadActions() {
        let user = this.getCurrentUser();
        let authToken = this.getAuthToken();
        let userId = user ? user.id : 0;
        let ticketId = this.state.ticket.id;

        let url = this.baseUrl + `/users/${userId}/tickets/${ticketId}/actions`;
        let config = {
            headers: {
                [authToken.header]: authToken.string
            }
        };

        axios
            .get(url, config)
            .then(response => {
                if (response.status === 200) {
                    console.log(response);
                    let actions = response.data;
                    actions = actions
                        .filter(action => action !== 'CREATE')
                        .map(action => Util.capitalize(action));
                    let currentAction = actions ? actions[0] : '';
                    this.setState({
                        actions: actions,
                        currentAction: currentAction
                    });
                }
            })
            .catch(error => this.handleRequestError(error));
    }

    doAction(event) {
        event.preventDefault();

        let user = this.getCurrentUser();
        let authToken = this.getAuthToken();
        let userId = user ? user.id : 0;
        let ticket = this.props.ticket;
        let ticketId = ticket.id;

        let url = this.baseUrl + `/users/${userId}/tickets/${ticketId}`;
        let config = {
            headers: {
                [authToken.header]: authToken.string
            }
        };

        let action = this.state.currentAction;

        if (!action) {
            return;
        }

        let name = ticket.name ? ticket.name.toLowerCase() : ticket.name;
        
        let payload = {
            "id": ticketId,
            "name": name,
            "description": ticket.description,
            "state": newStateByAction[action],
        };

        if (ticket.desiredResolutionDate) payload["desiredResolutionDate"] = ticket.desiredResolutionDate;
        if (ticket.category) payload["category"] = { "id": ticket.category.id }
        if (ticket.urgency) payload["urgency"] = ticket.urgency;
        
        console.log('Url: ' + url);
        console.log(payload);

        axios
            .patch(url, payload, config)
            .then(response => {
                if (response.status === 200) {
                    console.log(response);
                    let ticket = response.data;
                    this.setState({ ticket: ticket }, () => this.loadActions());
                }
            })
            .catch(error => this.handleRequestError(error));
    }

    showTicketOverview(event) {
        event.preventDefault();
        let ticketId = this.props.ticket ? this.props.ticket.id : 0;
        this.props.history.push(`/tickets/${ticketId}`);
    }

    showTicketsList() {
        this.props.history.push('/tickets');
    }

    getCurrentUser() {
        return AuthorizationService.getCurrentUser();
    }

    getAuthToken() {
        return AuthorizationService.getAuthorizationToken();
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

export default TicketItem;