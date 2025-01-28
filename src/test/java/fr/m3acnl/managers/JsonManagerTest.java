package fr.m3acnl.managers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;

/**
 * Classe de test de la classe JsonManager.
 * 
 * @see JsonManager
 * @see Tests
 */
public class JsonManagerTest extends Tests {

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {printNameAtStart(JsonManagerTest.class);}

    /**
     * Méthode de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {printNameAtEnd(JsonManagerTest.class);}

    /**
     * Test de la méthode getGrilleInfo de la classe JsonManager.
     */
    @Test
    public void testGetGrilleInfo() {
        JsonManager manager = new JsonManager();
        JsonManager.GrilleInfo grilleInfo = manager.getGrilleInfo("facile", 0);
        assertNotNull(grilleInfo);
        assertEquals(grilleInfo.taille(),6, "La taille de la grille devrait être de 6");
        assertEquals(grilleInfo.serialise(),"1,2,3,4,5,6", "La grille devrait être 1,2,3,4,5,6");
    }
    
}