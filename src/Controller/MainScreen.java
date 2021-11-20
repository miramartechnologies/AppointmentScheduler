package Controller;

import DAO.UserDaoImpl;
import Model.Appointment;
import Model.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import java.io.*;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Locale;
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

public class MainScreen implements Initializable {
    public TextField textUserName;
    public TextField textPassword;
    public Label labelUserName;
    public Label labelPassword;
    private static ObservableList<Appointment> customersAppointments = FXCollections.observableArrayList();

    ObservableList<User> Users= FXCollections.observableArrayList();

    public static Locale getLocale() {
        return Locale.getDefault();
    }

    Locale[] localeLanguages = {
            Locale.ENGLISH,
            Locale.GERMAN
    };

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Locale.setDefault(new Locale("fr"));

    ResourceBundle rb= ResourceBundle.getBundle("resources/Nat", Locale.getDefault());
    if(Locale.getDefault().getLanguage().equals("fr"))
    {
        System.out.println(rb.getString("hello") + " " + rb.getString("world"));
    }
        if(Locale.getDefault().getLanguage().equals("en"))
        {
            System.out.println(rb.getString("hello") + " " + rb.getString("world"));
        }
        System.out.println("Initialized");




   }
    private void setTextFields() throws Exception {

    }

    public void onLoginSubmitButton(ActionEvent actionEvent) throws IOException {
        String userNameInput = textUserName.getText();
        String passwordInput = textPassword.getText();
        String fileName = "login_activity.txt";
        FileWriter fw = new FileWriter(fileName, true);
        PrintWriter outputFile = new PrintWriter(fw);


        try {
            int userResult = UserDaoImpl.getUser(userNameInput, passwordInput);
            if (userResult>0){
                System.out.println("Success Username and PW");
                AddCustomerScreen.passUser(userNameInput);
                UserDaoImpl.passUser(userNameInput);



                AddAppointmentScreen.passUserId(userResult);
                ModifyAppointmentScreen.passUserId(userResult);
                Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 1000, 800);
                stage.setTitle("Appointments Form");
                stage.setScene(scene);
                stage.show();

                LocalDateTime ldtnow = LocalDateTime.now();
                LocalDateTime ldtWindow = ldtnow.plusMinutes(15);
                customersAppointments=UserDaoImpl.getAllAppointment();

                boolean meetingWindowCheck = false;

                for(Appointment x : customersAppointments)
                {

                    meetingWindowCheck=AppointmentCheck.loginMeetingCheck(ldtnow, x.getStartTime());

                   // System.out.println("Time Now: "+ ldtnow+" "+x.getStartTime() +" Time in 15 min "+ldtWindow);
                    if(meetingWindowCheck){
                        break;
                    }
                }

                if (meetingWindowCheck)
                {                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Upcoming Meetings");
                    alert.setHeaderText("Upcoming Meeting");
                    alert.setContentText("You have a meeting in the next 15 minutes");
                    alert.showAndWait();
                }
                else
                {                Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Upcoming Meetings");
                    alert.setHeaderText("No Upcoming Meeting");
                    alert.setContentText("You do not have any meetings in the next 15 minutes");
                    alert.showAndWait();
                }

                outputFile.println(userNameInput+" "+LocalDateTime.now()+" "+"Login Successful");


            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Incorrect Username or Password");
                alert.setHeaderText("alert Header");
                alert.setContentText("wrong username or password");
                alert.showAndWait();
                outputFile.println(userNameInput+" "+LocalDateTime.now()+" "+"Login Unsuccessful");


            }


        } catch (Exception e) {
            e.printStackTrace();
        }
        outputFile.close();
    }
}
