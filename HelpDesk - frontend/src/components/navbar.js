import React from 'react';
import { Link } from "react-router-dom";

import AuthorizationService from './authorizationService';

const Navbar = () => {
    let user = AuthorizationService.getCurrentUser();
    if (user) {
        return (
            <ul className="nav justify-content-end my-2">
                <li className="nav-item">
                    <Link className="nav-link" to="#" >{user.firstName} {user.lastName}</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="#" >{user.email}</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/logout">Logout</Link>
                </li>
            </ul>
        );
    } else {
        return (
            <ul className="nav justify-content-end my-2">
                <li className="nav-item">
                    <Link className="nav-link" to="/login">Login</Link>
                </li>
            </ul>
        )
    }
};

export default Navbar;