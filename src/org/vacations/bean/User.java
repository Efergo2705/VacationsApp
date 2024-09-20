package org.vacations.bean;

public class User {
    private int id_user;
    private String username;
    private String email;
    private String contrasena;
    private int id_rol;
    
    public User(){
        
    }
    
    public User(int idUser, String username, String email, String contrasena, int idRol){
        this.id_user = idUser;
        this.username = username;
        this.email = email;
        this.contrasena = contrasena;
        this.id_rol = idRol;
    }
    
    public User(String username, String contrasena){
        this.username = username;
        this.contrasena = contrasena;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }
    
    @Override
    public String toString(){
        return id_rol+" | "+username;
    }
}