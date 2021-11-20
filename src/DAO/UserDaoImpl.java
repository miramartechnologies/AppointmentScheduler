package DAO;

import Model.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.*;
import java.time.LocalDateTime;


public class UserDaoImpl {

    private static String importUser = null;

    public static void passUser(String user){
        importUser = user;
    }

    static boolean act;
    public static int getUser(String userName, String password) throws SQLException, Exception{
        // type is name or phone, value is the name or the phone #
        DBConnection.openConnection();
        String sqlStatement="SELECT * FROM client_schedule.users where user_Name ='"
                +userName+"' AND password ='"+password+"';";
        //  String sqlStatement="select FROM address";
        Query.makeQuery(sqlStatement);
        User userResult;
        ResultSet result=Query.getResult();
        if (result.next()){
            int userid=result.getInt("User_ID");
            String userNameG=result.getString("User_Name");
            String passwordG=result.getString("Password");
            userResult= new User(userid, userNameG, passwordG);
            DBConnection.closeConnection();
            return userid;

        }
        else{
            DBConnection.closeConnection();
            return -1;
        }
    }
    public static int getCountry(int id) throws SQLException, Exception{
        int countryId = -1;
        // type is name or phone, value is the name or the phone #
        DBConnection.openConnection();
        String sqlStatement="SELECT * FROM first_level_divisions where division_ID="
                +id+";";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        if (result.next()){
            countryId=result.getInt("Country_ID");
            DBConnection.closeConnection();

        }
        else{
            DBConnection.closeConnection();
        }
        return countryId;

    }

    public static int nextCustomerId () throws SQLException, Exception{
        DBConnection.openConnection();
        String sqlStatement="SELECT Auto_increment FROM information_schema.tables WHERE table_name='customers'";
        Query.makeQuery(sqlStatement);
        int customerIdResult = 0;
        ResultSet result = Query.getResult();
        if(result.next()){
            customerIdResult = result.getInt("AUTO_INCREMENT");
            customerIdResult++;

        }
        return customerIdResult;
    }


    public static ObservableList<Customer> getAllCustomers() throws SQLException, Exception{
        DBConnection.openConnection();
        ObservableList<Customer> allCustomers= FXCollections.observableArrayList();
        try{
            String sqlStatement="select c.*, d.division from customers c inner join first_level_divisions d on c.division_id = d.division_id";
            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlStatement);

            ResultSet result=ps.executeQuery();
            while(result.next()){
                int id=result.getInt("customer_id");
                String name=result.getString("Customer_Name");

                Customer userResult= new Customer(id, name,  result.getString("Address"),
                        result.getString("Postal_Code"), result.getString("phone"),result.getInt("Division_ID"),
                        result.getString("Created_BY"),result.getString("Last_Update"),
                        result.getString("Last_updated_by"), result.getString("Division"));
                allCustomers.add(userResult);
            }
       //     DBConnection.closeConnection();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return allCustomers;
    }

