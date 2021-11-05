package CheckingApp;

import CheckingApp.services.DataList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.Properties;

public class CheckBOQController {
    @FXML
    private TableView TORTable, cusTable ;
    @FXML
    private TextField searhTextField, projIDTextField ;
    @FXML
    Label cusNameLabel, telLabel, mailLabel ;
    @FXML
    Button confirmButton, viewBoqButton ;

    private TOR tempt ;
    private Customer tempc ;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DataList dataList ;
    private ObservableList<TOR> torObservableList ;

    Customer cus ;
    TOR tor ;

    @FXML
    public void initialize(){
        confirmButton.setDisable(true);
        TORTable.getColumns().clear();
        dataList = new DataList();
        dataList.getTorFormDataBase();
        dataList.getCusFromDatabase();
        showTable("");
        showCusTable();
        confirmButton.setDisable(true);
        viewBoqButton.setDisable(true);
        FilteredList<TOR> torFilteredList = new FilteredList<>(torObservableList,p-> true) ;
        searhTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            torFilteredList.setPredicate(tor -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return tor.getTO_Name().toLowerCase().contains(lowerCaseFilter);
            });
        });
        TORTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                showSelectedTor((TOR) newValue);
            }
        });
        cusTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                showSelectedCus((Customer) newValue) ;
            }
        });
        SortedList<TOR> torSortedList =new SortedList<>(torFilteredList) ;
        torSortedList.comparatorProperty().bind(TORTable.comparatorProperty());
        showSearchtable(torSortedList) ;
    }

    private void showSelectedCus(Customer newValue) {
        cus = newValue ;
        tempc = newValue ;
        cusNameLabel.setText(newValue.getCS_Name());
        telLabel.setText(newValue.getCS_Phone());
        mailLabel.setText(newValue.getCS_Email());
        if(tempt != null && tempc != null){
            confirmButton.setDisable(false);
        }
    }

    public void showSearchtable(SortedList<TOR> torObservableList){
        TORTable.getColumns().clear();
        TableColumn groupIDCol = new TableColumn("GroupID");
        TableColumn projNameCol = new TableColumn("Project Name");
        TableColumn matCol = new TableColumn("Material");
        TableColumn memberCol = new TableColumn("Member") ;
        TableColumn periodCol = new TableColumn("Period") ;

        groupIDCol.setCellValueFactory(new PropertyValueFactory<TOR,String>("TO_GroupID"));
        projNameCol.setCellValueFactory(new PropertyValueFactory<TOR,String>("TO_Name"));
        matCol.setCellValueFactory(new PropertyValueFactory<TOR,String>("TO_Materials"));
        memberCol.setCellValueFactory(new PropertyValueFactory<TOR, String>("TO_Member"));
        periodCol.setCellValueFactory(new PropertyValueFactory<TOR,Integer>("TO_Period"));
        TORTable.getColumns().addAll(groupIDCol,projNameCol,matCol,memberCol,periodCol) ;
        TORTable.setItems(torObservableList);
    }

    private void showSelectedTor(TOR selectedTOR) {
        this.tor = selectedTOR ;
        tempt = selectedTOR ;
        viewBoqButton.setDisable(false);
    }


    public void handleLookBOQ() throws IOException {
//         OpenFile openFile = new OpenFile("Excel",tor.getTO_Name()+".xlsx") ;
//        File f = openFile.GetFile();
//        try {
//            System.out.println(f.getAbsolutePath());
//            Runtime.getRuntime().exec("explorer.exe  /select," + f.getAbsolutePath());
//        } catch (Exception ex) {
//
//        }
        File file = new File("Excel/" + tor.getTO_Name() + ".xlsx") ;
        if(file.exists()){
            Desktop.getDesktop().open(new File("Excel/" + tor.getTO_Name() + ".xlsx"));
        }
        else {
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("ไม่มี BOQ ในระบบ!");
            a.show();
        }
    }

    public void handleSearchOnAction(){
        if (projIDTextField.getText().equals("")){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("Project ID ไม่ถูกต้อง! กรุณาลองใหม่...");
            a.show();
        }
        else{
            showTable(projIDTextField.getText());
        }
    }

    public void handleReset(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/checkBoq.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void showTable(String id) {
        TORTable.getColumns().clear();
        if (id.equals(""))
            torObservableList = FXCollections.observableArrayList(dataList.getSortedByGroupIDTor());
        else
            torObservableList = FXCollections.observableArrayList(dataList.searchGroupIDTOR(id));
        TableColumn groupIDCol = new TableColumn("GroupID");
        TableColumn projNameCol = new TableColumn("Project Name");
        TableColumn matCol = new TableColumn("Material");
        TableColumn memberCol = new TableColumn("Member") ;
        TableColumn periodCol = new TableColumn("Period") ;

        groupIDCol.setCellValueFactory(new PropertyValueFactory<TOR,String>("TO_GroupID"));
        projNameCol.setCellValueFactory(new PropertyValueFactory<TOR,String>("TO_Name"));
        matCol.setCellValueFactory(new PropertyValueFactory<TOR,String>("TO_Materials"));
        memberCol.setCellValueFactory(new PropertyValueFactory<TOR, String>("TO_Member"));
        periodCol.setCellValueFactory(new PropertyValueFactory<TOR,Integer>("TO_Period"));
        TORTable.getColumns().addAll(groupIDCol,projNameCol,matCol,memberCol,periodCol) ;
        TORTable.setItems(torObservableList);
    }

    public void showCusTable(){
        cusTable.getColumns().clear();
        ObservableList<Customer> customerObservableList = FXCollections.observableList(dataList.getCustomerArrayList());
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

    public void handleEditButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/selectTorToCreate.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleConfirmButton() throws MessagingException, IOException {
        ButtonType foo = new ButtonType("ใช่", ButtonBar.ButtonData.OK_DONE);
        ButtonType bar = new ButtonType("ไม่ใช่", ButtonBar.ButtonData.CANCEL_CLOSE);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION,"คุณแน่ใจว่าจะส่ง BOQ ให้ผู้ว่าจ้างหรือไม่?") ;
        alert.setTitle("Sent BOQ warning");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.get() != null){
            Properties properties = new Properties();
            properties.put("mail.smtp.auth", true);
            properties.put("mail.smtp.host", "smtp.gmail.com");
            properties.put("mail.smtp.port", 587);
            properties.put("mail.smtp.starttls.enable", true);
            properties.put("mail.transport.protocol", "smtp");

            // Create a mail session so you can create and send an email message.
            Session sessions = Session.getInstance(properties, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication("checking.boq@gmail.com", "Code1234");
                }
            });
            Message message = new MimeMessage(sessions);
            message.setSubject("นำส่งใบประเมินราคา(BOQ)ของโปรเจค " + tor.getTO_Name() + " ว่าจ้างโดย " + cus.getCS_Name());

            Address addressTo = new InternetAddress(cus.getCS_Email());

            message.setRecipient(Message.RecipientType.TO, addressTo);

            MimeMultipart multipart = new MimeMultipart();

            MimeBodyPart attachment = new MimeBodyPart();

            File file = new File("Excel/"+tor.getTO_Name()+".xlsx") ;
            if(file.exists()){
                attachment.attachFile(file) ;
                MimeBodyPart messageBodyPart = new MimeBodyPart();
//                messageBodyPart.setText("ไฟล์ Excel ของใบประเมินราคา(BOQ) " + tor.getTO_Name()
//                        + "ว่าจ้างโดย " + cus.getCS_Name() );
                messageBodyPart.setContent("<h1>Thank you for using the service.</h1>", "text/html");
                multipart.addBodyPart(messageBodyPart);
                MimeBodyPart messageBodyPart1 = new MimeBodyPart();
                messageBodyPart1.setText("ลิงค์ข้อสอบถาม: "+"https://forms.gle/eP48xni8tZMyMzf57");

                multipart.addBodyPart(attachment);
                multipart.addBodyPart(messageBodyPart1);
                message.setContent(multipart);
                Transport.send(message);
            }else{
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("ไม่มี BOQ ในระบบ!");
                a.show();
            }
//            attachment.attachFile(new File("Checking-BOQ/Excel/"+tor.getTO_Name()+".xlsx"));

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
