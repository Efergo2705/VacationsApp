/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vacations.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.vacations.main.Main;
import org.vacations.bean.User;
/**
 *
 * @author ESTEBAN-GOMEZ
 */
public class UserController implements Initializable {
    @FXML
    private Button btnNuevo;

    @FXML
    private Button btnNuevo1;

    @FXML
    private Button btnNuevo2;

    @FXML
    private TableColumn colEmail;

    @FXML
    private TableColumn colIdRol;

    @FXML
    private TableColumn colIdUser;

    @FXML
    private TableColumn colNameUser;

    @FXML
    private TableView tblUsers;

    private Main stagePrincipal;
    private LoginController loginController = new LoginController();
    
    public void getData(){
        tblUsers.setItems(loginController.getUsers());
        colIdUser.setCellValueFactory(new PropertyValueFactory<User,Integer>("id_user"));
        colNameUser.setCellValueFactory(new PropertyValueFactory<User,String>("username"));
        colEmail.setCellValueFactory(new PropertyValueFactory<User,String>("email"));
        colIdRol.setCellValueFactory(new PropertyValueFactory<User,Integer>("id_rol"));
    }
    
    public Main getStagePrincipal() {
        return stagePrincipal;
    }

    public void setStagePrincipal(Main stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }
    
    public void menuPrincipal(){
        stagePrincipal.menuPrincipal();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }
}
