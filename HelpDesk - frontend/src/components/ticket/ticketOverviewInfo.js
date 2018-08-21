import React, { Component } from 'react';

class TicketOverviewInfo extends Component {
    constructor(props) {
        super(props);
    }

    render() {
        return(
            <div className="container">
                <div className="row mt-4 mb-2">
                    <div className="col-sm-2">
                        <button className="btn btn-success btn-custom" onClick={() => this.showTicketsList()}>Ticket List</button>
                    </div>
                    <div className="col-sm-5">
                        {/* <h3>{`Ticket (${this.props.ticket.id}) - ${this.props.ticket.name}`}</h3> */}
                    </div>
                </div>
                {/* <!-- Created on --> */}
                <div className="row">
                    <div className="col-sm-4 text-right px-4">Created on:</div>
                    <div className="col-sm-3 text-left px-4">11/17/2017</div>
                </div>
                {/* <!-- Status, category --> */}
                <div className="row">
                    <div className="col-sm-4 text-right px-4">Status:</div>
                    <div className="col-sm-2 text-left px-4">Approved</div>
                    <div className="col-sm-2 text-right">Category:</div>
                    <div className="col-sm-3 text-left"></div>
                </div>
                {/* <!-- Urgency --> */}
                <div className="row">
                    <div className="col-sm-4 text-right px-4">Urgency:</div>
                    <div className="col-sm-3 text-left px-4"></div>
                </div>
                {/* <!-- Desired date --> */}
                <div className="row">
                    <div className="col-sm-4 text-right px-4">Desired resolution date:</div>
                    <div className="col-sm-3 text-left px-4">11/17/2018</div>
                </div>
                {/* <!-- Owner --> */}
                <div className="row">
                    <div className="col-sm-4 text-right px-4">Owner:</div>
                    <div className="col-sm-3 text-left px-4"></div>
                </div>
                {/* <!-- Approver --> */}
                <div className="row">
                    <div className="col-sm-4 text-right px-4">Approver:</div>
                    <div className="col-sm-3 text-left px-4">Manager1</div>
                </div>
                {/* <!-- Assignee --> */}
                <div className="row">
                    <div className="col-sm-4 text-right px-4">Assignee:</div>
                    <div className="col-sm-3 text-left px-4">Employee1</div>
                </div>
                {/* <!-- Attachments --> */}
                <div className="row">
                    <div className="col-sm-4 text-right px-4">Attachments:</div>
                    <div className="col-sm-3 text-left px-4"></div>
                </div>
                {/* <!-- Description --> */}
                <div className="row">
                    <div className="col-sm-4 text-right px-4">Description:</div>
                    <div className="col-sm-6 text-left px-4">
                        <textarea className="form-control" type="text"  rows="3" disabled></textarea>
                    </div>
                </div>
            </div>
        );
    }
}

export default TicketOverviewInfo;
