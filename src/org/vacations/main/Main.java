/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vacations.main;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.vacations.controller.LoginController;
import org.vacations.controller.MainController;
import org.vacations.controller.VacationController;

/**
 *
 * @author ESTEBAN-GOMEZ
 */
public class Main extends Application {
    private final String PACKAGE_VIEW = "/org/vacations/view/";
    private Stage stage;
    private Scene scene;
    
    @Override
    public void start(Stage stagePrincipal) {
        this.stage= stagePrincipal;
        this.stage.setTitle("Vacations App");
        stagePrincipal.setResizable(false);
        login();
        stagePrincipal.show();
        stagePrincipal.centerOnScreen();
    }
    
    
    public void login(){
        try {
            LoginController login = (LoginController) setScene("LoginView.fxml", 750, 525);
            login.setStagePrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void menuPrincipal(){
        try {
            MainController mainController = (MainController) setScene("MainView.fxml", 624, 681);
            mainController.setStagePrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void vacation(){
        try {
            VacationController vacation = (VacationController) setScene("VacationView.fxml", 780, 450);
            vacation.setStagePrincipal(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
    public Initializable setScene(String fxml, int width,int height) throws Exception{
        Initializable init= null;
        FXMLLoader chargeFXML = new FXMLLoader();
        InputStream fileFXMl= Main.class.getResourceAsStream(PACKAGE_VIEW+fxml);
        chargeFXML.setBuilderFactory(new JavaFXBuilderFactory());
        chargeFXML.setLocation(Main.class.getResource(PACKAGE_VIEW+fxml));
        scene = new Scene(chargeFXML.load(fileFXMl), width, height);
        stage.setScene(scene);
        stage.sizeToScene();
        init = (Initializable) chargeFXML.getController();
        return init;
    }
}
