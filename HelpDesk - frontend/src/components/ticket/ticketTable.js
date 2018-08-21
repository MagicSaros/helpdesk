import React, { Component } from "react";

import TicketItem from './ticketItem';
import TicketComparator from './ticketComparator';

class TicketTable extends Component {
    constructor(props) {
        super(props);
        this.state = {
            tickets: [],
            currentSortedColumn: 'urgency',
            isAscOrder: true
        }
    }

    render() {
        return(
            <table className="table table-sm table-bordered">
                <thead className="thead-light">
                    <tr className="d-flex">
                        <td className="table-secondary p-2 col-1" onClick={() => this.sort('id')}>Id</td>
                        <td className="table-secondary p-2 col-2" onClick={() => this.sort('name')}>Name</td>
                        <td className="table-secondary p-2 col-2" onClick={() => this.sort('desired date')}>Desired date</td>
                        <td className="table-secondary p-2 col-2" onClick={() => this.sort('urgency')}>Urgency</td>
                        <td className="table-secondary p-2 col-2" onClick={() => this.sort('status')}>Status</td>
                        <td className="table-secondary p-2 col-3">Action</td>
                    </tr>
                </thead>
                <tbody>
                    { this.state.tickets
                        .map(ticket => (<TicketItem key={ticket.id} ticket={ticket} history={this.props.history} />)) }
                </tbody>
            </table>
        );
    }

    componentWillReceiveProps(props) {
        this.setState({ tickets: props.tickets });
    }

    sort(column) {
        if (this.state.currentSortedColumn === column) {
            this.state.tickets.reverse();
        } else {
            switch (column) {
                case 'id':
                    this.state.tickets.sort(TicketComparator.compareId);
                    break;
                case 'name':
                    this.state.tickets.sort(TicketComparator.compareName);
                    break;
                case 'desired date':
                    this.state.tickets.sort(TicketComparator.compareDesiredDate);
                    break;
                case 'urgency':
                    this.state.tickets.sort(TicketComparator.compareUrgency);
                    break;
                case 'status':
                    this.state.tickets.sort(TicketComparator.compareStatus);
                    break;
                default:
                    break;
            }
        }
        this.setState({
            currentSortedColumn: column,
            isAscOrder: !this.state.isAscOrder
        });
    }
}

export default TicketTable;