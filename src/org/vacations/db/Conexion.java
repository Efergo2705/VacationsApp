package org.vacations.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.DriverManager;

public class Conexion {
    private Connection conexion;
    private static Conexion instancia;
    
    public Conexion(){
        try{
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/VacationsDb?userSSL=false","root","admin");
        }catch(ClassNotFoundException error ){
            error.printStackTrace();
        }catch(InstantiationException error){
            error.printStackTrace();
        }catch(IllegalAccessException error){
            error.printStackTrace();
        }catch(SQLException error){
            error.printStackTrace();
        }catch(Exception error){
            error.printStackTrace();
        }
    }
    
    public static Conexion getInstance(){
        if(instancia == null){
           instancia = new Conexion();
        }
        return instancia;
    }

    public Connection getConexion() {
        return conexion;
    }

    public void setConexion(Connection conexion) {
        this.conexion = conexion;
    }
}
