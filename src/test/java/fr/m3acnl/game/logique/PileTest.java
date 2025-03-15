package fr.m3acnl.game.logique;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;
import fr.m3acnl.game.logique.elementjeu.Lien;
import fr.m3acnl.game.logique.elementjeu.Noeud;

/**
 * Test pour la classe DoubleLien
 *
 * @see Pile
 * @see Tests
 *
 * @author MABIRE Aymeric
 */
public class PileTest extends Tests {

    /**
     * Constructeur de la classe de test
     */
    public PileTest() {
    }

    /**
     * Méthode d'initialisation de la classe de test
     *
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {
        printNameAtStart(PileTest.class);
    }

    /**
     * Méthode de de fin de la classe de test
     *
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {
        printNameAtEnd(PileTest.class);
    }

    /**
     * Test de la méthode depiler
     *
     * @see Pile#empiler(Lien)
     * @see Pile#depiler()
     */
    @Test
    void testDepiler() {
        Pile p = new Pile();
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l1 = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        Lien l2 = new Lien(new Noeud(4, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        Lien l3 = new Lien(new Noeud(6, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        assertEquals(null, p.depiler(), "Pile vide non dépilable renvoie null");
        p.empiler(l1);
        p.empiler(l2);
        p.empiler(l3);
        assertEquals(l3, p.depiler(), "Dépile l3");
    }

    /**
     * Test de la méthode empiler
     *
     * @see Pile#empiler(Lien)
     */
    @Test
    void testEmpiler() {
        Pile p = new Pile();
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l1 = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        p.empiler(l1);
        assertEquals(l1, p.depiler(), "Dépile l1");
    }

    /**
     * Test de la méthode estVide
     *
     * @see Pile#empiler(Lien)
     * @see Pile#estVide()
     */
    @Test
    void testEstVide() {
        Pile p = new Pile();
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l1 = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        assertEquals(true, p.estVide(), "Pile vide renvoie true");
        p.empiler(l1);
        assertEquals(false, p.estVide(), "Pile non vide renvoie false");
    }

    /**
     * Test de la méthode sommet
     *
     * @see Pile#empiler(Lien)
     * @see Pile#sommet()
     */
    @Test
    void testSommet() {
        Pile p = new Pile();
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l1 = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        Lien l2 = new Lien(new Noeud(5, 5, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        assertEquals(null, p.sommet(), "Pile vide renvoie null");
        p.empiler(l1);
        p.empiler(l2);
        assertEquals(l2, p.sommet(), "Pile non vide sommet est 2");
    }

    /**
     * Test de la méthode taille
     *
     * @see Pile#taille()
     */
    @Test
    void testTaille() {
        Pile p = new Pile();
        assertEquals(0, p.taille(), "Pile vide taille a 0");
    }

    /**
     * Test de la méthode toString
     *
     * @see Pile#toString()
     */
    @Test
    void testTo_s() {
        Pile p = new Pile();
        assertEquals("", p.toString(), "Pile vide renvoie uen chaine vide");
    }

    /**
     * Test de la méthode vidange
     *
     * @see Pile#empiler(Lien)
     * @see Pile#vidange()
     */
    @Test
    void testVidange() {

        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Lien l1 = new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);
        Lien l2 = new Lien(new Noeud(4, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1);

        Pile p = new Pile();
        p.empiler(l1);
        p.empiler(l2);
        p.empiler(l1);
        p.empiler(l2);
        p.empiler(l1);
        p.empiler(l2);
        p.vidange();
        assertEquals(0, p.taille(), "Pile vider taille 0");
    }
}
