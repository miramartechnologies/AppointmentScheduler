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

public class ReportCustomer implements Initializable {
    public TableView contactTableView;
    public TableColumn customerIdColumn;
    public TableColumn contactNameColumn;
    public TableColumn appointmentIdColumn;
    public TableColumn titleColumn;
    public TableColumn typeColumn;
    public TableColumn descriptionColumn;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn customerNameColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            contactTableView.setItems(UserDaoImpl.getReportCustomer());
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<>("customerId"));
            contactNameColumn.setCellValueFactory(new PropertyValueFactory<>("contactName"));
            appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<>("appointmentId"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<>("Type"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<>("description"));
            startColumn.setCellValueFactory(new PropertyValueFactory<>("Start"));
            endColumn.setCellValueFactory(new PropertyValueFactory<>("End"));
            customerNameColumn.setCellValueFactory(new PropertyValueFactory<>("customerName"));

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
