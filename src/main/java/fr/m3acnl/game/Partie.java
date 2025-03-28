package fr.m3acnl.game;

import java.io.IOException;
import java.time.Duration;
import java.time.Instant;

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

/**
 * Classe Partie pour la logique et la gestion d'une partie. 
 * Cette classe s'occupe aussi de la gestion du chronomètre et de la sauvegarde.
 *
 * @author PUREN Mewen
 *
 * @see Jeu
 * @see Difficulte
 * @see JsonSerializable
 * @see SauvegardePartieManager
 */
public class Partie implements JsonSerializable {

    // ==================== Attributs ====================
    /**
     * Le jeu en cours.
     */
    private final Jeu jeu;

    /**
     * Le chrono de la partie.
     */
    private Instant chrono;

    /**
     * La difficulté de la partie.
     */
    private final Difficulte difficulte;

    /**
     * Indique si la partie est en pause.
     * <br>
     * Vaut true si la partie est en pause, false sinon.
     * <br>
     * Quand la partie est en pause le chronomètre est à calculer entre le chronomètre et l'heure zéro.
     * <br>
     * Quand la partie n'est pas en pause le chronomètre est a calculer entre le chronomètre et l'heure actuelle.
     */
    private Boolean pause = false;

    /**
     * Constructeur pour une instance d'objet Partie. 
     * Crée ou charge une partie en fonction de la difficulté.
     *
     * @param difficulte la difficulté de la partie
     * @see SauvegardePartieManager#charger(Difficulte)
     */
    public Partie(Difficulte difficulte) {
        this.difficulte = difficulte;
        // cette fonction va créer une nouvelle partie ou charger une partie existante
        JeuEnCour jeuEnCour = SauvegardePartieManager.getInstance().charger(difficulte);

        jeu = jeuEnCour.jeu();
        chrono = Instant.now().minusMillis(jeuEnCour.chrono());
    }

    // ==================== Getter ====================
    /**
     * Méthode pour obtenir la durée du chronomètre.
     *
     * @return la durée du chronomètre
     */
    public Duration getChronoDuration() {
        if (!pause) {
            // Si le chronomètre est en cours, on calcule la durée entre le chronomètre et l'heure
            return Duration.between(chrono, Instant.now());
        }
        // Si le chronomètre est en pause, on calcule la durée entre le chronomètre et l'heure zéro
        return Duration.between(Instant.EPOCH, chrono);
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
     * Méthode pour obtenir la difficulté de la partie.
     *
     * @return la difficulté de la partie
     */
    public Difficulte getDifficulte() {
        return difficulte;
    }

    // ==================== Action ====================
    /**
     * Méthode pour arrêter le chronomètre. Si le chronomètre est déjà en pause, il ne fait rien.
     */
    public void stopChrono() {
        if (!pause) {
            // On définit le chronomètre comme l'heure zéro plus la durée du chrono
            chrono = Instant.EPOCH.plusMillis(getChronoDuration().toMillis());
            pause = true;
            sauvegarde();
        }
    }

    /**
     * Méthode pour reprendre le chronomètre. Si le chronomètre n'est pas en
     * pause, il ne fait rien.
     */
    public void startChrono() {
        if (pause) {
            // On définit le chronomètre comme l'heure actuelle moins la durée du chronomètre
            chrono = Instant.now().minusMillis(getChronoDuration().toMillis());
            pause = false;
            sauvegarde();
        }
    }
    
    /**
     * Méthode pour ajouter un malus au chronomètre.
     *
     * @param secondes le malus à ajouter en secondes
     */
    public void addMalus(long secondes) {
        chrono = chrono.minusSeconds(secondes);
    }

    /**
     * Méthode sauvegardant la partie.
     */
    public void sauvegarde() {
        SauvegardePartieManager.getInstance().sauvegarde(this);
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
        ProfileManager.getInstance().getProfileActif().getHistoriquePartieProfile().ajouterTemps(difficulte,
                getChronoDuration());
        SauvegardePartieManager.getInstance().supprimer(difficulte);
        ProfileManager.getInstance().sauvegarder();
    }

    // ==================== Serialisation ====================
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

        // Ajouter les coups de retour arrière
        partieNode.set("CoupJouerBuff", mapper.valueToTree(jeu.getCoupsJouerBuff()));

        // Ajouter le checkpoint de la partie
        ArrayNode pointDeSauvegarde = mapper.createArrayNode();
        jeu.getPointDeSauvegarde().forEach(lien -> pointDeSauvegarde.add(lien.getIndex()));
        partieNode.set("PointDeSauvegarde", pointDeSauvegarde);

        // Ajouter le tableau de validation
        ArrayNode sauvegardeAutomatique = mapper.createArrayNode();
        jeu.getSauvegardeAutomatique().forEach(lien -> sauvegardeAutomatique.add(lien.getIndex()));
        partieNode.set("SauvegardeAutomatique", sauvegardeAutomatique);

        // Ajouter le chronomètre
        partieNode.put("Chrono", getChronoDuration().toMillis());

        // Ecrire la partie dans l'objet JSON
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
}
