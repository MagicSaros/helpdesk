import React, { Component } from 'react';
import axios from "axios";

import Util from './../util';
import AuthorizationService from './../authorizationService';

import './ticketCreation.css';

const FILE_MAX_SIZE = 5242880;
const FILE_NAME_ERROR_MESSAGE = 'The selected file type is not allowed. Please select a file of one of the following types: pdf, png, doc, docx, jpg, jpeg';
const FILE_SIZE_ERROR_MESSAGE = 'The size of attached file should not be greater than 5 Mb. Please select another file';
const FILE_NAME_REGEX = /^([a-zA-Z0-9\s_\\.\-():])+(.pdf|.doc|.docx|.png|.jpeg|.jpg)$/i;
const DATE_FORMAT_REGEX = /^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/]\d{4}$/;

const displayClass = {
    active: '',
    inactive: 'd-none'
};

class TicketEdition extends Component {
    constructor(props) {
        super(props);
        this.ticketId = 0;
        this.state = {
            ticket: null,
            id: '',
            categories: [],
            categoryId: '',
            name: '',
            description: '',
            urgency: '',
            desiredDate: '',
            comment: '',
            attachments: [],
            isDateValid: true,
            isRequiredEmpty: false,
            file: null,
            isFileValid: true,
            isFileSelected: false,
			fileErrorMessage: '',
			isAttachmentUpdated: false
        }
    }

    render() {
		let file = this.state.file ? this.state.file : { name: '' };

        return(
            <div className="container">
                <div className="row my-4">
                    <div className="col-sm-1">
                        <button className="btn btn-success btn-custom" onClick={() => this.showTicketOverview()}>Ticket Overview</button>
                    </div>
                    <div className="offset-sm-1 col-sm-3">
                        <h4>Edit Ticket ({this.state.id})</h4>
                    </div>
                </div>
                <form className="justify-content-center">
                    <div className="form-group row my-4">
                        <label htmlFor="category" className="col-sm-4">
                            Category <span className="text-danger">*</span>
                        </label>
                        <div className="col-sm-4">
                            <select className={`custom-select ${this.toggleErrorClass(!this.state.isRequiredEmpty)}`} name="categoryId" value={this.state.categoryId} onChange={event => this.updateField(event)}>
                                <option defaultValue></option>
                                {this.state.categories.map(category => {
                                    return(<option key={category.id} value={category.id}>{category.name}</option>);
                                })}
                            </select>
                        </div>
                    </div>
                    <div className="form-group row my-4">
                        <label htmlFor="name" className="col-sm-4">
                            Name <span className="text-danger">*</span>
                        </label>
                        <div className="col-sm-4">
                            <input
                                className={`form-control ${this.toggleErrorClass(!this.state.isRequiredEmpty)}`}
                                type="text"
                                name="name"
                                id="name"
                                value={this.state.name}
                                onChange={event => this.updateField(event)}
                            />
                        </div>
                    </div>
                    <div className="form-group row my-4">
                        <label htmlFor="description" className="col-sm-4">Description</label>
                        <div className="col-sm-6">
                            <textarea
                                className="form-control"
                                name="description"
                                id="description"
                                cols="25" rows="6"
                                value={this.state.description}
                                onChange={event => this.updateField(event)}
                                ></textarea>
                        </div>
                    </div>
                    <div className="form-group row my-4">
                        <label htmlFor="urgency" className="col-sm-4">
                            Urgency <span className="text-danger">*</span>
                        </label>
                        <div className="col-sm-4">
                            <select className={`custom-select ${this.toggleErrorClass(!this.state.isRequiredEmpty)}`} name="urgency" value={this.state.urgency} onChange={event => this.updateField(event)}>
                                <option defaultValue></option>
                                <option value="LOW">Low</option>
                                <option value="MEDIUM">Medium</option>
                                <option value="HIGH">High</option>
                                <option value="CRITICAL">Critical</option>
                            </select>
                        </div>
                    </div>
                    <div className="form-group row my-4">
                        <label htmlFor="desiredDate" className="col-sm-4">Desired resoulution date</label>
                        <div className="col-sm-4">
                            <input
                                className={`form-control ${this.toggleErrorClass(this.state.isDateValid)}`}
                                type="text"
                                name="desiredDate"
                                id="desiredDate"
                                value={this.state.desiredDate}
                                onChange={event => this.updateField(event)}
                            />
                        </div>
                    </div>
                    <div className="form-group row mt-4">
                        <label className="col-sm-4">Add attachments</label>
                        <div className="col-sm-2">
                            <label htmlFor="attachment" className="btn btn-light btn-block btn-custom">Browse</label>
                            <input type="file" role="button" className="custom-file-input" id="attachment" onChange={event => this.selectFile(event)}/>
                        </div>
                        <div className="col-sm-4 text-left">
							<div className="row">
								<div className="col-sm-10">
									<a href="" className="badge badge-light" onClick={event => {event.preventDefault()}}>{file.name}</a>
								</div>
								<div className={`col-sm-2 ${this.toggleFileRemoveButtonClass()}`}>
									<button type="button" className="close text-left" aria-label="Close" onClick={() => this.removeFile()}>
										<span aria-hidden="true">&times;</span>
									</button>
								</div>
							</div>
                        </div>
                    </div>
                    <div className="form-group row">
						<div className={`offset-sm-4 col-sm-6 `}>
							<small className="form-text text-muted">
								{this.state.fileErrorMessage}
							</small>
						</div>
                    </div>
                    <div className="form-group row my-4">
                        <label htmlFor="comment" className="col-sm-4">Comment</label>
                        <div className="col-sm-6">
                            <textarea
                                className="form-control"
                                name="comment"
                                id="comment"
                                cols="25" rows="3"
                                value={this.state.comment}
                                onChange={event => this.updateField(event)}
                                ></textarea>
                        </div>
                    </div>
                    <div className="row my-5">
                        <div className="col-sm-3 offset-sm-6">
                            <button className="btn btn-light btn-block btn-custom" onClick={event => this.saveAsDraft(event)}>Save as Draft</button>
                        </div>
                        <div className="col-sm-3">
                            <button className="btn btn-success btn-block btn-custom" onClick={event => this.submit(event)}>Submit</button>
                        </div>
                    </div>
                </form>
            </div>
        );
    }

