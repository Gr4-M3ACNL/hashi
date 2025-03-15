package fr.m3acnl.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import fr.m3acnl.game.Difficulte;

import fr.m3acnl.Tests;

/**
 * Classe de test de la classe HistoriquePartieProfile.
 * 
 * @see HistoriquePartieProfile
 */
public class HistoriquePartieProfileTest extends Tests {

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {printNameAtStart(HistoriquePartieProfileTest.class);}

    /**
     * Méthode de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {printNameAtEnd(HistoriquePartieProfileTest.class);}

    /**
     * Constructeur de la classe de test.
     */
    public HistoriquePartieProfileTest() {}

    /**
     * Historique des parties jouées par un joueur.
     */
    private HistoriquePartieProfile historiquePartieProfile;

    /**
     * Méthode d'initialisation des tests.
     */
    @BeforeEach
    public void setUp() {
        historiquePartieProfile = new HistoriquePartieProfile();
    }

    /**
     * Test de la méthode getFacile de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getFacile
     */
    @Test
    public void testGetFacile() {
        List<Duration> facile = historiquePartieProfile.getFacile();
        assertTrue(facile.isEmpty());
    }

    /**
     * Test de la méthode getMoyen de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getMoyen
     */
    @Test
    public void testGetMoyen() {
        List<Duration> moyen = historiquePartieProfile.getMoyen();
        assertTrue(moyen.isEmpty());
    }

    /**
     * Test de la méthode getDifficile de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getDifficile
     */
    @Test
    public void testGetDifficile() {
        List<Duration> difficile = historiquePartieProfile.getDifficile();
        assertTrue(difficile.isEmpty());
    }

    /**
     * Test de la méthode getExpert de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getExpert
     */
    @Test
    public void testGetExpert() {
        List<Duration> expert = historiquePartieProfile.getExpert();
        assertTrue(expert.isEmpty());
    }

    /**
     * Test de la méthode getTemps de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getTemps
     */
    @Test
    public void testGetTemps() {
        List<Duration> facile = historiquePartieProfile.getTemps(Difficulte.facile);
        assertTrue(facile.isEmpty());

        List<Duration> moyen = historiquePartieProfile.getTemps(Difficulte.moyen);
        assertTrue(moyen.isEmpty());

        List<Duration> difficile = historiquePartieProfile.getTemps(Difficulte.difficile);
        assertTrue(difficile.isEmpty());

        List<Duration> expert = historiquePartieProfile.getTemps(Difficulte.expert);
        assertTrue(expert.isEmpty());
    }

    /**
     * Test de la méthode getNbPartieDifficulte de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getNbPartieDifficulte
     */
    @Test
    public void testGetNbPartieDifficulte() {
        assertEquals(0, historiquePartieProfile.getNbPartieDifficulte(Difficulte.facile));
        assertEquals(0, historiquePartieProfile.getNbPartieDifficulte(Difficulte.moyen));
        assertEquals(0, historiquePartieProfile.getNbPartieDifficulte(Difficulte.difficile));
        assertEquals(0, historiquePartieProfile.getNbPartieDifficulte(Difficulte.expert));
    }

    /**
     * Test de la méthode ajjouterTemps de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#ajouterTemps
     */
    @Test
    public void testAjouterTemps() {
        Duration time = Duration.ofSeconds(10);

        historiquePartieProfile.ajouterTemps(Difficulte.facile, time);
        assertEquals(1, historiquePartieProfile.getNbPartieDifficulte(Difficulte.facile));
        assertEquals(time, historiquePartieProfile.getFacile().get(0));

        historiquePartieProfile.ajouterTemps(Difficulte.moyen, time);
        assertEquals(1, historiquePartieProfile.getNbPartieDifficulte(Difficulte.moyen));
        assertEquals(time, historiquePartieProfile.getMoyen().get(0));

        historiquePartieProfile.ajouterTemps(Difficulte.difficile, time);
        assertEquals(1, historiquePartieProfile.getNbPartieDifficulte(Difficulte.difficile));
        assertEquals(time, historiquePartieProfile.getDifficile().get(0));

        historiquePartieProfile.ajouterTemps(Difficulte.expert, time);
        assertEquals(1, historiquePartieProfile.getNbPartieDifficulte(Difficulte.expert));
        assertEquals(time, historiquePartieProfile.getExpert().get(0));
    }

    /**
     * Test de la méthode setFacile de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#setFacile
     */
    @Test
    public void testSetFacile() {
        List<Duration> facile = historiquePartieProfile.getFacile();
        facile.add(Duration.ofSeconds(10));
        List<String> facileStrings = new ArrayList<String>();
        for (Duration d : facile) {
            facileStrings.add(d.toString());
        }
        historiquePartieProfile.setFacile(facileStrings);
        assertEquals(facile, historiquePartieProfile.getFacile());
    }

