CREATE TABLE user(
    id INTEGER PRIMARY KEY AUTO_INCREMENT,
    first_name VARCHAR(25),
    last_name VARCHAR(25),
    role_id INTEGER,
    email VARCHAR(100),
    password VARCHAR(20)
);