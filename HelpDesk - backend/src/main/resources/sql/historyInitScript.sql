CREATE TABLE history(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    ticket_id INTEGER,
    date TIMESTAMP,
    action VARCHAR(30),
    user_id INTEGER,
    description VARCHAR(50),

    CONSTRAINT users_history_fk FOREIGN KEY (user_id) REFERENCES user (id),
    CONSTRAINT tickets_history_fk FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);