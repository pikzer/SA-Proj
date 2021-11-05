package CheckingApp;

import CheckingApp.TOR;
import CheckingApp.services.DataList;
import CheckingApp.StringConfiguration;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.*;

public class SelectTorToCreateController {
    @FXML
    private TableView<TOR> TORTable ;
    @FXML
    private TextField projIDTextField, nameProfTextField, periodTextField, peopleTextField, MaterialTextField ;
    @FXML
    private CheckBox checkBox ;

    private Stage stage;
    private Scene scene;
    private Parent root;

    private DataList dataList;
    private ObservableList<TOR> torObservableList;
    @FXML
    TextField searhTextField ;

    private TOR tor;


    @FXML
    public void initialize(){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dataList = new DataList();
                dataList.getTorFormDataBase();
                dataList.getBoqFormDataBase();
                showTable("");
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
                        showSelectedTor(newValue);
                    }
                });

                SortedList<TOR> torSortedList =new SortedList<>(torFilteredList) ;
                torSortedList.comparatorProperty().bind(TORTable.comparatorProperty());
                showSearchtable(torSortedList) ;
                projIDTextField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                                        String newValue) {
                        if (!newValue.matches("\\d*")) {
                            projIDTextField.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    }
                });
            }
        });
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

    public void showSelectedTor(TOR selectedTOR){
        this.tor = selectedTOR ;
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

    public void handleReset(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/selectTorToCreate.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void handleCreateBOQButton(ActionEvent event) throws IOException {
        if(TORTable.getSelectionModel().getSelectedItem() == null){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("กรุณาเลือกโปรเจคที่ต้องการ!");
            a.show();
        }
        else {
            Button b = (Button) event.getSource();
            Stage stage = (Stage) b.getScene().getWindow();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/CheckingApp/createBoq.fxml"));
            stage.setScene(new Scene(loader.load(), 1024, 768));
            CreateBOQController createBOQController = loader.getController() ;
            createBOQController.setTOR(tor);
            stage.show();
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

    public void handleBackOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
