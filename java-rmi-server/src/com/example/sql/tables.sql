--creating tables

CREATE TABLE IF NOT EXISTS user (
    user_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    mail VARCHAR(255) NOT NULL,
    password VARCHAR(255) NOT NULL,
    registrationDate DATE NOT NULL
);

CREATE TABLE IF NOT EXISTS file (
    file_id INTEGER PRIMARY KEY AUTO_INCREMENT,
    fk_owner_id INTEGER NOT NULL,
    name VARCHAR(255) NOT NULL,
    size INTEGER NOT NULL,
    uploadDate DATE NOT NULL
);