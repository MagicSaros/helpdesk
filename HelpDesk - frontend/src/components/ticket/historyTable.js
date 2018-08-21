import React, { Component } from 'react';

import HistoryItem from './historyItem';

class HistoryTable extends Component {

    render() {
        return (
            <table className="table table-sm table-bordered">
                <thead className="thead-light">
                    <tr className="d-flex">
                        <td className="table-secondary p-2 col-2">Date</td>
                        <td className="table-secondary p-2 col-2">User</td>
                        <td className="table-secondary p-2 col-2">Action</td>
                        <td className="table-secondary p-2 col-6">Description</td>
                    </tr>
                </thead>
                <tbody>
                    { this.props.history
                        .map((action, i) => (<HistoryItem key={i} historyAction={action} />)) }
                </tbody>
            </table>           
        );
    }
}

export default HistoryTable;