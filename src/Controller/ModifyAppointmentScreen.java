package Controller;

import DAO.UserDaoImpl;
import Model.Appointment;
import Model.Contact;
import Model.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;

public class ModifyAppointmentScreen implements Initializable {
    public TextField appointmentTitle;
    public TextField appointmentDescription;
    public TextField appointmentLocation;
    public TextField appointmentType;
    public TextField appointmentUserId;
    public TextField appointmentId;
    public DatePicker appointmentDate;
    public ComboBox<Contact> appointmentContact;

    public ComboBox<String> appointmentHoursStartTime;
    public ComboBox<String> appointmentMinutesStartTime;
    public ComboBox<String> appointmentHoursEndTime;
    public ComboBox<String> appointmentMinutesEndTime;
    public ComboBox<Customer> appointmentCustomer;
    private int originalCustomerId=-1;
    private int originalContactId = -1;
    private static ObservableList<Appointment> customersAppointments = FXCollections.observableArrayList();


    private final ObservableList<String> hours = FXCollections.observableArrayList("00","01","02","03",
            "04","05","06","07","08","09","10","11","12","13","14","15","16","17","18","19","20"
    ,"21","22","23");
    private final ObservableList<String> minutes = FXCollections.observableArrayList("00","15","30","45");
    private static int importUserId;

