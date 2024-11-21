import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Individ
{
    private static final int NR_MISIONARI = 3;
    private static final int NR_CANIBALI = 3;
    private static final int MAX_PASI = 11;

    // Mutari posibile: {canibali, misionari}
    private static final int[][] MUTARI_POSIBILE = {{1, 0}, {0, 1}, {1, 1}, {2, 0}, {0, 2}};

    private List<Stare> individ;
    private double fitness;

    public Individ()
    {
        this.individ = null;
        this.fitness = 0;
    }

    public static List<Stare> genereazaIndivid()
    {
        List<Stare> individ = new ArrayList<>();
        Random random = new Random();

        for (int i = 0; i < MAX_PASI; i++)
        {
            int[] mutare = MUTARI_POSIBILE[random.nextInt(MUTARI_POSIBILE.length)];
            individ.add(new Stare(mutare[0], mutare[1]));
        }
        return individ;
    }

    public void setIndivid(List<Stare> individ)
    {
        this.individ = individ;
    }
    public List<Stare> getIndivid()
    {
        return individ;
    }
    public int getNrMisionari()
    {
        return NR_MISIONARI;
    }
    public int getNrCanibali()
    {
        return NR_CANIBALI;
    }
    public double getFitness()
    {
        return fitness;
    }
    public void setFitness(double fitness)
    {
        this.fitness = fitness;
    }
    public void afisareIndivid()
    {
        for (Stare stare : individ)
        {
            stare.afisareStare();
        }
        System.out.println();
    }
}
