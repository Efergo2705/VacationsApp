/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vacations.controller;

import java.net.URL;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DateCell;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.util.converter.LocalDateStringConverter;
import javax.swing.JOptionPane;
import org.vacations.bean.State;
import org.vacations.bean.User;
import org.vacations.db.Conexion;
import org.vacations.main.Main;
import org.vacations.bean.Vacation;

/**
 *
 * @author ESTEBAN-GOMEZ
 */
public class VacationController implements Initializable {

    private Main stagePrincipal;
    private LoginController login = new LoginController();

    private enum operation {
        NONE, ADD, EDIT
    };
    private operation typeOperation = operation.NONE;
    private ObservableList<State> listStates;
    @FXML
    private Button btnNuevo;
    @FXML
    private Button btnEditar;
    @FXML
    private Button btnEliminar;
    @FXML
    private Button btnCancelar;
    @FXML
    private TextField txtIdVacation;
    @FXML
    private TextArea txtAreaComments;
    @FXML
    private ComboBox cmbIdUser;
    @FXML
    private ComboBox cmbState;
    @FXML
    private GridPane gridPane;
    @FXML
    private DatePicker startDate;
    @FXML
    private DatePicker endDate;
    @FXML
    private TableView tblVacations;
    @FXML
    private TableColumn colIdVacation;
    @FXML
    private TableColumn colIdUser;
    @FXML
    private TableColumn colStartDate;
    @FXML
    private TableColumn colEndDate;
    @FXML
    private TableColumn colComments;
    @FXML
    private TableColumn colState;
    private ObservableList<Vacation> listVacations;
    private SimpleDateFormat formato = new SimpleDateFormat("yyyy/MM/dd");

    public ObservableList<State> getListState() {
        ArrayList<State> list = new ArrayList<State>();
        State state1 = new State();
        State state2 = new State();
        State state3 = new State();
        state1.setState("PROCESSING");
        state2.setState("ACCEPTED");
        state3.setState("REFUSED");
        list.add(state1);
        list.add(state2);
        list.add(state3);
        return listStates = FXCollections.observableArrayList(list);
    }