    public static void addAppointment (String title, String description, String location,
                                       String type, Timestamp start, Timestamp end,
                                       int customerId, int userId, int contactId){
        DBConnection.openConnection();
        try{
            Timestamp ts = Timestamp.valueOf(LocalDateTime.now());
            LocalDateTime localTimePlusHour =LocalDateTime.now().plusHours(1);
            Timestamp tsPlusHour = Timestamp.valueOf(localTimePlusHour);
            String sqlStatement1= "Insert into Appointments (Title, Description, Location, Type, Start, " +
                    "End, Customer_ID, User_ID, Contact_ID, Created_by, Last_Updated_By, Last_Update, Create_date ) ";
             String sqlStatement2= "Values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?, now(), now())";
             String sqlQuery = sqlStatement1+sqlStatement2;
             PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlQuery);
             ps.setString(1, title);
             ps.setString(2,description );
             ps.setString(3, location);
             ps.setString(4, type);
             ps.setTimestamp(5,start);
             ps.setTimestamp(6, end);
             ps.setInt(7,customerId);
            ps.setInt(8,userId);
            ps.setInt(9,contactId);
            ps.setString(10, importUser);
            ps.setString(11, importUser);
                ps.executeUpdate();
                System.out.println(ps);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void  updateAppointment(String title, String description, String location,
                                          String type, Timestamp start, Timestamp end,
                                          int customerId, int userId, int contactId, int appointmentId){
        DBConnection.openConnection();
        try{

            String sqlStatement = "Update  Appointments SET Title= ?, Description= ?," +
                    " Location=?, Type=?, Start=?, " +
                    "End=?, Customer_ID=?, User_ID=?, Contact_ID=?, Created_by=?, Last_Updated_By=?, Last_Update =now() " +
                    "where appointment_ID =?";



            PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlStatement);
            ps.setString(1, title);
            ps.setString(2,description );
            ps.setString(3, location);
            ps.setString(4, type);
            ps.setTimestamp(5,start);
            ps.setTimestamp(6, end);
            ps.setInt(7,customerId);
            ps.setInt(8,userId);
            ps.setInt(9,contactId);
            ps.setString(10, importUser);
            ps.setString(11, importUser);
            ps.setInt(12, appointmentId);
            System.out.println(ps);
            ps.executeUpdate();
           // System.out.println(ps);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static ObservableList<Appointment> getAllAppointment() throws SQLException, Exception{
        ObservableList<Appointment> allAppointment= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="select a.* from appointments a";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int id=result.getInt("Appointment_ID");
            String title=result.getString("Title");
            Timestamp ts = result.getTimestamp("Start");
            Timestamp endStamp = result.getTimestamp("End");
            Timestamp lastUpdateStamp = new Timestamp( System.currentTimeMillis());
            if(result.getTimestamp("Last_Update")!=null){
                lastUpdateStamp=result.getTimestamp("Last_Update");
            }


            Appointment userResult= new Appointment(id, title, result.getString("Description"),
                    result.getString("Location"),
                    result.getString("Type"),
                    ts.toLocalDateTime(),
                    endStamp.toLocalDateTime(),
                    result.getString("Create_date"),
                    result.getString("Created_BY"),
                    lastUpdateStamp.toLocalDateTime(),
                    result.getString("Last_updated_by"),
                    result.getInt("customer_id"),
                    result.getInt("user_id"),
                    result.getInt("contact_id")
            );
            allAppointment.add(userResult);

        }
        DBConnection.closeConnection();
        return allAppointment;
    }

    public static ObservableList<Appointment> getCustomerAppointment(int customerId, int appointment_id) throws SQLException, Exception{
        ObservableList<Appointment> allAppointment= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="select a.* from appointments a where customer_id=? and appointment_id != ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlStatement);
        ps.setInt(1, customerId);
        ps.setInt(2, appointment_id);
        System.out.println(ps);
        ps.executeQuery();

        ResultSet result=ps.executeQuery();
        while(result.next()){
            int id=result.getInt("Appointment_ID");
            String title=result.getString("Title");
            Timestamp ts = result.getTimestamp("Start");
            Timestamp endStamp = result.getTimestamp("End");
            Timestamp lastUpdateStamp = new Timestamp( System.currentTimeMillis());
            if(result.getTimestamp("Last_Update")!=null){
                lastUpdateStamp=result.getTimestamp("Last_Update");
            }


            Appointment userResult= new Appointment(id, title, result.getString("Description"),
                    result.getString("Location"),
                    result.getString("Type"),
                    ts.toLocalDateTime(),
                    endStamp.toLocalDateTime(),
                    result.getString("Create_date"),
                    result.getString("Created_BY"),
                    lastUpdateStamp.toLocalDateTime(),
                    result.getString("Last_updated_by"),
                    result.getInt("customer_id"),
                    result.getInt("user_id"),
                    result.getInt("contact_id")
            );
            allAppointment.add(userResult);

        }
        DBConnection.closeConnection();
        return allAppointment;
    }

