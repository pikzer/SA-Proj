package CheckingApp;

import java.util.ArrayList;

public class ExcelData {
    private String ProjectName ;
    private ArrayList<String> memberList ;
    private ArrayList<BOQ> listBOQ ;
    private int totalPrice ;

    public  ExcelData(){

    }

    public ExcelData(String projectName, ArrayList<String> memberList, ArrayList<BOQ> listBOQ, int totalPrice) {
        ProjectName = projectName;
        this.memberList = memberList;
        this.listBOQ = listBOQ;
        this.totalPrice = totalPrice;
    }

    public String getProjectName() {
        return ProjectName;
    }

    public void setProjectName(String projectName) {
        ProjectName = projectName;
    }

    public ArrayList<String> getMemberList() {
        return memberList;
    }

    public void setMemberList(ArrayList<String> memberList) {
        this.memberList = memberList;
    }

    public ArrayList<BOQ> getListBOQ() {
        return listBOQ;
    }

    public void setListBOQ(ArrayList<BOQ> listBOQ) {
        this.listBOQ = listBOQ;
    }

    public int getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(int totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "ExcelData{" +
                "ProjectName='" + ProjectName + '\'' +
                ", memberList=" + memberList +
                ", listBOQ=" + listBOQ +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
