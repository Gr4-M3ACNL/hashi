package fr.m3acnl.game;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import fr.m3acnl.game.logique.Jeu;
import fr.m3acnl.managers.ProfileManager;
import fr.m3acnl.managers.SauvegardePartieManager;
import fr.m3acnl.managers.SauvegardePartieManager.JeuEnCour;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;
import java.io.IOException;
import java.time.Duration;

import fr.m3acnl.Tests;

/**
 * Classe de test pour la classe Partie.
 * @see Partie
 * @see Tests
 */
public class PartieTest extends Tests {

    /**
     * Partie à tester.
     */
    private Partie partie;
    /**
     * Faux jeu pour les tests.
     */
    private Jeu jeuMock;
    /**
     * Faux sauvegardePartieManager pour les tests.
     */
    private SauvegardePartieManager sauvegardePartieManagerMock;
    /**
     * Faux jeuEnCour pour les tests.
     */
    private JeuEnCour jeuEnCourMock;

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {printNameAtStart(PartieTest.class);}

    /**
     * Méthode de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {printNameAtEnd(PartieTest.class);}

    /**
     * Méthode d'initialisation des tests
     */
    @BeforeEach
    public void setUp() {
        jeuMock = mock(Jeu.class);
        sauvegardePartieManagerMock = mock(SauvegardePartieManager.class);
        jeuEnCourMock = mock(JeuEnCour.class);

        when(jeuEnCourMock.jeu()).thenReturn(jeuMock);
        when(jeuEnCourMock.chrono()).thenReturn(1000L);

        try (MockedStatic<SauvegardePartieManager> mocked = mockStatic(SauvegardePartieManager.class)) {
            mocked.when(SauvegardePartieManager::getInstance).thenReturn(sauvegardePartieManagerMock);
            when(sauvegardePartieManagerMock.charger(any(Difficulte.class))).thenReturn(jeuEnCourMock);

            partie = new Partie(Difficulte.facile);
        }
    }

    /**
     * Constructeur de la classe de test
     */
    PartieTest() {}

    /**
     * Test de la méthode de récupération de la durée du chrono
     */
    @Test
    public void testGetChronoDuration() {
        Duration duration = partie.getChronoDuration();
        assertNotNull(duration);
        assertTrue(duration.toMillis() >= 0);
    }

    /**
     * Test de la méthode de sérialisation
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    @Test
    public void testSerialize() throws IOException {
        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        SerializerProvider serializerProvider = mock(SerializerProvider.class);

        partie.serialize(jsonGenerator, serializerProvider);

        verify(jsonGenerator).writeStartObject();
        verify(jsonGenerator).writeEndObject();
    }

    /**
     * Test de la méthode de sérialisation avec type
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    @Test
    public void testSerializeWithType() throws IOException {
        JsonGenerator jsonGenerator = mock(JsonGenerator.class);
        SerializerProvider serializerProvider = mock(SerializerProvider.class);
        TypeSerializer typeSerializer = mock(TypeSerializer.class);

        partie.serializeWithType(jsonGenerator, serializerProvider, typeSerializer);

        verify(jsonGenerator).writeStartObject();
        verify(jsonGenerator).writeEndObject();
    }

    /**
     * Test de la méthode d'ajout de malus
     */
    @Test
    public void testAddMalus() {
        Duration beforeMalus = partie.getChronoDuration();
        partie.addMalus(1000);
        Duration afterMalus = partie.getChronoDuration();

        assertTrue(afterMalus.minus(beforeMalus).toMillis() >= 1000);
    }

    /** 
     * Test la méthode de récupération du jeu
     */
    @Test
    public void testGetJeu() {
        assertNotNull(partie.getJeu());
    }

    /**
     * Test de la méthode de sauvegarde
     */
    @Disabled
    @Test
    public void testSauvegarde() {
        // test vide car juste un appel à une méthode de sauvegarde
    }

    /**
     * Test du constructeur
     */
    @Test
    public void testConstructeur() {
        assertNotNull(partie);
        assertNotNull(partie.getChronoDuration());
        verify(sauvegardePartieManagerMock).charger(eq(Difficulte.facile));
    }

    /**
     * Test de la méthode stopChrono quand le chrono n'est pas en pause
     */
    @Test
    public void testStopChronoSansPause() {
        ProfileManager.getInstance().creerProfil("testPartieChrono");
        partie.stopChrono();
        Duration beforeStop = partie.getChronoDuration();
        Duration afterStop = partie.getChronoDuration();
        
        assertEquals(beforeStop, afterStop);
        ProfileManager.getInstance().supprimerProfil("testPartieChrono");
    }

    /**
     * Test de la méthode stopChrono quand le chrono est déjà en pause
     */
    @Test
    public void testStopChronoWhenAvecPause() {
        ProfileManager.getInstance().creerProfil("testPartieChrono");
        partie.stopChrono(); // Première pause
        Duration beforeSecondStop = partie.getChronoDuration();
        partie.stopChrono(); // Seconde pause
        Duration afterSecondStop = partie.getChronoDuration();
        
        assertEquals(beforeSecondStop, afterSecondStop);
        ProfileManager.getInstance().supprimerProfil("testPartieChrono");
    }

    /**
     * Test de la méthode startChrono quand le chrono est en pause
     */
    @Test
    public void testStartChronoAvecPause() {
        ProfileManager.getInstance().creerProfil("testPartieChrono");
        partie.stopChrono(); // Met en pause d'abord
        Duration beforeStart = partie.getChronoDuration();
        partie.startChrono();
        Duration afterStart = partie.getChronoDuration();
        
        assertTrue(afterStart.compareTo(beforeStart) >= 0);
        ProfileManager.getInstance().supprimerProfil("testPartieChrono");
    }

    /**
     * Test de la méthode startChrono quand le chrono n'est pas en pause
     */
    @Test
    public void testStartChronoSansPause() {
        ProfileManager.getInstance().creerProfil("testPartieChrono");
        Duration beforeStart = partie.getChronoDuration();
        partie.startChrono();
        Duration afterStart = partie.getChronoDuration();
        
        assertTrue(afterStart.compareTo(beforeStart) >= 0);
        ProfileManager.getInstance().supprimerProfil("testPartieChrono");
    }
}