package fr.m3acnl.game.logique;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;

/**
 * Test pour la classe DoubleLien
 * 
 * @see Pile
 * @see Tests
 * 
 * @author MABIRE Aymeric
 */
public class PileTest extends Tests{

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll(){
        printNameAtStart(PileTest.class);
    }

    /**
     * Méthode de de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll(){
        printNameAtEnd(PileTest.class);
    }

    /**
     * Test de la méthode depiler
     * @see Pile#empiler(Object)
     * @see Pile#depiler()
     */
    @Test
    void testDepiler() {
        Pile p = new Pile();
        assertEquals(null, p.depiler(),"Pile vide non dépilable renvoie null");
        p.empiler(1);
        p.empiler(2);
        p.empiler(3);
        assertEquals(3, p.depiler(),"Dépile 3");
    }

    /**
     * Test de la méthode empiler
     * @see Pile#empiler(Object)
     */
    @Test
    void testEmpiler() {
        Pile p = new Pile();
        p.empiler(1);
        assertEquals(1, p.depiler(),"Dépile 1");
    }

    /**
     * Test de la méthode estVide
     * @see Pile#empiler(Object)
     * @see Pile#estVide()
     */
    @Test
    void testEstVide() {
        Pile p = new Pile();
        assertEquals(true, p.estVide(),"Pile vide renvoie true");
        p.empiler(1);
        assertEquals(false, p.estVide(),"Pile non vide renvoie false");
    }

    /**
     * Test de la méthode sommet
     * @see Pile#empiler(Object)
     * @see Pile#sommet()
     */
    @Test
    void testSommet() {
        Pile p = new Pile();
        assertEquals(null, p.sommet(),"Pile vide renvoie null");
        p.empiler(1);
        p.empiler(2);
        assertEquals(2, p.sommet(),"Pile non vide sommet est 2");
    }

    /**
     * Test de la méthode taille
     * @see Pile#taille()
     */
    @Test
    void testTaille() {
        Pile p = new Pile();
        assertEquals(0, p.taille(),"Pile vide taille a 0");
    }

    /**
     * Test de la méthode to_s
     * @see Pile#to_s()
     */
    @Test
    void testTo_s() {
        Pile p = new Pile();
        assertEquals("", p.to_s(),"Pile vide renvoie uen chaine vide");
    }

    /**
     * Test de la méthode vidange
     * @see Pile#empiler(Object)
     * @see Pile#vidange()
     */
    @Test
    void testVidange() {
        Pile p = new Pile();
        p.empiler(1);
        p.empiler(2);
        p.empiler(1);
        p.empiler(2);
        p.empiler(1);
        p.empiler(2);
        p.vidange();
        assertEquals(0, p.taille(),"Pile vider taille 0");
    }
}
