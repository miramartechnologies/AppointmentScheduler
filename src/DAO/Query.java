package DAO;

import java.sql.Connection;
import java.sql.Statement;

import java.sql.DriverManager;

import java.sql.ResultSet;

import static DAO.DBConnection.connection;

public class Query {
    private static String query;
    private static Statement stmt;
    private static ResultSet result;

    public static void makeQuery(String q){
        query =q;
        try{

            stmt=connection.createStatement();
            // determine query execution
            if(query.toLowerCase().startsWith("select"))
                result=stmt.executeQuery(q);
            if(query.toLowerCase().startsWith("delete")||query.toLowerCase().startsWith("insert")||query.toLowerCase().startsWith("update"))
                stmt.executeUpdate(q);

        }
        catch(Exception ex){
            System.out.println("Error: "+ex.getMessage());
        }
    }
    public static ResultSet getResult(){
        return result;
    }
}
