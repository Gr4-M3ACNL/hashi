package fr.m3acnl.managers;

import fr.m3acnl.Tests;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class OsManagerTest extends Tests{
    @BeforeAll
    public static void initAll() {printNameAtStart(OsManagerTest.class);}

    @AfterAll
    public static void endAll() {printNameAtEnd(OsManagerTest.class);}

    @Test
    public void GetInstance_DoitRetournerInstance() {
        OsManager osManager = OsManager.getInstance();
        assertNotNull(osManager);
    }

    @Test
    public void GetOsType_DoitRetournerType() {
        OsManager osManager = OsManager.getInstance();
        OsManager.OsType osType = osManager.getOsType();
        assertNotNull(osType);
    }
}
