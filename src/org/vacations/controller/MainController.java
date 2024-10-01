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
import javafx.scene.control.MenuItem;
import javax.swing.JOptionPane;
import org.vacations.bean.User;
import org.vacations.main.Main;

/**
 *
 *
 * @author ESTEBAN-GOMEZ
 */
public class MainController implements Initializable {

    private Main stagePrincipal;
    @FXML private MenuItem menuItemVacation;
    @FXML private MenuItem menuItemUser;
    @FXML private MenuItem menuItemRole;
    private User userLogued;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        userLogued = new LoginController().getUserLogued();
        switch (userLogued.getId_rol()) {
            case 1:
                break;
            case 2:
                break;
            case 3:
                menuItemRole.setVisible(false);
                menuItemUser.setVisible(false);
                break;
            default:
        }
    }

    public Main getStagePrincipal() {
        return stagePrincipal;
    }

    public void setStagePrincipal(Main stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

    public void getMenuVacation() {
        stagePrincipal.vacation();
    }

    public void closeSesion() {
        LoginController login = new LoginController();
        login.setUserLogued(null);
        stagePrincipal.login();
    }
}
