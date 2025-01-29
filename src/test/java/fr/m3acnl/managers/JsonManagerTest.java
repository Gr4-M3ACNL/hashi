package fr.m3acnl.managers;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

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
     * Constructeur de la classe de test JsonManagerTest.
     */
    JsonManagerTest() {}

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
     * @see JsonManager#getListeDifficultes
     */
    @Test
    public void testGetDifficultes() {
        JsonManager manager = new JsonManager();
        ArrayList<String> difficultes = manager.getListeDifficultes();
        assertNotNull(difficultes, "Les difficultés ne devraient pas être nulles");
        assertEquals(3, difficultes.size(), "Il devrait y avoir 3 difficultés");
        assertEquals(difficultes.get(0), "facile", "La première difficulté devrait être facile");
        assertEquals(difficultes.get(1), "moyen", "La deuxième difficulté devrait être moyen");
        assertEquals(difficultes.get(2), "difficile", "La troisième difficulté devrait être difficile");
    }

    /**
     * Test de la méthode getDifficultes de la classe JsonManager.
     * @see JsonManager#getListeProfils
     */
    @Test
    public void testGetListeProfils(){
        try {
            Files.deleteIfExists(switch (OsManager.getInstance().getOsType()) {
                case WINDOWS -> Path.of(System.getenv("APPDATA"), "HashiParmentier", "profils.json");
                case MAC -> Path.of(System.getProperty("user.home"), "Library", "Application Support", "HashiParmentier", "profils.json");
                default -> Path.of(System.getProperty("user.home"), ".game", "HashiParmentier", "profils.json");
            });
        } catch (Exception e) {
            e.printStackTrace();
        }

        JsonManager manager = new JsonManager();
        assertThrows(RuntimeException.class, () -> manager.getListeProfils(), "Le fichier de profils n'existe pas");
        try{
            //copie le fichier dans le repertoire ressources au bon endroit
            Files.copy(this.getClass().getResourceAsStream("/fr/m3acnl/managers/profils.json"), switch (OsManager.getInstance().getOsType()) {
                case WINDOWS -> Path.of(System.getenv("APPDATA"), "HashiParmentier", "profils.json");
                case MAC -> Path.of(System.getProperty("user.home"), "Library", "Application Support", "HashiParmentier", "profils.json");
                default -> Path.of(System.getProperty("user.home"), ".game", "HashiParmentier", "profils.json");
            });
        }catch(Exception e){
            e.printStackTrace();
            fail("Impossible de copier le fichier de profils");  
        }
        assertNotNull(manager.getListeProfils(), "La liste des profils ne devrait pas être nulle");
    }

    /**
     * Test de la méthode getCheminProfils de la classe JsonManager.
     * @see JsonManager#getCheminProfils
     */
    @Test
    public void testGetCheminProfils(){
        JsonManager manager = new JsonManager();
        assertNotNull(manager.getCheminProfils(), "Le chemin des profils ne devrait pas être nul");
    }
    
}