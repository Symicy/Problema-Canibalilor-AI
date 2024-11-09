import java.util.ArrayList;
import java.util.List;
import java.util.Random;

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
        System.out.println("CS:" + CS + " MS:" + MS + ", CB:" + CB + " MB:" + MB + ", CD:" + CD + " MD:" + MD + " Mal barca:" + afPozitieBarca(pozitieBarca));
    }
}

public class Main
{
    private static final int NR_MISIONARI = 3;
    private static final int NR_CANIBALI = 3;
    private static final int NR_INDIVIZI = 100;
    private static final int MAX_PASI = 50;

    // Mutari posibile: {canibali, misionari}
    private static final int[][] MUTARI_POSIBILE = {{1, 0}, {0, 1}, {1, 1}, {2, 0}, {0, 2}};

    // Functie generare individ
    private static List<Stare> genereazaIndivid()
    {
        List<Stare> individ = new ArrayList<>();
        Random random = new Random();

        // Stare initiala
        Stare stareCurenta = new Stare(NR_CANIBALI, NR_MISIONARI, 0, 0, 0, 0, true);
        individ.add(stareCurenta);

        for (int i = 1; i < MAX_PASI; i++)
        {
            int[] mutare = MUTARI_POSIBILE[random.nextInt(MUTARI_POSIBILE.length)];
            int canibaliMutati = mutare[0];
            int misionariMutati = mutare[1];

            // Verificam daca mutarea este valida
            if (stareCurenta.pozitieBarca)
            {
                if (stareCurenta.CS >= canibaliMutati && stareCurenta.MS >= misionariMutati && (canibaliMutati + misionariMutati > 0))
                {
                    // Calculam noua stare dupa mutare de pe malul stang catre malul drept
                    Stare nouaStare = new Stare(
                            stareCurenta.CS - canibaliMutati,
                            stareCurenta.MS - misionariMutati,
                            canibaliMutati,
                            misionariMutati,
                            stareCurenta.CD + canibaliMutati,
                            stareCurenta.MD + misionariMutati,
                            false
                    );

                    if (nouaStare.esteValida())
                    {
                        individ.add(nouaStare);
                        stareCurenta = nouaStare;
                    }
                }
            } else
            {
                if (stareCurenta.CD >= canibaliMutati && stareCurenta.MD >= misionariMutati && (canibaliMutati + misionariMutati > 0))
                {
                    // Calculam noua stare dupa mutare de pe malul drept catre malul stang
                    Stare nouaStare = new Stare(
                            stareCurenta.CS + canibaliMutati,
                            stareCurenta.MS + misionariMutati,
                            canibaliMutati,
                            misionariMutati,
                            stareCurenta.CD - canibaliMutati,
                            stareCurenta.MD - misionariMutati,
                            true
                    );

                    if (nouaStare.esteValida())
                    {
                        individ.add(nouaStare);
                        stareCurenta = nouaStare;
                    }
                }
            }
        }
        return individ;
    }

    // Functie pentru a genera o populatie de indivizi
    private static List<List<Stare>> genereazaPopulatie()
    {
        List<List<Stare>> populatie = new ArrayList<>();
        for (int i = 0; i < NR_INDIVIZI; i++) {
            List<Stare> individ = genereazaIndivid();
            populatie.add(individ);
        }
        return populatie;
    }

    private static int calculeazaFitness(List<Stare> individ) {
        int fitness = 0;
        final int PENALIZARE_STARE_INVALIDA = -1000; // Penalizare mare pentru stări invalide
        final int PREMII_FINAL_COMPLET = 1000; // Premiu pentru soluția finală

        for (Stare stare : individ) {
            // Penalizare dacă starea este invalidă
            if (!stare.esteValida()) {
                fitness += PENALIZARE_STARE_INVALIDA;
            }

            // Adaugăm puncte pentru numărul de canibali și misionari de pe malul drept
            fitness += stare.CD + stare.MD;
        }

        // Premiem individul dacă toți canibalii și misionarii au ajuns pe malul drept
        Stare ultimaStare = individ.get(individ.size() - 1);
        if (ultimaStare.CD == NR_CANIBALI && ultimaStare.MD == NR_MISIONARI) {
            fitness += PREMII_FINAL_COMPLET;
        }

        return fitness;
    }

    public static void main(String[] args)
    {
        List<List<Stare>> populatie = genereazaPopulatie();
        // Afisare indivizi(100)
        for (int i = 0; i < 100; i++)
        {
            System.out.println("Individ " + (i + 1) + ": ");
            List<Stare> individ = populatie.get(i);
            for (Stare stare : individ)
                stare.afisareStare();
            int fitness = calculeazaFitness(individ);
            System.out.println("Fitness individ " + (i + 1) + ": " + fitness);

            System.out.println();
        }
    }
}

