DROP DATABASE IF EXISTS SycamoreCalendar;
CREATE DATABASE SycamoreCalendar;

USE SycamoreCalendar;

CREATE TABLE User (
  userID int(5)      primary key not null auto_increment,
  fname  varchar(50) not null,
  lname  varchar(50) not null,
  image  varchar(30) not null
);

CREATE TABLE Event (
	eventID   int(5)       primary key not null auto_increment,
	eventName varchar(100) not null,
	startDate varchar(20)  not null,
	endDate   varchar(20)  not null,
	startTime varchar(20)  not null,
	endTime   varchar(20)  not null,
	userID    int(5)       not null,
	FOREIGN KEY (userID)   REFERENCES User(userID)
);


INSERT INTO User (fname, lname, image) VALUES ('Clay'   , 'Helton'    , 'img/ClayHelton.jpeg');
INSERT INTO User (fname, lname, image) VALUES ('Tee'    , 'Martin'    , 'img/TeeMartin.jpeg');
INSERT INTO User (fname, lname, image) VALUES ('Clancy' , 'Pendergast', 'img/ClancyPendergast.jpeg');
INSERT INTO User (fname, lname, image) VALUES ('Sam'    , 'Darnold'   , 'img/SamDarnold.jpeg');
INSERT INTO User (fname, lname, image) VALUES ('Ronald' , 'Jones'     , 'img/RonaldJones.jpeg');
INSERT INTO User (fname, lname, image) VALUES ('Steven' , 'Mitchell'  , 'img/StevenMitchell.jpeg');
INSERT INTO User (fname, lname, image) VALUES ('Stephen', 'Carr'      , 'img/StephenCarr.jpeg');
INSERT INTO User (fname, lname, image) VALUES ('Porter' , 'Gustin'    , 'img/PorterGustin.jpeg');