import { Component } from 'react';

class Util extends Component {

    static capitalize(string) {
        return string ? string.charAt(0).toUpperCase() + string.substr(1).toLowerCase() : string;
    }

    static toShortDateFormat(milliseconds) {
        return new Date(milliseconds).toLocaleDateString();
    }

    static parseDate(value) {
        let year = value.slice(6, 10);
        let month = value.slice(3, 5) - 1;
        let day = value.slice(0, 2);
        return new Date(year, month, day);
    }
}

export default Util;