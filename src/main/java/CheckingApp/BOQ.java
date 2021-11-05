package CheckingApp;

public class BOQ {
    private String BO_Name;
    private int BO_Price;
    private int BO_Amount;

    public BOQ() {
    }

    public BOQ(String BO_Name, int BO_Price, int BO_Amount) {
        this.BO_Name = BO_Name;
        this.BO_Price = BO_Price;
        this.BO_Amount = BO_Amount;
    }

    public String getBO_Name() {
        return BO_Name;
    }

    public void setBO_Name(String BO_Name) {
        this.BO_Name = BO_Name;
    }

    public int getBO_Price() {
        return BO_Price;
    }

    public void setBO_Price(int BO_Price) {
        this.BO_Price = BO_Price;
    }

    public int getBO_Amount() {
        return BO_Amount;
    }

    public void setBO_Amount(int BO_Amount) {
        this.BO_Amount = BO_Amount;
    }

    @Override
    public String toString() {
        return BO_Name + ", " + BO_Price + ", " + BO_Amount;
    }
}
