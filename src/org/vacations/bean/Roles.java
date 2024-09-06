package org.vacations.bean;

public class Roles {

    private int id_rol;
    private String name_rol;

    public Roles() {
    }
    
    public Roles(int idRol, String nameRol){
        this.id_rol = idRol;
        this.name_rol = nameRol;
    }

    public int getId_rol() {
        return id_rol;
    }

    public void setId_rol(int id_rol) {
        this.id_rol = id_rol;
    }

    public String getName_rol() {
        return name_rol;
    }

    public void setName_rol(String name_rol) {
        this.name_rol = name_rol;
    }
    
    

}
