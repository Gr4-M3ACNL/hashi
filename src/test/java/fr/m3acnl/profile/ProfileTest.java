package fr.m3acnl.profile;

import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


/**
 * Classe de test de la classe Profile.
 * 
 * @see Profile
 * @see Tests
 */
public class ProfileTest extends Tests {

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {printNameAtStart(ProfileTest.class);}

    /**
     * Méthode de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {printNameAtEnd(ProfileTest.class);}

    /**
     * Constructeur de la classe de test ProfileTest.
     */
    ProfileTest() {}

    /**
     * Test de la méthode getnom de la classe Profile.
     */
    @Test
    public void testProfileNom() {
        String nom = "TestProfile";
        Profile profile = new Profile(nom);
        assertEquals(nom, profile.getnom());
    }

    /**
     * Test de la méthode getParametre de la classe Profile.
     */
    @Test
    public void testProfileParametreNotNull() {
        Profile profile = new Profile("TestProfile");
        assertNotNull(profile.getParametre());
    }

    /**
     * Test de la méthode getParametre de la classe Profile.
     */
    @Test
    public void testGetHistoriquePartieProfile() {
        Profile profile = new Profile("TestProfile");
        assertNotNull(profile.getHistoriquePartieProfile(), "L'historique des parties jouées par un joueur ne doit pas être null.");
    }
}