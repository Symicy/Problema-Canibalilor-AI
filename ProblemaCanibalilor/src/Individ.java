import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Individ
{
    private static final int NR_MISIONARI = 3;
    private static final int NR_CANIBALI = 3;
    private static final int MAX_PASI = 50;

    // Mutari posibile: {canibali, misionari}
    private static final int[][] MUTARI_POSIBILE = {{1, 0}, {0, 1}, {1, 1}, {2, 0}, {0, 2}};

    private List<Stare> individ = new ArrayList<>();

    public Individ()
    {
        this.individ = genereazaIndivid();
    }

    private List<Stare> genereazaIndivid()
    {
        List<Stare> individ = new ArrayList<>();
        Random random = new Random();

        // Stare initiala
        Stare stareCurenta = new Stare(NR_CANIBALI, NR_MISIONARI, 0, 0, 0, 0, true);
        individ.add(stareCurenta);

        for (int i = 1; i < MAX_PASI; i++)
        {
            int[] mutare = MUTARI_POSIBILE[random.nextInt(MUTARI_POSIBILE.length)];
            int canibaliMutati = mutare[0];
            int misionariMutati = mutare[1];

            // Verificam daca mutarea este valida
            if (stareCurenta.pozitieBarca)
            {
                if (stareCurenta.CS >= canibaliMutati && stareCurenta.MS >= misionariMutati && (canibaliMutati + misionariMutati > 0))
                {
                    // Calculam noua stare dupa mutare de pe malul stang catre malul drept
                    Stare nouaStare = new Stare(
                            stareCurenta.CS - canibaliMutati,
                            stareCurenta.MS - misionariMutati,
                            canibaliMutati,
                            misionariMutati,
                            stareCurenta.CD + canibaliMutati,
                            stareCurenta.MD + misionariMutati,
                            false
                    );

                    if (nouaStare.esteValida())
                    {
                        individ.add(nouaStare);
                        stareCurenta = nouaStare;
                    }
                }
            }
            else
            {
                if (stareCurenta.CD >= canibaliMutati && stareCurenta.MD >= misionariMutati && (canibaliMutati + misionariMutati > 0))
                {
                    // Calculam noua stare dupa mutare de pe malul drept catre malul stang
                    Stare nouaStare = new Stare(
                            stareCurenta.CS + canibaliMutati,
                            stareCurenta.MS + misionariMutati,
                            canibaliMutati,
                            misionariMutati,
                            stareCurenta.CD - canibaliMutati,
                            stareCurenta.MD - misionariMutati,
                            true
                    );

                    if (nouaStare.esteValida())
                    {
                        individ.add(nouaStare);
                        stareCurenta = nouaStare;
                    }
                }
            }
        }
        return individ;
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
}
