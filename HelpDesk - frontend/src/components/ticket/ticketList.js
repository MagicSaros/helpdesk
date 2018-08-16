import React, { Component } from 'react';
import axios from 'axios';

import TicketTable from './ticketTable';
import Navbar from './../navbar';
import Util from './../util';
import './ticketList.css';

const buttonColorClass = {
    active: 'btn-primary',
    inactive: 'btn-outline-primary'
};

class TicketList extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tickets: [],
            allTickets: [],
            myTickets: [],
            isClickedAllTickets: true,
            isClickedMyTickets: false
        };
        this.userRole = {
            MANAGER: 'MANAGER',
            EMPLOYEE: 'EMPLOYEE',
            ENGINEER: 'ENGINEER'
        };
    }

    render() {
        return(
            <div className="container">
                <Navbar />
                <div className="row my-5">
                    <div className="offset-sm-8 col-sm-4">
                        <button className="btn btn-lg btn-success btn-block" onClick={() => this.openTicketCreation()}>Create New Ticket</button>
                    </div>
                </div>

                <div className="row my-5">
                    <div className="col-sm-6">
                        <button name="allTickets" className={`btn ${this.toggleButtonColorClass(this.state.isClickedAllTickets)} btn-block`} onClick={event => this.fillTickets(event)}>All Tickets</button>
                    </div>
                    <div className="col-sm-6">
                        <button name="myTickets" className={`btn ${this.toggleButtonColorClass(this.state.isClickedMyTickets)} btn-block`} onClick={event => this.fillTickets(event)}>My Tickets</button>
                    </div>
                </div>

                <div className="row my-5">
                    <div className="col-sm-4">
                        <input type="search" className="form-control search-input" onChange={event => this.handleSearch(event)} autoFocus />
                    </div>
                </div>

                <div className="row my-5">
                    <div className="col text-left">
                        <TicketTable tickets={this.state.tickets} />
                    </div>
                </div>
            </div>
        );
    }

    componentDidMount() {
        this.loadTickets();
    }

    loadTickets() {
        let user = JSON.parse(localStorage.getItem(this.props.authenticationData.user));
        let header = localStorage.getItem(this.props.authenticationData.header);
        let string = localStorage.getItem(this.props.authenticationData.string);
        let userId = user ? user.id : 0;

        let url = this.props.baseUrl + `/users/${userId}/tickets`;
        let config = {
            headers: {
                [header]: string
            }
        };

        let self = this;
        let allTickets = [];
        let myTickets = [];

        axios
            .get(url, config)
            .then(response => {
                if (response.status === 200) {
                    response.data.map(ticket => allTickets.push(ticket));
                    myTickets = self.filterTicketsByCurrentUser(allTickets);
                    self.setState({
                        tickets: allTickets,
                        allTickets: allTickets,
                        myTickets: myTickets
                    });
                }
            })
            .catch(error => {
                if (error.response) {
                    console.log(error.response.data.message);
                } else if (error.request) {
                    console.log(error.request);
                } else {
                    console.log('Error', error.message);
                }
            });
    }

    handleSearch(event) {
        let tickets = [];
        if (this.state.isClickedAllTickets) {
            tickets = this.state.allTickets;
        } else if (this.state.isClickedMyTickets) {
            tickets = this.state.myTickets;
        }
        this.setState({ tickets: tickets });

        let pattern = event.target.value.trim();
        if (!pattern) {
            return;
        }

        let re = new RegExp(`${pattern}`, 'i');
        let filteredTickets = [];

        tickets.forEach(ticket => {
            if ((`${ticket['id']}` ? `${ticket['id']}`.match(re) : false)
            || (ticket['name'] ? ticket['name'].match(re) : false)
            || (ticket['urgency'] ? ticket['urgency'].match(re) : false)
            || (ticket['state'] ? ticket['state'].match(re) : false)
            || (ticket['desiredResolutionDate'] ? Util.toShortDateFormat(ticket['desiredResolutionDate']).match(re) : false)) {
                filteredTickets.push(ticket);
            }
        });

        this.setState({
            tickets: filteredTickets
        });
    }

    fillTickets(event) {
        let buttonName = event.target.name;
        let tickets = [];
        let isAll = false;
        let isMy = false;
        switch (buttonName) {
            case 'allTickets':
                tickets = this.state.allTickets;
                isAll = true;
                isMy = false;
                break;
            case 'myTickets':
                tickets = this.state.myTickets;
                isAll = false;
                isMy = true;
                break;
            default:
                tickets = this.state.tickets;
                isAll = this.state.isAll;
                isMy = this.state.isMy;
        }
        this.setState({
            tickets: tickets,
            isClickedAllTickets: isAll,
            isClickedMyTickets: isMy
        });
    }

    toggleButtonColorClass(isActive) {
        return isActive ? buttonColorClass.active : buttonColorClass.inactive;
    }

    filterTicketsByCurrentUser(tickets) {
        let user = JSON.parse(localStorage.getItem(this.props.authenticationData.user));
        let userId = +user.id;
        let role = user.role;
        switch (role) {
            case this.userRole.MANAGER:
                return tickets.filter(ticket => ticket.owner.id === userId
                    || (ticket.approver.id === userId && ticket.state === 'APPROVED'));
            case this.userRole.EMPLOYEE:
                return tickets.filter(ticket => ticket.owner.id === userId);
            case this.userRole.ENGINEER:
                return tickets.filter(ticket => ticket.assignee.id === userId);
            default:
                return tickets;
        }
    }

    openTicketCreation() {
        this.props.history.push('/create');
    }
}

export default TicketList;