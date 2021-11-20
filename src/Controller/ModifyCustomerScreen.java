package Controller;

import DAO.UserDaoImpl;
import Model.City;
import Model.Country;
import Model.Customer;
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

public class ModifyCustomerScreen implements Initializable{
    public TextField customerNameTextField;
    public TextField AddressTextField;
    public TextField postalCodeTextField;
    public TextField phoneTextField;
    public ComboBox<Country> countryComboBox;
    public ComboBox<City> cityComboBox;
    public TextField customerIdTextField;

    private static Customer importCustomer = null;
    static void passCustomer(Customer customer){
        importCustomer=customer;

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            setTextField();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setTextField() throws Exception {
        this.customerIdTextField.setText(Integer.toString(importCustomer.getCustomerId()));
        this.customerNameTextField.setText(importCustomer.getCustomerName());
        this.AddressTextField.setText(importCustomer.getAddress());
        this.postalCodeTextField.setText(importCustomer.getPostalCode());
        this.phoneTextField.setText(importCustomer.getPhone());
        this.customerIdTextField.setText(Integer.toString(importCustomer.getCustomerId()));

        System.out.println(importCustomer.getDivision());
        countryComboBox.setItems(UserDaoImpl.getAllCountries());
       // cityComboBox.setItems(UserDaoImpl.getAllCities(importCustomer.));
        int countryId = UserDaoImpl.getCountry(importCustomer.getDivision());

        if(countryId>0) {
            for (Country c : countryComboBox.getItems()) {
                if (countryId == c.getId()) {
                    countryComboBox.setValue(c);
                }
            }
            cityComboBox.setItems(UserDaoImpl.getAllCities(countryId));
            for(City c : cityComboBox.getItems()){
                if(importCustomer.getDivision() == c.getId()){
                    cityComboBox.setValue((c));
                }
            }
        }




    }

    public void onCountryComboBoxAction(ActionEvent actionEvent) throws Exception {
        int id = countryComboBox.getValue().getId();
        System.out.println(id);
        cityComboBox.setItems(UserDaoImpl.getAllCities(id));
    }

    public void onSubmit(ActionEvent actionEvent) throws IOException {
        int countryId = countryComboBox.getValue().getId();
        String countryName = countryComboBox.getValue().getCountryName();
        int cityId = cityComboBox.getValue().getId();
        String cityName = cityComboBox.getValue().getCityName().trim();
        int customerID = Integer.parseInt(customerIdTextField.getText());
        String customerName = customerNameTextField.getText().trim();
        String address = AddressTextField.getText().trim();
        String postalCode = postalCodeTextField.getText().trim();
        String phone = phoneTextField.getText().trim();


        System.out.println(countryId+" "+countryName+" "+customerName+" "+address+" "+postalCode+" "+phone+" "+cityName);
        String query1 = "Update customers  ";
        String query2 = " set Customer_name='"+customerName+"', address='"+address+
                "', postal_code='"+postalCode+"', phone='"+phone+
                "', last_update=now(), division_id="+cityId;
        String query3 = " where customer_id ="+this.customerIdTextField.getText();
        String sqlStatement = query1+query2+query3;
        System.out.println(query1+query2+query3);
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
        stage.setTitle("Home Screen");
        stage.setScene(scene);
        stage.show();
    }
}
