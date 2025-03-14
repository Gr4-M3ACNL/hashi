package fr.m3acnl.game.logique;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;


import fr.m3acnl.Tests;
import fr.m3acnl.game.logique.elementjeu.Lien;
import fr.m3acnl.game.logique.elementjeu.Noeud;

/**
 * Classe test de la classe Jeu.
 *
 * @see Jeu
 * @see Matrice
 * @see Lien
 * @see Thread
 *
 * @author COGNARD Luka
 */
public class JeuTest extends Tests{

    /**
     * Méthode d'initialisation de la classe test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll(){
        printNameAtStart(JeuTest.class);
    }

    /**
     * Méthode de fin de la classe test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll(){
        printNameAtEnd(JeuTest.class);
    }

    /**
     * Constructeur de la classe de test de la classe Jeu.
     */
    public JeuTest() {
    }

    /**
     * Test de la méthode activeElemJeu.
     *
     * @see Jeu#activeElemJeu(int, int, Noeud)
     * @see Jeu#getPlateau()
     * @see Matrice#getElement(int, int)
     * @see Lien#estValide()
     */
    @Test
    void testActiveElem() {
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
        jeu.activeElemJeu(0, 1, null);
        jeu.activeElemJeu(0, 1, null);
        assertEquals(true, ((Lien) jeu.getPlateau().getElement(0, 1)).estValide(), "Lien 0,1 activer 2 fois donc valide");
    }

    /**
     * Test de la méthode avancer.
     *
     * @see Jeu#avancer()
     * @see Jeu#retour()
     * @see Jeu#activeElemJeu(int, int, Noeud)
     * @see Jeu#getPlateau()
     * @see Matrice#getElement(int, int)
     * @see Lien#estValide()
     */
    @Test
    void testAvancer() {
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
        jeu.activeElemJeu(0, 1, null);
        jeu.activeElemJeu(0, 1, null);
        jeu.retour();
        jeu.retour();
        jeu.avancer();
        jeu.activeElemJeu(1, 0, null);
        jeu.avancer();
        assertEquals(false, ((Lien) jeu.getPlateau().getElement(0, 1)).estValide(), "Lien 0,1 activer 2 fois, retour en arrière 2 fois puis avancer 1 fois donc non valide");
    }

    /**
     * Test de la méthode gagner.
     *
     * @see Jeu#gagner()
     * @see Jeu#activeElemJeu(int, int, Noeud)
     * @see Jeu#getPlateau()
     * @see Matrice#getElement(int, int)
     */
    @Test
    void testGagner() {
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
        assertEquals(false, jeu.gagner(), "Le jeu n'est pas gagner");
        jeu.activeElemJeu(0, 1, null);

        jeu.activeElemJeu(0, 0, null);
        jeu.activeElemJeu(0, 1, null);

        jeu.activeElemJeu(1, 0, null);
        jeu.activeElemJeu(1, 0, null);

        jeu.activeElemJeu(0, 0, null);
        jeu.activeElemJeu(0, 1, null);

        //Lien 0,1 état 2
        jeu.activeElemJeu(0, 1, null);
        jeu.activeElemJeu(0, 1, null);
        //Lien 0,3 état 2
        jeu.activeElemJeu(0, 3, null);
        jeu.activeElemJeu(0, 3, null);
        //Lien 1,2 état 1
        jeu.activeElemJeu(1, 2, null);
        //Lien 1,5 état 2
        jeu.activeElemJeu(1, 5, null);
        jeu.activeElemJeu(1, 5, null);
        //Lien 2,1 état 2
        jeu.activeElemJeu(2, 1, null);
        jeu.activeElemJeu(2, 1, null);
        //Lien 2,6 état 1
        jeu.activeElemJeu(2, 6, null);
        //Lien 3,0 état 1
        jeu.activeElemJeu(3, 0, null);
        //Lien 3,2 état 2
        jeu.activeElemJeu(3, 2, null);
        jeu.activeElemJeu(3, 2, null);
        //Lien 3,4 état 2
        jeu.activeElemJeu(3, 4, (Noeud) jeu.getPlateau().getElement(3, 5));
        jeu.activeElemJeu(3, 4, (Noeud) jeu.getPlateau().getElement(3, 5));
        //Lien 4,1 état 2
        jeu.activeElemJeu(4, 1, null);
        jeu.activeElemJeu(4, 1, null);
        //Lien 4,5 état 1
        jeu.activeElemJeu(4, 5, null);
        //Lien 5,2 état 2
        jeu.activeElemJeu(5, 2, null);
        jeu.activeElemJeu(5, 2, null);
        //Lien 6,1 état 1
        jeu.activeElemJeu(6, 1, null);
        //Lien 6,4 état 1
        jeu.activeElemJeu(6, 4, null);

        assertEquals(true, jeu.gagner(), "Le jeu est gagner");
    }

