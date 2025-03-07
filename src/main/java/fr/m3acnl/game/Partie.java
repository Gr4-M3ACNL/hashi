package fr.m3acnl.game;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.m3acnl.game.logique.Jeu;
import fr.m3acnl.managers.SauvegardePartieManager;
import fr.m3acnl.managers.SauvegardePartieManager.JeuEnCour;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;


/**
 * Classe Partie pour une représentation de la partie et gérer la partie.
 * elle a la gestion du chrono et de la sauvegarde.
 * 
 * @author PUREN Mewen
 * 
 * @see Jeu
 * @see Difficulte
 * @see JsonSerializable
 * @see SauvegardePartieManager
 */
public class Partie implements JsonSerializable {
    
    /**
     * Le jeu en cours.
     */
    private Jeu jeu;

    /**
     * Le chrono de la partie.
     */
    private Instant chrono;

    /**
     * La difficulté de la partie.
     */
    private Difficulte difficulte;

    /**
     * Constructeur pour une instance d'objet Partie.
     * Crée ou charge une partie en fonction de la difficulté.
     * 
     * @param difficulte la difficulté de la partie
     */
    public Partie(Difficulte difficulte) {
        this.difficulte = difficulte;
        JeuEnCour jeuEnCour = SauvegardePartieManager.getInstance().charger(difficulte);
        jeu = jeuEnCour.jeu();
        chrono = Instant.now().minusMillis(jeuEnCour.chrono());
    }

    /**
     * Méthode pour obtenir la durée du chronomètre.
     *
     * @return la durée du chronomètre
     */
    public Duration getChronoDuration() {
        return Duration.between(chrono, Instant.now());
    }

    /**
     * Méthode pour sérialiser une partie.
     * 
     * @param gen générateur de JSON
     * @param serializers fournisseur de sérialisation
     * 
     * @throws IOException si une erreur d'entrée/sortie survient
     * 
     * @see JsonSerializable#serialize
     */
    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode partieNode = mapper.createObjectNode();
        partieNode.set("CoupJouer", mapper.valueToTree(jeu.getCoupsJouer()));
        partieNode.set("CoupJouerBuff", mapper.valueToTree(jeu.getCoupsJouerBuff()));
        // TODO: Ajouter le checkpoint de la partie
        // TODO: Ajouter la pile de validation
        partieNode.put("Chrono", getChronoDuration().toMillis());

        gen.writeStartObject();
        gen.writeObjectField("partie", partieNode);
        gen.writeEndObject();
    }

    /**
     * Méthode pour sérialiser une partie avec un type.
     * 
     * @param gen générateur de JSON
     * @param serializers fournisseur de sérialisation
     * @param typeSer sérialiseur de type
     * 
     * @throws IOException si une erreur d'entrée/sortie survient
     * 
     * @see #serialize
     * @see JsonSerializable#serializeWithType
     */
    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

    /**
     * Méthode pour ajouter un malus au chrono.
     * 
     * @param malusEnMillisecondes le malus à ajouter en millisecondes
     */
    protected void addMalus(long malusEnMillisecondes) {
        chrono = chrono.minusMillis(malusEnMillisecondes);
    }

    /**
     * Méthode pour obtenir le jeu de la partie.
     * 
     * @return le jeu de la partie
     */
    public Jeu getJeu() {
        return jeu;
    }

    /**
     * Méthode sauvegardant la partie.
     */
    public void sauvegarde() {
        SauvegardePartieManager.getInstance().sauvegarde(this);
    }

    /**
     * Méthode pour obtenir la difficulté de la partie.
     * 
     * @return la difficulté de la partie
     */
    public Difficulte getDifficulte() {
        return difficulte;
    }    
}
