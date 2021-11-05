package CheckingApp;

public class Employer {
    private String EM_WorkID;
    private String EM_GroupID;
    private String EM_Name;
    private String EM_Phone;
    private String EM_Email;

    public Employer() {
    }

    public Employer(String EM_WorkID, String EM_GroupID, String EM_Name, String EM_Phone, String EM_Email) {
        this.EM_WorkID = EM_WorkID;
        this.EM_GroupID = EM_GroupID;
        this.EM_Name = EM_Name;
        this.EM_Phone = EM_Phone;
        this.EM_Email = EM_Email;
    }

    public String getEM_WorkID() {
        return EM_WorkID;
    }

    public void setEM_WorkID(String EM_WorkID) {
        this.EM_WorkID = EM_WorkID;
    }

    public String getEM_GroupID() {
        return EM_GroupID;
    }

    public void setEM_GroupID(String EM_GroupID) {
        this.EM_GroupID = EM_GroupID;
    }

    public String getEM_Name() {
        return EM_Name;
    }

    public void setEM_Name(String EM_Name) {
        this.EM_Name = EM_Name;
    }

    public String getEM_Phone() {
        return EM_Phone;
    }

    public void setEM_Phone(String EM_Phone) {
        this.EM_Phone = EM_Phone;
    }

    public String getEM_Email() {
        return EM_Email;
    }

    public void setEM_Email(String EM_Email) {
        this.EM_Email = EM_Email;
    }

    @Override
    public String toString() {
        return EM_WorkID + ", " + EM_GroupID + ", " + EM_Name + ", " + EM_Phone + ", " + EM_Email;
    }
}


