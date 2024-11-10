import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main
{
    private static final int NR_INDIVIZI = 100;
    // Functie pentru a genera o populatie de indivizi
    private static List<Individ> genereazaPopulatie()
    {
        List<Individ> populatie = new ArrayList<>();
        for (int i = 0; i < NR_INDIVIZI; i++)
        {
            Individ individ = new Individ();
            populatie.add(individ);
        }
        return populatie;
    }
    //Functie fitness
    //Acordare puncte pentru fiecare stare valida
    //Acordare penalizare pentru un nr minim de pasi
    //Acordare premiu pentru solutia finala
    //Acordare puncte daca nu se repeta starea
    private static final int MIN_PASI = 11;  // Numarul minim teoretic de pasi pentru solutie
    private static final int MAX_PASI = 50;  // Limita maxima permisa de pași
    private static final double PENALIZARE_STARE_IDENTICA = 0.5;  // Penalizare pentru stari identice consecutive
    private static final double PENALIZARE_STARE_FINALA_NEVALIDA = 2.0;  // Penalizare pentru stari nevalide
    private static final double PENALIZARE_PERSOANA_RAMASA=1.0; // Penalizare pentru persoanele ramase pe malul stang

    public static double calculeazaFitness(List<Stare> individ)
    {
        int numarPasi = individ.size();
        double penalizari = 0.0;
        // Parcurgem fiecare stare si aplicam penalizările
        for (int i = 0; i < individ.size() - 1; i++)
        {
            Stare stareCurenta = individ.get(i);
            Stare stareUrmatoare = individ.get(i + 1);
            // Verificam dacă exista doua stari identice consecutive
            if (stareCurenta.equals(stareUrmatoare))
                penalizari += PENALIZARE_STARE_IDENTICA;
        }

        // Adaugam o penalizare finala dacă ultima stare nu este starea soluției (toti misionarii si canibalii pe malul drept)
        Stare stareFinala = individ.getLast();
        if (!(stareFinala.CS == 0 && stareFinala.MS == 0 && stareFinala.CD == 3 && stareFinala.MD == 3))
        {
            penalizari += PENALIZARE_STARE_FINALA_NEVALIDA;
            penalizari += (stareFinala.CS + stareFinala.MS) * PENALIZARE_PERSOANA_RAMASA;
        }
        // Calculam fitness ul
        double fitness = numarPasi + penalizari;
        return  ((fitness - MIN_PASI) / (MAX_PASI - MIN_PASI))*10;
    }

    public static void main(String[] args)
    {

        List<Individ> populatie = genereazaPopulatie();
        // Afisare indivizi(100) si fitness
        for (int i = 0; i < 100; i++)
        {
            System.out.println("Individ " + (i + 1) + ": ");
            Individ individ = populatie.get(i);
            for (Stare stare : individ.getIndivid())
                stare.afisareStare();
            double fitness2=calculeazaFitness(individ.getIndivid());
            System.out.println("Fitness individ " + (i + 1) + ": " + fitness2);

            System.out.println();
        }
    }
}

