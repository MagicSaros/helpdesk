import React, { Component } from 'react';
import axios from 'axios';

import AuthorizationService from './../authorizationService';

import emptyStar from './../../img/empty_star.png';
import filledStar from './../../img/filled_star.png';

const displayClass = {
    active: '',
    inactive: 'd-none'
};

class Feedback extends Component {
    constructor(props) {
        super(props);
        this.ticketId = 0;
        this.state = {
            ticket: null,
            rate: null,
            text: '',
            stars: [emptyStar, emptyStar, emptyStar, emptyStar, emptyStar],
            feedback: null,
            isDone: false,
            message: 'Please, rate your satisfaction with the solution:'
        };
    }

    render() {
        let ticket = this.state.ticket ? this.state.ticket : {};
        let id = ticket.id ? ticket.id : '';
        let name = ticket.name ? ticket.name : '';

        return(
            <div className="container">
                <div className="row my-5">
                    <div className="col-sm-2">
                        <button className="btn btn-success btn-custom px-4" onClick={event => this.goBack(event)}>Back</button>
                    </div>
                    <div className="col-sm-4 text-left">
                        <h4>{`Ticket (${id}) - ${name}`}</h4>
                    </div>
                </div>
                <div className="row mt-5">
                    <div className="offset-sm-2 col-sm-6 text-left">
                        <h5>{this.state.message}</h5>
                    </div>
                </div>
                <div className="row my-4">
                    <div className="offset-sm-2 col-sm-6 text-left">
                        <figure className="figure">
                            <img src={this.state.stars[0]} className="figure-img img-fluid" alt="not" width="80px" onClick={() => this.updateRating(1)}/>
                            <figcaption className="figure-caption text-center">1</figcaption>
                        </figure>
                        <figure className="figure">
                            <img src={this.state.stars[1]} className="figure-img img-fluid" alt="not" width="80px" onClick={() => this.updateRating(2)}/>
                            <figcaption className="figure-caption text-center">2</figcaption>
                        </figure>
                        <figure className="figure">
                            <img src={this.state.stars[2]} className="figure-img img-fluid" alt="not" width="80px" onClick={() => this.updateRating(3)}/>
                            <figcaption className="figure-caption text-center">3</figcaption>
                        </figure>
                        <figure className="figure">
                            <img src={this.state.stars[3]} className="figure-img img-fluid" alt="not" width="80px" onClick={() => this.updateRating(4)}/>
                            <figcaption className="figure-caption text-center">4</figcaption>
                        </figure>
                        <figure className="figure">
                            <img src={this.state.stars[4]} className="figure-img img-fluid" alt="not" width="80px" onClick={() => this.updateRating(5)}/>
                            <figcaption className="figure-caption text-center">5</figcaption>
                        </figure>
                    </div>
                </div>
                <div className="row my-5">
                    <div className="offset-sm-2 col-sm-6 text-left">
                        <textarea
                            name="text"
                            id="text"
                            cols="60" rows="10"
                            value={this.state.text}
                            onChange={event => this.updateTextArea(event)}
                            ></textarea>
                    </div>
                </div>
                <div className={`row ${this.toggleButtonDisplay()}`}>
                    <div className="offset-sm-8 col-sm-4">
                        <button className="btn btn-success btn-custom px-5" onClick={event => this.submit(event)}>Submit</button>
                    </div>
                </div>
            </div>
        );
    }

    componentDidMount() {
        this.ticketId = this.props.match.params ? this.props.match.params.ticketId : 0;
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
                    this.setState({ ticket: response.data }, () => this.loadFeedback());
                }
            })
            .catch(error => this.handleRequestError(error));
    }

    loadFeedback() {
        let user = this.getCurrentUser();
        let authToken = this.getAuthToken();

        let userId = user ? user.id : 0;
        let ticketId = this.ticketId;
        
        let url = this.props.baseUrl + `/users/${userId}/tickets/${ticketId}/feedback`;
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
                    let feedback = response.data;
                    let rate = feedback.rate;
                    let text = feedback.text ? feedback.text : '';
                    if (feedback) {
                        this.setState({
                            feedback: response.data,
                            isDone: true,
                            text: text,
                            message: 'Feedback'
                        }, () => this.rate(rate));
                    }
                }
            })
            .catch(error => this.handleRequestError(error));
    }

    updateRating(value) {
        if (!this.state.isDone) {
            this.rate(value);
        }
    }

    rate(value) {
        let stars = [emptyStar, emptyStar, emptyStar, emptyStar, emptyStar];
        stars[value-1] = filledStar;
        this.setState({
            rate: value,
            stars: stars
        });
    }

    updateTextArea(event) {
        if (this.state.isDone) {
            return;
        }

        let text = event.target.value;
        this.setState({ text: text });
    }

    goBack(event) {
        event.preventDefault();
        this.props.history.goBack();
    }

    submit(event) {
        event.preventDefault();

        let rate = this.state.rate;

        if (!rate || this.state.isDone) {
            return;
        }

        let user = this.getCurrentUser();
        let authToken = this.getAuthToken();

        let userId = user ? user.id : 0;
        let ticketId = this.ticketId;
        
        let url = this.props.baseUrl + `/users/${userId}/tickets/${ticketId}/feedback`;
        let config = {
            headers: {
                [authToken.header]: authToken.string,
                'Accept': 'application/json',
                'Content-Type': 'application/json'
            }
        };

        let payload = {
            "rate": rate,
            "text": this.state.text
        };

        this.sendFeedback(url, payload, config);
    }

    sendFeedback(url, payload, config) {
        axios   
            .post(url, payload, config)
            .then(response => {
                if (response.status === 201) {
                    console.log(response);
                }
            })
            .then(() => this.props.history.goBack())
            .catch(error => this.handleRequestError(error));
    }

    toggleButtonDisplay() {
        return !this.state.isDone ? displayClass.active : displayClass.inactive;
    }

    getCurrentUser() {
        return AuthorizationService.getCurrentUser();
    }

    getAuthToken() {
        return AuthorizationService.getAuthorizationToken();
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

export default Feedback;