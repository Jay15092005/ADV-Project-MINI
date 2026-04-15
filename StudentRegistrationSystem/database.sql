CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

CREATE TABLE IF NOT EXISTS students (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    course VARCHAR(100) NOT NULL
);

INSERT INTO students (name, email, course) VALUES 
('Alice Smith', 'alice@example.com', 'Computer Science'),
('Bob Johnson', 'bob@example.com', 'Information Technology'),
('Charlie Brown', 'charlie@example.com', 'Software Engineering');
