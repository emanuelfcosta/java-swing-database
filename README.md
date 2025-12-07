## About

This project demonstrates the use of Java Swing through a JDBC connection with MySQL. Two tables, department and employee, are created with a one-to-many relationship. A department has many employees, and each employee belongs to one department.

![swing-database](https://erp.emanuelcosta.com.br/img-sistemas/swing-database.png)

## Run the Project

1- Run the script located in mysql-connector/script.sql

2- Add the jcalendar-1.4.jar dependency. It is located in the swing-database directory. You can also download it from the  [Maven repository](https://mvnrepository.com/artifact/com.toedter/jcalendar/1.4)  if you prefer.

3- Add the mysql-connector-j-8.3.0.jar dependency. It is located in the swing-database directory. You can also download it from the  [Maven repository](https://mvnrepository.com/artifact/com.mysql/mysql-connector-j/8.3.0)  if you prefer.

5- Update the DatabaseConfig.java file with your database settings.
