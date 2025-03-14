package fr.m3acnl.managers;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.Files;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.databind.JsonNode;

import fr.m3acnl.Tests;
import fr.m3acnl.game.Difficulte;
import fr.m3acnl.game.Partie;
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
     * 
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {
        printNameAtStart(JsonManagerTest.class);
    }

    /**
     * Méthode de fin de la classe de test
     * 
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {
        printNameAtEnd(JsonManagerTest.class);
    }

    /**
     * Constructeur de la classe de test JsonManagerTest.
     */
    JsonManagerTest() {
    }

    /**
     * Test de la méthode getGrilleInfo de la classe JsonManager.
     * 
     * @see JsonManager#getGrilleInfo
     */
    @Test
    public void testGetGrilleInfo() {
        JsonManager manager = new JsonManager();
        JsonManager.GrilleInfo grilleInfo = manager.getGrilleInfo(Difficulte.facile, 0);
        assertNotNull(grilleInfo);
        assertEquals(7, grilleInfo.taille(), "La taille de la grille devrait être de 7");
        
        // Vérification du format de la grille sérialisée
        Double[][] serialise = grilleInfo.serialise();
        assertNotNull(serialise, "La grille sérialisée ne devrait pas être nulle");
        assertEquals(7, serialise.length, "La grille devrait avoir 7 lignes");
        assertEquals(7, serialise[0].length, "La grille devrait avoir 7 colonnes");
        
        // Vérification de quelques valeurs spécifiques
        assertEquals(-4.0, serialise[0][0], "La première île devrait avoir une valeur de -4");
        assertEquals(0.2, serialise[0][1], "La connexion horizontale devrait avoir une valeur de 0.2");
        assertEquals(-3.0, serialise[0][2], "La deuxième île devrait avoir une valeur de -3");
    }

    /**
     * Test de la méthode getNbGrilles de la classe JsonManager.
     * 
     * @see JsonManager#getNbGrilles
     */
    @Test
    public void testGetNbGrilles() {
        JsonManager manager = new JsonManager();
        int nbGrilles = manager.getNbGrilles(Difficulte.facile);
        assertEquals(5, nbGrilles, "Le nombre de grilles pour la difficulté facile devrait être de 2");
    }

    /**
     * Test de la méthode getDifficultes de la classe JsonManager.
     * 
     * @see JsonManager#getListeDifficultes
     */
    @Test
    public void testGetDifficultes() {
        JsonManager manager = new JsonManager();
        List<String> difficultes = manager.getListeDifficultes();
        assertNotNull(difficultes, "Les difficultés ne devraient pas être nulles");
        assertEquals(4, difficultes.size(), "Il devrait y avoir 3 difficultés");
        assertEquals(difficultes.get(0), "facile", "La première difficulté devrait être facile");
        assertEquals(difficultes.get(1), "moyen", "La deuxième difficulté devrait être moyen");
        assertEquals(difficultes.get(2), "difficile", "La troisième difficulté devrait être difficile");
        assertEquals(difficultes.get(3), "expert", "La quatrième difficulté devrait être expert");
    }

    /**
     * Méthode d'initialisation de test des profils.
     * 
     * Copie le fichier de profils deja existant da un autre endroit.
     * puis supprime le fichier originale de profils dans le repertoire de
     * sauvegarde.
     */
    private void initProfils() {
        if (Files.exists(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"))) {
            if (!Files.exists(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json.bak"))) {
                try {
                    Files.copy(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"),
                            SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json.bak"));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        try {
            Files.deleteIfExists(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode de restauration des profils.
     * 
     * Supprime le fichier de profils actuel et le remplace par le fichier de
     * profils sauvegardé.
     */
    private void restoreProfils() {
        try {
            Files.deleteIfExists(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"));
            if (Files.exists(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json.bak"))) {
                Files.move(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json.bak"),
                        SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Test de la méthode getDifficultes de la classe JsonManager.
     * 
     * @see JsonManager#getListeProfils
     */
    @Test
    public void testGetListeProfils() {
        // initialisation des profils
        initProfils();
        JsonManager manager = new JsonManager();

        // vérifie que le fichier de profils n'existe pas
        assertNull(manager.getListeProfils(), "Le fichier de profils n'existe la liste devrai être null");

        try {
            // copie le fichier de test dans le repertoire ressources au bon endroit
            Files.copy(this.getClass().getResourceAsStream("/fr/m3acnl/managers/profils.json"),
                    SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve("profils.json"));
        } catch (Exception e) {
            e.printStackTrace();
            fail("Impossible de copier le fichier de profils");
        }
        // vérifie que le fichier de profils existe
        assertNotNull(manager.getListeProfils(), "La liste des profils ne devrait pas être nulle");
        List<String> profils = manager.getListeProfils();
        assertEquals(2, profils.size(), "Il devrait y avoir 2 profils");
        assertEquals("jacoboni", profils.get(0), "Le premier profil devrait être jacoboni");
        assertEquals("despres", profils.get(1), "Le deuxième profil devrait être despres");

        // restauration des profils
        restoreProfils();
    }

    /**
     * Test de la méthode sauvegarderProfil de la classe JsonManager.
     * 
     * @see JsonManager#sauvegarderProfil
     * @see JsonManager#chargerProfil
     */
    @Test
    public void testSauvegarderProfil() {
        // initialisation des profils
        initProfils();
        JsonManager manager = new JsonManager();
        Profile profile = new Profile("test");
        manager.sauvegarderProfil(profile);
        List<String> profils = manager.getListeProfils();
        assertEquals(1, profils.size(), "Il devrait y avoir 1 profil");
        assertEquals("test", profils.get(0), "Le premier profil devrait être test");

        // test du chargement du profil nouvellement créé
        Profile profileCharge = manager.chargerProfil("test");
        assertNotNull(profileCharge, "Le profil ne devrait pas être nul");

        // restauration des profils
        restoreProfils();
    }

    /**
     * Test de la méthode supprimerProfil de la classe JsonManager.
     * 
     * @see JsonManager#supprimerProfil
     */
    @Test
    public void testSupprimerProfil() {
        // initialisation des profils
        initProfils();
        JsonManager manager = new JsonManager();
        Profile profile = new Profile("test");
        manager.sauvegarderProfil(profile);

        // test de la suppression du profil
        manager.supprimerProfil("test");
        List<String> profils = manager.getListeProfils();
        assertEquals(0, profils.size(), "Il ne devrait y avoir aucun profil");

        // restauration des profils
        restoreProfils();
    }

    /**
     * Test de la méthode chargerProfil avec un profil inexistant.
     * 
     * @see JsonManager#chargerProfil
     */
    @Test
    public void testChargerProfilInexistant() {
        // initialisation des profils
        initProfils();
        JsonManager manager = new JsonManager();
        
        Profile profileInexistant = manager.chargerProfil("inexistant");
        assertNull(profileInexistant, "Le profil inexistant devrait être null");
        
        // restauration des profils
        restoreProfils();
    }

    /**
     * Test de la méthode chargerProfil avec un profil existant.
     * 
     * @see JsonManager#chargerProfil
     */
    @Test
    public void testChargerProfilExistant() {
        // initialisation des profils
        initProfils();
        JsonManager manager = new JsonManager();
        
        // Création et sauvegarde d'un profil test
        Profile profileOriginal = new Profile("testExistant");
        manager.sauvegarderProfil(profileOriginal);
        
        // Chargement et vérification du profil
        Profile profileCharge = manager.chargerProfil("testExistant");
        assertNotNull(profileCharge, "Le profil chargé ne devrait pas être null");
        assertEquals("testExistant", profileCharge.getNom(), "Le nom du profil devrait être testExistant");
        
        // restauration des profils
        restoreProfils();
    }

    /**
     * Test de la méthode getNbGrilles avec différentes difficultés.
     * 
     * @see JsonManager#getNbGrilles
     */
    @Test
    public void testGetNbGrillesAllDifficulties() {
        JsonManager manager = new JsonManager();
        
        assertEquals(5, manager.getNbGrilles(Difficulte.facile), "Nombre incorrect de grilles faciles");
        assertEquals(5, manager.getNbGrilles(Difficulte.moyen), "Nombre incorrect de grilles moyennes");
        assertEquals(5, manager.getNbGrilles(Difficulte.difficile), "Nombre incorrect de grilles difficiles");
        assertEquals(4, manager.getNbGrilles(Difficulte.expert), "Nombre incorrect de grilles expert");
    }

    /**
     * Test de la méthode getGrilleInfo pour toutes les difficultés et indices.
     * 
     * @see JsonManager#getGrilleInfo
     */
    @Test
    public void testGetGrilleInfoAllDifficulties() {
        JsonManager manager = new JsonManager();
        
        // Test pour chaque difficulté
        for (Difficulte diff : Difficulte.values()) {
            int nbGrilles = manager.getNbGrilles(diff);
            for (int i = 0; i < nbGrilles; i++) {
                JsonManager.GrilleInfo grilleInfo = manager.getGrilleInfo(diff, i);
                assertNotNull(grilleInfo, "La grille " + i + " de difficulté " + diff + " ne devrait pas être nulle");
                assertTrue(grilleInfo.taille() > 0, "La taille de la grille devrait être positive");
                assertNotNull(grilleInfo.serialise(), "La grille sérialisée ne devrait pas être nulle");
            }
        }
    }

    /**
     * Test de la méthode getGrilleInfo avec un indice invalide.
     * 
     * @see JsonManager#getGrilleInfo
     */
    @Test
    public void testGetGrilleInfoInvalidIndex() {
        JsonManager manager = new JsonManager();
        
        assertThrows(IllegalArgumentException.class, () -> manager.getGrilleInfo(Difficulte.facile, -1),
                "L'indice -1 devrait lancer une exception");
        assertThrows(IllegalArgumentException.class, () -> manager.getGrilleInfo(Difficulte.facile, 999),
                "L'indice 2 devrait lancer une exception");
    }

    /**
     * Test de la méthode sauvegardePartie de la classe JsonManager.
     * 
     * @see JsonManager#sauvegardePartie
     */
    @Test
    public void testSauvegardePartie() {
        // initialisation des profils
        initProfils();
        ProfileManager.getInstance().creerProfil("testUser");
        JsonManager manager = new JsonManager();
        
        // Création d'une partie test
        Partie partie = new Partie(Difficulte.facile);
        assertDoesNotThrow(() -> manager.sauvegardePartie(partie, "testUser"),
                "La sauvegarde de la partie devrait fonctionner");
        
        // Vérification que la partie a été sauvegardée
        JsonNode partieChargee = manager.chargerPartie("testUser", Difficulte.facile);
        assertNotNull(partieChargee, "La partie chargée ne devrait pas être nulle");
        
        // restauration des profils
        ProfileManager.getInstance().supprimerProfil("testUser");
        restoreProfils();
    }

    /**
     * Test de la méthode chargerPartie de la classe JsonManager.
     * 
     * @see JsonManager#chargerPartie
     */
    @Test
    public void testChargerPartie() {
        // initialisation des profils
        initProfils();
        ProfileManager.getInstance().creerProfil("testUser");
        JsonManager manager = new JsonManager();
        
        // Test avec un profil inexistant
        assertNull(manager.chargerPartie("inexistant", Difficulte.facile),
                "La partie d'un profil inexistant devrait être nulle");
        
        // Création et sauvegarde d'une partie test
        Partie partie = new Partie(Difficulte.facile);
        partie.getJeu().getCoupsJouer().empiler(partie.getJeu().getPlateau().getListeLien().get(0));
        partie.getJeu().getCoupsJouer().empiler(partie.getJeu().getPlateau().getListeLien().get(3));
        partie.sauvegarde();
        
        // Test du chargement
        JsonNode partieChargee = manager.chargerPartie("testUser", Difficulte.facile);
        assertNotNull(partieChargee, "La partie chargée ne devrait pas être nulle");
        
        // restauration des profils
        ProfileManager.getInstance().supprimerProfil("testUser");
        restoreProfils();
    }

    /**
     * Test de la méthode supprimerPartie de la classe JsonManager.
     * 
     * @see JsonManager#supprimerPartie
     */
    @Test
    public void testSupprimerPartie() {
        // initialisation des profils
        initProfils();
        ProfileManager.getInstance().creerProfil("testUser");
        JsonManager manager = new JsonManager();
        
        // Création et sauvegarde d'une partie test
        Partie partie = new Partie(Difficulte.facile);
        manager.sauvegardePartie(partie, "testUser");
        
        // Test de la suppression
        assertDoesNotThrow(() -> manager.supprimerPartie("testUser", Difficulte.facile),
                "La suppression de la partie devrait fonctionner");
        
        // Vérification que la partie a été supprimée
        assertNull(manager.chargerPartie("testUser", Difficulte.facile),
                "La partie supprimée devrait être nulle");
        
        // restauration des profils
        ProfileManager.getInstance().supprimerProfil("testUser");
        restoreProfils();
    }
}