    public DateCell getAllCells() {
        DateCell cell = new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                this.setDisable(false);
                this.setBackground(null);
                this.setTextFill(Color.BLACK);

                DayOfWeek dayweek = date.getDayOfWeek();

                if (dayweek == DayOfWeek.SATURDAY || dayweek == DayOfWeek.SUNDAY) {
                    this.setTextFill(Color.CADETBLUE);
                    this.setDisable(true);
                }
                boolean existHoliday = getFilterDays(date);
                if (existHoliday) {
                    this.setDisable(true);
                }
            }
        };
        return cell;
    }

    public boolean getFilterDays(LocalDate date) {
        boolean exists = false;
        try {
            PreparedStatement ps = Conexion.getInstance().getConexion().prepareCall("{call sp_listHolidays}");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("dayHoliday") == date.getDayOfMonth() && rs.getInt("monthHoliday")==date.getMonthValue() ) {
                    exists = true;
                }
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    public void getData() {
        tblVacations.setItems(getVacations());
        colIdVacation.setCellValueFactory(new PropertyValueFactory<Vacation, Integer>("id_vacation"));
        colIdUser.setCellValueFactory(new PropertyValueFactory<Vacation, Integer>("id_user"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<Vacation, Integer>("start_date"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<Vacation, Integer>("end_date"));
        colComments.setCellValueFactory(new PropertyValueFactory<Vacation, Integer>("comments"));
        colState.setCellValueFactory(new PropertyValueFactory<Vacation, Integer>("state"));
    }

    public ObservableList<Vacation> getVacations() {
        ArrayList<Vacation> list = new ArrayList<>();
        try {
            PreparedStatement ps = Conexion.getInstance().getConexion().prepareCall("{call sp_listVacation}");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Vacation(
                        rs.getInt("id_vacation"),
                        rs.getInt("id_user"),
                        rs.getDate("start_date"),
                        rs.getDate("end_date"),
                        rs.getString("comments"),
                        rs.getString("state")
                ));
            }
        } catch (Exception error) {
            error.printStackTrace();
        }
        return listVacations = FXCollections.observableArrayList(list);
    }

    public User getUser(int idUser) {
        User user = null;
        try {
            PreparedStatement ps = Conexion.getInstance().getConexion().prepareCall("{call sp_searchUser(?)}");
            ps.setInt(1, idUser);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                user = new User(
                        rs.getInt("id_user"),
                        rs.getString("username"),
                        rs.getString("email"),
                        rs.getString("contrasena"),
                        rs.getInt("id_rol")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    public void getElement() {
        operation stateOperation = typeOperation;
        if (stateOperation != typeOperation.ADD && stateOperation != typeOperation.EDIT && tblVacations.getSelectionModel() != null) {
            txtIdVacation.setText(String.valueOf(((Vacation) tblVacations.getSelectionModel().getSelectedItem())
                    .getId_vacation()));
            cmbIdUser.getSelectionModel().select(getUser(((Vacation) tblVacations.getSelectionModel().getSelectedItem()).getId_user()));
            txtAreaComments.setText(((Vacation) tblVacations.getSelectionModel().getSelectedItem()).getComments());
            startDate.setValue(LocalDate.parse(
                    ((Vacation) tblVacations.getSelectionModel().getSelectedItem()).getStart_date().toString(),
                     DateTimeFormatter.ISO_LOCAL_DATE)
            );
            endDate.setValue(LocalDate.parse(
                    ((Vacation) tblVacations.getSelectionModel().getSelectedItem()).getEnd_date().toString(),
                    DateTimeFormatter.ISO_LOCAL_DATE)
            );
            cmbState.getSelectionModel().select(((Vacation) tblVacations.getSelectionModel().getSelectedItem()).getState());
        }else{
            
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        startDate.setDayCellFactory(cells -> getAllCells());
        endDate.setDayCellFactory(cells -> getAllCells());
        cmbIdUser.setItems(login.getUsers());
        cmbState.setItems(getListState());
        cmbState.getSelectionModel().selectFirst();
        getData();
    }

    public void add() {
        switch (typeOperation) {
            case NONE:
                controlsOn();
                clearControls();
                btnNuevo.setText("CONFIRMAR");
                btnEditar.disableProperty().setValue(true);
                btnEliminar.disableProperty().setValue(true);
                cmbState.getSelectionModel().select(0);
                typeOperation = typeOperation.ADD;
                break;
            case ADD:
                save();
                btnNuevo.setText("SOLICITAR");
                btnEditar.disableProperty().setValue(false);
                btnEliminar.disableProperty().setValue(false);
                getData();
                clearControls();
                controlsOff();
                typeOperation = typeOperation.NONE;
            default:
        }
    }

    public void cancel() {
        switch (typeOperation) {
            case ADD:
                btnNuevo.setText("SOLICITAR");
                btnEditar.disableProperty().setValue(false);
                btnEliminar.disableProperty().setValue(false);
                clearControls();
                controlsOff();
                typeOperation = typeOperation.NONE;
                break;
            default:
                
        }
    }

    public void save() {
        try {
            PreparedStatement ps = Conexion.getInstance().getConexion().prepareCall("{call sp_addVacation(?,?,?,?,?)}");
            ps.setInt(1, ((User) cmbIdUser.getSelectionModel().getSelectedItem()).getId_user());
            ps.setString(2, startDate.getValue().toString());
            ps.setString(3, endDate.getValue().toString());
            ps.setString(4, txtAreaComments.getText());
            ps.setString(5, cmbState.getSelectionModel().selectedItemProperty().getValue().toString());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se ha hecho la solicitud", "Vacation", JOptionPane.CANCEL_OPTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void controlsOn() {
        cmbIdUser.disableProperty().setValue(false);
        startDate.disableProperty().setValue(false);
        startDate.setEditable(true);
        endDate.disableProperty().setValue(false);
        endDate.setEditable(true);
        cmbState.disableProperty().setValue(true);
        txtAreaComments.editableProperty().setValue(true);
    }

    public void controlsOff() {
        cmbIdUser.disableProperty().setValue(true);
        startDate.disableProperty().setValue(true);
        endDate.disableProperty().setValue(true);
        txtAreaComments.editableProperty().setValue(false);
        cmbState.disableProperty().setValue(true);
    }

    public void clearControls() {
        txtIdVacation.clear();
        txtAreaComments.clear();
        cmbIdUser.valueProperty().set(null);
        cmbState.getSelectionModel().select(0);
        startDate.setValue(null);
        endDate.setValue(null);
    }

    public void menuPrincipal() {
        stagePrincipal.menuPrincipal();
    }

    public Main getStagePrincipal() {
        return stagePrincipal;
    }

    public void setStagePrincipal(Main stagePrincipal) {
        this.stagePrincipal = stagePrincipal;
    }
}
