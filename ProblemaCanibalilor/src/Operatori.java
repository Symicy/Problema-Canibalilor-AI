import java.util.List;
import java.util.Random;

public class Operatori
{
    public Individ selectie(List<Individ> populatie)
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
}
