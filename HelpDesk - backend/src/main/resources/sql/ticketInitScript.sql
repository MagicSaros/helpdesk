CREATE TABLE ticket(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(50),
    description NVARCHAR,
    created_on DATE,
    desired_resolution_date DATE,
    assignee_id INTEGER,
    owner_id INTEGER,
    state_id INTEGER,
    category_id INTEGER,
    urgency_id INTEGER,
    approver_id INTEGER,

    CONSTRAINT assignees_fk FOREIGN KEY (assignee_id) REFERENCES user (id),
    CONSTRAINT owners_fk FOREIGN KEY (owner_id) REFERENCES user (id),
    CONSTRAINT categories_fk FOREIGN KEY (category_id) REFERENCES category (id),
    CONSTRAINT approvers_fk FOREIGN KEY (approver_id) REFERENCES user (id)
);