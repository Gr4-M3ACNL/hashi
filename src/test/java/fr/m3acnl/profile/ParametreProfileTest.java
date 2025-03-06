package fr.m3acnl.profile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.io.StringWriter;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import fr.m3acnl.Tests;

/**
 * Classe de test de la classe ParametreProfile.
 * 
 * @see ParametreProfile
 * @see Tests
 */
public class ParametreProfileTest extends Tests {

    /**
     * Méthode d'initialisation de la classe de test
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {printNameAtStart(ParametreProfileTest.class);}

    /**
     * Méthode de fin de la classe de test
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {printNameAtEnd(ParametreProfileTest.class);}

    /**
     * Constructeur de la classe de test ParametreProfileTest.
     */
    ParametreProfileTest() {}

    /**
     * Test de la méthode getEffetVisuel de la classe ParametreProfile.
     */
    @Test
    public void testGetEffetVisuel() {
        ParametreProfile parametreProfile = new ParametreProfile();
        assertEquals(true, parametreProfile.getEffetVisuel());
    }

    /**
     * Test de la méthode getNiveauAide de la classe ParametreProfile.
     */
    @Test
    public void testGetNiveauAide() {
        ParametreProfile parametreProfile = new ParametreProfile();
        assertEquals(0, parametreProfile.getNiveauAide());
    }

    /**
     * Test de la méthode getVolumeEffetsSonore de la classe ParametreProfile.
     */
    @Test
    public void testGetVolumeEffetsSonore() {
        ParametreProfile parametreProfile = new ParametreProfile();
        assertEquals(0.5f, parametreProfile.getVolumeEffetsSonore());
    }

    /**
     * Test du constructeur de la classe ParametreProfile.
     */
    @Test
    public void testSetEffetVisuel() {
        ParametreProfile parametreProfile = new ParametreProfile();
        parametreProfile.setEffetVisuel(false);
        assertEquals(false, parametreProfile.getEffetVisuel());
    }

    /**
     * Test de la méthode setNiveauAide de la classe ParametreProfile.
     */
    @Test
    public void testSetNiveauAide() {
        ParametreProfile parametreProfile = new ParametreProfile();
        parametreProfile.setNiveauAide(1);
        assertEquals(1, parametreProfile.getNiveauAide());

        assertThrows(IllegalArgumentException.class, () -> parametreProfile.setNiveauAide(-1), "Le niveau d'aide ne peut pas être négatif");
        assertThrows(IllegalArgumentException.class, () -> parametreProfile.setNiveauAide(3), "Le niveau d'aide ne peut pas être supérieur à 2");
    }

    /**
     * Test de la méthode setVolumeEffetsSonore de la classe ParametreProfile.
     */
    @Test
    public void testSetVolumeEffetsSonore() {
        ParametreProfile parametreProfile = new ParametreProfile();
        parametreProfile.setVolumeEffetsSonore(0.7f);
        assertEquals(0.7f, parametreProfile.getVolumeEffetsSonore());

        assertThrows(IllegalArgumentException.class, () -> parametreProfile.setVolumeEffetsSonore(-0.1f), "Le volume des effets sonores ne peut pas être négatif");
        assertThrows(IllegalArgumentException.class, () -> parametreProfile.setVolumeEffetsSonore(1.1f), "Le volume des effets sonores ne peut pas être supérieur à 1");
    }

    /**
     * Test de la méthode serialize de la classe ParametreProfile.
     * 
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    @Test
    public void testSerialize() throws IOException {
        ParametreProfile parametreProfile = new ParametreProfile();
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = new ObjectMapper().getFactory().createGenerator(writer);
        
        parametreProfile.serialize(jsonGenerator, null);
        jsonGenerator.flush();
        
        String json = writer.toString();
        assertTrue(json.contains("\"niveauAide\":0"));
        assertTrue(json.contains("\"volumeEffetsSonore\":0.5"));
        assertTrue(json.contains("\"effetVisuel\":true"));
    }

    /**
     * Test de la méthode serializeWithType de la classe ParametreProfile.
     * 
     * @throws IOException si une erreur d'entrée/sortie survient
     */
    @Test
    public void testSerializeWithType() throws IOException {
        ParametreProfile parametreProfile = new ParametreProfile();
        StringWriter writer = new StringWriter();
        JsonGenerator jsonGenerator = new ObjectMapper().getFactory().createGenerator(writer);
        
        parametreProfile.serializeWithType(jsonGenerator, null, null);
        jsonGenerator.flush();
        
        String json = writer.toString();
        assertTrue(json.contains("\"niveauAide\":0"));
        assertTrue(json.contains("\"volumeEffetsSonore\":0.5"));
        assertTrue(json.contains("\"effetVisuel\":true"));
    }
}
