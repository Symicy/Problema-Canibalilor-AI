
public class Stare {
    int CB, MB;
    public Stare(int CB, int MB) {
        this.CB = CB;
        this.MB = MB;
    }

    //Afisare stare
    public void afisareStare() {
        System.out.print("(" + CB + " : " + MB + "), ");
    }
    //Getters and Setters
    public int getCB() {
        return CB;
    }

    public void setCB(int CB) {
        this.CB = CB;
    }

    public int getMB() {
        return MB;
    }

    public void setMB(int MB) {
        this.MB = MB;
    }
}