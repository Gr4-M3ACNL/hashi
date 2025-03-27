package fr.m3acnl.profile;

import java.io.IOException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import fr.m3acnl.game.Difficulte;
import fr.m3acnl.managers.JsonManager;

/**
 * Classe qui permet de stocker les temps des parties jouées par difficulté pour
 * un joueur.
 */
public class HistoriquePartieProfile implements JsonSerializable {

    // ======================== Attributs ========================

    /**
     * Liste des temps des parties jouées en mode facile.
     */
    private List<Duration> facile;
    /**
     * Liste des temps des parties jouées en mode moyen.
     */
    private List<Duration> moyen;
    /**
     * Liste des temps des parties jouées en mode difficile.
     */
    private List<Duration> difficile;
    /**
     * Liste des temps des parties jouées en mode expert.
     */
    private List<Duration> expert;

    /**
     * Index de la grille de la dernière partie finie en facile. Ordonné dans l'ordre des partie.
     */
    private int indexFacile;
    /**
     * Index de la grille de la dernière partie finie en moyen. Ordonné dans l'ordre des partie.
     */
    private int indexMoyen;
    /**
     * Index de la grille de la dernière partie finie en difficile. Ordonné dans l'ordre des partie.
     */
    private int indexDifficile;
    /**
     * Index de la grille de la dernière partie finie en expert. Ordonné dans l'ordre des partie.
     */
    private int indexExpert;

    /**
     * Constructeur par défaut.
     */
    protected HistoriquePartieProfile() {
        this.facile = new ArrayList<>();
        this.moyen = new ArrayList<>();
        this.difficile = new ArrayList<>();
        this.expert = new ArrayList<>();
    }

    // ======================== Getter ========================
    /**
     * Retourne la liste des temps des parties jouées en mode facile.
     *
     * @return la liste des temps des parties jouées en mode facile.
     */
    public List<Duration> getFacile() {
        return this.facile;
    }

    /**
     * Retourne la liste des temps des parties jouées en mode moyen.
     *
     * @return la liste des temps des parties jouées en mode moyen.
     */
    public List<Duration> getMoyen() {
        return this.moyen;
    }

    /**
     * Retourne la liste des temps des parties jouées en mode difficile.
     *
     * @return la liste des temps des parties jouées en mode difficile.
     */
    public List<Duration> getDifficile() {
        return this.difficile;
    }

    /**
     * Retourne la liste des temps des parties jouées en mode expert.
     *
     * @return la liste des temps des parties jouées en mode expert.
     */
    public List<Duration> getExpert() {
        return this.expert;
    }

    /**
     * Retourne l'index de la grille de la dernière partie finie en facile.
     *
     * @return l'index de la grille de la dernière partie finie en facile.
     */
    public int getIndexFacile() {
        return this.indexFacile;
    }

    /**
     * Retourne l'index de la grille de la dernière partie finie en moyen.
     *
     * @return l'index de la grille de la dernière partie finie en moyen.
     */
    public int getIndexMoyen() {
        return this.indexMoyen;
    }

    /**
     * Retourne l'index de la grille de la dernière partie finie en difficile.
     *
     * @return l'index de la grille de la dernière partie finie en difficile.
     */
    public int getIndexDifficile() {
        return this.indexDifficile;
    }

    /**
     * Retourne l'index de la grille de la dernière partie finie en expert.
     *
     * @return l'index de la grille de la dernière partie finie en expert.
     */
    public int getIndexExpert() {
        return this.indexExpert;
    }

    /**
     * Retourne la liste des temps des parties jouées en fonction de la difficulté.
     *
     * @param difficulte la difficulté des parties.
     * @return la liste des temps des parties jouées en fonction de la difficulté.
     *
     * @throws IllegalArgumentException si la difficulté est inconnue.
     */
    public List<Duration> getTemps(Difficulte difficulte) throws IllegalArgumentException {
        switch (difficulte) {
            case facile:
                return this.facile;
            case moyen:
                return this.moyen;
            case difficile:
                return this.difficile;
            case expert:
                return this.expert;
            default:
                throw new IllegalArgumentException("Difficulté inconnue");
        }
    }

    /**
     * Retourne l'index de la grille de la dernière partie finie en fonction de la difficulté.
     *
     * @param difficulte la difficulté des parties.
     * @return l'index de la grille de la dernière partie finie en fonction de la difficulté.
     *
     * @throws IllegalArgumentException si la difficulté est inconnue.
     */
    public int getIndex(Difficulte difficulte) throws IllegalArgumentException {
        switch (difficulte) {
            case facile:
                return this.indexFacile;
            case moyen:
                return this.indexMoyen;
            case difficile:
                return this.indexDifficile;
            case expert:
                return this.indexExpert;
            default:
                throw new IllegalArgumentException("Difficulté inconnue");
        }
    }

    /**
     * Retourne le nombre de parties jouées en fonction de la difficulté.
     *
     * @param difficulte la difficulté des parties.
     * @return le nombre de parties jouées en fonction de la difficulté.
     *
     * @throws IllegalArgumentException si la difficulté est inconnue.
     */
    public int getNbPartieDifficulte(Difficulte difficulte) throws IllegalArgumentException {
        switch (difficulte) {
            case facile:
                return this.facile.size();
            case moyen:
                return this.moyen.size();
            case difficile:
                return this.difficile.size();
            case expert:
                return this.expert.size();
            default:
                throw new IllegalArgumentException("Difficulté inconnue");
        }
    }

    // ======================== Setter ========================
    /**
     * Méthode pour modifier la liste des temps des parties jouées en mode facile.
     * Utilisé pour la deserialization.
     *
     * @param facile la liste des temps des parties jouées en mode facile.
     */
    protected void setFacile(List<String> facile) {
        this.facile = new ArrayList<>();
        for (String time : facile) {
            this.facile.add(Duration.parse(time));
        }
    }

