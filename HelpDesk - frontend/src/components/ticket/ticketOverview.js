import React, { Component } from 'react';
import axios from 'axios';

import AttachmentItem from './attachmentItem';
import AuthorizationService from './../authorizationService';
import Util from './../util';
import CommentTable from './commentTable';
import HistoryTable from './historyTable';

const buttonColorClass = {
    active: 'btn-primary',
    inactive: 'btn-outline-primary'
};

const tableDisplayClass = {
    active: '',
    inactive: 'd-none'
};

const showButtonName = {
    show: 'Show All',
    hide: 'Show Less'
}

class TicketOverview extends Component {
    constructor(props) {
        super(props);
        this.ticketId = 0;
        this.state = {
            ticket: null,
            attachments: [],
            history: [],
            comments: [],
            records: [],
            isClickedHistory: true,
            isClickedComments: false,
            text: '',
            isAllHistoryShowed: false,
            isAllCommentsShowed: false,
            showButtonName: showButtonName.show
        }
    }

    render() {
        let ticket = this.state.ticket ? this.state.ticket : {};
        let emptyUser = { firstName: '', lastName: '' };
        let id = ticket.id ? ticket.id : '';
        let name = ticket.name ? ticket.name : '';
        let createdOn = ticket.createdOn ? Util.toShortDateFormat(ticket.createdOn) : '';
        let state = ticket.state ? Util.capitalize(ticket.state) : '';
        let category = ticket.category ? ticket.category.name : '';
        let urgency = ticket.urgency ? Util.capitalize(ticket.urgency): '';
        let desiredDate = ticket.desiredResolutionDate ? Util.toShortDateFormat(ticket.desiredResolutionDate) : '';
        let owner = ticket.owner ? ticket.owner : emptyUser;
        let approver = ticket.approver ? ticket.approver : emptyUser;
        let assignee = ticket.assignee ? ticket.assignee : emptyUser;
        let description = ticket.description ? ticket.description : '';

        return (
            <div className="container">
                <div className="row">
                    <div className="col-sm-9">
                        <div className="row mt-4 mb-2">
                            <div className="col-sm-2">
                                <button className="btn btn-success btn-custom" onClick={() => this.showTicketsList()}>Ticket List</button>
                            </div>
                            <div className="col-sm-10 text-left">
                                <h3>{`Ticket (${id}) - ${name}`}</h3>
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-sm-4 text-right px-4">Created on:</div>
                            <div className="col-sm-3 text-left px-4">{createdOn}</div>
                        </div>
                        <div className="row">
                            <div className="col-sm-4 text-right px-4">Status:</div>
                            <div className="col-sm-2 text-left px-4">{state}</div>
                            <div className="col-sm-2 text-right">Category:</div>
                            <div className="col-sm-4 text-left">{category}</div>
                        </div>
                        <div className="row">
                            <div className="col-sm-4 text-right px-4">Urgency:</div>
                            <div className="col-sm-3 text-left px-4">{urgency}</div>
                        </div>
                        <div className="row">
                            <div className="col-sm-4 text-right px-4">Desired resolution date:</div>
                            <div className="col-sm-3 text-left px-4">{desiredDate}</div>
                        </div>
                        <div className="row">
                            <div className="col-sm-4 text-right px-4">Owner:</div>
                            <div className="col-sm-3 text-left px-4">{`${owner.firstName} ${owner.lastName}`}</div>
                        </div>
                        <div className="row">
                            <div className="col-sm-4 text-right px-4">Approver:</div>
                            <div className="col-sm-3 text-left px-4">{`${approver.firstName} ${approver.lastName}`}</div>
                        </div>
                        <div className="row">
                            <div className="col-sm-4 text-right px-4">Assignee:</div>
                            <div className="col-sm-3 text-left px-4">{`${assignee.firstName} ${assignee.lastName}`}</div>
                        </div>
                        <div className="row">
                            <div className="col-sm-4 text-right px-4">Attachments:</div>
                            <div className="col-sm-3 text-left px-4">
                                {
                                    this.state.attachments.map((attachment, i) => {
                                        return(<AttachmentItem key={i} attachment={attachment} />);
                                    })
                                }
                            </div>
                        </div>
                        <div className="row">
                            <div className="col-sm-4 text-right px-4">Description:</div>
                            <div className="col-sm-6 text-left px-4">
                                <textarea className="form-control" type="text"  rows="3" value={description} disabled></textarea>
                            </div>
                        </div>
                    </div>
                    <div className="col-sm-3">
                        <div className="row mt-4 mx-4">
                            <button className="btn btn-success btn-custom btn-block" onClick={() => this.showTicketEdition()}>Edit</button>
                        </div>
                        <div className="row mt-2 mx-4">
                            <button className="btn btn-success btn-custom btn-block" onClick={() => this.showFeedBack()}>Feedback</button>
                        </div>
                    </div>
                </div>
                <div className="row mt-5">
                    <div className="col-sm-6">
                        <button
                            name="history"
                            className={`btn btn-primary ${this.toggleButtonColorClass(this.state.isClickedHistory)} btn-block`}
                            onClick={event => this.showHistory(event)}
                            >History</button>
                    </div>
                    <div className="col-sm-6">
                        <button
                            name="comments"
                            className={`btn btn-primary ${this.toggleButtonColorClass(this.state.isClickedComments)} btn-block`}
                            onClick={event => this.showComments(event)}
                            >Comments</button>
                    </div>
                </div>
                <div className="row mb-2 mt-4">
                    <div className="col-sm-3 text-left">
                        <a href="" className="nav-link" onClick={event => this.toggleAllRecordsShowing(event)}>{this.state.showButtonName}</a>
                    </div>
                </div>
                <div className="row">
                    <div className={`col-sm-12 ${this.toggleTableDisplayClass(this.state.isClickedHistory)}`}>
                        <HistoryTable history={this.state.records} />
                    </div>
                    <div className={`col-sm-12 ${this.toggleTableDisplayClass(this.state.isClickedComments)}`}>
                        <CommentTable comments={this.state.records} />
                    </div>
                </div>
                <div className={`row my-2 align-items-end ${this.toggleTableDisplayClass(this.state.isClickedComments)}`}>
                    <div className="col-sm-6">
                        <textarea
                            className="form-control"
                            name="comment"
                            id="comment"
                            rows="4"
                            value={this.state.text}
                            onChange={event => this.updateCommentArea(event)}
                        ></textarea>
                    </div>
                    <div className="col-sm-3">
                        <button className="btn btn-light btn-block px-3 btn-custom" onClick={() => this.addComment()}>Add Comment</button>
                    </div>
                </div>
            </div>
        );
    }

