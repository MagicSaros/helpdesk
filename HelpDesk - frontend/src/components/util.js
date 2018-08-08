import { Component } from 'react';

class Util extends Component {

    static capitalize(string) {
        return string.charAt(0).toUpperCase() + string.substr(1).toLowerCase();
    }

    static toShortDateFormat(milliseconds) {
        return new Date(milliseconds).toLocaleDateString();
    }

    static compareTicketsByUrgency(x, y) {
        let order = ['CRITICAL', 'HIGH', 'MEDIUM', 'LOW'];
        return order.indexOf(x.urgency) - order.indexOf(y.urgency);
    }
    
    static compareTicketsByDateDesc(x, y) {
        return x.desiredResolutionDate - y.desiredResolutionDate;
    }
}

export default Util;