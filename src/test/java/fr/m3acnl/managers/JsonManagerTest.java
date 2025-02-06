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
     * @see JsonManager#getGrilleInfo
     */
    @Test
    public void testGetGrilleInfo() {
        JsonManager manager = new JsonManager();
        JsonManager.GrilleInfo grilleInfo = manager.getGrilleInfo("facile", 0);
        assertNotNull(grilleInfo);
        assertEquals(6, grilleInfo.taille(), "La taille de la grille devrait être de 6");
        assertEquals("1,2,3,4,5,6", grilleInfo.serialise(), "La grille devrait être 1,2,3,4,5,6");
    }

    /**
     * Test de la méthode getGrilleInfo de la classe JsonManager.
     * @see JsonManager#getGrilleInfo
     */
    @Test
    public void testGetGrilleInfoGrilleInexistante() {
        JsonManager manager = new JsonManager();
        assertThrows(IllegalArgumentException.class, () -> manager.getGrilleInfo("Inexistante", 100), "La grille n'existe pas");
    }

    /**
     * Test de la méthode getNbGrilles de la classe JsonManager.
     * @see JsonManager#getNbGrilles
     */
    @Test
    public void testGetNbGrilles() {
        JsonManager manager = new JsonManager();
        int nbGrilles = manager.getNbGrilles("facile");
        assertEquals(2, nbGrilles, "Le nombre de grilles pour la difficulté facile devrait être de 2");
    }

    /**
     * Test de la méthode getNbGrilles de la classe JsonManager.
     * @see JsonManager#getNbGrilles
     */
    @Test
    public void testGetNbGrillesDifficulteInexistante() {
        JsonManager manager = new JsonManager();
        assertThrows(IllegalArgumentException.class, () -> manager.getNbGrilles("Inexistante"), "La difficulté n'existe pas");
    }

    /**
     * Test de la méthode getDifficultes de la classe JsonManager.
     * @see JsonManager#getDifficultes
     */
    @Test
    public void testGetDifficultes() {
        JsonManager manager = new JsonManager();
        String[] difficultes = manager.getDifficultes();
        assertNotNull(difficultes, "Les difficultés ne devraient pas être nulles");
        assertEquals(3, difficultes.length, "Il devrait y avoir 3 difficultés");
        assertEquals(difficultes[0], "facile", "La première difficulté devrait être facile");
        assertEquals(difficultes[1], "moyen", "La deuxième difficulté devrait être moyen");
        assertEquals(difficultes[2], "difficile", "La troisième difficulté devrait être difficile");
    }
    
}