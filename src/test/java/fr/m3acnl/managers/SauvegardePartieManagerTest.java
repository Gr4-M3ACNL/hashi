package fr.m3acnl.managers;

import fr.m3acnl.Tests;
import fr.m3acnl.game.Difficulte;
import fr.m3acnl.game.Partie;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Test pour la classe SauvegardePartieManager
 * 
 * @see SauvegardePartieManager
 * @see Tests
 * 
 * @author PUREN Mewen
 */
public class SauvegardePartieManagerTest extends Tests {

    /**
     * Instance de la classe ProfileManager.
     */
    private static ProfileManager profileManager;

    /**
     * Instance de la classe Partie.
     * utilisée pour les tests.
     */
    private Partie partie;

    /**
     * Nom du profile de test.
     */
    private static final String TEST_PROFILE = "testProfile";

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {
        printNameAtStart(SauvegardePartieManagerTest.class);
        profileManager = ProfileManager.getInstance();
        profileManager.creerProfil(TEST_PROFILE);
        profileManager.setProfileActif(TEST_PROFILE);
    }

    /**
     * Méthode de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {
        profileManager.supprimerProfil(TEST_PROFILE);
        printNameAtEnd(SauvegardePartieManagerTest.class);
    }

    /**
     * Constructeur de la classe de test SauvegardePartieManagerTest
     */
    SauvegardePartieManagerTest() {}

    /**
     * Méthode d'initialisation des tests
     * on crée une nouvelle partie pour chaque test
     * @see Partie
     */
    @BeforeEach
    public void setUp() {
        partie = new Partie(Difficulte.facile);
    }

    /**
     * Test de la méthode getInstance
     * @see SauvegardePartieManager#getInstance
     */
    @Test
    public void testGetInstance() {
        SauvegardePartieManager instance1 = SauvegardePartieManager.getInstance();
        SauvegardePartieManager instance2 = SauvegardePartieManager.getInstance();
        
        assertNotNull(instance1);
        assertSame(instance1, instance2, "Le singleton devrait retourner la même instance");
    }

    /**
     * Test de la méthode sauvegarde
     * @see SauvegardePartieManager#sauvegarde
     */
    @Test
    public void testSauvegarde() {
        // Sauvegarder la partie
        SauvegardePartieManager.getInstance().sauvegarde(partie);
        
        // Vérifier que la partie peut être rechargée
        SauvegardePartieManager.JeuEnCour jeuEnCour = SauvegardePartieManager.getInstance().charger(Difficulte.facile);
        assertNotNull(jeuEnCour);
        assertNotNull(jeuEnCour.jeu());
    }

    /**
     * Test de la méthode charger pour une nouvelle partie
     * @see SauvegardePartieManager#charger
     */
    @Test
    public void testChargerNouvellePartie() {
        // Supprimer toute sauvegarde existante
        SauvegardePartieManager.getInstance().supprimer(Difficulte.facile);
        
        // Charger une nouvelle partie
        SauvegardePartieManager.JeuEnCour jeuEnCour = SauvegardePartieManager.getInstance().charger(Difficulte.facile);
        
        assertNotNull(jeuEnCour);
        assertEquals(0, jeuEnCour.chrono());
        assertNotNull(jeuEnCour.jeu());
    }

    /**
     * Test de la méthode charger pour une partie existante
     * @see SauvegardePartieManager#charger
     */
    @Test
    public void testChargerPartieExistante() {
        // Sauvegarder une partie avec un chrono spécifique
        SauvegardePartieManager.getInstance().sauvegarde(partie);
        // Charger la partie sauvegardée
        SauvegardePartieManager.JeuEnCour jeuEnCour = SauvegardePartieManager.getInstance().charger(Difficulte.facile);
        
        assertNotNull(jeuEnCour);
        assertNotNull(jeuEnCour.jeu());
    }

    /**
     * Test de la méthode supprimer
     * @see SauvegardePartieManager#supprimer
     */
    @Test
    public void testSupprimer() {
        // Sauvegarder une partie
        SauvegardePartieManager.getInstance().sauvegarde(partie);
        
        // Vérifier que la partie existe
        SauvegardePartieManager.JeuEnCour jeuAvantSuppression = SauvegardePartieManager.getInstance().charger(Difficulte.facile);
        assertNotNull(jeuAvantSuppression);
        
        // Supprimer la partie
        SauvegardePartieManager.getInstance().supprimer(Difficulte.facile);
        
        // Vérifier que la partie a été supprimée (une nouvelle partie doit être créée)
        SauvegardePartieManager.JeuEnCour jeuApresSuppression = SauvegardePartieManager.getInstance().charger(Difficulte.facile);
        assertEquals(0,jeuApresSuppression.chrono(), "le temps devrais étre de 0");
    }
}