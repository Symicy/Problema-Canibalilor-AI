import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static java.lang.Math.pow;

public class Main
{
    private static final int NR_INDIVIZI = 100;
    private static final int NR_MISIONARI = 3;
    private static final int NR_CANIBALI = 3;
    //Functie fitness
    private static final double PUNCTE_STARI_DIFERITE = 0.25;
    private static final double PUNCTE_STARI_IDENTICE = -0.25;
    private static final double PUNCTE_MISIONARI_MANCATI = -0.75;
    private static final double PUNCTE_MUTARE_IMPOSIBILA = -1.0;
    private static final double PUNCTE_MUTARE_POSIBILA = 0.2;
    private static final double PUNCTE_PERSOANA_MUTATA = 0.3;

    public static void calculeazaFitness(Individ individ)
    {
        int nrMisionariStanga = NR_MISIONARI;
        int nrCanibaliStanga = NR_CANIBALI;
        int nrMisionariDreapta = 0;
        int nrCanibaliDreapta = 0;
        boolean barcaStanga = true; // Barca începe pe malul stâng
        double fitness = 0;

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
                    fitness += PUNCTE_MUTARE_IMPOSIBILA;
                }
                else
                {
                    fitness += PUNCTE_MUTARE_POSIBILA;
                }
                if(canibaliMutati==canibaliMutatiAnterior && misionariMutati==misionariMutatiAnterior)
                {
                    fitness += PUNCTE_STARI_IDENTICE;
                }
                else
                {
                    fitness += PUNCTE_STARI_DIFERITE;
                }
                if (canibaliMutati+nrMisionariDreapta>misionariMutati+nrCanibaliDreapta)
                {
                    fitness += PUNCTE_MISIONARI_MANCATI;
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
                    fitness += PUNCTE_MUTARE_IMPOSIBILA;
                }
                else
                {
                    fitness += PUNCTE_MUTARE_POSIBILA;
                }
                if(canibaliMutati==canibaliMutatiAnterior && misionariMutati==misionariMutatiAnterior)
                {
                    fitness += PUNCTE_STARI_IDENTICE;
                }
                else
                {
                    fitness += PUNCTE_STARI_DIFERITE;
                }
                if (canibaliMutati+nrMisionariStanga>misionariMutati+nrCanibaliStanga)
                {
                    fitness += PUNCTE_MISIONARI_MANCATI;
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
        fitness += PUNCTE_PERSOANA_MUTATA*(nrMisionariDreapta+nrCanibaliDreapta);
        //fitness=pow(fitness,2);
        individ.setFitness(fitness);
    }


    public static void main(String[] args)
    {
        List<Double> fitnessuri=new ArrayList<>();
        double medie_fitness=0;

        List<Individ>top_indivizi=new ArrayList<>();
        // Generare populatie initiala
        List<Individ> populatie = new ArrayList<>();
        for (int i = 0; i < NR_INDIVIZI; i++)
        {
            Individ individ = new Individ();
            individ.setIndivid(Individ.genereazaIndivid());
            calculeazaFitness(individ);
            populatie.add(individ);

            medie_fitness+=individ.getFitness();
        }

        medie_fitness/=NR_INDIVIZI;
        fitnessuri.add(medie_fitness);

        populatie.sort((individ1, individ2) -> Double.compare(individ2.getFitness(), individ1.getFitness()));
        top_indivizi.add(populatie.getFirst());

        //100 de generatii
        for (int i = 0; i < 100; i++)
        {
            medie_fitness=0;

            List<Individ> nouaPopulatie = new ArrayList<>();
            for (int j = 0; j < NR_INDIVIZI/2; j++)
            {
                Individ parinte1 = Operatori.selectieTurnir(populatie,4);
                Individ parinte2 = Operatori.selectieTurnir(populatie,4);
                List<Individ> copii = Operatori.incrucisare(parinte1, parinte2);
                Individ copil1 = copii.get(0);
                Individ copil2 = copii.get(1);
                Operatori.mutatie(copil1,5);
                Operatori.mutatie(copil2,5);
                calculeazaFitness(copil1);
                calculeazaFitness(copil2);
                nouaPopulatie.add(copil1);
                nouaPopulatie.add(copil2);

                medie_fitness+=copil1.getFitness();
                medie_fitness+=copil2.getFitness();
            }
            medie_fitness/=NR_INDIVIZI;
            fitnessuri.add(medie_fitness);

            nouaPopulatie.sort((individ1, individ2) -> Double.compare(individ2.getFitness(), individ1.getFitness()));
            top_indivizi.add(nouaPopulatie.getFirst());
            populatie = nouaPopulatie;
        }
        for(int i=0;i<100;i++)
        {
            System.out.println("Top Individ:"+i);
            top_indivizi.get(i).afisareIndivid();
            System.out.println("Fitness: " + top_indivizi.get(i).getFitness());
        }

        for(int i=0;i<100;i++)
        {
            System.out.println("Generatia: "+i);
            System.out.println("Medie fitness: "+fitnessuri.get(i));
        }
    }
}