    /**
     * Test de la méthode getTempsFinal
     *
     * @see Jeu#getTempsFinal()
     */
    @Test
    void testGetTempsFinal() {
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
        assertEquals(0, jeu.getTempsFinal(), "Jeu en cours sans pause non fini temps final a 0");
    }

    /**
     * Test de la méthode reprendreChrono.
     *
     * @see Jeu#reprendreChrono()
     * @see Jeu#stopChrono()
     * @see Jeu#getTempsFinal()
     * @see Thread#sleep(long)
     */
    @Test
    void testReprendreChrono() {
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        jeu.stopChrono();
        jeu.reprendreChrono();
        long temp = jeu.getTempsFinal();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        jeu.stopChrono();
        jeu.reprendreChrono();
        assertNotEquals(temp, jeu.getTempsFinal(), "Jeu avec 2 pause temps final non égale a son temp avant la première pause");
    }

    /**
     * Test de la méthode Retour.
     *
     * @see Jeu#retour()
     * @see Jeu#activeElemJeu(int, int, Noeud)
     * @see Jeu#getPlateau()
     * @see Matrice#getElement(int, int)
     * @see Lien#estValide()
     */
    @Test
    void testRetour() {
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
        jeu.activeElemJeu(0, 1, null);
        jeu.activeElemJeu(0, 1, null);
        jeu.retour();
        jeu.retour();
        jeu.retour();
        assertEquals(false, ((Lien) jeu.getPlateau().getElement(0, 1)).estValide(), "Activation 2 fois d'un lien et retour 3 fois en arrière lien non valide");
    }

    /**
     * Test de la méthode stopChrono.
     *
     * @see Jeu#reprendreChrono()
     * @see Jeu#stopChrono()
     * @see Jeu#getTempsFinal()
     * @see Thread#sleep(long)
     */
    @Test
    void testStopChrono() {
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
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
        }
        jeu.stopChrono();
        jeu.reprendreChrono();
        assertNotEquals(0, jeu.getTempsFinal(), "Jeu avec une pause temps final non a 0");
    }

    /**
     * Test de la méthode verificationHorizontal.
     *
     * @see Jeu#verificationHorizontal(Noeud, Noeud, int)
     * @see Jeu#activeElemJeu(int, int, Noeud)
     * @see Jeu#getPlateau()
     * @see Matrice#getElement(int, int)
     */
    @Test
    void testVerificationHorizontal() {
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
        jeu.activeElemJeu(4, 3, null);
        assertEquals(1, jeu.verificationHorizontal((Noeud) jeu.getPlateau().getElement(5, 1), (Noeud) jeu.getPlateau().getElement(5, 4), 1), "Lien horizontal non activable renvoie 1");
    }

    /**
     * Test de la méthode verificationVertical.
     *
     * @see Jeu#verificationVertical(Noeud, Noeud, int)
     * @see Jeu#activeElemJeu(int, int, Noeud)
     * @see Jeu#getPlateau()
     * @see Matrice#getElement(int, int)
     */
    @Test
    void testVerificationVertical() {
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
        jeu.activeElemJeu(5, 2, null);
        assertEquals(1, jeu.verificationVertical((Noeud) jeu.getPlateau().getElement(3, 3), (Noeud) jeu.getPlateau().getElement(6, 3), 1), "Lien verticale non activable renvoie 1");
    }
}
