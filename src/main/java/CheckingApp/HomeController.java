package CheckingApp;

import javafx.fxml.FXML;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;



public class HomeController {
    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    public void initialize(){


    }

    public void buttonSaveTOR(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/saveTOR.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void buttonCreateBOQ(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/selectTorToCreate.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void buttonCheckBOQ(ActionEvent event) throws IOException{
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/authenBOQ.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void buttonAddCus(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/addCus.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}

