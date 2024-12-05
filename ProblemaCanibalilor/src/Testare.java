import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Testare {
    private static final int[][] MUTARI_POSIBILE = {{1, 0}, {0, 1}, {1, 1}, {2, 0}, {0, 2}};
    private static final int MAX_PASI = 11;
    private static final Random random = new Random();

    @Test
    void selectieTurnirReturneazaCelMaiBunIndivid() {
        List<Individ> populatie = new ArrayList<>();
        Individ individ1 = new Individ();
        individ1.setFitness(1.0);
        Individ individ2 = new Individ();
        individ2.setFitness(2.0);
        Individ individ3 = new Individ();
        individ3.setFitness(3.0);
        Individ individ4 = new Individ();
        individ4.setFitness(4.0);
        populatie.add(individ1);
        populatie.add(individ2);
        populatie.add(individ3);
        populatie.add(individ4);
        Individ result = Operatori.selectieTurnir(populatie, 4);
        assertEquals(4.0, result.getFitness());
    }

    @Test
    void selectieTurnirCandEsteUnSingurIndivid() {
        List<Individ> populatie = new ArrayList<>();
        Individ individ1 = new Individ();
        individ1.setFitness(1.0);
        populatie.add(individ1);
        Individ result = Operatori.selectieTurnir(populatie, 4);
        assertEquals(1.0, result.getFitness());
    }

    @Test
    void incrucisareProducereDeDoiCopii() {
        Individ parinte1 = new Individ();
        Individ parinte2 = new Individ();
        parinte1.setIndivid(Individ.genereazaIndivid());
        parinte2.setIndivid(Individ.genereazaIndivid());
        List<Individ> copii = Operatori.incrucisare(parinte1, parinte2);
        assertEquals(2, copii.size());
    }

    @Test
    void incrucisareRezultatBunDeCopii(){
        Individ parinte1 = new Individ();
        Individ parinte2 = new Individ();
        List<Stare> stari1 = new ArrayList<>();
        List<Stare> stari2 = new ArrayList<>();
        for (int i = 0; i < 11; i++) {
            stari1.add(new Stare (1,1));
            stari2.add(new Stare (2,0));
        }
        parinte1.setIndivid(stari1);
        parinte2.setIndivid(stari2);

        List<Individ> copii = Operatori.incrucisare(parinte1, parinte2);
        int nrStari = 0;
        while (nrStari < 11 && copii.get(0).getIndivid().get(nrStari).equals(new Stare(1, 1))) {
            nrStari++;
        }
        Stare stare1 = new Stare(1, 1);
        Stare stare2 = new Stare(2, 0);
        for (int i = 0; i<11; i++) {

            if (i < nrStari) {
                assertEquals(stare1, copii.get(0).getIndivid().get(i));
                assertEquals(stare2, copii.get(1).getIndivid().get(i));
            } else {
                assertEquals(stare2, copii.get(0).getIndivid().get(i));
                assertEquals(stare1, copii.get(1).getIndivid().get(i));
            }
        }
    }
    @Test
    void mutatieSchimbaIndivid() {
        Individ individ = new Individ();
        Individ individCopie = new Individ();
        List<Stare> stari1 = new ArrayList<>();
        for (int i = 0; i <MAX_PASI; i++)
        {
            int[] mutare = MUTARI_POSIBILE[random.nextInt(MUTARI_POSIBILE.length)];
            stari1.add(new Stare(mutare[0], mutare[1]));
        }
        List<Stare> stari2 = new ArrayList<>(stari1);
        individ.setIndivid(stari1);
        individCopie.setIndivid(stari2);
        Operatori.mutatie(individ, 100);
        int schimbari = 0;
        for (int i = 0; i < individ.getIndivid().size(); i++) {
            if (!individ.getIndivid().get(i).equals(individCopie.getIndivid().get(i))) {
                schimbari++;
            }
        }
        assertEquals(1, schimbari);
    }
    @Test
    void mutatieNuSchimbaIndividul() {
        Individ individ = new Individ();
        Individ individCopie = new Individ();
        List<Stare> stari = new ArrayList<>();
        for (int i = 0; i < MAX_PASI; i++) {
            int[] mutare = MUTARI_POSIBILE[random.nextInt(MUTARI_POSIBILE.length)];
            stari.add(new Stare(mutare[0], mutare[1]));
        }
        individ.setIndivid(stari);
        individCopie.setIndivid(stari);
        Operatori.mutatie(individ, 0);
        int schimbari = 0;
        for (int i = 0; i < individ.getIndivid().size(); i++) {
            if (!individ.getIndivid().get(i).equals(individCopie.getIndivid().get(i))) {
                schimbari++;
            }
        }
        assertEquals(0, schimbari);
    }
}