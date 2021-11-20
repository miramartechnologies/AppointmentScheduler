package Controller;

import DAO.UserDaoImpl;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ReportType implements Initializable {
    public TableView typeTableView;
    public TableColumn typeColumn;
    public TableColumn monthColumn;
    public TableColumn numberOfAppointments;


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            typeTableView.setItems(UserDaoImpl.getReportType());
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("type"));
            monthColumn.setCellValueFactory(new PropertyValueFactory<>("Month"));
            numberOfAppointments.setCellValueFactory(new PropertyValueFactory<>("total"));

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void onClose(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }
}
