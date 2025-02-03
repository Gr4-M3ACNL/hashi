package fr.m3acnl.managers;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import fr.m3acnl.Tests;
import fr.m3acnl.profile.Profile;

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
        List<String> difficultes = manager.getListeDifficultes();
        assertNotNull(difficultes, "Les difficultés ne devraient pas être nulles");
        assertEquals(3, difficultes.size(), "Il devrait y avoir 3 difficultés");
        assertEquals(difficultes.get(0), "facile", "La première difficulté devrait être facile");
        assertEquals(difficultes.get(1), "moyen", "La deuxième difficulté devrait être moyen");
        assertEquals(difficultes.get(2), "difficile", "La troisième difficulté devrait être difficile");
    }

    /**
     * Méthode d'initialisation de test des profils.
     * 
     * Copie le fichier de profils deja existant da un autre endroit.
     * puis supprime le fichier originale de profils dans le repertoire de sauvegarde.
     */
    private void initProfils(){
        if (Files.exists(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"))) {
            try {
                Files.copy(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"), SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json.bak"));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try{
            Files.deleteIfExists(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Méthode de restauration des profils.
     * 
     * Supprime le fichier de profils actuel et le remplace par le fichier de profils sauvegardé.
     */
    private void restoreProfils(){
        try{
            Files.deleteIfExists(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"));
            Files.move(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json.bak"), SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"));
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Test de la méthode getDifficultes de la classe JsonManager.
     * @see JsonManager#getListeProfils
     */
    @Test
    public void testGetListeProfils(){
        //initialisation des profils
        initProfils();
        JsonManager manager = new JsonManager();
        
        // vérifie que le fichier de profils n'existe pas
        assertThrows(RuntimeException.class, () -> manager.getListeProfils(), "Le fichier de profils n'existe pas");
        
        try{
            //copie le fichier de test dans le repertoire ressources au bon endroit
            Files.copy(this.getClass().getResourceAsStream("/fr/m3acnl/managers/profils.json"), SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"));
        }catch(Exception e){
            e.printStackTrace();
            fail("Impossible de copier le fichier de profils");  
        }
        // vérifie que le fichier de profils existe
        assertNotNull(manager.getListeProfils(), "La liste des profils ne devrait pas être nulle");
        List<String> profils = manager.getListeProfils();
        assertEquals(2, profils.size(), "Il devrait y avoir 2 profils");
        assertEquals("jacoboni", profils.get(0), "Le premier profil devrait être jacoboni");
        assertEquals("despres", profils.get(1), "Le deuxième profil devrait être despres");

        //restauration des profils
        restoreProfils();
    }

    /**
     * Test de la méthode sauvegarderProfil de la classe JsonManager.
     * @see JsonManager#sauvegarderProfil
     * @see JsonManager#chargerProfil
     */
    @Test
    public void testSauvegarderProfil(){
        //initialisation des profils
        initProfils();
        JsonManager manager = new JsonManager();
        Profile profile = new Profile("test");
        manager.sauvegarderProfil(profile);
        List<String> profils = manager.getListeProfils();
        assertEquals(1, profils.size(), "Il devrait y avoir 1 profil");
        assertEquals("test", profils.get(0), "Le premier profil devrait être test");

        // test du chargement du profilnom
        Profile profileCharge = manager.chargerProfil("test");
        assertNotNull(profileCharge, "Le profil ne devrait pas être nul");

        //restauration des profils
        restoreProfils();
    }
}