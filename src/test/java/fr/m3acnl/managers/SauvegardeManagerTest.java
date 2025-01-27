package fr.m3acnl.managers;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;

import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.*;

public class SauvegardeManagerTest extends Tests {

    @BeforeAll
    public static void initAll() {
        printNameAtStart(SauvegardeManagerTest.class);
    }

    @AfterAll
    public static void endAll() {
        printNameAtEnd(SauvegardeManagerTest.class);
    }

    @Test
    public void testGetInstance() {
        SauvegardeManager instance1 = SauvegardeManager.getInstance();
        assertNotNull(instance1, "L'instance doit être non nulle");
        SauvegardeManager instance2 = SauvegardeManager.getInstance();
        assertSame(instance1, instance2, "Les deux instances doivent être les mêmes");
    }

    @Test
    public void testSaveDirectoryPath() {
        SauvegardeManager manager = SauvegardeManager.getInstance();
        Path savePath = manager.getRepertoireSauvegarde();
        
        OsManager.OsType currentOs = OsManager.getInstance().getOsType();
        Path expectedPath;
        
        switch (currentOs) {
            case WINDOWS:
                expectedPath = Path.of(System.getenv("APPDATA"), "HashiParmentier");
                break;
            case MAC:
                expectedPath = Path.of(System.getProperty("user.home"), "Library", "Application Support", "HashiParmentier");
                break;
            default: // LINUX
                expectedPath = Path.of(System.getProperty("user.home"), ".game", "HashiParmentier");
                break;
        }
        
        assertEquals(expectedPath, savePath, "Le chemin du répertoire de sauvegarde doit être correct");
    }

    @Test
    public void testInitialiseRepertoire() {
        SauvegardeManager manager = SauvegardeManager.getInstance();
        Path saveDir = manager.getRepertoireSauvegarde();
        assertTrue(saveDir.toFile().exists(), "Le répertoire de sauvegarde doit exister");
    }
    
}