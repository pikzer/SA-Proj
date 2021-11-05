package CheckingApp;

public class Customer {
    private String CS_Id;
    private String CS_Name;
    private String CS_Phone;
    private String CS_Email;

    public  Customer(){

    }

    public Customer(String CS_Id, String CS_Name, String CS_Phone, String CS_Email) {
        this.CS_Id = CS_Id;
        this.CS_Name = CS_Name;
        this.CS_Phone = CS_Phone;
        this.CS_Email = CS_Email;
    }

    public String getCS_Id() {
        return CS_Id;
    }

    public void setCS_Id(String CS_Id) {
        this.CS_Id = CS_Id;
    }

    public String getCS_Name() {
        return CS_Name;
    }

    public void setCS_Name(String CS_Name) {
        this.CS_Name = CS_Name;
    }

    public String getCS_Phone() {
        return CS_Phone;
    }

    public void setCS_Phone(String CS_Phone) {
        this.CS_Phone = CS_Phone;
    }

    public String getCS_Email() {
        return CS_Email;
    }

    public void setCS_Email(String CS_Email) {
        this.CS_Email = CS_Email;
    }

    @Override
    public String toString() {
        return CS_Id + ", " + CS_Name + ", " + CS_Phone + ", " + CS_Email;
    }
}
