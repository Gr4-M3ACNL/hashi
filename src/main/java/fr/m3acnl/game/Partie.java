package fr.m3acnl.game;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.m3acnl.game.logique.Jeu;
import fr.m3acnl.managers.ProfileManager;
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

        // Ajoute la liste des coups joués
        partieNode.set("CoupJouer", mapper.valueToTree(jeu.getCoupsJouer()));
        
        // Ajouter les coup de retour arrière
        partieNode.set("CoupJouerBuff", mapper.valueToTree(jeu.getCoupsJouerBuff()));
        
        // Ajouter le checkpoint de la partie
        ArrayNode pointDeSauvegarde = mapper.createArrayNode();
        jeu.getPointDeSauvegarde().forEach(lien -> pointDeSauvegarde.add(lien.getIndex()));
        partieNode.set("PointDeSauvegarde", pointDeSauvegarde);
        
        // Ajouter le tableaux de validation
        ArrayNode sauvegardeAutomatique = mapper.createArrayNode();
        jeu.getSauvegardeAutomatique().forEach(lien -> sauvegardeAutomatique.add(lien.getIndex()));
        partieNode.set("SauvegardeAutomatique", sauvegardeAutomatique);

        // Ajouter le chrono
        partieNode.put("Chrono", getChronoDuration().toMillis());

        // écrie la partie dans l'objet JSON
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

    /**
     * Méthode pour terminer la partie.
     * 
     * @throws IllegalStateException si la partie n'est pas terminée
     */
    public void finPartie() {
        if (!jeu.gagner()) {
            throw new IllegalStateException("La partie n'est pas terminée");
        }
        ProfileManager.getInstance().getProfileActif().getHistoriquePartieProfile().ajouterTemps(difficulte, getChronoDuration());
        SauvegardePartieManager.getInstance().supprimer(difficulte);
        ProfileManager.getInstance().sauvegarder();
    }
}
