package CheckingApp;

import CheckingApp.services.DataList;
import javafx.application.Platform;
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
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.swing.plaf.ActionMapUIResource;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class CreateBOQController {

    private DataList dataList ;

    private Stage stage;
    private Scene scene;
    private Parent root;
    int total ;

    @FXML
    TableView boqTableView, boqFinalTable;
    @FXML
    TextField searchItemTextField, itemNameTextField, priceTextField, noOfItemTextField ;
    @FXML
    TextArea requireTextArea ;
    @FXML
    Label totalLabel ;
    ArrayList<BOQ> finalBOQ ;
    ObservableList<BOQ> obBOQ ;
    private TOR tor ;
    OpenFile openFile ;

    public void setTOR(TOR seTOR){
        this.tor = seTOR ;
    }
    @FXML
    public void initialize(){

        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                dataList = new DataList();
                dataList.getBoqFormDataBase();
                dataList.getTorFormDataBase();
                itemNameTextField.setDisable(true);
                showTable(FXCollections.observableList(dataList.getBoqArrayList()));
                showTextArea(tor);
                total = 0 ;
                finalBOQ = new ArrayList<>() ;
                boqTableView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        showDetailItem((BOQ) newValue);
                    }
                });
                priceTextField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                                        String newValue) {
                        if (!newValue.matches("\\d*")) {
                            priceTextField.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    }
                });
                noOfItemTextField.textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                                        String newValue) {
                        if (!newValue.matches("\\d*")) {
                            noOfItemTextField.setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    }
                });
            };
        });
    }




    public void showTextArea(TOR showTor){
        requireTextArea.appendText("ชื่อโปรเจค: " + showTor.getTO_Name() + "\n");
        requireTextArea.appendText("GroupID: " + showTor.getTO_GroupID() + "\n");
        requireTextArea.appendText("รายการผู้มีส่วนเกี่ยวข้อง: " + "\n");
        List<String> matItem ;
        List<String> memItem ;
        matItem = Arrays.asList(showTor.getTO_Materials().split("/")) ;
        memItem = Arrays.asList(showTor.getTO_Member().split("/")) ;
        int i = 1 ;
        for(String s: memItem){
            requireTextArea.appendText(String.valueOf(i)+") "+s+"\n");
            i++ ;
        }
        requireTextArea.appendText("รายการวัสดุ: " + "\n");
        i = 1 ;
        for(String s: matItem){
            requireTextArea.appendText(String.valueOf(i)+") "+s+"\n");
            i++ ;
        }
    }

    public void handleSearchOnAction(){
        ObservableList<BOQ> observableList =
                FXCollections.observableList(dataList.searchNameBOQ(searchItemTextField.getText())) ;
        showTable(observableList);
        searchItemTextField.clear();
    }
    public void handleReset(){
        showTable(FXCollections.observableList(dataList.getBoqArrayList()));
    }

    public void showTable(ObservableList<BOQ> observableList){
        boqTableView.getColumns().clear();
        TableColumn nameCol = new TableColumn("Material Name");
        TableColumn priceCol = new TableColumn("Price");
        TableColumn amountCol = new TableColumn("Amount");

        nameCol.setCellValueFactory(new PropertyValueFactory<BOQ,String>("BO_Name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<BOQ,Integer>("BO_Price"));
        amountCol.setCellValueFactory(new PropertyValueFactory<BOQ,Integer>("BO_Amount"));

        boqTableView.getColumns().addAll(nameCol,priceCol,amountCol) ;
        boqTableView.setItems(observableList);
        boqTableView.getSortOrder().add(nameCol) ;
    }
    public void showTableFinal(){
        boqFinalTable.getColumns().clear();
        ObservableList<BOQ> finalOb = FXCollections.observableArrayList(finalBOQ) ;
        TableColumn nameCol = new TableColumn("Material Name");
        TableColumn priceCol = new TableColumn("Price");
        TableColumn amountCol = new TableColumn("Amount");

        nameCol.setCellValueFactory(new PropertyValueFactory<BOQ,String>("BO_Name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<BOQ,Integer>("BO_Price"));
        amountCol.setCellValueFactory(new PropertyValueFactory<BOQ,Integer>("BO_Amount"));

        boqFinalTable.getColumns().addAll(nameCol,priceCol,amountCol) ;
        boqFinalTable.setItems(finalOb);
        boqFinalTable.getSortOrder().add(nameCol) ;
    }

    public void showDetailItem(BOQ selectedItem){
        itemNameTextField.setText(selectedItem.getBO_Name());
        priceTextField.setText(String.valueOf(dataList.getPriceOfOne(selectedItem.getBO_Name())));
//        noOfItemTextField.setText(String.valueOf(selectedItem.getBO_Amount()));
    }
    public void handleAddToBoq(){
        if(boqTableView.getSelectionModel().getSelectedItem() == null){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("กรุณาเลือกวัสดุที่ต้องการเพิ่ม!");
            a.show();
        }
        if(itemNameTextField.getText().isEmpty() || priceTextField.getText().isEmpty() ||
                noOfItemTextField.getText().isEmpty() ){
            Alert a = new Alert(Alert.AlertType.NONE);
            a.setAlertType(Alert.AlertType.WARNING);
            a.setContentText("กรุณากรอกกรอกจำนวณที่ต้องการหรือราคา!");
            a.show();
        }
        else {
            BOQ addItem = new BOQ(itemNameTextField.getText(),
                    Integer.parseInt(priceTextField.getText()), Integer.parseInt(noOfItemTextField.getText()));
            finalBOQ.add(addItem) ;
            total += Integer.valueOf(priceTextField.getText()) * Integer.valueOf(noOfItemTextField.getText()) ;
            totalLabel.setText(String.valueOf(total));
            itemNameTextField.clear();
            noOfItemTextField.clear();
            priceTextField.clear();
            noOfItemTextField.clear();
            boqTableView.getSelectionModel().clearSelection();
            showTableFinal();
        }
    }

    public void handleCrateBOQ(ActionEvent event) throws IOException, InterruptedException {
        // column name
        String[] columns = {"ลำดับ","รายละเอียด", "ปริมาณ","ราคาต่อหน่วย", "รวม"};
        // exel object new
        Workbook workbook = new XSSFWorkbook();
        // file excel name from proj name
        String fileName = tor.getTO_Name();

        Sheet sheet = workbook.createSheet("fileName");

        Font headerFont = workbook.createFont();
        headerFont.setBold(true);
        headerFont.setFontHeightInPoints((short) 18);
        headerFont.setColor(IndexedColors.BLUE.getIndex());
        CreationHelper createHelper = workbook.getCreationHelper();
        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFont(headerFont);
        // sheet.addMergedRegion(rowFrom,rowTo,colFrom,colTo);
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,2)) ;
        sheet.addMergedRegion(new CellRangeAddress(1,1,0,2)) ;
        Row row1 = sheet.createRow(0);
        row1.createCell(0).setCellValue(
                createHelper.createRichTextString("ชื่อโครงการ: " + tor.getTO_Name() +
                        "   GroupID: " + tor.getTO_GroupID())+ "    ขอบเขตการทำงาน: " + String.valueOf(tor.getTO_Period()) + " วัน");
        //create header row
        String[] memItem = tor.getTO_Member().split("/");
        StringBuilder mem = new StringBuilder();
        int ij = 1 ;
        for(String s: memItem){
            mem.append(ij).append(")").append(s).append(" ");
            ij++ ;
        }
        Row headerRow = sheet.createRow(2);
        row1 = sheet.createRow(1);
        row1.createCell(0).setCellValue(createHelper.createRichTextString("ผู้มีส่วนเกี่ยวข้อง มีดังนี้ " + mem));
        // add Column
        for (int i = 0; i < columns.length; i++) {
            Cell cell = headerRow.createCell(i);
            cell.setCellValue(columns[i]);
            cell.setCellStyle(headerCellStyle);
        }
        // start row for data
        int rowNum = 3 ;
        int k = 1 ;
        ArrayList<BOQ> ref = dataList.getBoqArrayList() ;
        int total = 0 ;
        for(BOQ boq: finalBOQ){
            Row row = sheet.createRow(rowNum++);
            row.createCell(0).setCellValue(String.valueOf(k));
            k++ ;
            row.createCell(1).setCellValue(boq.getBO_Name());
            row.createCell(2).setCellValue(boq.getBO_Amount());
            row.createCell(3).setCellValue(boq.getBO_Price());
            row.createCell(4).setCellValue(dataList.getPriceOfOne(boq.getBO_Name())*boq.getBO_Amount());
            total += dataList.getPriceOfOne(boq.getBO_Name())*boq.getBO_Amount() ;
        }
        Row row2 = sheet.createRow(rowNum+2);
        row2.createCell(3).setCellValue(createHelper.createRichTextString("รวม")) ;
        row2.createCell(4).setCellValue(createHelper.createRichTextString(String.valueOf(total))) ;
        row2.createCell(5).setCellValue(createHelper.createRichTextString("บาท")) ;

        // Resize all columns to fit the content size
        for (int i = 0; i < columns.length; i++) {
            sheet.autoSizeColumn(i);
        }
        FileOutputStream fileOut = new FileOutputStream("Excel/"+ fileName+".xlsx");
        workbook.write(fileOut);
        fileOut.close();
        workbook.close();
        Alert a = new Alert(Alert.AlertType.NONE);
        a.setAlertType(Alert.AlertType.INFORMATION);
        a.setContentText("สร้าง BOQ สำเร็จ!");
        a.show();
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/selectTorToCreate.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }


    public void handleBackOnAction(ActionEvent event) throws IOException {
        root = FXMLLoader.load(getClass().getResource("/CheckingApp/selectTorToCreate.fxml"));
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
