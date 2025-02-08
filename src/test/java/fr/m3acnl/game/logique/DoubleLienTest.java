package fr.m3acnl.game.logique;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
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
public class DoubleLienTest extends Tests{

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll(){
        printNameAtStart(DoubleLienTest.class);
    }

    /**
     * Méthode de de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll(){
        printNameAtEnd(DoubleLienTest.class);
    }

    /**
     * Test de la méthode activeInterrupteur
     * @see DoubleLien#activeInterrupteur()
     * @see DoubleLien#getInterupteur()
     */
    @Test
    void testActiveInterrupteur() {
        int mat[][] = {{-4, 2, -4, 2, -2, 0, 0},
                        {2, -3, 1, -3, 2, 2, -3},
                        {-3, 2, 0, 0, 0, 0, 1},
                        {1, -6, 2, -4, 2, -3, 1},
                        {0, 2, 0, 0, 0, 1, -1},
                        {1, -4, 2, 2, -2, 1, 0},
                        {-2, 1, 1, -2, 1, -2, 0}};
        DoubleLien dl=new DoubleLien(new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1,new Jeu(5,mat),1),
                      new Lien(new Noeud(4, 3, 1), new Noeud(6, 3, 1), 1,new Jeu(5,mat),1));
        dl.activeInterrupteur();
        assertEquals(true, dl.getInterupteur(),"Interrupteur activer a true");
        
    }

    /**
     * Test de la méthode activer
     * @see DoubleLien#activer(Noeud)
     * @see Noeud#estValide()
     */
    @Test
    void testactiver() {
        int mat[][] = {{-4, 2, -4, 2, -2, 0, 0},
                        {2, -3, 1, -3, 2, 2, -3},
                        {-3, 2, 0, 0, 0, 0, 1},
                        {1, -6, 2, -4, 2, -3, 1},
                        {0, 2, 0, 0, 0, 1, -1},
                        {1, -4, 2, 2, -2, 1, 0},
                        {-2, 1, 1, -2, 1, -2, 0}};
        Noeud n1 = new Noeud(5, 2, 1);
        Noeud n2 = new Noeud(5, 4, 1);
        Noeud n3 = new Noeud(4, 3, 1);
        Noeud n4 = new Noeud(6, 3, 1);
        DoubleLien dl=new DoubleLien(new Lien(n1,n2, 1,new Jeu(5,mat),1),new Lien(n3,n4, 1,new Jeu(5,mat),1));
        dl.activer(n1);
        dl.activer(n2);
        assertEquals(-1, n1.estValide(),"Lien1 activer 2 fois le noeud lié a -1 deg soluce 1");
        dl.activer(n3);
        assertEquals(1, n4.estValide(),"Aucune activationdu lien2, Lien1 déja actif renvoie 1");
        dl.activer(n2);
        dl.activer(n3);
        dl.activer(n4);
        assertEquals(-1, n4.estValide(),"Lien2 activer 2 fois le noeud lié a -1 deg soluce 1");

    }

    /**
     * Test de la méthode desactiveInterrupteur
     * @see DoubleLien#activeInterrupteur()
     * @see DoubleLien#desactiveInterrupteur()
     * @see DoubleLien#getInterupteur()
     */
    @Test
    void testDesactiveInterrupteur() {
        int mat[][] = {{-4, 2, -4, 2, -2, 0, 0},
                        {2, -3, 1, -3, 2, 2, -3},
                        {-3, 2, 0, 0, 0, 0, 1},
                        {1, -6, 2, -4, 2, -3, 1},
                        {0, 2, 0, 0, 0, 1, -1},
                        {1, -4, 2, 2, -2, 1, 0},
                        {-2, 1, 1, -2, 1, -2, 0}};
        DoubleLien dl=new DoubleLien(new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1,new Jeu(5,mat),1),
                      new Lien(new Noeud(4, 3, 1), new Noeud(6, 3, 1), 1,new Jeu(5,mat),1));
        dl.activeInterrupteur();
        dl.desactiveInterrupteur();
        assertEquals(false, dl.getInterupteur(),"Interrupteur désactiver a false");
    }

    /**
     * Test de la méthode getInterrupteur()
     * @see DoubleLien#getInterupteur()
     */
    @Test
    void testGetInterupteur() {
        int mat[][] = {{-4, 2, -4, 2, -2, 0, 0},
                        {2, -3, 1, -3, 2, 2, -3},
                        {-3, 2, 0, 0, 0, 0, 1},
                        {1, -6, 2, -4, 2, -3, 1},
                        {0, 2, 0, 0, 0, 1, -1},
                        {1, -4, 2, 2, -2, 1, 0},
                        {-2, 1, 1, -2, 1, -2, 0}};
        DoubleLien dl=new DoubleLien(new Lien(new Noeud(5, 2, 1), new Noeud(5, 4, 1), 1,new Jeu(5,mat),1),
                      new Lien(new Noeud(4, 3, 1), new Noeud(6, 3, 1), 1,new Jeu(5,mat),1));
        assertEquals(false, dl.getInterupteur(),"Interrupteur de base desactiver a false");
    }
}
