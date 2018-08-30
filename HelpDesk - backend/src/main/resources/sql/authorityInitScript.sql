CREATE TABLE authority(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER,
    user_role VARCHAR(100),
    
    CONSTRAINT users_authorities_fk FOREIGN KEY (user_id) REFERENCES user (id)
);