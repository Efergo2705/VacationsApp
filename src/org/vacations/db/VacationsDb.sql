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

-- ----------------------------------------- Roles Stored Procedure
-- Add Rol

DELIMITER $$
	Create procedure sp_addRoles(in rol varchar(50))
		begin
			Insert into Roles (name_rol) 
				values (rol);
		end $$
DELIMITER ;

call sp_addRoles('Jefe');
call sp_addRoles('trabajador');

-- List Rol

DELIMITER $$
	Create procedure sp_listRoles()
		Begin
			select R.id_rol, 
            R.name_rol
				from Roles R;
		End $$
DELIMITER ;

-- Update Rol

DELIMITER $$
	Create procedure sp_updateRoles(in cod_rol int ,in rol varchar(50))
		begin
			update Roles
				set name_rol = rol where id_rol = cod_rol;
		end $$
DELIMITER ;


-- Delete Rol

DELIMITER $$
	Create procedure sp_deleteRoles(in cod_rol int)
		begin
			delete from Roles
				where id_rol = cod_rol;
		end $$
DELIMITER ;

-- Search Rol

DELIMITER $$
	Create procedure sp_searchRoles(in cod_rol int)
		begin
			Select R.id_rol,
			R.name_rol
					from Roles R
					where id_rol = cod_rol;
        end $$
DELIMITER ;

-- ----------------------------------------- Users Stored Procedure
-- add users

DELIMITER $$
	Create procedure sp_addUsers(in nickname varchar(50), in mail varchar(100), contra varchar(100), in idRol int)
		begin
			insert into Users (username, email, contrasena, id_rol)
				values (nickname, mail, contra, idRol);
		end $$
DELIMITER ;

call sp_addUsers('efergo','efgl@ggmail.com','123456', 1);

-- list users

DELIMITER $$
	Create procedure sp_listUser()
		begin
			select U.id_user, U.username, U.email, U.contrasena, U.id_rol
				from Users U;
        end $$
DELIMITER ;

-- update Users

DELIMITER $$
	Create procedure sp_updateUser(in idUser int ,in nickname varchar(50), in mail varchar(100), contra varchar(100), in idRol int)
		begin
			update Users
            set username = nickname, email = mail, contrasena = contra, id_rol = idRol
				where id_user = idUser;
        end $$
DELIMITER ;

-- Search Users

DELIMITER $$
	Create procedure sp_searchUser(in idUser int)
		begin
			select U.id_user, U.username, U.email, U.contrasena, U.id_rol
				from Users U
					where id_user = idUser;
        end $$
DELIMITER ;

-- delete Users

DELIMITER $$
	Create procedure sp_deleteUser(in idUser int)
		begin
			delete from Users	
				where id_user = idUser;
        end $$
DELIMITER ;


