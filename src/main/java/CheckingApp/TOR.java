package CheckingApp;

public class TOR {
    private String TO_GroupID;
    private String TO_Name;
    private String TO_Materials;
    private String TO_Member;
    private Integer TO_Period;


    public TOR() {
    }

    public TOR(String TO_GroupID, String TO_Name, String TO_Materials, String TO_Member, int TO_Period) {
        this.TO_GroupID = TO_GroupID;
        this.TO_Name = TO_Name;
        this.TO_Materials = TO_Materials;
        this.TO_Member = TO_Member;
        this.TO_Period = TO_Period;
    }

    public String getTO_Materials() {
        return TO_Materials;
    }

    public void setTO_Materials(String TO_Materials) {
        this.TO_Materials = TO_Materials;
    }

    public String getTO_GroupID() {
        return TO_GroupID;
    }

    public void setTO_GroupID(String TO_GroupID) {
        this.TO_GroupID = TO_GroupID;
    }

    public String getTO_Member() {
        return TO_Member;
    }

    public void setTO_Member(String TO_Member) {
        this.TO_Member = TO_Member;
    }

    public int getTO_Period() {
        return TO_Period;
    }

    public void setTO_Period(int TO_Period) {
        this.TO_Period = TO_Period;
    }

    public String getTO_Name() {
        return TO_Name;
    }

    public void setTO_Name(String TO_Name) {
        this.TO_Name = TO_Name;
    }

    public String testPrint(){
        return TO_Name + "/" + TO_Period + "/" + TO_Member + "/" + TO_Materials + "/" + TO_GroupID ;
    }

    @Override
    public String toString() {
        return TO_GroupID + ", " + TO_Name + ", " + TO_Materials + ", " + TO_Member + ", " + TO_Period;
    }

}
