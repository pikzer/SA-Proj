package CheckingApp.services;

import CheckingApp.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;

public class DataList {
    private final ArrayList<BOQ> boqArrayList;
    private final ArrayList<Customer> customerArrayList;
    private final ArrayList<Employer> employerArrayList;
    private final ArrayList<Inquiry> inquiryArrayList;
    private final ArrayList<TOR> torArrayList;


    public DataList() {
        boqArrayList = new ArrayList<>();
        customerArrayList = new ArrayList<>();
        employerArrayList = new ArrayList<>();
        inquiryArrayList = new ArrayList<>();
        torArrayList = new ArrayList<>();
    }

    public void addBOQ(BOQ boq) {
        this.boqArrayList.add(boq);
    }

    public void addCustomer(Customer customer) {
        this.customerArrayList.add(customer);
    }

    public void addEmployer(Employer employer) {
        this.employerArrayList.add(employer);
    }

    public void addInquiry(Inquiry inquiry) {
        this.inquiryArrayList.add(inquiry);
    }

    public void addTOR(TOR tor) {
        this.torArrayList.add(tor);
    }

    public ArrayList<BOQ> getBoqArrayList() {
        return boqArrayList;
    }

    public ArrayList<Customer> getCustomerArrayList() {
        return customerArrayList;
    }

    public ArrayList<Employer> getEmployerArrayList() {
        return employerArrayList;
    }

    public ArrayList<Inquiry> getInquiryArrayList() {
        return inquiryArrayList;
    }

    public ArrayList<TOR> getTorArrayList() {
        return torArrayList;
    }

