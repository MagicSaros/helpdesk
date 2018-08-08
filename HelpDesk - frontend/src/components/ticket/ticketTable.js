import React, { Component } from "react";

import TicketItem from './ticketItem';
import Util from './../util';

class TicketTable extends Component {
    
    render() {
        return(
            <table className="table table-sm table-bordered">
                <thead className="thead-light">
                    <tr>
                        <td className="table-secondary p-2">Id</td>
                        <td className="table-secondary p-2">Name</td>
                        <td className="table-secondary p-2">Desired date</td>
                        <td className="table-secondary p-2">Urgency</td>
                        <td className="table-secondary p-2">Status</td>
                        <td className="table-secondary p-2">Action</td>
                    </tr>
                </thead>
                <tbody>
                    { this.props.tickets
                        .sort(Util.compareTicketsByDateDesc)
                        .sort(Util.compareTicketsByUrgency)
                        .map(ticket => (<TicketItem key={ticket.id} ticket={ticket} />)) }
                </tbody>
            </table>
        );
    }
}

export default TicketTable;