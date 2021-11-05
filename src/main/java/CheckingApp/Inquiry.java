package CheckingApp;

public class Inquiry {
    private String Inq_Name;
    private int Inq_Price;
    private int Inq_Amount;

    public Inquiry() {
    }

    public Inquiry(String inq_Name, int inq_Price, int inq_Amount) {
        Inq_Name = inq_Name;
        Inq_Price = inq_Price;
        Inq_Amount = inq_Amount;
    }

    public String getInq_Name() {
        return Inq_Name;
    }

    public void setInq_Name(String inq_Name) {
        Inq_Name = inq_Name;
    }

    public int getInq_Price() {
        return Inq_Price;
    }

    public void setInq_Price(int inq_Price) {
        Inq_Price = inq_Price;
    }

    public int getInq_Amount() {
        return Inq_Amount;
    }

    public void setInq_Amount(int inq_Amount) {
        Inq_Amount = inq_Amount;
    }

    @Override
    public String toString() {
        return Inq_Name + ", " + Inq_Price + ", " + Inq_Amount;
    }
}
