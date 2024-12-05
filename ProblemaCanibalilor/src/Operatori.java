import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Operatori
{
    private static final int[][] MUTARI_POSIBILE = {{1, 0}, {0, 1}, {1, 1}, {2, 0}, {0, 2}};
    private static final Random random = new Random();

    public static Individ selectieTurnir(List<Individ> populatie, int k)
    {
        // Alegem un subset de k indivizi
        List<Individ> turnir = new ArrayList<>();
        for (int i = 0; i < k; i++)
        {
            int indexAleator = random.nextInt(populatie.size());
            turnir.add(populatie.get(indexAleator));
        }
        // Gasim cel mai bun individ din turnir (cel cu fitness-ul cel mai mare)
        turnir.sort((individ1, individ2) -> Double.compare(individ2.getFitness(), individ1.getFitness()));
        Individ celMaiBun = turnir.getFirst();
        return celMaiBun;
    }

    public static List<Individ> incrucisare(Individ parinte1, Individ parinte2)
    {
        // Incrucisarea a doi parinti
        List<Stare> copilIndivid1 = new ArrayList<>();
        List<Stare> copilIndivid2 = new ArrayList<>();
        Individ copil1 = new Individ();
        Individ copil2 = new Individ();
        int punctIncrucisare = random.nextInt(parinte1.getIndivid().size()-1);
        for (int i = 0; i < parinte1.getIndivid().size(); i++)
        {
            if (i < punctIncrucisare)
            {
                copilIndivid1.add(parinte1.getIndivid().get(i));
                copilIndivid2.add(parinte2.getIndivid().get(i));
            }
            else
            {
                copilIndivid1.add(parinte2.getIndivid().get(i));
                copilIndivid2.add(parinte1.getIndivid().get(i));
            }
        }
        copil1.setIndivid(copilIndivid1);
        copil2.setIndivid(copilIndivid2);
        return List.of(copil1, copil2);
    }

    public static List<Individ> incrucisare2Puncte(Individ individ1, Individ individ2)
    {
        // Incrucisarea a doi parinti
        List<Stare> copilIndivid1 = new ArrayList<>();
        List<Stare> copilIndivid2 = new ArrayList<>();
        Individ copil1 = new Individ();
        Individ copil2 = new Individ();
        int punctIncrucisare1 = random.nextInt(individ1.getIndivid().size() - 1);
        int punctIncrucisare2 = punctIncrucisare1 + random.nextInt(individ1.getIndivid().size() - punctIncrucisare1);
        for (int i = 0; i < individ1.getIndivid().size(); i++)
        {
            if (i < punctIncrucisare1 || i >= punctIncrucisare2)
            {
                copilIndivid1.add(individ1.getIndivid().get(i));
                copilIndivid2.add(individ2.getIndivid().get(i));
            }
            else
            {
                copilIndivid1.add(individ2.getIndivid().get(i));
                copilIndivid2.add(individ1.getIndivid().get(i));
            }
        }
        copil1.setIndivid(copilIndivid1);
        copil2.setIndivid(copilIndivid2);
        return List.of(copil1, copil2);
    }

    public static void mutatie(Individ individ, int sansaMutatie)
    {
        // Mutatie a unui individ
        int sansa = random.nextInt(100);
        if (sansa <= sansaMutatie)
        {
            int pozitieMutatie = random.nextInt(individ.getIndivid().size()-1);
            int[] mutare = MUTARI_POSIBILE[random.nextInt(MUTARI_POSIBILE.length)];
            while (individ.getIndivid().get(pozitieMutatie).getCB() == mutare[0] && individ.getIndivid().get(pozitieMutatie).getMB() == mutare[1])
            {
                mutare = MUTARI_POSIBILE[random.nextInt(MUTARI_POSIBILE.length)];
            }
            individ.getIndivid().set(pozitieMutatie, new Stare(mutare[0], mutare[1]));
        }
    }

}