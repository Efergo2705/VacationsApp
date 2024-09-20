/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vacations.controller;

import eu.schudt.javafx.controls.calendar.DatePicker;
import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javax.swing.JOptionPane;
import org.vacations.bean.User;
import org.vacations.db.Conexion;
import org.vacations.main.Main;
/**
 *
 * @author bryan
 */
public class VacationController implements Initializable{
    private Main stagePrincipal;
    private LoginController login = new LoginController();
    private enum operation{NONE, ADD, EDIT};
    private operation typeOperation = operation.NONE;
    private ObservableList <String> listStates;
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
    
     public ObservableList <String> getListState(){
        ArrayList <String>list = new ArrayList<String>();
        list.add("PROCESSING");
        list.add("ACCEPTED");
        list.add("REFUSED");
        return listStates = FXCollections.observableArrayList(list);
    }
    
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
        startDate.disableProperty().setValue(Boolean.TRUE);
        endDate.disableProperty().setValue(Boolean.TRUE);
        gridPane.add(startDate, 1, 1);
        gridPane.add(endDate,3,1);
        cmbIdUser.setItems(login.getUsers());
        cmbState.setItems(getListState());
    }
    
    public void add(){
        switch (typeOperation) {
            case NONE:
                controlsOn();
                btnNuevo.setText("Save");
                btnEditar.disableProperty().setValue(true);
                btnEliminar.disableProperty().setValue(true);
                cmbState.getSelectionModel().select(0);
                typeOperation = typeOperation.ADD;
                break;
            case ADD:
                save();
                btnNuevo.setText("New");
                btnEditar.disableProperty().setValue(false);
                btnEliminar.disableProperty().setValue(false);
                clearControls();
                controlsOff();
                typeOperation = typeOperation.NONE;
            default:
        }
    }
    
    public void cancel(){
        switch (typeOperation) {
            case ADD:
                btnNuevo.setText("New");
                btnEditar.disableProperty().setValue(false);
                btnEliminar.disableProperty().setValue(false);
                clearControls();
                controlsOff();
                typeOperation = typeOperation.NONE;
                break;
            default:
                throw new AssertionError();
        }
    }
    
    public void save(){
        try {
            PreparedStatement ps = Conexion.getInstance().getConexion().prepareCall("{call sp_addVacation(?,?,?,?,?)}");
            ps.setInt(1, ((User)cmbIdUser.getSelectionModel().getSelectedItem()).getId_user());
            ps.setDate(2, new java.sql.Date(startDate.getSelectedDate().getTime()));
            ps.setDate(3, new java.sql.Date(endDate.getSelectedDate().getTime()));
            ps.setString(4, txtAreaComments.getText());
            ps.setString(5, cmbState.getSelectionModel().selectedItemProperty().getValue().toString());
            ps.execute();
            JOptionPane.showMessageDialog(null, "VACATION ADDED", "Vacation",JOptionPane.CANCEL_OPTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public void controlsOn(){
        cmbIdUser.disableProperty().setValue(false);
        startDate.disableProperty().setValue(false);
        endDate.disableProperty().setValue(false);
        cmbState.disableProperty().setValue(true);
        txtAreaComments.editableProperty().setValue(true);
    }
    
    public void controlsOff(){
        cmbIdUser.disableProperty().setValue(true);
        startDate.disableProperty().setValue(true);
        endDate.disableProperty().setValue(true);
        txtAreaComments.editableProperty().setValue(false);
        cmbState.disableProperty().setValue(true);
    }
    
    public void clearControls(){
        txtIdVacation.clear();
        txtAreaComments.clear();
        cmbIdUser.valueProperty().set(null);
        cmbState.valueProperty().set(null);
        startDate.selectedDateProperty().setValue(null);
        endDate.selectedDateProperty().setValue(null);
    }
    
    public void menuPrincipal(){
        stagePrincipal.menuPrincipal();
    }
    
    public Main getStagePrincipal(){
        return stagePrincipal;
    }
    
    public void setStagePrincipal(Main stagePrincipal){
        this.stagePrincipal = stagePrincipal;
    }
}
