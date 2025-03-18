package fr.m3acnl.profile;

import org.junit.jupiter.api.Test;

import fr.m3acnl.Tests;
import fr.m3acnl.game.Difficulte;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.StringWriter;
import java.time.Duration;

/**
 * Classe de test de la classe Profile.
 * 
 * @see Profile
 * @see Tests
 */
public class ProfileTest extends Tests {

    /**
     * Méthode d'initialisation de la classe de test
     * 
     * @see Tests#printNameAtStart
     */
    @BeforeAll
    public static void initAll() {
        printNameAtStart(ProfileTest.class);
    }

    /**
     * Méthode de fin de la classe de test
     * 
     * @see Tests#printNameAtEnd
     */
    @AfterAll
    public static void endAll() {
        printNameAtEnd(ProfileTest.class);
    }

    /**
     * Constructeur de la classe de test ProfileTest.
     */
    ProfileTest() {
    }

    /**
     * Test de la méthode getnom de la classe Profile.
     * 
     * @see Profile#getNom
     */
    @Test
    public void testProfileNom() {
        String nom = "TestProfile";
        Profile profile = new Profile(nom);
        assertEquals(nom, profile.getNom());
    }

    /**
     * Test de la méthode getParametre de la classe Profile.
     * 
     * @see Profile#getParametre
     */
    @Test
    public void testProfileParametreNotNull() {
        Profile profile = new Profile("TestProfile");
        assertNotNull(profile.getParametre());
    }

    /**
     * Test de la méthode getParametre de la classe Profile.
     * 
     * @see Profile#getHistoriquePartieProfile
     */
    @Test
    public void testGetHistoriquePartieProfile() {
        Profile profile = new Profile("TestProfile");
        assertNotNull(profile.getHistoriquePartieProfile(),
                "L'historique des parties jouées par un joueur ne doit pas être null.");
    }

    /**
     * Test de la méthode serialize de la classe Profile.
     * 
     * @throws IOException si une erreur d'entrée/sortie survient
     * 
     * @see Profile#serialize
     */
    @Test
    public void testSerialize() throws IOException {
        Profile profile = new Profile("TestProfile");
        JsonGenerator jsonGenerator = new ObjectMapper().getFactory().createGenerator(new StringWriter());
        profile.serialize(jsonGenerator, null);
        jsonGenerator.close();
    }

    /**
     * Test de la méthode serializeWithType de la classe Profile.
     * 
     * @throws IOException si une erreur d'entrée/sortie survient
     * 
     * @see Profile#serializeWithType
     */
    @Test
    public void testSerializeWithType() throws IOException {
        Profile profile = new Profile("TestProfile");
        JsonGenerator jsonGenerator = new ObjectMapper().getFactory().createGenerator(new StringWriter());
        profile.serializeWithType(jsonGenerator, null, null);
        jsonGenerator.close();
    }

    /**
     * Test de la méthode setParametre de la classe Profile.
     * 
     * @see Profile#setParametre
     */
    @Test
    public void testSetParametre() {
        Profile profile = new Profile("TestProfile");
        ParametreProfile nouveauParam = new ParametreProfile();
        nouveauParam.setEffetVisuel(false);
        nouveauParam.setVolumeEffetsSonore(0.7f);
        nouveauParam.setNiveauAide(2);

        profile.setParametre(nouveauParam);

        assertEquals(false, profile.getParametre().getEffetVisuel());
        assertEquals(0.7f, profile.getParametre().getVolumeEffetsSonore());
        assertEquals(2, profile.getParametre().getNiveauAide());
    }

    /**
     * Test de la méthode setHistoriquePartieProfile de la classe Profile.
     * 
     * @see Profile#setHistoriquePartieProfile
     */
    @Test
    public void testSetHistoriquePartieProfile() {
        Profile profile = new Profile("TestProfile");
        HistoriquePartieProfile historiquePartieProfile = new HistoriquePartieProfile();
        historiquePartieProfile.ajouterTemps(Difficulte.facile, Duration.ofSeconds(0));
        historiquePartieProfile.ajouterTemps(Difficulte.moyen, Duration.ofSeconds(10));
        historiquePartieProfile.ajouterTemps(Difficulte.difficile, Duration.ofSeconds(100));
        historiquePartieProfile.ajouterTemps(Difficulte.expert, Duration.ofSeconds(1000));
        historiquePartieProfile.ajouterTemps(Difficulte.facile, Duration.ofSeconds(5));
        profile.setHistoriquePartieProfile(historiquePartieProfile);
        assertEquals(historiquePartieProfile.getFacile(), profile.getHistoriquePartieProfile().getFacile());
        assertEquals(historiquePartieProfile.getMoyen(), profile.getHistoriquePartieProfile().getMoyen());
        assertEquals(historiquePartieProfile.getDifficile(), profile.getHistoriquePartieProfile().getDifficile());
        assertEquals(historiquePartieProfile.getExpert(), profile.getHistoriquePartieProfile().getExpert());
    }

    /**
     * Test de la méthode setPseudo de la classe Profile.
     * 
     * @see Profile#Profile
     */
    @Test
    public void testSetPseudo() {
        Profile profile = new Profile("TestProfile");
        String nouveauPseudo = "NouveauPseudo";
        profile.setPseudo(nouveauPseudo);
        assertEquals(nouveauPseudo, profile.getNom());
    }
}