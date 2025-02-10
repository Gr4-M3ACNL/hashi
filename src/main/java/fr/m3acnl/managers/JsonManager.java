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
    private static String fichierProfils = "profils.json";
    
    /**
     * Constructeur de la classe JsonManager.
     */
    public JsonManager() {}
    
    /**
     * Classe interne permettant de stocker les informations d'une grille.
     * @param taille Taille de la grille
     * @param serialise Grille sérialisée
     */
    public record GrilleInfo(int taille, String serialise) {}
    
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
    public String[] getDifficultes() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(getClass().getResourceAsStream(fichierNiveau));

            ArrayList<String> difficultes = new ArrayList<>(); // ArrayList pour pouvoir utiliser toArray

            rootNode.fieldNames().forEachRemaining(difficultes::add); // Ajoute chaque difficulté à la liste

            return difficultes.toArray(new String[0]);
        } catch (Exception e) {
            e.printStackTrace();
            return new String[0];
        }
    }
}
