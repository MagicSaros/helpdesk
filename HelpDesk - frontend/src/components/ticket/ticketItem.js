import React, { Component } from 'react';

import Util from './../util';

class TicketItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            actions: ['Submit', 'Approve', 'Decline', 'Cancel', 'Assign to me', 'Save as Draft', 'Done'],
            currentAction: 'Submit'
        }
    }

    render() {
        let name = this.props.ticket.name ? this.props.ticket.name : '-';
        let date = this.props.ticket.desiredResolutionDate ? Util.toShortDateFormat(this.props.ticket.desiredResolutionDate) : '';
        let urgency = this.props.ticket.urgency ? Util.capitalize(this.props.ticket.urgency) : '';

        return (
            <tr className="d-flex">
                <td className="p-2 col-1">
                    {this.props.ticket.id}
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
                    {Util.capitalize(this.props.ticket.state)}
                </td>
                <td className="p-2 col-3">
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

    showTicketOverview(event) {
        event.preventDefault();
        this.props.history.push('/overview', this.props.ticket);
    }
}

export default TicketItem;