import React from 'react';
import { Link } from "react-router-dom";

import AuthorizationService from './authorizationService';

const Navbar = () => {
    return (
            <ul className="nav justify-content-end my-2">
                <li className="nav-item">
                    <Link className="nav-link" to="#" >{AuthorizationService.getCurrentUser().firstName} {AuthorizationService.getCurrentUser().lastName}</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="#" >{AuthorizationService.getCurrentUser().email}</Link>
                </li>
                <li className="nav-item">
                    <Link className="nav-link" to="/logout">Logout</Link>
                </li>
            </ul>
    );
};

export default Navbar;