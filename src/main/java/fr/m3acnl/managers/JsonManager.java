package fr.m3acnl.managers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Classe permettant d'extraire et de sauvegarder des données au format JSON.
 * 
 * @author PUREN Mewen
 */
public class JsonManager {

    /**
     * Chemin du fichier JSON contenant les grilles de jeu.
     */
    private static String niveau = "/META-INF/grilles.json";
    
    /**
     * Classe interne permettant de stocker les informations d'une grille.
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
            JsonNode rootNode = mapper.readTree(getClass().getResourceAsStream(niveau));
            
            JsonNode difficulteNode = rootNode.get(difficulte.toLowerCase());
            if (difficulteNode != null && index < difficulteNode.size()) {
                JsonNode grilleNode = difficulteNode.get(index);
                return new GrilleInfo(
                    grilleNode.get("taille").asInt(),
                    grilleNode.get("serialise").asText()
                );
            }
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
