package fr.m3acnl.managers;

import fr.m3acnl.Tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

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
     * Constructeur de la classe.
     */
    public OsManagerTest() {}
}
