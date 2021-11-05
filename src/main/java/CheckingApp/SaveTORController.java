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
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class SaveTORController {
    @FXML
    private TableView<TOR> TORTable ;
    @FXML
    private TextField nameProfTextField,periodTextField, peopleTextField, materialTextField ;
    @FXML
    private TextArea memTextArea, matTextArea ;

    @FXML
    private Button saveButton,editButton,memDelButton,matDelButton ;


    private Stage stage;
    private Scene scene;
    private Parent root;

    ArrayList<String> peopleArray ;
    ArrayList<String> materialArray ;

    ObservableList<TOR> torObservableList ;

    private DataList dataList ;
    private List<String> matItem ;
    private List<String> memItem ;
    private TOR tor;
    private TOR temp ;

    @FXML
    public void initialize(){
        memDelButton.setDisable(true);
        matDelButton.setDisable(true);
        peopleArray = new ArrayList<>() ;
        materialArray = new ArrayList<>() ;
        dataList = new DataList();
        dataList.getTorFormDataBase();
        dataList.getBoqFormDataBase();
        showTable();
        memTextArea.appendText("รายผู้มีส่วนเกี่ยวข้อง" + "\n");
        matTextArea.appendText("รายการวัสดุ" + "\n");
        editButton.setDisable(true);
        periodTextField.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    periodTextField.setText(newValue.replaceAll("[^\\d]", ""));
                }
            }
        });
        TORTable.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue != null){
                showSelectedTor(newValue) ;
            }
        });
    }

    public void showTable(){
    // Get data from Database
        TORTable.getColumns().clear();
        dataList = new DataList();
        dataList.getTorFormDataBase();
        torObservableList = FXCollections.observableArrayList(dataList.getSortedByGroupIDTor());
        // Ajarn Solution
//        TORTable.getColumns().clear();
//        torObservableList = FXCollections.observableList(tableShowTOR) ;
//        TORTable.setItems(torObservableList);
//        ArrayList<StringConfiguration> configs = new ArrayList<>();
//        configs.add(new StringConfiguration("title:GroupID", "field:TO_GroupID")) ;
//        configs.add(new StringConfiguration("title:Project Name", "field:TO_Name")) ;
//        configs.add(new StringConfiguration("title:Material", "field:TO_Materials")) ;
//        configs.add(new StringConfiguration("title:Member", "field:TO_Member")) ;
//        for (StringConfiguration conf: configs) {
//            TableColumn col = new TableColumn(conf.get("title"));
//            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
//            TORTable.getColumns().add(col);
//        }
        // default JavaFX
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
//        TORTable.getSortOrder().add(groupIDCol) ;

        // Ajarn with sorting connect Database
//        ArrayList<StringConfiguration> configs = new ArrayList<>();
//        configs.add(new StringConfiguration( "title:Group ID", "field:TO_GroupID"));
//        configs.add(new StringConfiguration( "title:Name", "field:TO_Name"));
//        configs.add(new StringConfiguration( "title:Materials", "field:TO_Materials"));
//        configs.add(new StringConfiguration( "title:Members", "field:TO_Member"));
//        configs.add(new StringConfiguration("title:Period", "field:TO_Period"));
//
//        for (StringConfiguration conf : configs){
//            TableColumn col = new TableColumn(conf.get("title"));
//            col.setCellValueFactory(new PropertyValueFactory<>(conf.get("field")));
//            TORTable.getColumns().add(col);
//            if (conf.get("title").equals("Group ID")){
//                TORTable.getSortOrder().addAll(col);
//                col.setSortType(TableColumn.SortType.DESCENDING);
//            }
//            if (conf.get("title").equals("Materials")){
//                TORTable.getSortOrder().addAll(col);
//                col.setSortType(TableColumn.SortType.DESCENDING);
//            }
//        }
    }

    public void handleDelPeopleButton(){
        if(peopleTextField.getText()==null){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("กรุณกรอกผู้มีส่วนเกี่ยวข้อง!");
            a.show();
            peopleTextField.clear();
        }
//        else if(memItem.contains(peopleTextField.getText())){
//            // ซ้ำ
//
//            Alert a = new Alert(Alert.AlertType.NONE);
//            a.setAlertType(Alert.AlertType.WARNING);
//            a.setContentText("ผู้มีส่วนเกี่ยวข้องมีอยู่แล้วในโปรเจค");
//            a.show();
//        }
        else if(!memItem.contains(peopleTextField.getText())){
            //ไม่พบ
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("ไม่พบผู้เกี่ยวข้องในโปรเจค!");
            a.show();
            peopleTextField.clear();
        }
        else{
            peopleArray.remove(peopleTextField.getText()) ;
//            System.out.println(peopleArray.toString());
            setTextAreaMem(peopleArray);
            peopleTextField.clear();
        }
    }
    public void handleDelMatButton(){
        if(materialTextField.getText()==null){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("กรุณากรอกวัสดุที่ต้องการลบ!");
            a.show();
            materialTextField.clear();
        }
//        else if(memItem.contains(peopleTextField.getText())){
//            // ซ้ำ
//
//            Alert a = new Alert(Alert.AlertType.NONE);
//            a.setAlertType(Alert.AlertType.WARNING);
//            a.setContentText("ผู้มีส่วนเกี่ยวข้องมีอยู่แล้วในโปรเจค");
//            a.show();
//        }
        else if(!matItem.contains(materialTextField.getText())){
            //ไม่พบ
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("ไม่พบวัสดุนี้ในโปรเจค!");
            a.show();
            materialTextField.clear();
        }
        else {
            materialArray.remove(materialTextField.getText()) ;
//            System.out.println(peopleArray.toString());
            setTextAreaMat(materialArray);
            materialTextField.clear();
        }
    }
    public void setTextAreaMem(ArrayList<String> memList){
        memTextArea.setText(null);
        memTextArea.appendText("รายผู้มีส่วนเกี่ยวข้อง\n"); ;
        int i = 1 ;
        for(String s:memList){
            memTextArea.appendText(i+") "+ s + "\n");
            i++;
        }
    }
    public void setTextAreaMat(ArrayList<String> matList){
        matTextArea.setText(null);
        matTextArea.appendText("รายผู้มีส่วนเกี่ยวข้อง\n"); ;
        int i = 1 ;
        for(String s:matList){
            matTextArea.appendText(i+") "+ s + "\n");
            i++;
        }
    }



    public void showSelectedTor (TOR newValue){
        memDelButton.setDisable(false);
        matDelButton.setDisable(false);
        clearAll();
        temp = newValue ;
        editButton.setDisable(false);
        saveButton.setDisable(true);
        materialArray = new ArrayList<>() ;
        peopleArray = new ArrayList<>() ;
        nameProfTextField.setText(newValue.getTO_Name());
        periodTextField.setText(String.valueOf(newValue.getTO_Period()));
        matItem = Arrays.asList(newValue.getTO_Materials().split("/")) ;
        memItem = Arrays.asList(newValue.getTO_Member().split("/")) ;
        int i = 1 ;
        for(String s: matItem){
            matTextArea.appendText(String.valueOf(i)+") "+s+"\n");
            materialArray.add(s) ;
            i++ ;
        }
        i = 1 ;
        for(String s: memItem){
            memTextArea.appendText(String.valueOf(i)+") "+s+"\n");
            peopleArray.add(s) ;
            i++ ;
        }
    }

    public void handleAddPeopleButton(){
        if(TORTable.getSelectionModel().getSelectedItem() == null){
            if(!peopleTextField.getText().isEmpty()){
                peopleArray.add(peopleTextField.getText()) ;
                memTextArea.appendText(peopleArray.size() + ") " +peopleTextField.getText()+"\n");
                peopleTextField.clear();
            }else {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("กรุณกรอกผู้มีส่วนเกี่ยวข้อง!");
                a.show();
            }
        }
        else if(TORTable.getSelectionModel().getSelectedItem() != null){
            if(peopleTextField.getText().isEmpty()){
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("กรุณกรอกผู้มีส่วนเกี่ยวข้อง!");
                a.show();
                peopleTextField.clear();
            }
            else if(peopleArray.contains(peopleTextField.getText())){
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("ผู้มีส่วนเกี่ยวข้องมีอยู่ในโปรเจคอยู่แล้ว!");
                a.show();
                peopleTextField.clear();
            }
            else{
                peopleArray.add(peopleTextField.getText()) ;
                memTextArea.appendText(peopleArray.size() + ") " +peopleTextField.getText()+"\n");
                peopleTextField.clear();
            }
        }
    }

    public void handleAddMaterialButton(){
        if(TORTable.getSelectionModel().getSelectedItem() == null){
            if(!materialTextField.getText().isEmpty()){
                materialArray.add(materialTextField.getText()) ;
                matTextArea.appendText(materialArray.size() + ") " +materialTextField.getText()+"\n");
                materialTextField.clear();
            }else {
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("กรุณกรอกข้อมูลวัสดุ!");
                a.show();
            }
        }
        else if(TORTable.getSelectionModel().getSelectedItem() != null){
            if(materialTextField.getText().isEmpty()){
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("กรุณกรอกข้อมูลวัสดุ!");
                a.show();
                materialTextField.clear();
            }
            else if(materialArray.contains(materialTextField.getText())){
                Alert a = new Alert(Alert.AlertType.NONE);
                a.setAlertType(Alert.AlertType.WARNING);
                a.setContentText("มีวัสดุนี้อยู่ในโปรเจคอยู่แล้ว!");
                a.show();
                materialTextField.clear();
            }
            else{
                materialArray.add(materialTextField.getText()) ;
                matTextArea.appendText(materialArray.size() + ") " +materialTextField.getText()+"\n");
                materialTextField.clear();
            }
        }
    }

    public void clearAll(){
        peopleArray = new ArrayList<>() ;
        materialArray = new ArrayList<>() ;
        nameProfTextField.clear();
        periodTextField.clear();
        peopleTextField.clear();
        materialTextField.clear();
        memTextArea.setText("รายผู้มีส่วนเกี่ยวข้อง" + "\n");
        matTextArea.setText("รายการวัสดุ" + "\n");
    }

    public void handleBackButton(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/home.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void mouseClickAnchor(){
        if(TORTable.getSelectionModel().getSelectedItem() != null){
            clearAll();
            saveButton.setDisable(false);
            editButton.setDisable(true);
            TORTable.getSelectionModel().clearSelection();
        }
    }

    public void handleEditButton(){
        if(nameProfTextField.getText().isEmpty() || periodTextField.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("กรุณากรอกข้อมูลให้ครบถ้วน!");
            a.show();
        }
        if(peopleArray.isEmpty()||materialArray.isEmpty()){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("กรุณาเพิ่มผู้มีส่วนเกี่ยวข้องหรือรายการวัสดุ!");
            a.show();
        }
        else{
            StringBuilder member= new StringBuilder();
            StringBuilder material= new StringBuilder();
            for (String s:peopleArray){
                member.append(s);
                if(!s.equals(peopleArray.get(peopleArray.size() - 1))){
                    member.append("/");
                }
            }
            for (String s:materialArray){
                material.append(s);
                if(!s.equals(materialArray.get(materialArray.size() - 1))){
                    material.append("/");
                }
            }
            TOR newTor = new TOR(temp.getTO_GroupID(), nameProfTextField.getText(),
                    material.toString(), member.toString(), Integer.parseInt(periodTextField.getText())) ;
            dataList.updateTorDatabase(newTor,temp);
            showTable();
            clearAll();
        }
    }


    public void handleSaveButton(){
        if(nameProfTextField.getText().isEmpty() || periodTextField.getText().isEmpty()){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("กรุณากรอกข้อมูลให้ครบถ้วน!");
            a.show();
        }
        if(peopleArray.isEmpty()||materialArray.isEmpty()){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("กรุณาเพิ่มผู้มีส่วนเกี่ยวข้องหรือรายการวัสดุ!");
            a.show();
        }
        else{
            StringBuilder member= new StringBuilder();
            StringBuilder material= new StringBuilder();
            for (String s:peopleArray){
                member.append(s);
                if(!s.equals(peopleArray.get(peopleArray.size() - 1))){
                    member.append("/");
                }
            }
            for (String s:materialArray){
                material.append(s);
                if(!s.equals(materialArray.get(materialArray.size() - 1))){
                    material.append("/");
                }
            }
            String id = "" ;
            id += String.valueOf(dataList.GenIDTor()) ;
            tor = new TOR(id, nameProfTextField.getText(),
                    material.toString(), member.toString(), Integer.parseInt(periodTextField.getText())) ;
            System.out.println(tor.toString());
            dataList.insertTorToDatabase(tor);
            showTable();
            clearAll();
        }
    }
}
