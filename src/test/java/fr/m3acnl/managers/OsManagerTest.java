package fr.m3acnl.managers;

import fr.m3acnl.Tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Classe de test pour OsManager
 * @see OsManager
 * @see Tests
 * 
 * @author PUREN Mewen
 */
public class OsManagerTest extends Tests{
    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {printNameAtStart(OsManagerTest.class);}

    /**
     * Méthode de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {printNameAtEnd(OsManagerTest.class);}

    /**
     * Test de la méthode getInstance
     * @see OsManager#getInstance
     */
    @Test
    public void GetInstance_DoitRetournerInstance() {
        OsManager osManager = OsManager.getInstance();
        assertNotNull(osManager);
    }

    /**
     * Test de la méthode getOsType
     * @see OsManager#getOsType
     */
    @Test
    public void GetOsType_DoitRetournerType() {
        OsManager osManager = OsManager.getInstance();
        OsManager.OsType osType = osManager.getOsType();
        assertNotNull(osType);
    }

    /**
     * Test de la détection des différents systèmes d'exploitation
     */
    @Test
    public void DetectionOS_DoitRetournerBonType() {
        String osActuel = System.getProperty("os.name").toLowerCase();
        OsManager osManager = OsManager.getInstance();
        OsManager.OsType osType = osManager.getOsType();

        if (osActuel.contains("windows")) {
            assertEquals(OsManager.OsType.WINDOWS, osType);
        } else if (osActuel.contains("mac") || osActuel.contains("darwin")) {
            assertEquals(OsManager.OsType.MAC, osType);
        } else if (osActuel.contains("linux") || osActuel.contains("unix")) {
            assertEquals(OsManager.OsType.LINUX, osType);
        }
    }

    /**
     * Test de la détection avec un système d'exploitation inconnu
     */
    @Test
    public void DetectionOS_SystemeInconnu_DoitRetournerNull() {
        // On utilise la réflexion pour tester avec un OS inconnu
        try {
            Field osTypeField = OsManager.class.getDeclaredField("osType");
            osTypeField.setAccessible(true);
            
            Method getOsParNom = OsManager.OsType.class.getDeclaredMethod("getOsParNom", String.class);
            getOsParNom.setAccessible(true);
            
            Object resultat = getOsParNom.invoke(null, "systeme_inexistant");
            assertNull(resultat);
        } catch (Exception e) {
            fail("Le test a échoué : " + e.getMessage());
        }
    }

    /**
     * Constructeur de la classe.
     */
    public OsManagerTest() {}
}