    componentDidMount() {
        this.ticketId = this.props.match.params ? this.props.match.params.ticketId : null;
        if (this.ticketId) {
            this.loadTicket();
        }
    }

    loadTicket() {
        let user = this.getCurrentUser();
        let authToken = this.getAuthToken();

        let userId = user ? user.id : 0;
        let ticketId = this.ticketId;
        
        let url = this.props.baseUrl + `/users/${userId}/tickets/${ticketId}`;
        let config = {
            headers: {
                [authToken.header]: authToken.string,
                'Accept': 'application/json'
            }
        };

        axios
            .get(url, config)
            .then(response => {
                if (response.status === 200) {
                    console.log(response);
                    this.setState({ ticket: response.data }, () => this.loadTicketDetails());
                }
            })
            .catch(error => this.handleRequestError(error));
    }

    loadTicketDetails() {
        this.loadAttachments();
        this.loadHistory();
        this.loadComments();
    }

    loadAttachments() {
        let user = this.getCurrentUser();
        let authToken = this.getAuthToken();

        let userId = user ? user.id : 0;
        let ticketId = this.state.ticket.id ? this.state.ticket.id : 0;
        
        let url = this.props.baseUrl + `/users/${userId}/tickets/${ticketId}/attachments`;
        let config = {
            headers: {
                [authToken.header]: authToken.string,
                'Accept': 'application/json'
            }
        };

        let attachments = [];

        axios
            .get(url, config)
            .then(response => {
                if (response.status === 200) {
                    console.log(response);
                    response.data.map(attachment => attachments.push(attachment));
                    this.setState({ attachments: attachments });
                }
            })
            .catch(error => this.handleRequestError(error));
    }