    public static ObservableList<Appointment> getMonthAppointment() throws SQLException, Exception{
        ObservableList<Appointment> allAppointment= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="SELECT * FROM client_schedule.appointments\n" +
                "where month(start)=month(now())";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int id=result.getInt("Appointment_ID");
            String title=result.getString("Title");
            Timestamp ts = result.getTimestamp("Start");
            Timestamp endStamp = result.getTimestamp("End");
            Timestamp lastUpdateStamp = new Timestamp( System.currentTimeMillis());
            if(result.getTimestamp("Last_Update")!=null){
                lastUpdateStamp=result.getTimestamp("Last_Update");
            }


            Appointment userResult= new Appointment(id, title, result.getString("Description"),
                    result.getString("Location"),
                    result.getString("Type"),
                    ts.toLocalDateTime(),
                    endStamp.toLocalDateTime(),
                    result.getString("Create_date"),
                    result.getString("Created_BY"),
                    lastUpdateStamp.toLocalDateTime(),
                    result.getString("Last_updated_by"),
                    result.getInt("customer_id"),
                    result.getInt("user_id"),
                    result.getInt("contact_id")
            );
            allAppointment.add(userResult);

        }
        DBConnection.closeConnection();
        return allAppointment;
    }
    public static ObservableList<Appointment> getWeekAppointment() throws SQLException, Exception{
        ObservableList<Appointment> allAppointment= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="SELECT * FROM client_schedule.appointments\n" +
                "where week(start)=week(now())";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int id=result.getInt("Appointment_ID");
            String title=result.getString("Title");
            Timestamp ts = result.getTimestamp("Start");
            Timestamp endStamp = result.getTimestamp("End");
            Timestamp lastUpdateStamp = new Timestamp( System.currentTimeMillis());
            if(result.getTimestamp("Last_Update")!=null){
                lastUpdateStamp=result.getTimestamp("Last_Update");
            }


            Appointment userResult= new Appointment(id, title, result.getString("Description"),
                    result.getString("Location"),
                    result.getString("Type"),
                    ts.toLocalDateTime(),
                    endStamp.toLocalDateTime(),
                    result.getString("Create_date"),
                    result.getString("Created_BY"),
                    lastUpdateStamp.toLocalDateTime(),
                    result.getString("Last_updated_by"),
                    result.getInt("customer_id"),
                    result.getInt("user_id"),
                    result.getInt("contact_id")
            );
            allAppointment.add(userResult);

        }
        DBConnection.closeConnection();
        return allAppointment;
    }


    public static ObservableList<Country> getAllCountries() throws SQLException, Exception{
        ObservableList<Country> allCountries= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="select c.* from countries c";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int id=result.getInt("Country_id");
            String country=result.getString("Country");

            Country userResult= new Country(id, country );
            allCountries.add(userResult);
        }
        DBConnection.closeConnection();
        return allCountries;
    }



    public static ObservableList<Contact> getAllContacts() throws SQLException, Exception{
        ObservableList<Contact> allContacts= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="select c.* from contacts c";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int id=result.getInt("Contact_ID");
            String name=result.getString("Contact_name");


            Contact contactResult= new Contact(id, name );
            allContacts.add(contactResult);
        }
        DBConnection.closeConnection();
        return allContacts;
    }

    public static ObservableList<City> getAllCities(int id) throws SQLException, Exception{
        ObservableList<City> allCities= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="select c.* from first_level_divisions c where country_id ="+id;
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int cityId=result.getInt("division_id");
            String division=result.getString("division");
            int countryId = result.getInt("country_id");

            City userResult= new City(cityId, division, countryId );
            allCities.add(userResult);
        }
        DBConnection.closeConnection();
        return allCities;
    }

    public static void addCustomer (String sqlStatement){
        DBConnection.openConnection();
        Query.makeQuery(sqlStatement);

    }

    public static void deleteAppointment(int appointmentId) throws SQLException {
        DBConnection.openConnection();
        String sqlStatement = "Delete from appointments where Appointment_ID = ?";
        PreparedStatement ps = DBConnection.getConnection().prepareStatement(sqlStatement);
        ps.setInt(1,appointmentId);
        ps.executeUpdate();
    }
    public static void deleteCustomer(int customerId) throws SQLException {
        DBConnection.openConnection();
        String deleteAppointmentStatement = "Delete from Appointments where customer_id=  ?";
        PreparedStatement ps1 = DBConnection.getConnection().prepareStatement(deleteAppointmentStatement);
        ps1.setInt(1,customerId);
        ps1.executeUpdate();

        DBConnection.openConnection();
        String sqlStatement =  "Delete from Customers where customer_id=  ?";
        PreparedStatement ps2 = DBConnection.getConnection().prepareStatement(sqlStatement);
        ps2.setInt(1,customerId);
        ps2.executeUpdate();
    }
    public static ObservableList<ReportType> getReportType() throws SQLException, Exception{
        ObservableList<ReportType> reports= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="select type, month (start) as Month, count(appointment_id) as total\n" +
                "from appointments\n" +
                "group by type, month(start)";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){
            int month=result.getInt("month");
            String type=result.getString("type");
            int total = result.getInt("total");
            System.out.println("Num of Appnts "+total);
            ReportType userResult= new ReportType(type, month, total);
            reports.add(userResult);
        }
        DBConnection.closeConnection();
        return reports;
    }

    public static ObservableList<ReportContact> getReportContact() throws SQLException, Exception{
        ObservableList<ReportContact> reports= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="select c.contact_name, a.appointment_id, a.title, a.type, a.description, a.start, a.end, a.customer_id\n" +
                "from appointments a \n" +
                "inner join contacts c\n" +
                "on c.contact_id = a.contact_id\n";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){

            String contactName=result.getString("contact_name");
            int appointmentId=result.getInt("appointment_id");
            String title =result.getString("title");
            String type =result.getString("type");
            String description =result.getString("description");
            int customerId = result.getInt("customer_id");
            Timestamp ts = result.getTimestamp("Start");
            Timestamp endStamp = result.getTimestamp("End");

            ReportContact userResult= new ReportContact(contactName,appointmentId,
                    title, type, description, ts.toLocalDateTime(),
                    endStamp.toLocalDateTime(), customerId);

            reports.add(userResult);
        }
        DBConnection.closeConnection();
        return reports;
    }
    public  static ObservableList<ReportCustomer> getReportCustomer() throws SQLException, Exception{
        ObservableList<ReportCustomer> reports= FXCollections.observableArrayList();
        DBConnection.openConnection();
        String sqlStatement="select x.Customer_Name, c.contact_name, a.appointment_id, a.title, a.type, a.description, a.start, a.end, a.customer_id\n" +
                "from appointments a \n" +
                "inner join contacts c\n" +
                "on c.contact_id = a.contact_id\n" +
                "inner join customers x\n" +
                "on x.Customer_ID = a.Customer_ID\n" +
                "order by Customer_Name ";
        Query.makeQuery(sqlStatement);
        ResultSet result=Query.getResult();
        while(result.next()){

            String contactName=result.getString("contact_name");
            int appointmentId=result.getInt("appointment_id");
            String title =result.getString("title");
            String type =result.getString("type");
            String description =result.getString("description");
            int customerId = result.getInt("customer_id");
            Timestamp ts = result.getTimestamp("Start");
            Timestamp endStamp = result.getTimestamp("End");
            String customerName = result.getString("customer_Name");

            ReportCustomer userResult= new ReportCustomer(contactName,appointmentId,
                    title, type, description, ts.toLocalDateTime(),
                    endStamp.toLocalDateTime(), customerId, customerName);

            reports.add(userResult);
        }
        DBConnection.closeConnection();
        return reports;
    }
}