    componentDidMount() {
        this.ticketId = this.props.location.state ? this.props.location.state.ticketId : null;
        if (this.ticketId) {
            this.loadTicket();
        }
		this.loadCategories();
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
                    this.setState({ ticket: response.data }, () => this.fillFields());
                }
                return response;
            })
            .then(response => this.loadAttachments())
            .catch(error => this.handleRequestError(error));
    }

    loadCategories() {
        let authToken = this.getAuthToken();
        let url = this.props.baseUrl + `/config/tickets/categories`;
        let config = {
            headers: {
                [authToken.header]: authToken.string,
                'Accept': 'application/json' 
            }
        }

        console.log('--- Loading categories ---');
        console.log('Url: ' + url);

        axios
            .get(url, config)
            .then(response => {
                if (response.status === 200) {
                    console.log(response);
                    let categories = response.data;
                    this.setState({
                        categories: categories
                    });
                }
            })
            .catch(error => this.handleRequestError(error));
    }

    loadAttachments() {
        let user = this.getCurrentUser();
        let authToken = this.getAuthToken();

        let userId = user ? user.id : 0;
        let ticketId = this.ticketId;
        
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
					let file = attachments.length !== 0 ? { name: attachments[0].name } : null;
					let isSelected = file ? true : false;
                    this.setState({
						file: file,
						attachments: attachments,
						isFileSelected: isSelected
					});
                }
            })
            .catch(error => this.handleRequestError(error));
    }

    fillFields() {
        let ticket = this.state.ticket;
        let category = ticket.category ? ticket.category : '';
        let name = ticket.name ? ticket.name : '';
        let description = ticket.description ? ticket.description : '';
        let urgency = ticket.urgency ? ticket.urgency : '';
        let date = ticket.desiredResolutionDate ? Util.toShortDateFormat(ticket.desiredResolutionDate) : '';
        this.setState({
            id: this.ticketId,
            categoryId: category.id,
            name: name,
            description: description,
            urgency: urgency,
            desiredDate: date,
        });
    }

    showTicketOverview() {
        this.props.history.push('/overview', { ticketId: this.ticketId });
    }

    updateField(event) {
        let name = event.target.name;
        let value = event.target.value;

        this.setState({
            [name]: value },
            () => this.validateField(name, value));
    }

    validateField(fieldName, value) {
        switch (fieldName) {
            case 'desiredDate':
                this.validateDate(value);
                break;
            case 'name':
            case 'category':
            case 'urgency':
                this.setState({ isRequiredEmpty: false });
                break;
            default:
                break;
        }
    }

    validateDate(value) {
        let re = DATE_FORMAT_REGEX;
        let desiredDate = Util.parseDate(value);
        let now = new Date();
        now.setDate(now.getDate() - 1);

        let isDateValid = !value || (value.match(re) && now.getTime() <= desiredDate.getTime()) ? true : false;

        this.setState({ isDateValid: isDateValid});
    }

    checkIfRequiredIsEmpty(value) {
        let isEmpty = this.state.isRequiredEmpty || (!value.trim() ? true : false);
        this.setState({ isRequiredEmpty: isEmpty });
        return isEmpty;
    }

    toggleErrorClass(isValid) {
        return(isValid ? '' : 'is-invalid');
    }

    toggleFileMessageClass() {
        return !this.state.isFileValid ? displayClass.active : displayClass.inactive;
	}
	
	toggleFileRemoveButtonClass() {
		return this.state.isFileSelected ? displayClass.active : displayClass.inactive;
	}

    selectFile(event) {
		let file = event.target.files[0];

		if (!file) {
			return;
		}

		this.removeFile();

		let isUpdated = this.state.isAttachmentUpdated;
		if (this.state.attachments.length !== 0) {
			isUpdated = true;
		}

        this.setState(
            {
				file: file,
				isAttachmentUpdated: isUpdated
			}, () => this.validateFile());
    }

    validateFile() {
        let file = this.state.file;
        if (file == null) {
            this.setState({
				isFileSelected: false,
				isFileValid: true
			});
            return;
		}
		
		let re = FILE_NAME_REGEX;
		let name = file.name;
		let size = file.size;

		let isNameValid = name.match(re) ? true : false;
		let isSizeValid = 0 < size && size <= FILE_MAX_SIZE;
		let isFileValid = isNameValid && isSizeValid;
		let	errorMessage = isNameValid ? (isSizeValid ? '' : FILE_SIZE_ERROR_MESSAGE ) : FILE_NAME_ERROR_MESSAGE;

        this.setState({
            fileErrorMessage: errorMessage,
            isFileValid: isFileValid,
            isFileSelected: true
		});
    }

    removeFile() {
		let isUpdated = this.state.isAttachmentUpdated;
		if (this.state.attachments.length !== 0) {
			isUpdated = true;
		}

        this.setState({
			file: null,
			fileErrorMessage: '',
			isFileSelected: false,
			isFileValid: true,
			isAttachmentUpdated: isUpdated
        });
    }

    
    getCurrentUser() {
        return AuthorizationService.getCurrentUser();
    }
    
    getAuthToken() {
        return AuthorizationService.getAuthorizationToken();
    }

    saveAsDraft(event) {
        event.preventDefault();

        let ticket = this.state.ticket;
        if (!ticket) {
            return;
        }

        let isSelectedFilesValid = !this.state.isFileSelected || this.state.isFileValid;
        
        if (!isSelectedFilesValid) {
            return;
        }

        let user = this.getCurrentUser();
        let userId = user ? user.id : 0;
        let authToken = this.getAuthToken();
        let ticketId = ticket.id;

        if(!(+ticketId)) {
            return;
        }

        let url = this.props.baseUrl + `/users/${userId}/tickets/${ticketId}`;
        let config = {
            headers: {
                [authToken.header]: authToken.string,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };

        let name = this.state.name.toLowerCase();

        let payload = {
            "id": ticketId,
            "name": name,
            "description": this.state.description,
            "state": "DRAFT"
        };

        if (this.state.desiredDate) payload["desiredResolutionDate"] = Util.parseDate(this.state.desiredDate);
        if (this.state.categoryId) payload["category"] = { "id": this.state.categoryId };
        if (this.state.urgency) payload["urgency"] = this.state.urgency;

        this.sendTicket(url, payload, config);
    }

    submit(event) {
        event.preventDefault();

        let ticket = this.state.ticket;
        if (!ticket) {
            return;
        }

        let isRequiredEmpty = this.checkIfRequiredIsEmpty(this.state.categoryId + '')
            || this.checkIfRequiredIsEmpty(this.state.name)
            || this.checkIfRequiredIsEmpty(this.state.urgency);

        let isSelectedFilesValid = !this.state.isFileSelected || this.state.isFileValid;

        if (isRequiredEmpty || !isSelectedFilesValid) {
            return;
        }

        let user = this.getCurrentUser();
        let userId = user ? user.id : 0;
        let authToken = this.getAuthToken();
        let ticketId = ticket.id;

        if(!(+ticketId)) {
            return;
        }

        let url = this.props.baseUrl + `/users/${userId}/tickets/${ticketId}`;
        let config = {
            headers: {
                [authToken.header]: authToken.string,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };

        let name = this.state.name.toLowerCase();
        
        let payload = {
            "id": ticketId,
            "name": name,
            "description": this.state.description,
            "state": "NEW",
            "category": { "id": this.state.categoryId },
            "urgency": this.state.urgency,
        };

        if (this.state.desiredDate) payload["desiredResolutionDate"] = Util.parseDate(this.state.desiredDate);

        this.sendTicket(url, payload, config);
    }

    sendTicket(url, payload, config) {
        console.log('--- Sending ticket ---');
        console.log('Url: ' + url);
        console.log(payload);

        axios
            .put(url, payload, config)
            .then(response => {
                if (response.status === 200) {
                    console.log(response);
                    return response;
                }
            })
            .then(response => {
                if (this.state.comment) {
                    let urlComment = url + `/comments`;
                    let payload = {
                        "text": this.state.comment
                    };
    
                    this.sendComment(urlComment, payload, config);
				}
                if (this.state.isAttachmentUpdated && this.state.file != null) {
					let attachmentId = this.state.attachments[0].id;
					let urlAttachment = url + `/attachments/${attachmentId}`;
					
					this.deleteAttachment(urlAttachment, config);

					urlAttachment = url + `/attachments`;
			
					let file = this.state.file;
					let formData = new FormData();
					formData.append('file', file);

					config.headers['Content-Type'] = 'multipart/form-data';

					this.sendAttachment(urlAttachment, formData, config);

					// let file = this.state.file;
					// let formData = new FormData();
					// formData.append('file', file);

					// config.headers['Content-Type'] = 'multipart/form-data';

					// this.updateAttachment(urlAttachment, formData, config);
                } else if (this.state.isAttachmentUpdated && this.state.file == null) {
					let attachmentId = this.state.attachments[0].id;
					let urlAttachment = url + `/attachments/${attachmentId}`;

					this.deleteAttachment(urlAttachment, config);
				} else if (!this.state.isAttachmentUpdated && this.state.file != null) {
					let urlAttachment = url + `/attachments`;
					
					let file = this.state.file;
					let formData = new FormData();
					formData.append('file', file);

					config.headers['Content-Type'] = 'multipart/form-data';

					this.sendAttachment(urlAttachment, formData, config);
				}
                return;
            })
            .then(() => this.showTicketOverview())
            .catch(error => this.handleRequestError(error));
            
    }

    sendComment(url, payload, config) {
        console.log('--- Sending comment ---');
        console.log('Url: ' + url);
        console.log(payload);

        axios
            .post(url, payload, config)
            .then(response => {
                if (response.status === 201) {
                    console.log(response);
                }
            })
            .catch(error => this.handleRequestError(error));
	}
	
    sendAttachment(url, payload, config) {
		console.log('--- Uploading file ---');
        console.log('Url: ' + url);
        console.log('File name: ' + payload.get('file').name);
		
        axios
			.post(url, payload, config)
			.then(response => {
				if (response.status === 201) {
					console.log(response);
				}
			})
			.catch(error => this.handleRequestError(error));
	}

    updateAttachment(url, payload, config) {
		console.log('--- Uploading file ---');
        console.log('Url: ' + url);
        console.log('File name: ' + payload.get('file').name);
		
        axios
			.put(url, payload, config)
			.then(response => {
				if (response.status === 200) {
					console.log(response);
				}
			})
			.catch(error => this.handleRequestError(error));
	}
	
	deleteAttachment(url, config) {
		console.log('--- Deleting file from server ---');
		console.log('Url: ' + url);

		axios
			.delete(url, config)
			.then(response => {
				if (response.status === 200) {
					console.log(response);
				}
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

export default TicketEdition;