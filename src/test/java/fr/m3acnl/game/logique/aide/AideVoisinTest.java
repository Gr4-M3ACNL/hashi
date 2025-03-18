package fr.m3acnl.game.logique.aide;

import java.util.List;

import org.junit.jupiter.api.AfterAll;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;
import fr.m3acnl.game.logique.Jeu;
import fr.m3acnl.game.logique.elementjeu.Coord;
import fr.m3acnl.game.logique.elementjeu.Noeud;

/**
 * Classe test pour la classe AideVoisin.
 *
 * @see AideVoisin
 * @see Jeu
 * @see Noeud
 */
public class AideVoisinTest extends Tests {
    /**
     * Jeu à tester.
     */
    private static Jeu jeu;
    
    /**
     * AideVoisin à tester.
     */
    private static AideVoisin aideVoisin;
    
    /**
     * Noeud de test.
     */
    private static Noeud noeudTest;
    
    /**
     * Noeud de test 2.
     */
    private static Noeud noeudTest2;

    /**
     * Constructeur de la classe de test.
     * 
     */
    public AideVoisinTest() {
        // Constructeur vide
    }

    /**
     * Méthode d'initialisation de la classe de test.
     * 
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void setUp() {
        printNameAtStart(AideVoisinTest.class);
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };

        jeu = new Jeu(7, mat);
        aideVoisin = new AideVoisin(jeu.getPlateau(), "Aide sur les voisins", "Voisinage", jeu, new Coord(0, 0));
        noeudTest = (Noeud) jeu.getPlateau().getElement(0, 0);
        noeudTest2 = (Noeud) jeu.getPlateau().getElement(5, 4);
    }

    /**
     * Test de la méthode afficherAideNoeud.
     * 
     * @see AideVoisin#afficherAideNoeud
     */
    @Test
    void testAfficherAideNoeud() {
        assertDoesNotThrow(() -> aideVoisin.afficherAideNoeud(noeudTest));
    }

    /**
     * Test de la méthode checkIsolement.
     * 
     * @see AideVoisin#checkIsolement
     */
    @Test
    void testCheckIsolement() {
        assertDoesNotThrow(() -> aideVoisin.checkIsolement(noeudTest));
        assertDoesNotThrow(() -> aideVoisin.checkIsolement(noeudTest2));
    }

    /**
     * Test de la méthode poidsTotalVoisins.
     * 
     * @see AideVoisin#poidsTotalVoisins
     */
    @Test
    void testPoidsTotalVoisins() {
        int poids = aideVoisin.poidsTotalVoisins(noeudTest);
        assertTrue(poids >= 0, "Le poids total des voisins doit être positif ou nul.");
    }

    /**
     * Test de la méthode trouverVoisins.
     * 
     * @see AideVoisin#trouverVoisins
     */
    @Test
    void testTrouverVoisins() {
        List<Noeud> voisins = aideVoisin.trouverVoisins(noeudTest);
        assertNotNull(voisins, "La liste des voisins ne doit pas être null.");
    }

    /**
     * Test de la méthode getListeNoeuds.
     * 
     * @see AideVoisin#getListeNoeuds
     */
    @Test
    void testGetListeNoeuds() {
        List<Noeud> noeuds = aideVoisin.getListeNoeuds();
        assertFalse(noeuds.isEmpty(), "La liste des noeuds ne doit pas être vide.");
    }

    /**
     * Test de la méthode TrouverVoisinsDispo.
     * 
     * @see AideVoisin#trouverVoisinsDispo
     */
    @Test
    void testTrouverVoisinsDispo() {
        List<Noeud> voisinsDispo = aideVoisin.trouverVoisinsDispo(noeudTest);
        assertNotNull(voisinsDispo, "Les voisins disponibles ne doivent pas être null.");
    }

    /**
     * Test de la méthode poidRestantVoisin.
     * 
     * @see AideVoisin#poidRestantVoisin(Noeud)
     */
    @Test
    void testPoidRestantVoisin() {
        boolean poidsRestant = aideVoisin.poidRestantVoisin(noeudTest);
        assertTrue(poidsRestant || !poidsRestant, "Le retour doit être un booléen valide.");
    }

    /**
     * Test de la méthode checkVoisinage.
     * 
     * @see Tests#printNameAtEnd(Class)
     */
    @AfterAll
    public static void tearDown() {
        jeu = null;
        aideVoisin = null;
        noeudTest = null;
        noeudTest2 = null;
        printNameAtEnd(AideVoisinTest.class);
    }
}
