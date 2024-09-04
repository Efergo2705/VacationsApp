/*
Esteban Fernando Gómez Loaiza
Vacations App Database
03 - september - 2024
*/

Drop database if exists VacationsDb;
Create database VacationsDb;

use VacationsDb;

Create Table Roles (
    id_rol int primary key auto_increment not null,
    name_rol varchar(50) not null
);

Create Table Users (
    id_user int primary key auto_increment not Null,
    username varchar(50) not null,
    email varchar(100) not null,
    passcode varchar(100) not null,
    id_rol int,
    Foreign Key (id_rol) References Roles(id_rol)
);

Create Table Vacation(
	id_vacation int primary key auto_increment not null,
    id_user int not null,
    start_date date not null,
    end_date date not null,
    comments varchar(200) not null,
	state varchar(50) not null,
    Foreign Key (id_user) references Users(id_user)
);

Create Table Request(
	id_request int primary key auto_increment not Null,
    id_user int not null,
    id_vacation int not null,
    request_date date not null,
    request_state varchar(50),
    Foreign Key (id_user) references Users(id_user),
    Foreign key (id_vacation) references Vacation(id_vacation)
);