    /**
     * Méthode pour modifier la liste des temps des parties jouées en mode moyen.
     * Utilisé pour la deserialization.
     *
     * @param moyen la liste des temps des parties jouées en mode moyen.
     */
    protected void setMoyen(List<String> moyen) {
        this.moyen = new ArrayList<>();
        for (String time : moyen) {
            this.moyen.add(Duration.parse(time));
        }
    }

    /**
     * Méthode pour modifier la liste des temps des parties jouées en mode difficile.
     * Utilisé pour la deserialization.
     *
     * @param difficile la liste des temps des parties jouées en mode difficile.
     */
    protected void setDifficile(List<String> difficile) {
        this.difficile = new ArrayList<>();
        for (String time : difficile) {
            this.difficile.add(Duration.parse(time));
        }
    }

    /**
     * Méthode pour modifier la liste des temps des parties jouées en mode expert.
     * Utilisé pour la deserialization.
     *
     * @param expert la liste des temps des parties jouées en mode expert.
     */
    protected void setExpert(List<String> expert) {
        this.expert = new ArrayList<>();
        for (String time : expert) {
            this.expert.add(Duration.parse(time));
        }
    }

    /**
     * Méthode pour modifier l'index de la grille de la dernière partie finie en facile.
     * Utilisé pour la deserialization.
     *
     * @param indexFacile l'index de la grille de la dernière partie finie en facile.
     */
    protected void setIndexFacile(int indexFacile) {
        this.indexFacile = indexFacile;
    }

    /**
     * Méthode pour modifier l'index de la grille de la dernière partie finie en moyen.
     * Utilisé pour la deserialization.
     *
     * @param indexMoyen l'index de la grille de la dernière partie finie en moyen.
     */
    protected void setIndexMoyen(int indexMoyen) {
        this.indexMoyen = indexMoyen;
    }

    /**
     * Méthode pour modifier l'index de la grille de la dernière partie finie en difficile.
     * Utilisé pour la deserialization.
     *
     * @param indexDifficile l'index de la grille de la dernière partie finie en difficile.
     */
    protected void setIndexDifficile(int indexDifficile) {
        this.indexDifficile = indexDifficile;
    }

    /**
     * Méthode pour modifier l'index de la grille de la dernière partie finie en expert.
     * Utilisé pour la deserialization.
     *
     * @param indexExpert l'index de la grille de la dernière partie finie en expert.
     */
    protected void setIndexExpert(int indexExpert) {
        this.indexExpert = indexExpert;
    }

    /**
     * Ajoute un temps de partie à l'historique.
     *
     * @param difficulte la difficulté de la partie.
     * @param temps le temps de la partie.
     *
     * @throws IllegalArgumentException si la difficulté est inconnue.
     */
    public void ajouterTemps(Difficulte difficulte, Duration temps) throws IllegalArgumentException {
        JsonManager jsonManager = new JsonManager();
        switch (difficulte) {
            case facile:
                this.facile.add(temps);
                this.indexFacile++;
                this.indexFacile %= jsonManager.getNbGrilles(Difficulte.facile);
                break;
            case moyen:
                this.moyen.add(temps);
                this.indexMoyen++;
                this.indexMoyen %= jsonManager.getNbGrilles(Difficulte.moyen);
                break;
            case difficile:
                this.difficile.add(temps);
                this.indexDifficile++;
                this.indexDifficile %= jsonManager.getNbGrilles(Difficulte.difficile);
                break;
            case expert:
                this.expert.add(temps);
                this.expert.sort(Duration::compareTo);
                this.indexExpert++;
                this.indexExpert %= jsonManager.getNbGrilles(Difficulte.expert);
                break;
            default:
                throw new IllegalArgumentException("Difficulté inconnue");
        }
    }

    // ======================== Sérialisation ========================
    /**
     * Serialize les temps des parties jouées pour un format JSON.
     *
     * @param gen le générateur de JSON.
     * @param serializers le fournisseur de sérialiseurs.
     *
     * @throws IOException si une erreur d'entrée/sortie survient.
     * @see JsonSerializable#serialize
     */
    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();

        gen.writeFieldName("facile");
        gen.writeStartArray();
        for (Duration time : this.facile) {
            gen.writeString(time.toString());
        }
        gen.writeEndArray();
        gen.writeNumberField("indexFacile", indexFacile);

        gen.writeFieldName("moyen");
        gen.writeStartArray();
        for (Duration time : this.moyen) {
            gen.writeString(time.toString());
        }
        gen.writeEndArray();
        gen.writeNumberField("indexMoyen", indexMoyen);

        gen.writeFieldName("difficile");
        gen.writeStartArray();
        for (Duration time : this.difficile) {
            gen.writeString(time.toString());
        }
        gen.writeEndArray();
        gen.writeNumberField("indexDifficile", indexDifficile);

        gen.writeFieldName("expert");
        gen.writeStartArray();
        for (Duration time : this.expert) {
            gen.writeString(time.toString());
        }
        gen.writeEndArray();
        gen.writeNumberField("indexExpert", indexExpert);

        gen.writeEndObject();
    }

    /**
     * Serialize les temps des parties jouées pour un format JSON avec un type.
     *
     * @param gen le générateur de JSON.
     * @param serializers le fournisseur de sérialiseurs.
     * @param typeSer le sérialiseur de type.
     *
     * @throws IOException si une erreur d'entrée/sortie survient.
     * @see #serialize
     * @see JsonSerializable#serializeWithType
     */
    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }

}
