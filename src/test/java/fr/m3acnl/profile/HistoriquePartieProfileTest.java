package fr.m3acnl.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.sql.Time;
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
     * Test de la méthode getfacile de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getfacile
     */
    @Test
    public void testGetFacile() {
        List<Time> facile = historiquePartieProfile.getFacile();
        assertTrue(facile.isEmpty());
    }

    /**
     * Test de la méthode getMoyen de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getMoyen
     */
    @Test
    public void testGetMoyen() {
        List<Time> moyen = historiquePartieProfile.getMoyen();
        assertTrue(moyen.isEmpty());
    }

    /**
     * Test de la méthode getDifficile de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getDifficile
     */
    @Test
    public void testGetDifficile() {
        List<Time> difficile = historiquePartieProfile.getDifficile();
        assertTrue(difficile.isEmpty());
    }

    /**
     * Test de la méthode getExpert de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getExpert
     */
    @Test
    public void testGetExpert() {
        List<Time> expert = historiquePartieProfile.getExpert();
        assertTrue(expert.isEmpty());
    }

    /**
     * Test de la méthode getTemps de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#getTemps
     */
    @Test
    public void testGetTemps() {
        List<Time> facile = historiquePartieProfile.getTemps(Difficulte.facile);
        assertTrue(facile.isEmpty());

        List<Time> moyen = historiquePartieProfile.getTemps(Difficulte.moyen);
        assertTrue(moyen.isEmpty());

        List<Time> difficile = historiquePartieProfile.getTemps(Difficulte.difficile);
        assertTrue(difficile.isEmpty());

        List<Time> expert = historiquePartieProfile.getTemps(Difficulte.expert);
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
        Time time = new Time(System.currentTimeMillis());

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
        List<Time> facile = historiquePartieProfile.getFacile();
        facile.add(new Time(System.currentTimeMillis()));
        historiquePartieProfile.setFacile(facile);
        assertEquals(facile, historiquePartieProfile.getFacile());
    }

    /**
     * Test de la méthode setMoyen de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#setMoyen
     */
    @Test
    public void testSetMoyen() {
        List<Time> moyen = historiquePartieProfile.getMoyen();
        moyen.add(new Time(System.currentTimeMillis()));
        historiquePartieProfile.setMoyen(moyen);
        assertEquals(moyen, historiquePartieProfile.getMoyen());
    }

    /**
     * Test de la méthode setDifficile de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#setDifficile
     */
    @Test
    public void testSetDifficile() {
        List<Time> difficile = historiquePartieProfile.getDifficile();
        difficile.add(new Time(System.currentTimeMillis()));
        historiquePartieProfile.setDifficile(difficile);
        assertEquals(difficile, historiquePartieProfile.getDifficile());
    }

    /**
     * Test de la méthode setExpert de la classe HistoriquePartieProfile.
     * 
     * @see HistoriquePartieProfile#setExpert
     */
    @Test
    public void testSetExpert() {
        List<Time> expert = historiquePartieProfile.getExpert();
        expert.add(new Time(System.currentTimeMillis()));
        historiquePartieProfile.setExpert(expert);
        assertEquals(expert, historiquePartieProfile.getExpert());
    }
}