    /**
     * Test de la méthode setMoyen de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#setMoyen
     */
    @Test
    public void testSetMoyen() {
        List<Duration> moyen = historiquePartieProfile.getMoyen();
        moyen.add(Duration.ofSeconds(10));
        List<String> moyenStrings = new ArrayList<String>();
        for (Duration d : moyen) {
            moyenStrings.add(d.toString());
        }
        historiquePartieProfile.setMoyen(moyenStrings);
        assertEquals(moyen, historiquePartieProfile.getMoyen());
    }

    /**
     * Test de la méthode setDifficile de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#setDifficile
     */
    @Test
    public void testSetDifficile() {
        List<Duration> difficile = historiquePartieProfile.getDifficile();
        difficile.add(Duration.ofSeconds(10));
        List<String> difficileStrings = new ArrayList<String>();
        for (Duration d : difficile) {
            difficileStrings.add(d.toString());
        }
        historiquePartieProfile.setDifficile(difficileStrings);
        assertEquals(difficile, historiquePartieProfile.getDifficile());
    }

    /**
     * Test de la méthode setExpert de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#setExpert
     */
    @Test
    public void testSetExpert() {
        List<Duration> expert = historiquePartieProfile.getExpert();
        expert.add(Duration.ofSeconds(10));
        List<String> expertStrings = new ArrayList<String>();
        for (Duration d : expert) {
            expertStrings.add(d.toString());
        }
        historiquePartieProfile.setExpert(expertStrings);
        assertEquals(expert, historiquePartieProfile.getExpert());
    }

    /**
     * Test de la méthode getIndexFacile de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getIndexFacile
     */
    @Test
    public void testGetIndexFacile() {
        assertEquals(0, historiquePartieProfile.getIndexFacile());
        historiquePartieProfile.ajouterTemps(Difficulte.facile, Duration.ofSeconds(10));
        assertEquals(1, historiquePartieProfile.getIndexFacile());
    }

    /**
     * Test de la méthode getIndexMoyen de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getIndexMoyen
     */
    @Test
    public void testGetIndexMoyen() {
        assertEquals(0, historiquePartieProfile.getIndexMoyen());
        historiquePartieProfile.ajouterTemps(Difficulte.moyen, Duration.ofSeconds(10));
        assertEquals(1, historiquePartieProfile.getIndexMoyen());
    }

    /**
     * Test de la méthode getIndexDifficile de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getIndexDifficile
     */
    @Test
    public void testGetIndexDifficile() {
        assertEquals(0, historiquePartieProfile.getIndexDifficile());
        historiquePartieProfile.ajouterTemps(Difficulte.difficile, Duration.ofSeconds(10));
        assertEquals(1, historiquePartieProfile.getIndexDifficile());
    }

    /**
     * Test de la méthode getIndexExpert de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getIndexExpert
     */
    @Test
    public void testGetIndexExpert() {
        assertEquals(0, historiquePartieProfile.getIndexExpert());
        historiquePartieProfile.ajouterTemps(Difficulte.expert, Duration.ofSeconds(10));
        assertEquals(1, historiquePartieProfile.getIndexExpert());
    }

    /**
     * Test de la méthode getIndex de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getIndex
     */
    @Test
    public void testGetIndex() {
        // Test initial values
        assertEquals(0, historiquePartieProfile.getIndex(Difficulte.facile));
        assertEquals(0, historiquePartieProfile.getIndex(Difficulte.moyen));
        assertEquals(0, historiquePartieProfile.getIndex(Difficulte.difficile));
        assertEquals(0, historiquePartieProfile.getIndex(Difficulte.expert));

        // Test after adding times
        historiquePartieProfile.ajouterTemps(Difficulte.facile, Duration.ofSeconds(10));
        historiquePartieProfile.ajouterTemps(Difficulte.moyen, Duration.ofSeconds(10));
        historiquePartieProfile.ajouterTemps(Difficulte.difficile, Duration.ofSeconds(10));
        historiquePartieProfile.ajouterTemps(Difficulte.expert, Duration.ofSeconds(10));

        assertEquals(1, historiquePartieProfile.getIndex(Difficulte.facile));
        assertEquals(1, historiquePartieProfile.getIndex(Difficulte.moyen));
        assertEquals(1, historiquePartieProfile.getIndex(Difficulte.difficile));
        assertEquals(1, historiquePartieProfile.getIndex(Difficulte.expert));
    }
}