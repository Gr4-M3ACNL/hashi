package fr.m3acnl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;


/**
 * Test pour la classe HashiParmentier
 * 
 * @see HashiParmentier
 * @see Tests
 * 
 * @author PUREN Mewen
 */
public class HashiParmentierTest extends Tests {
    
    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {
        printNameAtStart(HashiParmentierTest.class);
    }

    /**
     * Méthode de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {
        printNameAtEnd(HashiParmentierTest.class);
    }

    /**
     * Constructeur de la classe de test HashiParmentierTest
     */
    HashiParmentierTest(){}

    /**
     * Test de la méthode init
     * @see HashiParmentier#init
     */
    @Disabled
    @Test
    public void init_Fonctionne() {
        HashiParmentier hashi = new HashiParmentier();
        try {
            hashi.init();
        } catch (Exception e) {
            fail("L'initialisation de l'application a échoué.");
        }
        assertTrue(true);
    }

}