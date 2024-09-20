/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vacations.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import org.vacations.main.Main;
/**
 *
 * @author bryan
 */
public class MainController implements Initializable{
    private Main stagePrincipal;
            
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       
    }

    
    public Main getStagePrincipal() {
        return stagePrincipal;
    }

    public void setStagePrincipal(Main stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }
    
    public void getMenuVacation(){
        stagePrincipal.vacation();
    }
}
