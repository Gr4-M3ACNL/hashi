package fr.m3acnl.profile;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import fr.m3acnl.game.Difficulte;
import fr.m3acnl.managers.JsonManager;
import java.io.IOException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;


/**
 * Classe qui permet de stocker les temps des parties jouées par difficulté pour un joueur.
 */
public class HistoriquePartieProfile implements JsonSerializable {

    /**
     * Taille maximale de l'historique des parties.
     */
    private int tailleHistorique = 10;

    /**
     * Liste des temps des parties jouées en mode facile.
     */
    private List<Time> facile;
    /**
     * Liste des temps des parties jouées en mode moyen.
     */
    private List<Time> moyen;
    /**
     * Liste des temps des parties jouées en mode difficile.
     */
    private List<Time> difficile;
    /**
     * Liste des temps des parties jouées en mode expert.
     */
    private List<Time> expert;

    /**
     * Index de la grille de la dernière partie finie en facile.
     */
    private int indexFacile;
    /**
     * Index de la grille de la dernière partie finie en moyen.
     */
    private int indexMoyen;
    /**
     * Index de la grille de la dernière partie finie en difficile.
     */
    private int indexDifficile;
    /**
     * Index de la grille de la dernière partie finie en expert.
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

    /**
     * Retourne la liste des temps des parties jouées en mode facile.
     * 
     * @return la liste des temps des parties jouées en mode facile.
     */
    public List<Time> getFacile() {
        return this.facile;
    }
    
    /**
     * Retourne la liste des temps des parties jouées en mode moyen.
     * 
     * @return la liste des temps des parties jouées en mode moyen.
     */
    public List<Time> getMoyen() {
        return this.moyen;
    }

    /**
     * Retourne la liste des temps des parties jouées en mode difficile.
     * 
     * @return la liste des temps des parties jouées en mode difficile.
     */
    public List<Time> getDifficile() {
        return this.difficile;
    }

    /**
     * Retourne la liste des temps des parties jouées en mode expert.
     * 
     * @return la liste des temps des parties jouées en mode expert.
     */
    public List<Time> getExpert() {
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
    public List<Time> getTemps(Difficulte difficulte) throws IllegalArgumentException {
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

    /**
     * Ajoute un temps de partie à l'historique.
     * 
     * @param difficulte la difficulté de la partie.
     * @param temps le temps de la partie.
     * 
     * @throws IllegalArgumentException si la difficulté est inconnue.
     */
    public void ajouterTemps(Difficulte difficulte, Time temps) throws IllegalArgumentException {
        JsonManager jsonManager = new JsonManager();
        switch (difficulte) {
            case facile:
                this.facile.add(temps);
                if (this.facile.size() > tailleHistorique) {
                    this.facile.remove(0);
                }
                this.indexFacile++;
                this.indexFacile %= jsonManager.getNbGrilles(Difficulte.facile);
                break;
            case moyen:
                this.moyen.add(temps);
                if (this.moyen.size() > tailleHistorique) {
                    this.moyen.remove(0);
                }
                this.indexMoyen++;
                this.indexMoyen %= jsonManager.getNbGrilles(Difficulte.moyen);
                break;
            case difficile:
                this.difficile.add(temps);
                if (this.difficile.size() > tailleHistorique) {
                    this.difficile.remove(0);
                }
                this.indexDifficile++;
                this.indexDifficile %= jsonManager.getNbGrilles(Difficulte.difficile);
                break;
            case expert:
                this.expert.add(temps);
                if (this.expert.size() > tailleHistorique) {
                    this.expert.remove(0);
                }
                this.indexExpert++;
                this.indexExpert %= jsonManager.getNbGrilles(Difficulte.expert);
                break;
            default:
                throw new IllegalArgumentException("Difficulté inconnue");
        }
    }

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
        for (Time time : this.facile) {
            gen.writeString(time.toString());
        }
        gen.writeEndArray();
        gen.writeNumberField("indexFacile", indexFacile);
        
        gen.writeFieldName("moyen");
        gen.writeStartArray();
        for (Time time : this.moyen) {
            gen.writeString(time.toString());
        }
        gen.writeEndArray();
        gen.writeNumberField("indexMoyen", indexMoyen);
        
        gen.writeFieldName("difficile");
        gen.writeStartArray();
        for (Time time : this.difficile) {
            gen.writeString(time.toString());
        }
        gen.writeEndArray();
        gen.writeNumberField("indexDifficile", indexDifficile);
        
        gen.writeFieldName("expert");
        gen.writeStartArray();
        for (Time time : this.expert) {
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

    /**
     * Méthode pour modifier la liste des temps des parties jouées en mode facile.
     * Utilisé pour la deserialization.
     * 
     * @param facile la liste des temps des parties jouées en mode facile.
     */
    protected void setFacile(List<Time> facile) {
        this.facile = new ArrayList<Time>(facile);
    }

    /**
     * Méthode pour modifier la liste des temps des parties jouées en mode moyen.
     * Utilisé pour la deserialization.
     * 
     * @param moyen la liste des temps des parties jouées en mode moyen.
     */
    protected void setMoyen(List<Time> moyen) {
        this.moyen = new ArrayList<Time>(moyen);
    }

    /**
     * Méthode pour modifier la liste des temps des parties jouées en mode difficile.
     * Utilisé pour la deserialization.
     * 
     * @param difficile la liste des temps des parties jouées en mode difficile.
     */
    protected void setDifficile(List<Time> difficile) {
        this.difficile = new ArrayList<Time>(difficile);
    }

    /**
     * Méthode pour modifier la liste des temps des parties jouées en mode expert.
     * Utilisé pour la deserialization.
     * 
     * @param expert la liste des temps des parties jouées en mode expert.
     */
    protected void setExpert(List<Time> expert) {
        this.expert = new ArrayList<Time>(expert);
    }

}
