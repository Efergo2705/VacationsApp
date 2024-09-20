/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vacations.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import org.vacations.main.Main;
/**
 *
 * @author bryan
 */
public class VacationController implements Initializable{
    private Main stagePrincipal;
     @FXML private Button btnNuevo;
    @FXML private Button btnEditar;
    @FXML private Button btnEliminar;
    @FXML private Button btnCancelar;
    @FXML private TextField txtIdVacation;
    @FXML private TextArea txtAreaComments;
    @FXML private ComboBox cmbIdUser;
    @FXML private ComboBox cmbState;
    @FXML private GridPane gridPane;
    private DatePicker startDate;
    private DatePicker endDate;
    private SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startDate = new DatePicker(Locale.ENGLISH);
        endDate = new DatePicker(Locale.ENGLISH);
        startDate.setDateFormat(formato);
        endDate.setDateFormat(formato);
        startDate.getCalendarView().setShowWeeks(false);
        endDate.getCalendarView().setShowWeeks(false);
        startDate.getCalendarView().todayButtonTextProperty().set("Today");
        endDate.getCalendarView().todayButtonTextProperty().set("Today");
        gridPane.add(startDate, 1, 1);
        gridPane.add(endDate,3,1);
    }
    
    public Main getStagePrincipal(){
        return stagePrincipal;
    }
    
    public void setStagePrincipal(Main stagePrincipal){
        this.stagePrincipal = stagePrincipal;
    }
}
