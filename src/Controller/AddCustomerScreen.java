package Controller;

import DAO.UserDaoImpl;
import Model.City;
import Model.Country;
import Model.User;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AddCustomerScreen implements Initializable {


    public TextField customerNameTextField;
    public TextField AddressTextField;
    public TextField postalCodeTextField;
    public TextField phoneTextField;
    public ComboBox<Country> countryComboBox;
    public ComboBox<City> cityComboBox;
    public TextField customerIdTextField;

    private static String importUser = null;

    public static void passUser(String user){
        importUser = user;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {


            countryComboBox.setItems(UserDaoImpl.getAllCountries());
            countryComboBox.setPromptText("Select A Country");

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Lambda expression used to combine Strings into SQL queries.
     * @param actionEvent
     * @throws IOException
     */
    public void onSubmit(ActionEvent actionEvent) throws IOException {
        int countryId = countryComboBox.getValue().getId();
        String countryName = countryComboBox.getValue().getCountryName();
        int cityId = cityComboBox.getValue().getId();
        String cityName = cityComboBox.getValue().getCityName().trim();

        String customerName = customerNameTextField.getText().trim();
        String address = AddressTextField.getText().trim();
        String postalCode = postalCodeTextField.getText().trim();
        String phone = phoneTextField.getText().trim();

        System.out.println(importUser);
        System.out.println(countryId+" "+countryName+" "+customerName+" "+address+" "+postalCode+" "+phone+" "+cityName);
        String query1 = "Insert into customers (Customer_name, Address, Postal_Code, Phone, Create_Date, Created_By, last_Update, last_updated_by, Division_id) ";
        String query2 = " Values ('"+customerName+"', '"+address+"', '"+postalCode+"', '"+phone+"', now(), '"+importUser+"', now(), '"+importUser+"', "+cityId +");";

        CombineQuery queries = (s1, s2) -> s1 + s2;

        String sqlStatement = queries.concatQuery(query1,query2);

        UserDaoImpl.addCustomer(sqlStatement);

        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Appointments Form");
        stage.setScene(scene);
        stage.show();

    }

    public void onCancel(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.fxml"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 1000, 800);
        stage.setTitle("Login");
        stage.setScene(scene);
        stage.show();
    }

    public void onCountryComboBoxAction(ActionEvent actionEvent) throws Exception {
        int id = countryComboBox.getValue().getId();
        System.out.println(id);
        cityComboBox.setItems(UserDaoImpl.getAllCities(id));

    }
}
