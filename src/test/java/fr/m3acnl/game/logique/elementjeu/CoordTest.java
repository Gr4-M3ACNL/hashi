package fr.m3acnl.game.logique.elementjeu;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;
import fr.m3acnl.game.logique.elementjeu.Coord;

/**
 * Test pour la classe Coord
 * 
 * @see Coord
 * @see Tests
 * 
 * @author COGNARD Luka
 */
public class CoordTest extends Tests {

    /**
     * Constructeur de la classe de test
     */
    public CoordTest() {
    }

    /**
     * Méthode d'initialisation de la classe test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll(){
        printNameAtStart(CoordTest.class);
    }

    /**
     * Méthode de fin de la classe test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll(){
        printNameAtEnd(CoordTest.class);
    }

    /**
     * Test de la méthode CompareTo de Coord
     * @see Coord#compareTo
     */
    @Test
    void testCompareTo() {
        Coord c1 = new Coord(5,2);
        Coord c2 = new Coord(5, 2);
        
        assertEquals(0, c1.compareTo(c2), "La comparaison est valide donc 0");
        c2 = new Coord(5, 3);
        assertEquals(-1, c1.compareTo(c2),"La comparaison est invalide inférieur donc 1");
        c2 = new Coord(5, 1);
        assertEquals(1, c1.compareTo(c2), "La comparaison est invalide supérieur donc -1");
    }

    /**
     * Test de la méthode getCoordX de la classe Coord
     * @see Coord#getCoordX
     */
    @Test
    void testGetCoordX() {
        Coord c1 = new Coord(5,2);
        assertEquals(5, c1.getCoordX(),"Récupère la coordonnée en X étant 5");
    }

    /**
     * Test de la méthode getCoordY de la classe Coord
     * @see Coord#getCoordY
     */
    @Test
    void testGetCoordY() {
        Coord c1 = new Coord(5,2);
        assertEquals(2, c1.getCoordY(),"Récupère la coordonnée en Y étant 5");
    }
}
