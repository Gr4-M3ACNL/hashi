package fr.m3acnl.game.logique;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;

/**
 * Test pour la classe Noeud
 * 
 * @see Noeud
 * @see Tests
 * 
 * @author COGNARD Luka
 */
public class NoeudTest extends Tests{

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll(){
        printNameAtStart(NoeudTest.class);
    }

    /**
     * Méthode de de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll(){
        printNameAtEnd(NoeudTest.class);
    }

    /**
     * Test de la méthode ajouterDegre
     * @see Noeud#ajouterDegre
     * @see Noeud#estValide
     */
    @Test
    void testAjouterDegre() {
        Noeud n= new Noeud(5, 2, 4);
        n.ajouterDegre();
        n.ajouterDegre();
        n.ajouterDegre();
        n.ajouterDegre();
        assertEquals(0, n.estValide(),"Valide car degré=degréSoluce via ajouterDegre");
    }

    /**
     * Test de la méthode compareTo
     * @see Noeud#compareTo
     */
    @Test
    void testCompareTo() {
        Noeud n1= new Noeud(5, 2, 4);
        Noeud n2= new Noeud(6, 2, 4);
        assertEquals(-1, n1.compareTo(n2), "Comparaison a -1 car coordonnée supérieur");
        n2=new Noeud(5, 2, 4);
        assertEquals(0, n1.compareTo(n2), "Comparaison a 0 car même noeud");
        n2=new Noeud(5, 1, 4);
        assertEquals(1, n1.compareTo(n2), "Comparaison a 1 car coordonnée inférieur");
    }

    /**
     * Test pour la méthode enleverDegre
     * @see Noeud#ajouterDegre
     * @see Noeud#enleverDegre
     * @see Noeud#estValide
     */
    @Test
    void testsuppressionDegre() {
        Noeud n= new Noeud(5, 2, 4);
        for(int i=0; i<6;i++){
            n.ajouterDegre();
        }
        n.suppressionDegre();
        assertEquals(0, n.estValide(),"Valide car degré=degréSoluce via enleverdegré");
    }

    /**
     * Test pour la méthode estvalide
     * @see Noeud#ajouterDegre
     * @see Noeud#suppressionDegre()
     * @see Noeud#estValide
     */
    @Test
    void testEstValide() {
        Noeud n= new Noeud(5, 2, 4);
        for(int i=0; i<6;i++){
            n.ajouterDegre();
        }
        assertEquals(-2, n.estValide(),"invalide -2 car degré supérieur de 2 au degré soluce");
        n.suppressionDegre();
        assertEquals(0, n.estValide(),"valide 0 car degré égale au degré soluce");
        n.suppressionDegre();
        assertEquals(2, n.estValide(),"invalide 2 car degré inférieur de 2 au degré soluce");
    }

    /**
     * test de la méthode getDegreSoluce
     * @see Noeud#getDegreSoluce 
     */
    @Test
    void testGetDegreSoluce() {
        Noeud n=new Noeud(5, 2, 4);
        assertEquals(4, n.getDegreSoluce(), "Le degré soluce est de 4 a la création");
    }

    /**
     * test de la méthode getPosition
     * @see Noeud#getPosition()
     */
    @Test
    void testGetPosition() {
        Noeud n=new Noeud(5, 2, 4);
        Coord c= new Coord(5, 2);
        assertEquals(0, n.getPosition().compareTo(c), "Position valide donc 0 en le comparant a une autre position égale ");
    }

    /**
     * Test de la méthode getSurbrillance
     * @see Noeud#getSurbrillance()
     */
    @Test
    void testGetSurbrillance() {
        Noeud n=new Noeud(5, 2, 4);
        assertEquals(false, n.getSurbrillance(), "Surbeillance désactivé de base donc false");
    }

    /**
     * Test de la méthode surbrillanceOff
     * @see Noeud#surbrillanceOn
     * @see Noeud#surbrillanceOff
     * @see Noeud#getSurbrillance()
     */
    @Test
    void testSurbrillanceOff() {
        Noeud n=new Noeud(5, 2, 4);
        n.surbrillanceOn();
        n.surbrillanceOff();
        assertEquals(false, n.getSurbrillance(), "Surbeillance à été désactivé donc false");
    }

    /**
     * Test de la méthode surbrillanceOn
     * @see Noeud#surbrillanceOn
     * @see Noeud#getSurbrillance
     */
    @Test
    void testSurbrillanceOn() {
        Noeud n=new Noeud(5, 2, 4);
        n.surbrillanceOn();
        assertEquals(true, n.getSurbrillance(), "Surbeillance à été activé donc true");
    }
}