    private static Appointment importAppointment = null;
    static void passAppointment(Appointment appointment){
        importAppointment = appointment;
    }
    public static void passUserId(int user){
        importUserId = user;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setTextField();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTextField() throws Exception{
        try {
            appointmentHoursStartTime.setItems(hours);
            appointmentMinutesStartTime.setItems(minutes);
            appointmentHoursEndTime.setItems(hours);
            appointmentMinutesEndTime.setItems(minutes);
            appointmentContact.setItems(UserDaoImpl.getAllContacts());
            appointmentCustomer.setItems(UserDaoImpl.getAllCustomers());
            appointmentTitle.setText(importAppointment.getTitle());
            appointmentDescription.setText(importAppointment.getDescription());
            appointmentLocation.setText(importAppointment.getLocation());
            appointmentType.setText(importAppointment.getType());
            appointmentUserId.setText(String.valueOf(importAppointment.getUserId()));
            appointmentId.setText(String.valueOf(importAppointment.getId()));
            String startHour = String.format("%02d",importAppointment.getStartTime().getHour());
            String endHour = String.format("%02d",importAppointment.getEndTime().getHour());

            appointmentHoursStartTime.setValue(startHour);
            originalContactId=importAppointment.getContactId();
            originalCustomerId=importAppointment.getCustomerId();
            appointmentHoursEndTime.setValue(endHour);
            for(Contact x : appointmentContact.getItems()){
                if(importAppointment.getContactId()==x.getContactId()){
                    appointmentContact.setValue(x);
                }
            }
            for(Customer x : appointmentCustomer.getItems()){
                if(importAppointment.getCustomerId()==x.getCustomerId()){
                    appointmentCustomer.setValue(x);
                }
            }
            if(importAppointment.getStartTime().getHour()==0)
            {
                appointmentHoursStartTime.setValue("00");
            }
            if(importAppointment.getEndTime().getHour()==0)
            {
                appointmentHoursEndTime.setValue("00");
            }
           if(importAppointment.getStartTime().getMinute()==0)
           {
               appointmentMinutesStartTime.setValue("00");
           }
           else
           {
               appointmentMinutesStartTime.setValue(String.valueOf(importAppointment.getStartTime().getMinute()));
           }
            if(importAppointment.getEndTime().getMinute()==0)
            {
                appointmentMinutesEndTime.setValue("00");
            }
            else
            {
                appointmentMinutesEndTime.setValue(String.valueOf(importAppointment.getEndTime().getMinute()));
            }
            appointmentDate.setValue(importAppointment.getStartTime().toLocalDate());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public void onSubmit(ActionEvent actionEvent) throws Exception {
        ZoneId est = ZoneId.of("America/New_York");

        String title =     appointmentTitle.getText().trim();
        String description =     appointmentDescription.getText().trim();
        String location =     appointmentLocation.getText().trim();
        String type =     appointmentType.getText().trim();
        int userId = importUserId;
        int customerId = appointmentCustomer.getValue().getCustomerId();
        LocalDate startDate = appointmentDate.getValue();
        String startTimeHours = appointmentHoursStartTime.getValue().toString();
        String startTimeMinutes = appointmentMinutesStartTime.getValue().toString();
        String endTimeHours = appointmentHoursEndTime.getValue().toString();
        String endTimeMinutes = appointmentMinutesEndTime.getValue().toString();

        if(type=="")
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Pleas enter appointment Type");
            alert.showAndWait();
            return;
        }

        System.out.println(startTimeHours);


        int appointmentId = importAppointment.getId();
        int contactId = appointmentContact.getValue().getContactId();



        System.out.println(startTimeHours);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String startString = startTimeHours+":"+startTimeMinutes;
        String endString = endTimeHours+":"+endTimeMinutes;
        LocalTime ltStart = LocalTime.parse(startString, formatter);
        LocalTime ltEnd =LocalTime.parse(endString, formatter);
        System.out.println(ltStart);
        System.out.println(startDate);
        LocalDateTime localStartDateAndTime = LocalDateTime.of(startDate, ltStart);
        LocalDateTime localendDateAndTime = LocalDateTime.of(startDate, ltEnd);
        System.out.println(localStartDateAndTime);

        Timestamp tsStart = Timestamp.valueOf(localStartDateAndTime);
        Timestamp tsEnd = Timestamp.valueOf(localendDateAndTime);


        String openHoursString = "08:00";
        String closeHoursString = "22:00";
        LocalTime openHours = LocalTime.parse(openHoursString,formatter);
        LocalTime closeHours = LocalTime.parse(closeHoursString, formatter);
        LocalDateTime openLDT = LocalDateTime.of(startDate, openHours);
        LocalDateTime closeLDT = LocalDateTime.of(startDate, closeHours);



        ZonedDateTime openCheck =localStartDateAndTime.atZone(ZoneId.systemDefault());
        ZonedDateTime openCheck2 = openCheck.withZoneSameInstant(est);
        LocalDateTime openBackToLDT = openCheck2.toLocalDateTime();

        ZonedDateTime closeCheck =localendDateAndTime.atZone(ZoneId.systemDefault());
        ZonedDateTime closeCheck2 = closeCheck.withZoneSameInstant(est);
        LocalDateTime closeBackToLDT = closeCheck2.toLocalDateTime();

        int aptId =importAppointment.getId() ;
        customersAppointments = UserDaoImpl.getCustomerAppointment(customerId, aptId);

        for(Appointment x : customersAppointments){
            System.out.println("Start Time "+x.getStartTime()+" End Time "+x.getEndTime());
            boolean startWindowCheck = false;
            boolean endWindowCheck = false;
            boolean outsideWindowCheck = false;
            startWindowCheck = AppointmentCheck.startWindowCheck(localStartDateAndTime, localendDateAndTime,
                    x.getStartTime(), x.getEndTime());
            endWindowCheck = AppointmentCheck.endWindowCheck(localStartDateAndTime, localendDateAndTime,
                    x.getStartTime(), x.getEndTime());

            outsideWindowCheck = AppointmentCheck.outsideWindowCheck(localStartDateAndTime, localendDateAndTime,
                    x.getStartTime(), x.getEndTime());

            if (!startWindowCheck){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Appointment time conflicts with an existing appointment");
                alert.showAndWait();
                return;
            }

            if (!endWindowCheck){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Appointment time conflicts with an existing appointment");
                alert.showAndWait();
                return;
            }

            if (!outsideWindowCheck){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Error");
                alert.setContentText("Appointment time conflicts with an existing appointment");
                alert.showAndWait();
                return;
            }

        }

        if((openBackToLDT.isBefore(openLDT) || openBackToLDT.isAfter(closeLDT)) ||
                (closeBackToLDT.isAfter(closeLDT) || closeBackToLDT.isBefore(openLDT))
        )
        {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("Pleas enter time during Open Hours");
            alert.showAndWait();

            return;


        }
        else if (closeBackToLDT.isBefore(openBackToLDT)){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText("End time cannot be before Start Time");
            alert.showAndWait();

            return;
        }
        else
        {


            UserDaoImpl.updateAppointment(title, description, location, type,
                    tsStart, tsEnd,customerId,userId, contactId, appointmentId);

            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 1000, 800);
            stage.setTitle("Home Screen");
            stage.setScene(scene);
            stage.show();
        }
    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }
}