    loadHistory() {
        let user = this.getCurrentUser();
        let authToken = this.getAuthToken();

        let userId = user ? user.id : 0;
        let ticketId = this.state.ticket.id ? this.state.ticket.id : 0;
        
        let url = this.props.baseUrl + `/users/${userId}/tickets/${ticketId}/history`;
        let config = {
            headers: {
                [authToken.header]: authToken.string,
                'Accept': 'application/json'
            }
        };

        let historyActions = [];

        axios
            .get(url, config)
            .then(response => {
                if (response.status === 200) {
                    console.log(response);
                    response.data.map(action => historyActions.push(action));
                    this.setState({ history: historyActions },
                        () => this.state.isClickedHistory ? this.showHistory() : {});
                }
            })
            .catch(error => this.handleRequestError(error));
    }

    loadComments() {
        let user = this.getCurrentUser();
        let authToken = this.getAuthToken();

        let userId = user ? user.id : 0;
        let ticketId = this.state.ticket.id ? this.state.ticket.id : 0;
        
        let url = this.props.baseUrl + `/users/${userId}/tickets/${ticketId}/comments`;
        let config = {
            headers: {
                [authToken.header]: authToken.string,
                'Accept': 'application/json'
            }
        };

        let comments = [];

        axios
            .get(url, config)
            .then(response => {
                if (response.status === 200) {
                    console.log(response);
                    response.data.map(comment => comments.push(comment));
                    this.setState({ comments: comments },
                        () => this.state.isClickedComments ? this.showComments() : {});
                }
            })
            .catch(error => this.handleRequestError(error));
    }

    showHistory() {
        let records = [];
        if (this.state.isAllHistoryShowed) {
            records = this.state.history;
        } else {
            records = this.state.history.slice(0, 5);
        }

        this.setState({
            records: records,
            isClickedHistory: true,
            isClickedComments: false
        });
    }

    showComments() {
        let records = [];
        if (this.state.isAllCommentsShowed) {
            records = this.state.comments;
        } else {
            records = this.state.comments.slice(0, 5);
        }

        this.setState({
            records: records,
            isClickedHistory: false,
            isClickedComments: true
        });
    }

    toggleAllRecordsShowing(event) {
        event.preventDefault();

        let isAllHistoryShowed = this.state.isAllHistoryShowed;
        if (this.state.isClickedHistory) {
            let name = isAllHistoryShowed ? showButtonName.show : showButtonName.hide;
            this.setState({
                isAllHistoryShowed: !isAllHistoryShowed,
                showButtonName: name
            }, () => this.showHistory());
            return;
        }

        let isAllCommentsShowed = this.state.isAllCommentsShowed;
        if (this.state.isClickedComments) {
            let name = isAllCommentsShowed ? showButtonName.show : showButtonName.hide;
            this.setState({
                isAllCommentsShowed: !isAllCommentsShowed,
                showButtonName: name
            }, () => this.showComments());
            return;
        }
    }

    updateCommentArea(event) {
        this.setState({ text: event.target.value });
    }

    addComment() {
        if (!this.state.ticket) {
            return;
        }

        let user = this.getCurrentUser();
        let authToken = this.getAuthToken();
        let userId = user ? user.id : 0;
        let ticketId = this.state.ticket ? this.state.ticket.id : 0;

        let url = this.props.baseUrl + `/users/${userId}/tickets/${ticketId}/comments`;
        let payload = {
            "text": this.state.text
        };
        let config = {
            headers: {
                [authToken.header]: authToken.string,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };

        axios
            .post(url, payload, config)
            .then(response => {
                if (response.status === 201) {
                    console.log(response);
                    this.setState({ text: '' }, () => this.loadComments());
                }
            })
            .catch(error => this.handleRequestError(error));
    }

    getCurrentUser() {
        return AuthorizationService.getCurrentUser();
    }
    
    getAuthToken() {
        return AuthorizationService.getAuthorizationToken();
    }

    showTicketsList() {
        this.props.history.push('/tickets');
    }

    showTicketEdition() {
        if (!this.state.ticket) {
            return;
        }
        let ticketId = this.state.ticket.id;
        this.props.history.push(`/tickets/${ticketId}/edit`)
    }

    showFeedBack() {
        if (!this.state.ticket) {
            return;
        }
        let ticketId = this.state.ticket.id;
        this.props.history.push(`/tickets/${ticketId}/feedback`);
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

    toggleButtonColorClass(isActive) {
        return isActive ? buttonColorClass.active : buttonColorClass.inactive;
    }

    toggleTableDisplayClass(isActive) {
        return isActive ? tableDisplayClass.active : tableDisplayClass.inactive;
    }
}

export default TicketOverview;