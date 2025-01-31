package fr.m3acnl.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;

public class ParametreProfileTest extends Tests {

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {printNameAtStart(ParametreProfileTest.class);}

    /**
     * Méthode de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {printNameAtEnd(ParametreProfileTest.class);}

    /**
     * Constructeur de la classe de test ParametreProfileTest.
     */
    ParametreProfileTest() {}

    @Test
    void testGetEffetVisuel() {
        ParametreProfile parametreProfile = new ParametreProfile();
        assertEquals(true, parametreProfile.getEffetVisuel());
    }

    @Test
    void testGetNiveauAide() {
        ParametreProfile parametreProfile = new ParametreProfile();
        assertEquals(0, parametreProfile.getNiveauAide());
    }

    @Test
    void testGetVolumeEffetsSonore() {
        ParametreProfile parametreProfile = new ParametreProfile();
        assertEquals(0.5f, parametreProfile.getVolumeEffetsSonore());
    }

    @Test
    void testSetEffetVisuel() {
        ParametreProfile parametreProfile = new ParametreProfile();
        parametreProfile.setEffetVisuel(false);
        assertEquals(false, parametreProfile.getEffetVisuel());
    }

    @Test
    void testSetNiveauAide() {
        ParametreProfile parametreProfile = new ParametreProfile();
        parametreProfile.setNiveauAide(1);
        assertEquals(1, parametreProfile.getNiveauAide());

        assertThrows(IllegalArgumentException.class, () -> parametreProfile.setNiveauAide(-1), "Le niveau d'aide ne peut pas être négatif");
        assertThrows(IllegalArgumentException.class, () -> parametreProfile.setNiveauAide(3), "Le niveau d'aide ne peut pas être supérieur à 2");
    }

    @Test
    void testSetVolumeEffetsSonore() {
        ParametreProfile parametreProfile = new ParametreProfile();
        parametreProfile.setVolumeEffetsSonore(0.7f);
        assertEquals(0.7f, parametreProfile.getVolumeEffetsSonore());

        assertThrows(IllegalArgumentException.class, () -> parametreProfile.setVolumeEffetsSonore(-0.1f), "Le volume des effets sonores ne peut pas être négatif");
        assertThrows(IllegalArgumentException.class, () -> parametreProfile.setVolumeEffetsSonore(1.1f), "Le volume des effets sonores ne peut pas être supérieur à 1");
    }
}
