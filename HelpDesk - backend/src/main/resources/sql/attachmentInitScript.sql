CREATE TABLE attachment(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    blob BLOB,
    ticket_id INTEGER,
    name VARCHAR(50),

    CONSTRAINT tickets_attachment_fk FOREIGN KEY (ticket_id) REFERENCES ticket (id)
);