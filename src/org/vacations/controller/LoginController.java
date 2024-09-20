/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vacations.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javax.swing.JOptionPane;
import org.vacations.bean.User;
import org.vacations.db.Conexion;
import org.vacations.main.Main;

/**
 *
 * @author bryan
 */
public class LoginController implements Initializable {

    private Main stagePrincipal;
    private ObservableList<User> listUser;
    @FXML
    private Button btnLogin;
    @FXML
    private TextField txtUsername;
    @FXML
    private PasswordField txtPassword;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public ObservableList<User> getUsers() {
        ArrayList<User> listUsers = new ArrayList<User>();
        try {
            PreparedStatement ps = Conexion.getInstance().getConexion().prepareCall("{call sp_listUser}");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                listUsers.add(new User(rs.getInt("id_user"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("contrasena"),
                        rs.getInt("id_rol")
                ));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return listUser = FXCollections.observableArrayList(listUsers);
    }

    public void login() {
        User login = new User();
        int x = 0;
        boolean limit = false;
        login.setUsername(txtUsername.getText());
        login.setContrasena(txtPassword.getText());
        while (x < getUsers().size()) {
            String user = getUsers().get(x).getUsername();
            String password = getUsers().get(x).getContrasena();
            if (login.getUsername().equals(user)
                    & login.getContrasena().equals(password)) {
                x = getUsers().size();
                limit = true;
                System.out.println("estas logueado");
            } else {
                x++;
            }
        }
        if (limit == false) {
            JOptionPane.showMessageDialog(null, "Verify your credentials");
        }
    }

    public Main getStagePrincipal() {
        return stagePrincipal;
    }

    public void setStagePrincipal(Main stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

}
