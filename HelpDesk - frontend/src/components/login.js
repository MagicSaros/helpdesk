import React, { Component } from 'react';
import axios from 'axios';

import "./login.css";


class Login extends Component {
    constructor(props) {
        super(props);
        this.state = {
            username: '',
            password: '',
            formErrors: {
                username: '',
                password: ''
            },
            isUsernameValid: false,
            isPasswordValid: false,
            isFormValid: true,
            isDataValid: true
        }
    }

    render() {
        return (
            <div className="container login-container">
                <div className="row justify-content-center">
                    <h1 className="h4 mb-3 font-weight-normal">Login to the Help Desk</h1>
                </div>
                <div className="row justify-content-center">
                    <div className="col-sm-6">
                        <form name="login-form">
                            <div className="form-group row">
                                <label htmlFor="username" className="col-sm-4 col-form-label col-form-label-sm">User Name:</label>
                                <div className="col-sm-8">
                                    <input
                                        className={ `form-control form-control-sm ${this.toggleErrorClass(this.state.formErrors.username)}` }
                                        type="email"
                                        id="username"
                                        name="username"
                                        ref="username"
                                        value={ this.state.username }
                                        onChange={ event => this.updateField(event) }
                                    />
                                </div>
                            </div>
                            <div className="form-group row">
                                <label htmlFor="password" className="col-sm-4 col-form-label col-form-label-sm">Password:</label>
                                <div className="col-sm-8">
                                    <input
                                        className={ `form-control form-control-sm ${this.toggleErrorClass(this.state.formErrors.password)}` }
                                        type="password"
                                        id="password"
                                        name="password"
                                        ref="password"
                                        value={ this.state.password }
                                        onChange={ event => this.updateField(event) }
                                    />
                                </div>
                            </div>
                            <div className={ this.toggleErrorMessageClass() }>
                                <small className="form-text text-muted">
                                    Please make sure you are using a valid email or password
                                </small>
                            </div>
                            <div className="row justify-content-end">
                                <button
                                className="col-sm-4 btn btn-light btn-sm btn-block"
                                type="submit"
                                onClick={ event => this.handleClick(event) }
                                disabled={ !this.state.isFormValid }
                                >Enter</button>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        );
    }

    updateField(event) {
        let name = event.target.name;
        let value = event.target.value;
        this.setState({ [name]: value },
            () => { this.validateField(name, value) });
    }

    handleClick(event) {
        event.preventDefault();

        let self = this;
        let baseUrl = this.props.baseUrl
        let requestUrl = '/login';
        let url = baseUrl + requestUrl;
        let payload = {
            "username": this.state.username,
            "password": this.state.password
        };

        axios
            .post(url, payload)
            .then(response => {
                if (response.status === 200) {
                    self.setState({ isDataValid: true });
                    localStorage.setItem(self.props.authenticationData.user, JSON.stringify(response.data.user));
                    localStorage.setItem(self.props.authenticationData.header, response.data.tokenHeader);
                    localStorage.setItem(self.props.authenticationData.string, response.data.tokenString);
                    self.props.history.push('/tickets')
                }
            })
            .catch(error => {
                if (error.response && error.response.status === 401) {
                    console.log(error.response.data.message);
                    self.setState({ isDataValid: false });
                } else if (error.request) {
                    console.log(error.request);
                } else {
                    console.log('Error', error.message);
                }
            });
    }

    validateField(fieldName, value) {
        let fieldValidationErrors = this.state.formErrors;
        let usernameValid = this.state.isUsernameValid;
        let passwordValid = this.state.isPasswordValid;;
        switch(fieldName) {
            case 'username':
                usernameValid = value.match(/^([\w\W]+)@([\w\W]+\.)+([\w]{2,})$/i) && value.length <= 100;
                fieldValidationErrors.username = usernameValid ? '' : 'invalid';
                break;
            case 'password':
                passwordValid = value.match(/^.*(?=.*[a-z])(?=.*[A-Z])(?=.*[\d])(?=.*[\W]).*$/) && value.length >= 6 && value.length <= 20;
                fieldValidationErrors.password = passwordValid ? '' : 'invalid';
                break;
            default:
                break;
        }
        this.setState({
            formErrors: fieldValidationErrors,
            isUsernameValid: usernameValid,
            isPasswordValid: passwordValid,
        }, this.validateForm);
    }

    validateForm() {
        this.setState({ isFormValid: this.state.isUsernameValid && this.state.isPasswordValid });
    }

    toggleErrorClass(error) {
        return(error.length === 0 ? '' : 'is-invalid');
    }

    toggleErrorMessageClass() {
        return this.state.isFormValid && this.state.isDataValid ? 'd-none' : 'd-block';
    }
}

export default Login;