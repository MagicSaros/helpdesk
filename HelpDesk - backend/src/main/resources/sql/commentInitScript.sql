CREATE TABLE comment(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER,
    text TEXT(500),
    date DATE,
    ticket_id INTEGER,

    CONSTRAINT users_fk FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT tickets_fk FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);