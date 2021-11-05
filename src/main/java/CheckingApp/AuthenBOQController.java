package CheckingApp;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class AuthenBOQController {

    @FXML
    private PasswordField pinPasswordField ;

    private Stage stage;
    private Scene scene;
    private Parent root;



    @FXML
    public void initialize()  {

    }

    public void handleLoginButton(ActionEvent event) throws IOException {
        if(pinPasswordField.getText().equals("123456")){
            root = FXMLLoader.load(getClass().getResource("/CheckingApp/checkBOQ.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
        else{
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("รหัสไม่ถูกต้อง! กรุณาลองใหม่...");
            a.show();
            root = FXMLLoader.load(getClass().getResource("/CheckingApp/authenBOQ.fxml"));
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }

    public void handleBackButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
