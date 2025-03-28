package fr.m3acnl.managers;

import fr.m3acnl.Tests;
import fr.m3acnl.game.Difficulte;
import fr.m3acnl.profile.Profile;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.Duration;
import java.util.List;

/**
 * Classe de Test de la classe ProfileManager.
 * 
 * @see ProfileManager
 * @see Tests
 */
public class ProfileManagerTest extends Tests {

    /**
     * Stockage de l'objet profile utiliser pour les test
     */
    private Profile profileTest;

    /**
     * Constructeur de la classe de Test
     */
    public ProfileManagerTest() {
    }

    /**
     * Méthode d'initialisation de la classe de Test
     * 
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {
        printNameAtStart(ProfileManagerTest.class);
        if (ProfileManager.getInstance().listeProfils().contains("TestManager")){
            ProfileManager.getInstance().supprimerProfil("TestManager");
        }
    }

    /**
     * Méthode de fin de la classe de Test
     * 
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {
        printNameAtEnd(ProfileManagerTest.class);
        if (ProfileManager.getInstance().listeProfils().contains("TestManager")){
            ProfileManager.getInstance().supprimerProfil("TestManager");
        }
    }

    /**
     * Test de la méthode getInstance de la classe ProfileManager.
     * Vérifie que l'instance de la classe ProfileManager est bien créée.
     * 
     * @see ProfileManager#getInstance
     */
    @Test
    public void testGetInstance() {
        ProfileManager profileManager = ProfileManager.getInstance();
        assertNotNull(profileManager);
    }

    /**
     * Test de la méthode getProfileActif de la classe ProfileManager.
     * Vérifie que le profil actif est bien retourné.
     * 
     * @see ProfileManager#getProfileActif
     */
    @Test
    public void testGetProfileActif() {
        ProfileManager profileManager = ProfileManager.getInstance();
        Profile profile = profileManager.getProfileActif();
        profileManager.creerProfil("TestManager");
        profileManager.desactiverProfileActif();
        assertNull(profile);
        profileManager.setProfileActif(profileManager.listeProfils().get(0));
        profile = profileManager.getProfileActif();
        assertNotNull(profile);
        profileManager.supprimerProfil("TestManager");
    }

    /**
     * Test de la méthode desactiverProfileActif de la classe ProfileManager.
     * Vérifie que le profil actif est bien désactivé.
     * 
     * @see ProfileManager#desactiverProfileActif
     */
    @Test
    public void testDesactiverProfileActif() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("TestManager");
        assertNotNull(profileManager.getProfileActif());
        profileManager.desactiverProfileActif();
        assertNull(profileManager.getProfileActif());
        profileManager.supprimerProfil("TestManager");
    }

    /**
     * Test de la méthode setProfileActif de la classe ProfileManager.
     * Vérifie que le profil actif est bien défini.
     * 
     * @see ProfileManager#setProfileActif
     */
    @Test
    public void testSetProfileActif() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("TestManager");
        profileManager.setProfileActif(profileManager.listeProfils().get(0));
        Profile profile = profileManager.getProfileActif();
        assertNotNull(profile);
        profileManager.supprimerProfil("TestManager");
    }

    /**
     * Test de la méthode listeProfils de la classe ProfileManager.
     * Vérifie que la liste des profils est bien retournée.
     * 
     * @see ProfileManager#listeProfils
     */
    @Test
    public void testListeProfils() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("TestManager");
        assertNotNull(profileManager.listeProfils());
        profileManager.supprimerProfil("TestManager");
    }

    /**
     * Test de la méthode sauvegarder de la classe ProfileManager.
     * Vérifie que le profil actif est bien sauvegardé.
     * 
     * @see ProfileManager#sauvegarder
     */
    @Test
    public void testSauvegarder() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("TestManager");
        profileTest = profileManager.getProfileActif();
        profileManager.sauvegarder();
        profileManager.supprimerProfil(profileTest);
    }

    /**
     * Test de la méthode supprimerProfil de la classe ProfileManager.
     * Vérifie que le profil est bien supprimé.
     * 
     * @see ProfileManager#supprimerProfil
     */
    @Test
    public void testSupprimerProfilByName() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("TestManager");
        profileTest = profileManager.getProfileActif();
        profileManager.supprimerProfil("TestManager");
        assertNull(profileManager.getProfileActif());
    }

    /**
     * Test de la méthode supprimerProfil de la classe ProfileManager.
     * Vérifie que le profil est bien supprimé.
     * 
     * @see ProfileManager#supprimerProfil
     */
    @Test
    public void testSupprimerProfilByObject() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("TestManager");
        profileTest = profileManager.getProfileActif();
        profileManager.supprimerProfil(profileTest);
        assertNull(profileManager.getProfileActif());
    }

    /**
     * Test de la méthode creerProfil de la classe ProfileManager.
     * Vérifie que le profil est bien créé.
     * 
     * @see ProfileManager#creerProfil
     */
    @Test
    public void testCreerProfil() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("TestManager");
        profileTest = profileManager.getProfileActif();
        assertNotNull(profileTest);
        assertThrows(IllegalArgumentException.class, () -> profileManager.creerProfil("TestManager"),
                "on ne devrais pas pouvoir crée un profil qui existe déjà");
        profileManager.supprimerProfil(profileTest);
    }

    /**
     * Test de la méthode getClassementTemps de la classe ProfileManager.
     * Vérifie que le classement des temps est bien retourné.
     * 
     * @see ProfileManager#getClassementTemps
     */
    @Test
    public void testGetClassemntTemps() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("TestManager");
        profileTest = profileManager.getProfileActif();
        assertNotNull(profileTest);
        profileTest.getHistoriquePartieProfile().ajouterTemps(Difficulte.difficile, Duration.ofSeconds(1));
        profileTest.getHistoriquePartieProfile().ajouterTemps(Difficulte.difficile, Duration.ofSeconds(2));
        profileTest.getHistoriquePartieProfile().ajouterTemps(Difficulte.difficile, Duration.ofSeconds(3));
        profileTest.getHistoriquePartieProfile().ajouterTemps(Difficulte.difficile, Duration.ofSeconds(4));
        profileTest.getHistoriquePartieProfile().ajouterTemps(Difficulte.difficile, Duration.ofSeconds(5));
        
        profileManager.sauvegarder(profileTest);


        List<ProfileManager.TempsPartie> classement = profileManager.getClassementTemps(Difficulte.difficile);
        assertNotNull(classement);
        assertEquals(5, classement.size());
        assertEquals("TestManager", classement.get(0).nomProfil());
        assertEquals(Duration.ofSeconds(1), classement.get(0).duree());
        assertEquals("TestManager", classement.get(1).nomProfil());
        assertEquals(Duration.ofSeconds(2), classement.get(1).duree());
        assertEquals("TestManager", classement.get(2).nomProfil());
        assertEquals(Duration.ofSeconds(3), classement.get(2).duree());
        assertEquals("TestManager", classement.get(3).nomProfil());
        assertEquals(Duration.ofSeconds(4), classement.get(3).duree());
        assertEquals("TestManager", classement.get(4).nomProfil());
        assertEquals(Duration.ofSeconds(5), classement.get(4).duree());
        profileManager.supprimerProfil(profileTest);      

    }
}