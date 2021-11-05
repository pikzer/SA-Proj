package CheckingApp;

import CheckingApp.services.DataList;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import java.io.IOException;

public class addCusController {

    private Stage stage;
    private Scene scene;
    private Parent root;

    @FXML
    Button editButton, addButton ;
    @FXML
    TextField nameTextField, telTexField, emailTextField ;
    @FXML
    TableView cusTable ;


    DataList dataList ;
    ObservableList<Customer> customerObservableList ;
    Customer customer, oldCus ;

    private static boolean isValidEmailAddress(String email) {
        boolean result = true;
        try {
            InternetAddress emailAddr = new InternetAddress(email);
            emailAddr.validate();
        } catch (AddressException ex) {
            result = false;
        }
        return result;
    }

    @FXML
    public void initialize(){
        dataList = new DataList() ;
        dataList.getCusFromDatabase();
        showTable() ;
        editButton.setDisable(true);
        addButton.setDisable(false);
        telTexField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    telTexField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        cusTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                showSelectedCus((Customer) newValue) ;
            }
        });
    }

    public void showTable(){
        cusTable.getColumns().clear();
        customerObservableList = FXCollections.observableList(dataList.getCustomerArrayList()) ;
        TableColumn idCol = new TableColumn("ID");
        TableColumn nameCol = new TableColumn("Name");
        TableColumn phoneCol = new TableColumn("Phone");
        TableColumn emailCol = new TableColumn("Email");
        idCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("CS_Id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("CS_Name"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("CS_Phone"));
        emailCol.setCellValueFactory(new PropertyValueFactory<Customer,String>("CS_Email"));
        cusTable.getColumns().addAll(idCol,nameCol,phoneCol,emailCol) ;
        cusTable.setItems(customerObservableList);
    }


    public void showSelectedCus(Customer newValue){
        editButton.setDisable(false);
        addButton.setDisable(true);
        nameTextField.setText(newValue.getCS_Name());
        telTexField.setText(newValue.getCS_Phone());
        emailTextField.setText(newValue.getCS_Email());
        oldCus = newValue ;
    }

    public void handleAnchorClick(){
        if(cusTable.getSelectionModel().getSelectedItem() != null)
            clearAll();
    }

    public void clearAll(){
        cusTable.getSelectionModel().clearSelection();
        nameTextField.clear();
        telTexField.clear();
        emailTextField.clear();
        initialize();
    }

    public void handleAddButton(){
        if(nameTextField.getText().isEmpty() || telTexField.getText().isEmpty() || emailTextField.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("กรุณากรอกข้อมูลให้ครบถ้วน!");
            a.show();
        }
        else if(!isValidEmailAddress(emailTextField.getText())){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("ไม่สามารถใช้อีเมลนี้ได้!");
            a.show();
        }
        else{
            String id = String.valueOf(dataList.GenIDCus()) ;
            customer = new Customer(id,nameTextField.getText(),telTexField.getText(),emailTextField.getText());
            dataList.insertCusToDatabase(customer);
            clearAll();
        }
    }

    public void handleEditButton(){
        if(nameTextField.getText().isEmpty() || telTexField.getText().isEmpty() || emailTextField.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("กรุณากรอกข้อมูลให้ครบถ้วน!");
            a.show();
        }
        else if(!isValidEmailAddress(emailTextField.getText())){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("ไม่สามารถใช้อีเมลนี้ได้!");
            a.show();
        }
        else{
            Customer newCus = new Customer(oldCus.getCS_Id(),nameTextField.getText(),telTexField.getText(),
                    emailTextField.getText()) ;
            dataList.updateCusToDatabase(newCus,oldCus);
            clearAll();
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
