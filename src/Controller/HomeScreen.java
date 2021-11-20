package Controller;

import DAO.UserDaoImpl;
import Model.Appointment;
import Model.Customer;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.scene.Node;

import javax.swing.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class HomeScreen implements Initializable {
    public TableView tableHomeScreenAppointments;
    public TableView tableHomeScreenCustomers;
    public TableColumn homeCustomerIdColumn;
    public TableColumn homeCustomerNameColumn;
    public TableColumn homeCustomerAddressColumn;
    public TableColumn homeAppointmentIdColumn;
    public TableColumn homeAppointmentDescriptionColumn;
    public TableColumn homeAppointmentTitleColumn;
    public TableColumn homeAppointmentLocationColumn;
    public TableColumn homeAppointmentTypeColumn;
    public TableColumn homeCustomerDivisionColumn;
    public TableColumn homeAppointmentStartTimeColumn;
    public TableColumn homeAppointmentEndTimeColumn;
    public RadioButton radioAll;
    public RadioButton radioMonth;
    public RadioButton radioWeek;
    public Button typeButton;
    public Button contactButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            System.out.println("Customer ID: "+UserDaoImpl.nextCustomerId());
            tableHomeScreenCustomers.setItems(UserDaoImpl.getAllCustomers());
            homeCustomerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            homeCustomerNameColumn.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
            homeCustomerAddressColumn.setCellValueFactory(new PropertyValueFactory<>("address"));
            homeCustomerDivisionColumn.setCellValueFactory(new PropertyValueFactory<>("cityName"));

            tableHomeScreenAppointments.setItems(UserDaoImpl.getAllAppointment());
            homeAppointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            homeAppointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            homeAppointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            homeAppointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
            homeAppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
            homeAppointmentStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
            homeAppointmentEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

            setAppointmentTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void onAddAppointment(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddAppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Add Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onAddCustomer(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddCustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Add Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onModifyAppointment(ActionEvent actionEvent) throws IOException {
        Appointment importAppointment = (Appointment) tableHomeScreenAppointments.getSelectionModel().getSelectedItem();

        if(importAppointment==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("alert Header");
            alert.setContentText("Pleas select appointment to modify");
            alert.showAndWait();
            return;
        }

        ModifyAppointmentScreen.passAppointment(importAppointment);


        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyAppointmentScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Modify Appointment");
        stage.setScene(scene);
        stage.show();
    }

    public void onModifyCustomer(ActionEvent actionEvent) throws IOException {

        Customer importCustomer = (Customer) tableHomeScreenCustomers.getSelectionModel().getSelectedItem();
        if(importCustomer==null)
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("alert Header");
            alert.setContentText("Pleas select Customer to modify");
            alert.showAndWait();
            return;
        }
        ModifyCustomerScreen.passCustomer(importCustomer);

        Parent root = FXMLLoader.load(getClass().getResource("/view/ModifyCustomerScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Modify Customer");
        stage.setScene(scene);
        stage.show();
    }

    public void onDeleteAppointment(ActionEvent actionEvent) throws Exception {
        Appointment deletedAppointment = (Appointment)tableHomeScreenAppointments.getSelectionModel().getSelectedItem();
        int appointmentId = deletedAppointment.getId();
        UserDaoImpl.deleteAppointment(appointmentId);
        tableHomeScreenAppointments.setItems(UserDaoImpl.getAllAppointment());

    }

    public void onDeleteCustomer(ActionEvent actionEvent) throws Exception {
        Customer customer = (Customer) tableHomeScreenCustomers.getSelectionModel().getSelectedItem();

        UserDaoImpl.deleteCustomer(customer.getCustomerId());
        tableHomeScreenCustomers.setItems(UserDaoImpl.getAllCustomers());
        tableHomeScreenAppointments.setItems(UserDaoImpl.getAllAppointment());


    }

    public void setAppointmentTable () throws Exception {
        tableHomeScreenAppointments.setItems(UserDaoImpl.getAllAppointment());
        homeAppointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        homeAppointmentTitleColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
        homeAppointmentDescriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
        homeAppointmentLocationColumn.setCellValueFactory(new PropertyValueFactory<>("location"));
        homeAppointmentTypeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
        homeAppointmentStartTimeColumn.setCellValueFactory(new PropertyValueFactory<>("startTime"));
        homeAppointmentEndTimeColumn.setCellValueFactory(new PropertyValueFactory<>("endTime"));

    }

    public void onHomeScreenExit(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/MainScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void onRadioAll(ActionEvent actionEvent) throws Exception {
        tableHomeScreenAppointments.setItems(UserDaoImpl.getAllAppointment());

    }

    public void onRadioMonth(ActionEvent actionEvent) throws Exception {
        tableHomeScreenAppointments.setItems(UserDaoImpl.getMonthAppointment());

    }

    public void onRadioWeek(ActionEvent actionEvent) throws Exception {
        tableHomeScreenAppointments.setItems(UserDaoImpl.getWeekAppointment());

    }

    public void onTypeButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportType.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Report Type");
        stage.setScene(scene);
        stage.show();
    }

    public void onContactButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/ReportContact.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Report Contact");
        stage.setScene(scene);
        stage.show();
    }

    public void onCustomerButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/view/ReportCustomer.fxml")));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1200, 800);
        stage.setTitle("Report Customer");
        stage.setScene(scene);
        stage.show();
    }
}
