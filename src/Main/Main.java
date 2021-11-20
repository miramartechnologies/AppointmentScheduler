package main;

import DAO.DBConnection;
import helper.JDBC;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.Timestamp;
import java.time.*;
import java.time.format.DateTimeFormatter;

/**
 * @author Albert Kong
 *
 *
 *  Inventory Program that allows you to add parts and products.
 *  each product can contain the parts that make up the product,
 *  similar to a Bill of Materials (BOM)
 *
 * FUTURE ENHANCEMENT
 * a future enhancement to this program is to automatically delete a part from the
 * product part page if the part itself is deleted from the main screen.
 * if a part is no longer part of inventory then it shouldn't be part of a
 * product either
 */

//Launches Application and Mainscreen.fxml

public class Main extends Application {
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
        stage.setTitle("Main Screen");
        stage.setScene(new Scene(root, 1000,800));
        stage.show();
    }

    public static void main(String[] args){
        Timestamp ts = Timestamp.valueOf(LocalDateTime.now());

        //LocalDateTime
        LocalDateTime date = LocalDateTime.now();
        ZoneId est = ZoneId.of("America/New_York");
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
        String startString = "08:00";
        String closeHoursString = "22:00";
        LocalTime startTimeString = LocalTime.parse(startString, formatter);

        LocalDate ld = LocalDateTime.now().toLocalDate();
        LocalDateTime ltStart = LocalDateTime.of(ld, startTimeString);







        ZonedDateTime date1 = LocalDateTime.now().atZone(ZoneId.systemDefault());
        ZonedDateTime date2 = date1.withZoneSameInstant(est);
        LocalDateTime backToLDT = date2.toLocalDateTime();

        System.out.println("LDT "+date);
        System.out.println("EST "+date2);
        System.out.println(ltStart+" "+ltStart.atZone(est)+"LT Start at est");
        System.out.println("Back to LDT but in EST "+backToLDT);
        if(backToLDT.isAfter(LocalDateTime.now()))
        {
            System.out.println("Yes it is");
        }
        else{
            System.out.println("No its not");
        }



        String.valueOf(ts);
        ts.toLocalDateTime();

        DBConnection.openConnection();
        launch(args);

        DBConnection.closeConnection();
    }

}
