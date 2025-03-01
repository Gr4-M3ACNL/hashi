package fr.m3acnl.game.logique;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;

/**
 * Test pour la classe Lien
 *
 * @see Lien
 * @see Tests
 * @see Noeud
 *
 * @author COGNARD Luka
 */
public class LienTest extends Tests {

    /**
     * Constructeur de la classe de test
     */
    public LienTest() {
    }

    /**
     * Méthode d'initialisation de la classe de test
     *
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {
        printNameAtStart(LienTest.class);
    }

    /**
     * Méthode de de fin de la classe de test
     *
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {
        printNameAtEnd(LienTest.class);
    }

    /**
     * Test de la méthode estValide()
     *
     * @see Lien#activer()
     * @see Lien#estValide()
     */
    @Test
    void testEstValide() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        assertEquals(false, l.estValide(), "Lien invalide renvoie false");
        l.activer();
        assertEquals(true, l.estValide(), "Lien invalide renvoie false");
    }

    /**
     * Test de la méthode getNbLien
     *
     * @see Lien#getNbLien()
     */
    @Test
    void testGetNbLien() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        assertEquals(0, l.getNbLien(), "Lien créer pas de lien nbLien a 0");
    }

    /**
     * Test de la méthode getNbLienSoluce
     *
     * @see Lien#getNbLienSoluce()
     */
    @Test
    void testGetNbLienSoluce() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        assertEquals(1, l.getNbLienSoluce(), "Lien créer pas de lien nbLien a 0");
    }

    /**
     * Test de la méthode getNoeud1
     *
     * @see Lien#getNoeud1()
     * @see Noeud#compareTo(Noeud)
     */
    @Test
    void testGetNoeud1() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        assertEquals(0, l.getNoeud1().compareTo(new Noeud(5, 2, 1)), "comparaison entre le noeud1 récupéré et un autre identique rnevoie 0");
    }

    /**
     * Test de la méthode getNoeud2
     *
     * @see Lien#getNoeud2()
     * @see Noeud#compareTo(Noeud)
     */
    @Test
    void testGetNoeud2() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        assertEquals(0, l.getNoeud2().compareTo(new Noeud(5, 4, 1)), "comparaison entre le noeud2 récupéré et un autre identique rnevoie 0");
    }

    /**
     * Test de la méthode getSurbrillance
     *
     * @see Lien#getSurbrillance()
     */
    @Test
    void testGetSurbrillance() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        assertEquals(false, l.getSurbrillance(), "Surbrillance a false");
    }

    /**
     * Test de la méthode activer()
     *
     * @see Lien#activer()
     * @see Lien#getNbLien()
     * @see Lien#getNoeud1()
     * @see Lien#getNoeud2()
     * @see Noeud#estValide()
     */
    @Test
    void testActiver() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        l.activer();
        assertEquals(1, l.getNbLien(), "Lien activé donc 1 ");
        assertEquals(0, l.getNoeud1().estValide(), "noeud incrémenter lié ");
        assertEquals(0, l.getNoeud2().estValide(), "noeud incrémenter lié ");
        l.activer();
        assertEquals(2, l.getNbLien(), "Lien activé 2 fois donc 2 ");
        assertEquals(-1, l.getNoeud1().estValide(), "noeud incrémenter lié ");
        assertEquals(-1, l.getNoeud2().estValide(), "noeud incrémenter lié ");
        l.activer();
        assertEquals(0, l.getNbLien(), "Lien activé 3 fois donc 0 ");
        assertEquals(1, l.getNoeud1().estValide(), "noeud incrémenter lié ");
        assertEquals(1, l.getNoeud2().estValide(), "noeud incrémenter lié ");
    }

    /**
     * Test de la méthode retourArriere()
     *
     * @see Lien#activer()
     * @see Lien#getNbLien()
     * @see Lien#getNoeud1()
     * @see Lien#getNoeud2()
     * @see Noeud#estValide()
     * @see Lien#retourArriere()
     */
    @Test
    void testRetourArriere() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        l.activer();
        l.retourArriere();
        assertEquals(0, l.getNbLien(), "Lien activé donc 1 ");
        assertEquals(1, l.getNoeud1().estValide(), "noeud incrémenter lié ");
        assertEquals(1, l.getNoeud2().estValide(), "noeud incrémenter lié ");
        l.activer();
        l.activer();
        l.retourArriere();
        assertEquals(1, l.getNbLien(), "Lien activé 2 fois donc 2 ");
        assertEquals(0, l.getNoeud1().estValide(), "noeud incrémenter lié ");
        assertEquals(0, l.getNoeud2().estValide(), "noeud incrémenter lié ");
        l.activer();
        l.activer();
        l.retourArriere();
        assertEquals(2, l.getNbLien(), "Lien activé 3 fois donc 0 ");
        assertEquals(-1, l.getNoeud1().estValide(), "noeud incrémenter lié ");
        assertEquals(-1, l.getNoeud2().estValide(), "noeud incrémenter lié ");
    }

    /**
     * Test de la méthode noeudDansLien
     *
     * @see Lien#noeudDansLien(Noeud)
     */
    @Test
    void testNoeudDansLien() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        Noeud n = new Noeud(5, 2, 1);
        assertEquals(0, l.noeudDansLien(n), "Recherche premier noeud identique renvoie 0");
        n = new Noeud(5, 4, 1);
        assertEquals(0, l.noeudDansLien(n), "Recherche deuxième noeud identique renvoie 0");
        n = new Noeud(5, 5, 1);
        assertEquals(-1, l.noeudDansLien(n), "Recherche Noeud non présent dans le lien renvoie ");
        n = new Noeud(5, 3, 1);
        assertEquals(1, l.noeudDansLien(n), "Recherche Noeud non présent dans le lien renvoie ");
    }

    /**
     * Test de la méthode surbrillanceOff
     *
     * @see Lien#surbrillanceOn()
     * @see Lien#surbrillanceOff()
     * @see Lien#getSurbrillance()
     */
    @Test
    void testSurbrillanceOff() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        l.surbrillanceOn();
        l.surbrillanceOff();
        assertEquals(false, l.getSurbrillance(), "Surbrillance a false");
    }

    /**
     * Test de la méthode surbrillanceOff
     *
     * @see Lien#surbrillanceOn()
     * @see Lien#getSurbrillance()
     */
    @Test
    void testSurbrillanceOn() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        l.surbrillanceOn();
        assertEquals(true, l.getSurbrillance(), "Surbrillance a true");
    }
}
