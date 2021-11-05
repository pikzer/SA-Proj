package CheckingApp.services;

import java.sql.Connection;
import java.sql.DriverManager;

public class DatabaseConnection {
    public Connection databaseLink;

    public Connection getConnection(){

        String databaseName = "check_boq";
        String databaseUser = "root";
        String databasePass = "{Jxj{6~vA~9,<D8wB";
        String url = "jdbc:mysql://localhost/" +databaseName;


        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            databaseLink = DriverManager.getConnection(url, databaseUser, databasePass);

        }catch (Exception e){
            e.printStackTrace();
        }

        return databaseLink;

    }
}
