DROP DATABASE IF EXISTS Hangman;
CREATE DATABASE Hangman;

USE Hangman;

CREATE TABLE User (
  userID int(4) primary key not null auto_increment,
  username  varchar(50) not null,
  password  varchar(50) not null,
  wins   int(4) not null,
  losses int(4) not null
);