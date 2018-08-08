import React, { Component } from 'react';

import Util from './../util';

import './ticketItem.css';

class TicketItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            actions: ['Submit', 'Approve', 'Decline', 'Cancel', 'Assign to me', 'Save as Draft', 'Done'],
            currentAction: 'Submit'
        }
    }

    render() {
        return (
            <tr>
                <td className="p-2">
                    {this.props.ticket.id}
                </td>
                <td className="p-2">
                    {this.props.ticket.name}
                </td>
                <td className="p-2">
                    {Util.toShortDateFormat(this.props.ticket.desiredResolutionDate)}
                </td>
                <td className="p-2">
                    {Util.capitalize(this.props.ticket.urgency)}
                </td>
                <td className="p-2">
                    {Util.capitalize(this.props.ticket.state)}
                </td>
                <td className="p-2 dropdown-td">
                    <div className="btn-group">
                        <button type="button" className="btn btn-success">
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
}

export default TicketItem;