    public void getTorFormDataBase() {
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();
        String connectQuery = "SELECT * FROM check_boq.tor";

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(connectQuery);
            while (queryOutPut.next()) {
                torArrayList.add(new TOR(queryOutPut.getString("TO_GroupID"),
                        queryOutPut.getString("TO_Name"),
                        queryOutPut.getString("TO_Materials"),
                        queryOutPut.getString("TO_Member"),
                        queryOutPut.getInt("TO_Period")));
            }
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    public ArrayList<TOR> getSortedByGroupIDTor(){
        ArrayList<String> strArr = new ArrayList<>() ;
        ArrayList<TOR> temp = new ArrayList<>() ;
        temp = torArrayList ;
        Set<Integer> set = new TreeSet<>() ;
        for(int i = 0 ; i < temp.size(); i++){
            strArr.add(temp.get(i).getTO_GroupID()) ;
        }
        for(String s:strArr){
            set.add(Integer.parseInt(s)) ;
        }
        ArrayList<String> sortedStrTorList = new ArrayList<>() ;
        for(int s:set){
            sortedStrTorList.add(String.valueOf(s)) ;
        }
        ArrayList<TOR> sortedTorArrayList = new ArrayList<>() ;
        for(String s:sortedStrTorList){
            for(int i = 0 ; i < temp.size(); i++){
                if(temp.get(i).getTO_GroupID().equals(s)){
                    sortedTorArrayList.add(temp.get(i)) ;
                }
            }
        }
        return  sortedTorArrayList ;
    }



    public void getBoqFormDataBase() {
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();
        String connectQuery = "SELECT * FROM check_boq.boq";

        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(connectQuery);
            while (queryOutPut.next()) {
                boqArrayList.add(new BOQ(queryOutPut.getString("BO_Name"),
                        queryOutPut.getInt("BO_Price"),
                        queryOutPut.getInt("BO_Amount")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<TOR> searchGroupIDTOR(String id) {
        ArrayList<TOR> tmpTOR = new ArrayList<>();
        for (TOR tor : getTorArrayList()){
            if (tor.getTO_GroupID().equals(id))
                tmpTOR.add(tor);
        }
        return tmpTOR;
    }

    public ArrayList<BOQ> searchNameBOQ(String id) {
        ArrayList<BOQ> tmpTOR = new ArrayList<>();
        for (BOQ tor : getBoqArrayList()){
            if (tor.getBO_Name().contains(id))
                tmpTOR.add(tor);
        }
        return tmpTOR;
    }

    public int getPriceOfOne(String name){
        for(BOQ b:boqArrayList){
            if(b.getBO_Name().equals(name)){
                return b.getBO_Price()/ b.getBO_Amount() ;
            }
        }
        return 0 ;
    }

    /**
     * @param :
     * @return : void
     * @throws : SQLException
     * @implNote : Never use!
     */

    public void insertTorToDatabase(TOR tor){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();
        String id = "\"" +  tor.getTO_GroupID() + "\""  ;
        String name = "\"" + tor.getTO_Name() + "\"" ;
        String mat = "\"" + tor.getTO_Materials() + "\"";
        String mem = "\"" + tor.getTO_Member() + "\"";
        String period =  String.valueOf(tor.getTO_Period()) ;
        String sql = "INSERT INTO tor VALUES(" + id + "," + name + ","
        + mat + "," + mem + "," + period + ")" ;
        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateTorDatabase(TOR newTor, TOR oldTor){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();
        String query = "" ;
        query += "UPDATE tor SET";
        query+= " TO_Name = " + "\"" + newTor.getTO_Name() + "\"" + ",";
        query+= "TO_Materials = " + "\"" + newTor.getTO_Materials() + "\"" +  "," ;
        query+= "TO_Member =" + "\"" + newTor.getTO_Member() + "\"" + "," ;
        query+= "TO_Period ="  + newTor.getTO_Period() + " " ;
        query+= "WHERE " + "TO_GroupID = " + oldTor.getTO_GroupID() ;
        System.out.println(query);
        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void getCusFromDatabase(){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();
        String query = "SELECT * FROM check_boq.customer" ;
        try {
            Statement statement = connectionDB.createStatement();
            ResultSet queryOutPut = statement.executeQuery(query);
            while (queryOutPut.next()) {
                customerArrayList.add(new Customer(queryOutPut.getString("CS_Id"),
                        queryOutPut.getString("CS_Name"),
                        queryOutPut.getString("CS_Phone"),
                        queryOutPut.getString("CS_Email"))) ;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void insertCusToDatabase(Customer cus){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();
        String query = "insert into customer values(" ;
        query+=   cus.getCS_Id()  + "," ;
        query+= "\"" + cus.getCS_Name() + "\"" + "," ;
        query+= "\"" + cus.getCS_Phone() + "\"" + "," ;
        query+= "\"" + cus.getCS_Email() + "\""+")" ;
        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }

    public void updateCusToDatabase(Customer newCus, Customer oldCus){
        DatabaseConnection connectionNow = new DatabaseConnection();
        Connection connectionDB = connectionNow.getConnection();
        String query = "update customer set " ;
        query+= "CS_Name = " + "\"" + newCus.getCS_Name() + "\"" + "," ;
        query+= "CS_Phone = " + "\"" + newCus.getCS_Phone() + "\"" + "," ;
        query+= "CS_Email = "+ "\"" + newCus.getCS_Email() + "\"" ;
        query+= " where " + "CS_Id = " + oldCus.getCS_Id() ;
        try {
            Statement statement = connectionDB.createStatement();
            statement.executeUpdate(query);
        } catch (SQLException e) {
//            e.printStackTrace();
        }
    }






    /**
     * @param : groupID - groupID TOR
     * @return : int (Total price)
     * @throws : SQLException
     * @implNote : Never use! (NOT DONE)
     */
    public StringBuilder createBOQ(String groupID){
        StringBuilder csv = new StringBuilder();
        int total = 0;
        int price;
        /**
         * tor1, tor2, tor3 | (boq1, boq2, boq3), (boq1, boq2, boq3)
         */
        for (TOR tor : torArrayList) {
            for (BOQ boq : boqArrayList) {
                if (tor.getTO_Materials().equals(boq.getBO_Name()) ){
                    price = boq.getBO_Price()/boq.getBO_Amount();
                    csv.append(boq.getBO_Name()).append(", ");
                    csv.append(price);
                    csv.append("\n"); // "\n" = System.getProperty("line.separator")

                    total+=price;
                    break;
                }
                else if (tor.getTO_Materials().contains(boq.getBO_Name())){
                    price = boq.getBO_Price()/boq.getBO_Amount();
                    csv.append(boq.getBO_Name()).append(", ");
                    csv.append(price);
                    csv.append(System.getProperty("line.separator"));

                    total+=price;
                    break;
                }
            }
        }
        csv.append("Total ").append(total);

        return csv;
    }

    public int GenIDTor(){
        int i;
        ArrayList<Integer> ids = getIdList();
        for (i = 1; i <= ids.size() ; i++) {
            if (ids.get(i-1) > i )
                return i;
        }
        return i;

    }
    public ArrayList<Integer> getIdList(){
        ArrayList<Integer> ids = new ArrayList<>();
        for (TOR list : torArrayList) {
            ids.add(Integer.parseInt(list.getTO_GroupID()));
        }
        Collections.sort(ids);
        return ids;
    }

    public int GenIDCus(){
        int i;
        ArrayList<Integer> ids = getIDListCus();
        for (i = 1; i <= ids.size() ; i++) {
            if (ids.get(i-1) > i )
                return i;
        }
        return i;

    }
    public ArrayList<Integer> getIDListCus(){
        ArrayList<Integer> ids = new ArrayList<>();
        for (Customer list : customerArrayList) {
            ids.add(Integer.parseInt(list.getCS_Id()));
        }
        Collections.sort(ids);
        return ids;
    }

}