CREATE TABLE feedback(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER,
    rate INTEGER,
    date DATE,
    text TEXT(500),
    ticket_id INTEGER,

    CONSTRAINT users_feedback_fk FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT tickets_feedback_fk FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);