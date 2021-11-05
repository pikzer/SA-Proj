package CheckingApp;

import CheckingApp.services.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainController {

    @FXML
    private Label showNameLabel;

    public void connectButton(ActionEvent event){
        DatabaseConnection connectNow = new DatabaseConnection();
        Connection connectDB = connectNow.getConnection();

        String connectQuery = "SELECT name_P FROM `microchip`.`product`";

        try {

            Statement statement = connectDB.createStatement();
            ResultSet queryOutput = statement.executeQuery(connectQuery);

            while (queryOutput.next()) {
                showNameLabel.setText(queryOutput.getString("name_P"));
                System.out.println(showNameLabel.toString());
            }

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}