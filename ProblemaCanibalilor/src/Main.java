import java.util.ArrayList;
import java.util.List;

public class Main
{
    private static final int NR_INDIVIZI = 100;
    private static final int NR_MISIONARI = 3;
    private static final int NR_CANIBALI = 3;
    //private static final int PUNCTE_MAX = 1;
    //private static final int PUNCTE_MIN = 0;
    //Functie fitness
    private static final double PUNCTE_STARI_DIFERITE = 0.25;
    private static final double PUNCTE_STARI_IDENTICE = -0.25;
    private static final double PUNCTE_MISIONARI_MANCATI = -0.60;
    private static final double PUNCTE_MUTARE_IMPOSIBILA = -1.0;
    private static final double PUNCTE_MUTARE_POSIBILA = 0.15;
    private static final double PUNCTE_PERSOANA_MUTATA = 0.15;

    public static void calculeazaFitness(Individ individ) {
        int nrMisionariStanga = NR_MISIONARI;
        int nrCanibaliStanga = NR_CANIBALI;
        int nrMisionariDreapta = 0;
        int nrCanibaliDreapta = 0;
        boolean barcaStanga = true;
        double fitness = 0;

        int canibaliMutatiAnterior = -1;
        int misionariMutatiAnterior = -1;

        for (Stare stare : individ.getIndivid()) {
            int canibaliMutati = stare.getCB();
            int misionariMutati = stare.getMB();

            // Verificare mutare imposibilă
            if (barcaStanga && (canibaliMutati > nrCanibaliStanga || misionariMutati > nrMisionariStanga)
                    || !barcaStanga && (canibaliMutati > nrCanibaliDreapta || misionariMutati > nrMisionariDreapta)) {
                fitness += PUNCTE_MUTARE_IMPOSIBILA;
                continue;
            }

            // Recompensare progres
            fitness += PUNCTE_MUTARE_POSIBILA;

            // Penalizare pentru mutări redundante
            if (canibaliMutati == canibaliMutatiAnterior && misionariMutati == misionariMutatiAnterior) {
                fitness += PUNCTE_STARI_IDENTICE;
            } else {
                fitness += PUNCTE_STARI_DIFERITE;
            }

            // Verificare dacă misionarii sunt mâncați
            if ((nrCanibaliStanga - canibaliMutati > nrMisionariStanga - misionariMutati && nrMisionariStanga - misionariMutati > 0)
                    || (nrCanibaliDreapta + canibaliMutati > nrMisionariDreapta + misionariMutati && nrMisionariDreapta + misionariMutati > 0)) {
                fitness += PUNCTE_MISIONARI_MANCATI;
            }

            // Actualizare stări
            if (barcaStanga) {
                nrCanibaliStanga -= canibaliMutati;
                nrMisionariStanga -= misionariMutati;
                nrCanibaliDreapta += canibaliMutati;
                nrMisionariDreapta += misionariMutati;
            } else {
                nrCanibaliDreapta -= canibaliMutati;
                nrMisionariDreapta -= misionariMutati;
                nrCanibaliStanga += canibaliMutati;
                nrMisionariStanga += misionariMutati;
            }

            // Recompensare progres incremental
            fitness += PUNCTE_PERSOANA_MUTATA * (nrMisionariDreapta + nrCanibaliDreapta);

            // Actualizare stări anterioare
            canibaliMutatiAnterior = canibaliMutati;
            misionariMutatiAnterior = misionariMutati;

            // Barca își schimbă poziția
            barcaStanga = !barcaStanga;
        }

        // Recompensă pentru soluție completă
        if (nrMisionariDreapta == NR_MISIONARI && nrCanibaliDreapta == NR_CANIBALI) {
            fitness += 2; // Bonus mare pentru soluție completă
        }

        individ.setFitness(fitness);
    }


    public static void main(String[] args)
    {
        List<Individ>top_indivizi=new ArrayList<>();
        // Generare populatie initiala
        List<Individ> populatie = new ArrayList<>();
        for (int i = 0; i < NR_INDIVIZI; i++)
        {
            Individ individ = new Individ();
            individ.setIndivid(Individ.genereazaIndivid());
            calculeazaFitness(individ);
            populatie.add(individ);
        }
        populatie.sort((individ1, individ2) -> Double.compare(individ2.getFitness(), individ1.getFitness()));
        top_indivizi.add(populatie.getFirst());

        //100 de generatii
        for (int i = 0; i < 100; i++)
        {
            List<Individ> nouaPopulatie = new ArrayList<>();
            for (int j = 0; j < NR_INDIVIZI; j++)
            {
                Individ parinte1 = Operatori.selectieTurnir(populatie,5);
                Individ parinte2 = Operatori.selectieTurnir(populatie,5);
                List<Individ> copii = Operatori.incrucisare(parinte1, parinte2);
                Individ copil1 = copii.get(0);
                Individ copil2 = copii.get(1);
                Operatori.mutatie(copil1,5);
                Operatori.mutatie(copil2,5);
                calculeazaFitness(copil1);
                calculeazaFitness(copil2);
                nouaPopulatie.add(copil1);
                nouaPopulatie.add(copil2);
            }
            populatie = nouaPopulatie;
            populatie.sort((individ1, individ2) -> Double.compare(individ2.getFitness(), individ1.getFitness()));
            top_indivizi.add(populatie.getFirst());
        }
        //top_indivizi.sort((individ1, individ2) -> Double.compare(individ2.getFitness(), individ1.getFitness()));
        for(int i=0;i<10;i++)
        {
            System.out.println("Top Individ:"+i);
            top_indivizi.get(i).afisareIndivid();
            System.out.println("Fitness: " + top_indivizi.get(i).getFitness());
        }
    }
}