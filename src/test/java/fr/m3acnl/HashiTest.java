package fr.m3acnl;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

/**
 * Unit test for simple App.
 */
public class HashiTest extends Tests{

    @BeforeAll
    public static void initAll() {printNameAtStart(HashiTest.class);}

    @AfterAll
    public static void endAll() {printNameAtEnd(HashiTest.class);}
    
    @Test
    public void shouldAnswerWithTrue() {
        assertTrue(true);
    }
}
