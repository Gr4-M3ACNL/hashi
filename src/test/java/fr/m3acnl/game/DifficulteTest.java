package fr.m3acnl.game;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;

/**
 * Classe de test de l'enum Difficulte.
 * 
 */
public class DifficulteTest extends Tests {

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {printNameAtStart(DifficulteTest.class);}

    /**
     * Méthode de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {printNameAtEnd(DifficulteTest.class);}

    /**
     * Constructeur de la classe de test ProfileTest.
     */
    DifficulteTest() {}

    /**
     * Test de la méthode values de l'enum Difficulte.
     */
    @Test
    public void testEnumValues() {
        assertEquals(4, Difficulte.values().length);
        assertArrayEquals(
            new Difficulte[] {
                Difficulte.facile,
                Difficulte.moyen,
                Difficulte.difficile,
                Difficulte.expert
            },
            Difficulte.values()
        );
    }

    /**
     * Test de la méthode valueOf de l'enum Difficulte.
     */
    @Test
    public void testValueOf() {
        assertEquals(Difficulte.facile, Difficulte.valueOf("facile"));
        assertEquals(Difficulte.moyen, Difficulte.valueOf("moyen"));
        assertEquals(Difficulte.difficile, Difficulte.valueOf("difficile"));
        assertEquals(Difficulte.expert, Difficulte.valueOf("expert"));
    }

    /**
     * Test de la méthode valueOf de l'enum Difficulte avec une valeur invalide.
     */
    @Test
    public void testValueOfInvalid() {
        assertThrows(IllegalArgumentException.class, () -> {
            Difficulte.valueOf("invalidValue");
        });
    }
}
