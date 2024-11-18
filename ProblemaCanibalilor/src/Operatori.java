import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Operatori
{
    private static final int[][] MUTARI_POSIBILE = {{1, 0}, {0, 1}, {1, 1}, {2, 0}, {0, 2}};

    public Individ selectieRuleta(List<Individ> populatie)
    {
        // Selectia individului prin metoda ruleta
        double sumaFitness = 0;
        for (Individ individ : populatie)
            sumaFitness += individ.getFitness();
        Random random = new Random();
        double valoareAleatoare = random.nextDouble() * sumaFitness;
        double suma = 0;
        for (Individ individ : populatie)
        {
            suma += individ.getFitness();
            if (suma > valoareAleatoare)
                return individ;
        }
        return null;
    }

    public static Individ selectieTurnir(List<Individ> populatie, int k)
    {
        Random random = new Random();
        // Alegem un subset de k indivizi
        List<Individ> turnir = new ArrayList<>();
        for (int i = 0; i < k; i++)
        {
            int indexAleator = random.nextInt(populatie.size());
            turnir.add(populatie.get(indexAleator));
        }
        // Găsim cel mai bun individ din turnir (fitness minim)
        turnir.sort((individ1, individ2) -> Double.compare(individ1.getFitness(), individ2.getFitness()));
        Individ celMaiBun = turnir.getFirst();
        return celMaiBun;
    }

    public static Individ incrucisare(Individ parinte1, Individ parinte2)
    {
        // Incrucisarea a doi parinti
        Individ copil = new Individ();
        Random random = new Random();
        List<Stare> copilIndivid = new ArrayList<>();
        int punctIncrucisare = random.nextInt(parinte1.getIndivid().size());
        for (int i = 0; i < parinte1.getIndivid().size(); i++)
        {
            if (i < punctIncrucisare)
                copilIndivid.add(parinte1.getIndivid().get(i));
            else
                copilIndivid.add(parinte2.getIndivid().get(i));
        }
        copil.setIndivid(copilIndivid);
        return copil;
    }

    public static void mutatie(Individ individ)
    {
        // Mutatie a unui individ
        Random random = new Random();
        int pozitieMutatie = random.nextInt(individ.getIndivid().size());
        int[] mutare = MUTARI_POSIBILE[random.nextInt(MUTARI_POSIBILE.length)];
        individ.getIndivid().set(pozitieMutatie, new Stare(mutare[0], mutare[1]));
    }
}