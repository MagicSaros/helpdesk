CREATE TABLE token(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    user_id INTEGER,
    access_token VARCHAR(100)
);