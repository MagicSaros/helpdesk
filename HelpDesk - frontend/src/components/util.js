import { Component } from 'react';

class Util extends Component {

    static capitalize(string) {
        return string.charAt(0).toUpperCase() + string.substr(1).toLowerCase();
    }

    static toShortDateFormat(milliseconds) {
        return new Date(milliseconds).toLocaleDateString();
    }
}

export default Util;