import React, { Component } from 'react';
import FileSaver from 'file-saver/FileSaver';
import axios from 'axios';

import AuthorizationService from './../authorizationService';

class AttachmentItem extends Component {
    constructor(props) {
        super(props);
        this.state = {
            attachment: this.props.attachment
        };
    }

    render() {
        return(
            <div>
                <a href="" className="badge badge-light" title="Download" onClick={event => this.downloadFile(event)}>{this.state.attachment.name}</a>
            </div>
        );
    }

    downloadFile(event) {
        event.preventDefault();

        let user = AuthorizationService.getCurrentUser();
        let authToken = AuthorizationService.getAuthorizationToken();
        let baseUrl = 'http://localhost:8080/api';

        let userId = user ? user.id : 0;
        let ticketId = this.state.attachment.ticket.id ? this.state.attachment.ticket.id : 0;
        let attachmentId = this.state.attachment.id ? this.state.attachment.id : 0;
        let attachmentName = this.state.attachment.name;
        
        let url = baseUrl + `/users/${userId}/tickets/${ticketId}/attachments/${attachmentId}/download`;

        axios({
            url: url,
            method: 'GET',
            headers: { [authToken.header]: authToken.string },
            responseType: 'blob'
        })
            .then((response) => {
                console.log(response);
                FileSaver.saveAs(new Blob([response.data]), attachmentName);
            })
            .catch(error => this.handleRequestError(error));
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

export default AttachmentItem;