CREATE DATABASE IF NOT EXISTS db_swing
  DEFAULT CHARACTER SET utf8mb4
  DEFAULT COLLATE utf8mb4_unicode_ci;

use db_swing;
CREATE TABLE IF NOT EXISTS department (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL
);


CREATE TABLE IF NOT EXISTS employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    birth_date DATE NOT NULL,
    salary DOUBLE NOT NULL,
    department_id INT NOT NULL,


    CONSTRAINT fk_employee_department
        FOREIGN KEY (department_id)
        REFERENCES department(id)
        ON UPDATE CASCADE
        ON DELETE RESTRICT
);
