package fr.m3acnl.profile;

import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;


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

    @Test
    public void testProfileName() {
        String name = "TestProfile";
        Profile profile = new Profile(name);
        assertEquals(name, profile.getName());
    }

    @Test
    public void testProfileParametreNotNull() {
        Profile profile = new Profile("TestProfile");
        assertNotNull(profile.getParametre());
    }
}