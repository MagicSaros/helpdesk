import React, { Component } from 'react';

import CommentItem from './commentItem';

class CommentTable extends Component {

    render() {
        return (
            <table className="table table-sm table-bordered">
                <thead className="thead-light">
                    <tr className="d-flex">
                        <td className="table-secondary p-2 col-3">Date</td>
                        <td className="table-secondary p-2 col-3">User</td>
                        <td className="table-secondary p-2 col-6">Comment</td>
                    </tr>
                </thead>
                <tbody>
                    { this.props.comments
                        .map((comment, i) => (<CommentItem key={i} comment={comment} />)) }
                </tbody>
            </table>           
        );
    }
}

export default CommentTable;