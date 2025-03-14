package fr.m3acnl.game.logique.elementjeu;

import fr.m3acnl.game.logique.Jeu;
import fr.m3acnl.game.logique.elementjeu.DoubleLien;
import fr.m3acnl.game.logique.elementjeu.Lien;
import fr.m3acnl.game.logique.elementjeu.Noeud;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;

/**
 * Test pour la classe DoubleLien
 *
 * @see DoubleLien
 * @see Tests
 * @see Noeud
 *
 * @author COGNARD Luka
 */
public class DoubleLienTest extends Tests {

    /**
     * Constructeur de la classe de test
     */
    public DoubleLienTest() {
    }

    /**
     * Méthode d'initialisation de la classe de test
     *
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {
        printNameAtStart(DoubleLienTest.class);
    }

    /**
     * Méthode de de fin de la classe de test
     *
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {
        printNameAtEnd(DoubleLienTest.class);
    }

    /**
     * Test de la méthode activeInterrupteur
     *
     * @see DoubleLien#activeInterrupteur()
     * @see DoubleLien#getInterrupteur()
     */
    @Test
    void testActiveInterrupteur() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        DoubleLien dl = new DoubleLien(new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1),
                new Lien(new Noeud(4, 3, 1), new Noeud(6, 3, 1), 1, new Jeu(7, mat), 1));
        dl.activeInterrupteur();
        assertEquals(true, dl.getInterrupteur(), "Interrupteur activer a true");

    }

    /**
     * Test de la méthode activer
     *
     * @see DoubleLien#activer(Noeud)
     * @see Noeud#estValide()
     * @see Jeu#activeElemJeu(int, int, Noeud)
     * @see Jeu#getPlateau()
     * @see Matrice#getElement(int, int)
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
        Jeu jeu = new Jeu(7, mat);

        jeu.activeElemJeu(5, 3, (Noeud) jeu.getPlateau().getElement(5, 4));
        jeu.activeElemJeu(5, 3, (Noeud) jeu.getPlateau().getElement(5, 4));

        assertEquals(0, ((Noeud) jeu.getPlateau().getElement(5, 4)).estValide(), "Lien1 activer 2 fois le noeud lié a -1 deg soluce 1");
        assertEquals(true, ((DoubleLien) jeu.getPlateau().getElement(5, 3)).getInterrupteur(), "interrupteur actif");

        jeu.activeElemJeu(5, 3, (Noeud) jeu.getPlateau().getElement(6, 3));

        assertEquals(2, ((Noeud) jeu.getPlateau().getElement(6, 3)).estValide(), "Aucune activation du lien2, Lien1 déja actif renvoie 1");
        assertEquals(true, ((DoubleLien) jeu.getPlateau().getElement(5, 3)).getInterrupteur(), "interrupteur actif");

        jeu.activeElemJeu(5, 3, (Noeud) jeu.getPlateau().getElement(5, 4));

        assertEquals(false, ((DoubleLien) jeu.getPlateau().getElement(5, 3)).getInterrupteur(), "interrupteur inactif");

        jeu.activeElemJeu(5, 3, (Noeud) jeu.getPlateau().getElement(6, 3));
        jeu.activeElemJeu(5, 3, (Noeud) jeu.getPlateau().getElement(6, 3));

        assertEquals(0, ((Noeud) jeu.getPlateau().getElement(6, 3)).estValide(), "Aucune activation du lien2, Lien1 déja actif renvoie 1");

    }

    /**
     * Test de la méthode desactiveInterrupteur
     *
     * @see DoubleLien#activeInterrupteur()
     * @see DoubleLien#desactiveInterrupteur()
     * @see DoubleLien#getInterrupteur()
     */
    @Test
    void testDesactiveInterrupteur() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        DoubleLien dl = new DoubleLien(new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1),
                new Lien(new Noeud(4, 3, 1), new Noeud(6, 3, 1), 1, new Jeu(7, mat), 1));
        dl.activeInterrupteur();
        dl.desactiveInterrupteur();
        assertEquals(false, dl.getInterrupteur(), "Interrupteur désactiver a false");
    }

    /**
     * Test de la méthode getInterrupteur()
     *
     * @see DoubleLien#getInterrupteur()
     */
    @Test
    void testgetInterrupteur() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        DoubleLien dl = new DoubleLien(new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1, new Jeu(7, mat), 1),
                new Lien(new Noeud(4, 3, 1), new Noeud(6, 3, 1), 1, new Jeu(7, mat), 1));
        assertEquals(false, dl.getInterrupteur(), "Interrupteur de base desactiver a false");
    }
}
