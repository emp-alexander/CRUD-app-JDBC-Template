# CRUD приложение для работы с данными книг и читателей
CRUD app. Tech stack: java spring, thymeleaf, HTML, CSS, postgres, JDBC Template

# Код генерации БД:

CREATE TABLE Person(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    fio varchar(150) UNIQUE,
    year_of_birth date NOT NULL
);

CREATE TABLE Book(
    id int GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    name varchar(150) NOT NULL,
    author varchar(100) NOT NULL,
    year numeric NOT NULL,
    person_id int REFERENCES Person(id) ON DELETE RESTRICT ON UPDATE CASCADE 
);
