/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.vacations.controller;

import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
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
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
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
        NONE, ADD, EDIT, DELETE
    };
    private operation typeOperation = operation.NONE;
    private ObservableList<State> listStates;
    @FXML
    private Button btnRequest;
    @FXML
    private Button btnEdit;
    @FXML
    private Button btnDelete;
    @FXML
    private Button btnCalculateDays;
    @FXML
    private Button btnCancel;
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

    private User userLogued = new LoginController().getUserLogued();
    private ObservableList<Vacation> listVacations;
    private static ArrayList<String> listDaysVacations = new ArrayList<>();
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
                if (date.isBefore(LocalDate.now()) && typeOperation != operation.EDIT) {
                    this.setDisable(true);
                }

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

    public static ArrayList<String> getDaysVacations(){
        return listDaysVacations;
    }
    
    public static void setDaysVacations(ArrayList<String> newDaysVacations){
        listDaysVacations = newDaysVacations;
    }
    
    public boolean getFilterDays(LocalDate date) {
        boolean exists = false;
        try {
            PreparedStatement ps = Conexion.getInstance().getConexion().prepareCall("{call sp_listHolidays}");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                if (rs.getInt("dayHoliday") == date.getDayOfMonth() && rs.getInt("monthHoliday") == date.getMonthValue()) {
                    exists = true;
                }
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return exists;
    }

    public boolean validateDate(LocalDate date) {
        boolean holiday = getFilterDays(date);
        boolean exists = false;
        if (holiday==true) {
            exists = true;
        }
        if (date.getDayOfWeek().equals(DayOfWeek.SATURDAY) || date.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            exists = true;
        }
        return exists;
    }

    public void calculateDaysVacations(LocalDate startDate, LocalDate endDate) {
        int startDayMonth = startDate.getDayOfMonth();
        int startMonth = startDate.getMonthValue();
        int startYear = startDate.getYear();
        int endDayMonth = endDate.getDayOfMonth();
        int endMonth = endDate.getMonthValue();
        int endYear = endDate.getYear();
        while (startDayMonth != endDayMonth || startMonth != endMonth || startYear != endYear) {
            if (startDayMonth <= startDate.getMonth().maxLength()) {
                startDayMonth++;
            }
            if (startDayMonth > startDate.getMonth().maxLength()) {
                startDayMonth = 1;
                startMonth++;
            }
            if (startMonth > 12) {
                startYear++;
                startMonth = 1;
                startDayMonth = 1;
            }
            LocalDate dateValue = LocalDate.of(startYear, startMonth, startDayMonth);
            boolean validate = this.validateDate(dateValue);
            if (validate == false) {    
                listDaysVacations.add(dateValue.getDayOfWeek()+" "
                        +startDayMonth+" "+dateValue.getMonth() +" "+startYear);
            }
        }
    }

    public void getData() {
        tblVacations.setItems(getVacations());
        colIdVacation.setCellValueFactory(new PropertyValueFactory<Vacation, Integer>("id_vacation"));
        colIdUser.setCellValueFactory(new PropertyValueFactory<Vacation, Integer>("id_user"));
        colStartDate.setCellValueFactory(new PropertyValueFactory<Vacation, java.util.Date>("start_date"));
        colEndDate.setCellValueFactory(new PropertyValueFactory<Vacation, java.util.Date>("end_date"));
        colComments.setCellValueFactory(new PropertyValueFactory<Vacation, String>("comments"));
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

    public void getUserCmb() {
        ArrayList<User> userList = new ArrayList<>();
        userList.add(userLogued);
        cmbIdUser.setItems(FXCollections.observableArrayList(userList));
        cmbIdUser.getSelectionModel().select(0);
    }

    public int getIdRole(String role) {
        int idRole = 0;
        switch (role) {
            case "PROCESSING":
                idRole = 1;
                break;
            case "ACCEPTED":
                idRole = 2;
                break;
            case "REFUSED":
                idRole = 3;
                break;
            default:
        }
        return idRole;
    }

    public void getElement() {
        operation stateOperation = typeOperation;
        if (stateOperation != typeOperation.ADD && stateOperation != typeOperation.EDIT) {
            if (tblVacations.getSelectionModel().getSelectedItem() != null) {
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
                btnCalculateDays.setDisable(false);
            }
        } else {
        }
    }

    public void getListDays() {
        calculateDaysVacations(startDate.getValue(),endDate.getValue());
        stagePrincipal.daysVacation();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbState.setItems(getListState());
        cmbState.getSelectionModel().selectFirst();
        startDate.setDayCellFactory(x -> getAllCells());
        endDate.setDayCellFactory(x -> getAllCells());
        endDate.setEditable(false);
        startDate.setEditable(false);
        getData();
        getUserCmb();
    }

    public void add() {
        switch (typeOperation) {
            case NONE:
                controlsOn();
                clearControls();
                getUserCmb();
                btnRequest.setText("CONFIRMAR");
                btnEdit.disableProperty().setValue(true);
                btnDelete.disableProperty().setValue(true);
                cmbState.getSelectionModel().select(0);
                typeOperation = typeOperation.ADD;
                break;
            case ADD:
                save();
                btnRequest.setText("SOLICITAR");
                btnEdit.disableProperty().setValue(false);
                btnDelete.disableProperty().setValue(false);
                getData();
                clearControls();
                controlsOff();
                typeOperation = typeOperation.NONE;
            default:
        }
    }

    public void edit() {
        if (tblVacations.getSelectionModel().getSelectedItem() != null) {
            User userSelected = getUser(((Vacation) tblVacations.getSelectionModel().getSelectedItem()).getId_user());
            switch (typeOperation) {
                case NONE:
                    switch (userLogued.getId_rol()) {
                        case 1:
                            controlsOn();
                            cmbState.setDisable(false);
                            break;
                        case 2:
                            if (userSelected.getId_rol() != 2 && userSelected.getId_rol() != 1) {
                                cmbState.setDisable(false);
                            } else if (userLogued.getId_user() == userSelected.getId_user()) {
                                controlsOn();
                            }
                        case 3:
                            if (userSelected.getId_user() == userLogued.getId_user()) {
                                controlsOn();
                            }
                            break;
                    }
                    btnEdit.setText("ACTUALIZAR");
                    btnRequest.setDisable(true);
                    btnDelete.setDisable(true);
                    typeOperation = typeOperation.EDIT;
                    break;
                case EDIT:
                    update();
                    btnEdit.setText("EDITAR");
                    btnRequest.setDisable(false);
                    btnDelete.disableProperty().setValue(false);
                    getData();
                    clearControls();
                    controlsOff();
                    typeOperation = typeOperation.NONE;
                    break;
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado un elemento");
        }
    }

    public void delete() {
        if (tblVacations.getSelectionModel().getSelectedItem() != null) {
            int op = JOptionPane.showConfirmDialog(null, "¿Deseas eliminar este registro?", "Vacation", JOptionPane.YES_NO_OPTION, JOptionPane.CANCEL_OPTION);
            if (op == JOptionPane.YES_NO_OPTION) {
                try {
                    PreparedStatement ps = Conexion.getInstance().getConexion().prepareCall("{call sp_deleteVacation(?)}");
                    ps.setInt(1, Integer.parseInt(txtIdVacation.getText()));
                    ps.execute();
                    clearControls();
                    getData();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                clearControls();
                getData();
            }
        } else {
            JOptionPane.showMessageDialog(null, "No se ha seleccionado un elemento");
        }
    }

    public void cancel() {
        switch (typeOperation) {
            case ADD:
                btnRequest.setText("SOLICITAR");
                btnEdit.disableProperty().setValue(false);
                btnDelete.disableProperty().setValue(false);
                clearControls();
                controlsOff();
                typeOperation = typeOperation.NONE;
                break;
            case EDIT:
                btnEdit.setText("EDITAR");
                btnRequest.setDisable(false);
                btnDelete.disableProperty().setValue(false);
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

    public void update() {
        try {
            PreparedStatement ps = Conexion.getInstance().getConexion().prepareCall("{call sp_updateVacation(?,?,?,?,?,?)}");
            ps.setInt(1, Integer.valueOf(txtIdVacation.getText()));
            ps.setInt(2, ((User) cmbIdUser.getSelectionModel().getSelectedItem()).getId_user());
            ps.setString(3, startDate.getValue().toString());
            ps.setString(4, endDate.getValue().toString());
            ps.setString(5, txtAreaComments.getText());
            ps.setString(6, cmbState.getSelectionModel().selectedItemProperty().getValue().toString());
            ps.execute();
            JOptionPane.showMessageDialog(null, "Se ha hecho actualizado la solicitud", "Vacation", JOptionPane.CANCEL_OPTION);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void controlsOn() {
        startDate.disableProperty().setValue(false);
        endDate.disableProperty().setValue(false);
        txtAreaComments.editableProperty().setValue(true);
        btnCalculateDays.setDisable(true);
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
        btnCalculateDays.setDisable(true);
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
