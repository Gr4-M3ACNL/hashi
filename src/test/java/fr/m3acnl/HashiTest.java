package fr.m3acnl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Test pour la classe Hashi
 * 
 * @see Hashi
 * @see Tests
 * 
 * @author PUREN Mewen
 */
public class HashiTest extends Tests{

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {printNameAtStart(HashiTest.class);}

    /**
     * Méthode de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {printNameAtEnd(HashiTest.class);}
    
    /**
     * Test de la méthode main
     * @see Hashi#main
     */
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }

    /**
     * Constructeur de la classe.
     */
    public HashiTest() {}
}
