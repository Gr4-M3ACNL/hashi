package fr.m3acnl.managers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;

/**
 * Classe permettant d'extraire et de sauvegarder des données au format JSON.
 * 
 * @author PUREN Mewen
 */
public class JsonManager {

    /**
     * Chemin du fichier JSON contenant les grilles de jeu.
     */
    private static String fichierNiveau = "/META-INF/grilles.json";

    /**
     * Le nom du fichier contenant les profils.
     */
    private static String NomfichierProfils = "profils.json";

    /**
     * Chemin du fichier JSON contenant les profils.
     */
    private static String fichierProfils = SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve(NomfichierProfils).toString();
    
    /**
     * Classe interne permettant de stocker les informations d'une grille.
     * @param taille Taille de la grille
     * @param serialise Grille sérialisée
     */
    public record GrilleInfo(int taille, String serialise) {}

    /**
     * Constructeur de la classe JsonManager.
     */
    public JsonManager() {}
    
    /**
     * Récupère les informations d'une grille de jeu.
     * 
     * @param difficulte Difficulté de la grille
     * @param index Index de la grille
     * @return Les informations de la grille
     */
    public GrilleInfo getGrilleInfo(String difficulte, int index) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(getClass().getResourceAsStream(fichierNiveau));
            
            JsonNode difficulteNode = rootNode.get(difficulte.toLowerCase());
            if (difficulteNode != null && index < difficulteNode.size()) {
                JsonNode grilleNode = difficulteNode.get(index);
                return new GrilleInfo(
                    grilleNode.get("taille").asInt(),
                    grilleNode.get("serialise").asText()
                );
            }
            throw new IllegalArgumentException("La grille n'existe pas (difficulté : " + difficulte + ", index : " + index + ")");
        } catch (Exception e) {
            throw new IllegalArgumentException("La grille n'existe pas (difficulté : " + difficulte + ", index : " + index + ")");
        }
    }

    /**
     * Récupère le nombre de grilles de jeu pour une difficulté donnée.
     * 
     * @param difficulte Difficulté des grilles
     * @return Le nombre de grilles
     * @throws IllegalArgumentException si la difficulté n'existe pas
     */
    public int getNbGrilles(String difficulte) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(getClass().getResourceAsStream(fichierNiveau));
            
            JsonNode difficulteNode = rootNode.get(difficulte.toLowerCase());
            if (difficulteNode == null) {
                throw new IllegalArgumentException("La difficulté n'existe pas");
            }
            return difficulteNode.size();
        } catch (Exception e) {
            throw new IllegalArgumentException("La difficulté n'existe pas");
        }
    }

    /**
     * Récupère les différentes difficultés disponibles.
     * 
     * @return Les différentes difficultés
     */
    public ArrayList<String> getListeDifficultes() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(getClass().getResourceAsStream(fichierNiveau));

            ArrayList<String> difficultes = new ArrayList<String>(); // ArrayList pour pouvoir utiliser toArray

            rootNode.fieldNames().forEachRemaining(difficultes::add); // Ajoute chaque difficulté à la liste
            return difficultes;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    /**
     * Récupère le chemin du fichier contenant les profils.
     * 
     * @return Le chemin du fichier contenant les profils
     */
    public String getCheminProfils() {
        return fichierProfils;
    }

    /**
     * Récupère la liste des profils sauvegardés.
     * 
     * @return La liste des profils
     */
    public ArrayList<String> getListeProfils() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve(NomfichierProfils).toFile());

            ArrayList<String> profils = new ArrayList<String>(); // ArrayList pour pouvoir utiliser toArray

            rootNode.fieldNames().forEachRemaining(profils::add); // Ajoute chaque profil à la liste
            System.out.println(profils);
            return profils;
        } catch (Exception e) {
            throw new RuntimeException("Impossible de charger les profils");
        }
    }


}
