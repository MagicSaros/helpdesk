import React, { Component } from 'react';
import axios from 'axios';

import TicketTable from './ticketTable';
import Navbar from './../navbar';
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
    }

    render() {
        return(
            <div className="container">
                <Navbar />
                <div className="row my-5">
                    <div className="offset-sm-8 col-sm-4">
                        <button className="btn btn-lg btn-success btn-block">Create New Ticket</button>
                    </div>
                </div>

                <div className="row my-5">
                    <div className="col-sm-6">
                        <button className={`btn ${this.toggleButtonColorClass(this.state.isClickedAllTickets)} btn-block`} onClick={() => this.fillTickets('all')}>All Tickets</button>
                    </div>
                    <div className="col-sm-6">
                        <button className={`btn ${this.toggleButtonColorClass(this.state.isClickedMyTickets)} btn-block`} onClick={() => this.fillTickets('my')}>My Tickets</button>
                    </div>
                </div>

                <div className="row my-5">
                    <div className="col-sm-4">
                        <input type="search" className="form-control search-input" />
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
        let userId = localStorage.getItem(this.props.authenticationData.userId);
        let header = localStorage.getItem(this.props.authenticationData.header);
        let string = localStorage.getItem(this.props.authenticationData.string);
        
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

    fillTickets(mode) {
        let tickets = [];
        let isAll = false;
        let isMy = false;
        switch (mode) {
            case 'all':
                tickets = this.state.allTickets;
                isAll = true;
                isMy = false;
                break;
            case 'my':
                tickets = this.state.myTickets;
                isAll = false;
                isMy = true;
                break;
            default:
                this.fillTickets('all');
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
        let userId = +localStorage.getItem(this.props.authenticationData.userId);
        return tickets.filter(ticket => ticket.owner.id === userId);
    }
}

export default TicketList;