import React, { Component } from 'react';
import axios from "axios";

import Util from './../util';

import './ticketCreation.css';

const FILE_MAX_SIZE = 5242880;
const FILE_NAME_ERROR_MESSAGE = 'The selected file type is not allowed. Please select a file of one of the following types: pdf, png, doc, docx, jpg, jpeg';
const FILE_SIZE_ERROR_MESSAGE = 'The size of attached file should not be greater than 5 Mb. Please select another file';
const FILE_NAME_REGEX = /^([a-zA-Z0-9\s_\\.\-():])+(.pdf|.doc|.docx|.png|.jpeg|.jpg)$/i;
const DATE_FORMAT_REGEX = /^(0[1-9]|[12][0-9]|3[01])[/](0[1-9]|1[012])[/]\d{4}$/;

class TicketCreation extends Component {
    constructor(props) {
        super(props);
        this.state = {
            categories: [],
            categoryId: '',
            name: '',
            description: '',
            urgency: '',
            desiredDate: '',
            comment: '',
            isDateValid: true,
            isRequiredEmpty: false,
            file: null,
            isFileValid: true,
            isFileSelected: false,
            fileMessage: ''
        }
    }

    render() {
        return(
            <div className="container">
                <div className="row my-4">
                    <div className="col-sm-1">
                        <button className="btn btn-success btn-custom" onClick={() => this.showTicketsList()}>Ticket List</button>
                    </div>
                    <div className="offset-sm-1 col-sm-3">
                        <h3>Create New Ticket</h3>
                    </div>
                </div>
                <form className="justify-content-center">
                    <div className="form-group row my-4">
                        <label htmlFor="category" className="col-sm-4">
                            Category <span className="text-danger">*</span>
                        </label>
                        <div className="col-sm-4">
                            <select className={`custom-select ${this.toggleErrorClass(!this.state.isRequiredEmpty)}`} name="categoryId" onChange={event => this.updateField(event)}>
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
                    <div className="form-group row my-4">
                        <label className="col-sm-4">Add attachments</label>
                        <div className="col-sm-2">
                            <label htmlFor="attachment" className="btn btn-light btn-block btn-custom">Browse</label>
                            <input type="file" role="button" className="custom-file-input" id="attachment" onChange={event => this.selectFile(event)}/>
                        </div>
                        <div className="col-sm-4 text-left">
                            <div className={this.toggleFileMessageClass()}>
                                <button type="button" className="close text-left" aria-label="Close" onClick={() => this.removeFile()}>
                                    <span aria-hidden="true">&times;</span>
                                </button>
                                <small className="form-text text-muted">
                                    {this.state.fileMessage}
                                </small>
                            </div>
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
                                vlaue={this.state.comment}
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
        this.loadCategories();
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

    showTicketsList() {
        this.props.history.push('/tickets');
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
        return this.state.isFileSelected ? 'd-block' : 'd-none';
    }

    selectFile(event) {
        let file = event.target.files[0];
        this.setState(
            { file: file },
            () => this.validateFile(file));
    }

    validateFile(file) {
        if (file == null) {
            this.setState({ isFileSelected: false });
            return;
        }

        let name = file.name;
        let size = file.size;
        let re = FILE_NAME_REGEX;

        let isNameValid = name.match(re) ? true : false;
        let isSizeValid = 0 < size && size <= FILE_MAX_SIZE;
        let isFileValid = isNameValid && isSizeValid;
        let fileMessage = isNameValid ? (isSizeValid ? name : FILE_SIZE_ERROR_MESSAGE ) : FILE_NAME_ERROR_MESSAGE;

        this.setState({
            fileMessage: fileMessage,
            isFileValid: isFileValid,
            isFileSelected: true
        });
    }

    removeFile() {
        this.setState({
            file: null,
            fileMessage: '',
            isFileValid: true,
            isFileSelected: false
        });
    }

    
    getCurrentUser() {
        let localStorageData = this.getLocalStorageData();
        return localStorageData.user;
    }
    
    getAuthToken() {
        let localStorageData = this.getLocalStorageData();
        return localStorageData.authToken;
    }

    getLocalStorageData() {
        let user = JSON.parse(localStorage.getItem(this.props.authenticationData.user));
        let header = localStorage.getItem(this.props.authenticationData.header);
        let string = localStorage.getItem(this.props.authenticationData.string);

        return {
            user: user,
            authToken: {
                header: header,
                string: string
            }
        };
    }


    saveAsDraft(event) {
        event.preventDefault();

        let isSelectedFileValid = !this.state.isFileSelected || this.state.isFileValid;
        
        if (!isSelectedFileValid) {
            return;
        }

        let user = this.getCurrentUser();
        let userId = user ? user.id : 0;
        let authToken = this.getAuthToken();

        let url = this.props.baseUrl + `/users/${userId}/tickets`;
        let config = {
            headers: {
                [authToken.header]: authToken.string,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };

        let name = this.state.name.toLowerCase();
        let urgency = this.state.urgency ? this.state.urgency : 'LOW';
        let categoryId = this.state.categoryId ? this.state.categoryId : 1;

        let payload = {
            "category": {
                "id": categoryId
            },
            "name": name,
            "urgency": urgency,
            "state": "DRAFT"
        };

        if (this.state.description) payload["description"] = this.state.description;
        if (this.state.desiredDate) payload["desiredResolutionDate"] = Util.parseDate(this.state.desiredDate);

        this.sendTicket(url, payload, config);
    }

    submit(event) {
        event.preventDefault();

        let isRequiredEmpty = this.checkIfRequiredIsEmpty(this.state.categoryId)
            || this.checkIfRequiredIsEmpty(this.state.name)
            || this.checkIfRequiredIsEmpty(this.state.urgency);

        let isSelectedFileValid = !this.state.isFileSelected || this.state.isFileValid;

        if (isRequiredEmpty || !isSelectedFileValid) {
            return;
        }

        let user = this.getCurrentUser();
        let userId = user ? user.id : 0;
        let authToken = this.getAuthToken();

        let url = this.props.baseUrl + `/users/${userId}/tickets`;
        let config = {
            headers: {
                [authToken.header]: authToken.string,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };

        let name = this.state.name.toLowerCase();
        
        let payload = {
            "category": {
                "id": this.state.categoryId
            },
            "name": name,
            "urgency": this.state.urgency,
            "state": "NEW"
        };
        
        if (this.state.description) payload["description"] = this.state.description;
        if (this.state.desiredDate) payload["desiredResolutionDate"] = Util.parseDate(this.state.desiredDate);

        this.sendTicket(url, payload, config);
    }

    sendTicket(url, payload, config) {
        console.log('--- Sending ticket ---');
        console.log('Url: ' + url);
        console.log(payload);

        axios
            .post(url, payload, config)
            .then(response => {
                if (response.status === 201) {
                    console.log(response);
                    return response;
                }
            })
            .then(response => {
                if (this.state.comment) {
                    let ticketId = response.data.id;
                    let urlComment = url + `/${ticketId}/comments`;
                    let payload = {
                        "text": this.state.comment
                    };
    
                    this.sendComment(urlComment, payload, config);
                }
                if (this.state.file) {
                    let ticketId = response.data.id;
                    let urlAttachment = url + `/${ticketId}/attachments`;

                    let formData = new FormData();
                    formData.append('file', this.state.file);
                    config.headers['Content-Type'] = 'multipart/form-data';

                    this.sendAttachment(urlAttachment, formData, config);
                }
                return;
            })
            .then(() => this.showTicketsList())
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

export default TicketCreation;