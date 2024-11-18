import java.util.ArrayList;
import java.util.List;

public class Main
{
    private static final int NR_INDIVIZI = 100;
    private static final int NR_MISIONARI = 3;
    private static final int NR_CANIBALI = 3;
    private static final int PUNCTE_MAX = 1;
    private static final int PUNCTE_MIN = 0;
    //Functie fitness
    //Acordare premiu pentru solutia finala
    //Acordare puncte daca nu se repeta starea
    private static final double PUNCTE_STARI_DIFERITE = 0.25;
    private static final double PUNCTE_STARI_IDENTICE = -0.25;
    private static final double PUNCTE_MISIONARI_MANCATI = -0.5;
    private static final double PUNCTE_MUTARE_IMPOSIBILA = -1.0;
    private static final double PUNCTE_MUTARE_POSIBILA = 0.2;
    private static final double PUNCTE_PERSOANA_MUTATA = 0.1;

    public static void calculeazaFitness(Individ individ)
    {
        int nrMisionariStanga = NR_MISIONARI;
        int nrCanibaliStanga = NR_CANIBALI;
        int nrMisionariDreapta = 0;
        int nrCanibaliDreapta = 0;
        boolean barcaStanga = true; // Barca începe pe malul stâng
        double puncte = 0;

        int canibaliMutatiAnterior = 0;
        int misionariMutatiAnterior = 0;

        for (Stare stare : individ.getIndivid())
        {
            int canibaliMutati = stare.getCB();
            int misionariMutati = stare.getMB();

            // Actualizare stări bazate pe direcția bărcii
            if (barcaStanga)
            {
                if (canibaliMutati > nrCanibaliStanga || misionariMutati > nrMisionariStanga)
                {
                    puncte += PUNCTE_MUTARE_IMPOSIBILA;
                }
                else
                {
                    puncte += PUNCTE_MUTARE_POSIBILA;
                }
                if(canibaliMutati==canibaliMutatiAnterior && misionariMutati==misionariMutatiAnterior)
                {
                    puncte += PUNCTE_STARI_IDENTICE;
                }
                else
                {
                    puncte += PUNCTE_STARI_DIFERITE;
                }
                if (canibaliMutati+nrMisionariDreapta>misionariMutati+nrCanibaliDreapta)
                {
                    puncte += PUNCTE_MISIONARI_MANCATI;
                }
                canibaliMutatiAnterior=canibaliMutati;
                misionariMutatiAnterior=misionariMutati;

                nrCanibaliStanga -= canibaliMutati;
                nrMisionariStanga -= misionariMutati;
                nrCanibaliDreapta += canibaliMutati;
                nrMisionariDreapta += misionariMutati;
            }
            else
            {
                if (canibaliMutati > nrCanibaliDreapta || misionariMutati > nrMisionariDreapta)
                {
                    puncte += PUNCTE_MUTARE_IMPOSIBILA;
                }
                else
                {
                    puncte += PUNCTE_MUTARE_POSIBILA;
                }
                if(canibaliMutati==canibaliMutatiAnterior && misionariMutati==misionariMutatiAnterior)
                {
                    puncte += PUNCTE_STARI_IDENTICE;
                }
                else
                {
                    puncte += PUNCTE_STARI_DIFERITE;
                }
                if (canibaliMutati+nrMisionariStanga>misionariMutati+nrCanibaliStanga)
                {
                    puncte += PUNCTE_MISIONARI_MANCATI;
                }
                canibaliMutatiAnterior=canibaliMutati;
                misionariMutatiAnterior=misionariMutati;

                nrCanibaliDreapta -= canibaliMutati;
                nrMisionariDreapta -= misionariMutati;
                nrCanibaliStanga += canibaliMutati;
                nrMisionariStanga += misionariMutati;
            }
            // Barca își schimbă poziția
            barcaStanga = !barcaStanga;
        }

//        // Verificare dacă jocul este complet
//        if (nrMisionariStanga == 0 && nrCanibaliStanga == 0 && nrMisionariDreapta == NR_MISIONARI && nrCanibaliDreapta == NR_CANIBALI)
//        {
//            individ.setFitness(1.0); // Solutie gasita
//            return;
//        }
        //
        puncte += PUNCTE_PERSOANA_MUTATA*(nrMisionariDreapta+nrCanibaliDreapta);
        // Calculul fitness-ului pentru un joc incomplet
        double fitness= PUNCTE_MIN + (PUNCTE_MAX - PUNCTE_MIN) * ((puncte - PUNCTE_MIN) / (PUNCTE_MISIONARI_MANCATI - PUNCTE_MIN));
        individ.setFitness(fitness);
    }

    public static List<Individ> combinare_populatii(List<Individ> populatie1, List<Individ> populatie2)
    {
        List<Individ> populatie_combinata = new ArrayList<>();
        for(int i=0;i<(int)(populatie1.size()*0.2);i++)
        {
            populatie_combinata.add(populatie1.get(i));
        }
        for(int i=0;i<(int)(populatie2.size()*0.8);i++)
        {
            populatie_combinata.add(populatie2.get(i));
        }
        return populatie_combinata;
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
                Individ copil = Operatori.incrucisare(parinte1, parinte2);
                Operatori.mutatie(copil);
                calculeazaFitness(copil);
                nouaPopulatie.add(copil);
            }
            populatie = nouaPopulatie;
            //populatie=combinare_populatii(populatie,nouaPopulatie);

            populatie.sort((individ1, individ2) -> Double.compare(individ2.getFitness(), individ1.getFitness()));
            top_indivizi.add(populatie.getFirst());
        }
        top_indivizi.sort((individ1, individ2) -> Double.compare(individ2.getFitness(), individ1.getFitness()));
        for(int i=0;i<10;i++)
        {
            System.out.println("Top Individ:"+i);
            for(Stare stare : top_indivizi.get(i).getIndivid())
            {
                stare.afisareStare();
            }
            System.out.println("\nFitness: " + top_indivizi.get(i).getFitness());
        }
    }
}

