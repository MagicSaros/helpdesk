import React, { Component } from 'react';

import Util from './../util';

class CommentItem extends Component {

    render() {
        let date = this.props.comment ? Util.toShortDateTimeFormat(this.props.comment.date) : '';
        let user = this.props.comment ? this.props.comment.user : '';
        let text = this.props.comment ? this.props.comment.text : '';

        return (
            <tr className="d-flex">
                <td className="p-2 col-3">{date}</td>
                <td className="p-2 col-3">{user.firstName} {user.lastName}</td>
                <td className="p-2 col-6">{text}</td>
            </tr>
        );
    }
}

export default CommentItem;