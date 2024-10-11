/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vacations.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.vacations.main.Main;

/**
 *
 * @author ESTEBAN-GOMEZ
 */
public class DaysVacationsController implements Initializable {

    private Main stagePrincipal;

    @FXML
    private TableColumn<String, String> colDates;

    @FXML
    private TableView<String> tblDaysVacations;

    private ObservableList<String> daysVacations;

    private VacationController vacationController = new VacationController();

    @FXML
    void menuPrincipal() {
        vacationController.setDaysVacations(new ArrayList<>());
        stagePrincipal.vacation();
    }

    public Main getStagePrincipal() {
        return stagePrincipal;
    }

    public void setStagePrincipal(Main stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }

    public ObservableList<String> getDaysVacations() {
        return daysVacations = FXCollections.observableArrayList(vacationController.getDaysVacations());
    }

    public void getData() {
        tblDaysVacations.setItems(getDaysVacations());
        colDates.setCellValueFactory(cellData ->new SimpleStringProperty(cellData.getValue()));
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        getData();
    }
}
