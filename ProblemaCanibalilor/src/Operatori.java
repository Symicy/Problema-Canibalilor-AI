import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Operatori
{
    public List<Individ> selectie(List<Individ> populatie, int nrNouaPopulatie)
    {
        // Selectia prin metoda ruleta
        List<Individ> populatieNoua = new ArrayList<>();
        // Calculam suma fitness-urilor
        double sumaFitness = 0;
        for (Individ individ : populatie)
        {
            sumaFitness += individ.getFitness();
        }
        Random random = new Random();
        for(int i = 0; i < nrNouaPopulatie; i++)
        {
            double valoareAleatoare = random.nextDouble() * sumaFitness;
            double suma = 0;
            for (Individ individ : populatie)
            {
                suma += individ.getFitness();
                if (suma > valoareAleatoare)
                {
                    populatieNoua.add(individ);
                    populatie.remove(individ);
                    sumaFitness -= individ.getFitness();
                    break;
                }
            }
        }
        return populatieNoua;
    }
}
