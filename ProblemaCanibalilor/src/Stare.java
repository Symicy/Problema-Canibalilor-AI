// Stare individ (CS, MS, CB, MB, CD, MD)
class Stare
{
    int CS, MS, CB, MB, CD, MD;
    boolean pozitieBarca;

    public Stare(int CS, int MS, int CB, int MB, int CD, int MD, boolean pozitieBarca)
    {
        this.CS = CS;
        this.MS = MS;
        this.CB = CB;
        this.MB = MB;
        this.CD = CD;
        this.MD = MD;
        this.pozitieBarca = pozitieBarca;
    }

    // Metoda pentru a verifica daca starea este valida
    public boolean esteValida()
    {
        if (CS > MS && MS > 0)
            return false;
        if (CD > MD && MD > 0)
            return false;
        return true;
    }
    private String afPozitieBarca(boolean pozitieBarca)
    {
        if (pozitieBarca) {
            return "Stanga";
        } else {
            return "Dreapta";
        }
    }
    //Afisare stare
    public void afisareStare()
    {
        System.out.print("(CB:" + CB + " MB:" + MB + "), ");
        //System.out.print("{(" + CS + ":" + MS + ") - (" + CB + ":" + MB + ") - (" + CD + ":" + MD + ")}, ");
    }
}