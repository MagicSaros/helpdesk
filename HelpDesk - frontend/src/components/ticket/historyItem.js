import React, { Component } from 'react';

import Util from './../util';

class HistoryItem extends Component {

    render() {
        let date = this.props.historyAction ? Util.toShortDateTimeFormat(this.props.historyAction.date) : '';
        let user = this.props.historyAction ? this.props.historyAction.user : '';
        let action = this.props.historyAction ? this.props.historyAction.action : '';
        let description = this.props.historyAction ? this.props.historyAction.description : '';

        return (
            <tr className="d-flex">
                <td className="p-2 col-2">{date}</td>
                <td className="p-2 col-2">{user.firstName} {user.lastName}</td>
                <td className="p-2 col-2">{action}</td>
                <td className="p-2 col-6">{description}</td>
            </tr>
        );
    }
}

export default HistoryItem;