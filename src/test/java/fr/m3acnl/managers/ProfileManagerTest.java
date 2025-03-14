package fr.m3acnl.managers;

import fr.m3acnl.Tests;
import fr.m3acnl.profile.Profile;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de Test de la classe ProfileManager.
 * 
 * @see ProfileManager
 * @see Tests
 */
public class ProfileManagerTest extends Tests{

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
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {printNameAtStart(ProfileManagerTest.class);}

    /**
     * Méthode de fin de la classe de Test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {printNameAtEnd(ProfileManagerTest.class);}

    /**
     * Test de la méthode getInstance de la classe ProfileManager.
     * Vérifie que l'instance de la classe ProfileManager est bien créée.
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
     * @see ProfileManager#getProfileActif
     */
    @Test
    public void testGetProfileActif() {
        ProfileManager profileManager = ProfileManager.getInstance();
        Profile profile = profileManager.getProfileActif();
        profileManager.creerProfil("Test");
        profileManager.desactiverProfileActif();
        assertNull(profile);
        profileManager.setProfileActif(profileManager.listeProfils().get(0));
        profile = profileManager.getProfileActif();
        assertNotNull(profile);
        profileManager.supprimerProfil("Test");
    }

    /**
     * Test de la méthode desactiverProfileActif de la classe ProfileManager.
     * Vérifie que le profil actif est bien désactivé.
     * @see ProfileManager#desactiverProfileActif
     */
    @Test
    public void testDesactiverProfileActif() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("Test");
        assertNotNull(profileManager.getProfileActif());
        profileManager.desactiverProfileActif();
        assertNull(profileManager.getProfileActif());
        profileManager.supprimerProfil("Test");
    }

    /**
     * Test de la méthode setProfileActif de la classe ProfileManager.
     * Vérifie que le profil actif est bien défini.
     * @see ProfileManager#setProfileActif
     */
    @Test
    public void testSetProfileActif() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("Test");
        profileManager.setProfileActif(profileManager.listeProfils().get(0));
        Profile profile = profileManager.getProfileActif();
        assertNotNull(profile);
        profileManager.supprimerProfil(profile);
    }

    /**
     * Test de la méthode listeProfils de la classe ProfileManager.
     * Vérifie que la liste des profils est bien retournée.
     * @see ProfileManager#listeProfils
     */
    @Test
    public void testListeProfils() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("Test");
        assertNotNull(profileManager.listeProfils());
        profileManager.supprimerProfil("Test");
    }

    /**
     * Test de la méthode sauvegarder de la classe ProfileManager.
     * Vérifie que le profil actif est bien sauvegardé.
     * @see ProfileManager#sauvegarder
     */
    @Test
    public void testSauvegarder() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("Test");
        profileTest = profileManager.getProfileActif();
        profileManager.sauvegarder();
        profileManager.supprimerProfil(profileTest);
    }

    /**
     * Test de la méthode supprimerProfil de la classe ProfileManager.
     * Vérifie que le profil est bien supprimé.
     * @see ProfileManager#supprimerProfil
     */
    @Test
    public void testSupprimerProfilByName() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("Test");
        profileTest = profileManager.getProfileActif();
        profileManager.supprimerProfil("Test");
        assertNull(profileManager.getProfileActif());
    }

    /**
     * Test de la méthode supprimerProfil de la classe ProfileManager.
     * Vérifie que le profil est bien supprimé.
     * @see ProfileManager#supprimerProfil
     */
    @Test
    public void testSupprimerProfilByObject() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("Test");
        profileTest = profileManager.getProfileActif();
        profileManager.supprimerProfil(profileTest);
        assertNull(profileManager.getProfileActif());
    }

    /**
     * Test de la méthode creerProfil de la classe ProfileManager.
     * Vérifie que le profil est bien créé.
     * @see ProfileManager#creerProfil
     */
    @Test
    public void testCreerProfil() {
        ProfileManager profileManager = ProfileManager.getInstance();
        profileManager.creerProfil("Test");
        profileTest = profileManager.getProfileActif();
        assertNotNull(profileTest);
        assertThrows(IllegalArgumentException.class,() -> profileManager.creerProfil("Test"), "on ne devrais pas pouvoir crée un profil qui existe déjà");
        profileManager.supprimerProfil(profileTest);
    }
}