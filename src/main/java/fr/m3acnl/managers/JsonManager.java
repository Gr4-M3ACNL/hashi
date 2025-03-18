package fr.m3acnl.managers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import fr.m3acnl.game.Difficulte;
import fr.m3acnl.game.Partie;
import fr.m3acnl.profile.Profile;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Classe permettant d'extraire et de sauvegarder des données au format JSON.
 *
 * @author PUREN Mewen
 */
public class JsonManager {

    // ======================== Attributs ========================
    /**
     * Chemin du fichier JSON contenant les grilles de jeu. Ce fichier est situé
     * dans le dossier resources(dans le jar).
     */
    private static String fichierNiveau = "/META-INF/grilles.json";

    /*
     * Chemin du fichier JSON contenant les aides de jeu.
     * Ce fichier est situé dans le dossier resources(dans le jar).
     * TODO: créer le fichier d'aide (avec quelques exemples)
     */
    //private static String fichierAide = "/META-INF/aides.json";
    /**
     * Le nom du fichier contenant les profils. Ce fichier est situé dans le
     * dossier de sauvegarde(sur le client).
     */
    private static String nomFichierProfils = "profils.json";

    /**
     * Le nom du fichier contenant les parties en cours. Ce fichier est situé
     * dans le dossier de sauvegarde(sur le client).
     */
    private static String nomFichierPartie = "partie.json";

    /**
     * Classe interne permettant de stocker les informations d'une grille.
     *
     * @param taille Taille de la grille
     * @param serialise Grille sérialisée sous forme de tableau 2D de Double
     */
    public record GrilleInfo(int taille, Double[][] serialise) {

    }

    /**
     * Constructeur de la classe JsonManager.
     */
    public JsonManager() {
    }

    // ======================== Getter ========================
    /**
     * Récupère les informations d'une grille de jeu.
     *
     * @param difficulte Difficulté de la grille
     * @param index Index de la grille
     * @return Les informations de la grille
     */
    public GrilleInfo getGrilleInfo(Difficulte difficulte, int index) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(getClass().getResourceAsStream(fichierNiveau));

            JsonNode difficulteNode = rootNode.get(difficulte.toString());
            if (difficulteNode != null && index < difficulteNode.size()) {
                JsonNode grilleNode = difficulteNode.get(index);
                return new GrilleInfo(
                        grilleNode.get("taille").asInt(),
                        mapper.convertValue(grilleNode.get("serialise"), Double[][].class));
            }
            throw new IllegalArgumentException(
                    "La grille n'existe pas (difficulté : " + difficulte + ", index : " + index + ")");
        } catch (Exception e) {
            throw new IllegalArgumentException(
                    "La grille n'existe pas (difficulté : " + difficulte + ", index : " + index + ")");
        }
    }

    /**
     * Récupère le nombre de grilles de jeu pour une difficulté donnée.
     *
     * @param difficulte Difficulté des grilles
     * @return Le nombre de grilles
     * @throws IllegalArgumentException si la difficulté n'existe pas
     */
    public int getNbGrilles(Difficulte difficulte) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(getClass().getResourceAsStream(fichierNiveau));

            JsonNode difficulteNode = rootNode.get(difficulte.toString());
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
    public List<String> getListeDifficultes() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(getClass().getResourceAsStream(fichierNiveau));

            List<String> difficultes = new ArrayList<String>(); // ArrayList pour pouvoir utiliser toArray

            rootNode.fieldNames().forEachRemaining(difficultes::add); // Ajoute chaque difficulté à la liste
            return difficultes;
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<String>();
        }
    }

    /**
     * Récupère la liste des profils sauvegardés.
     *
     * @return La liste des profils
     */
    protected List<String> getListeProfils() {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Path cheminFichier = SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve(nomFichierProfils);

            if (!cheminFichier.toFile().exists()) {
                return new ArrayList<String>();
            }

            JsonNode rootNode = mapper.readTree(cheminFichier.toFile());

            if (!rootNode.has("profils")) {
                return new ArrayList<String>();
            }

            JsonNode profilsNode = rootNode.get("profils");

            List<String> profils = new ArrayList<String>();
            for (JsonNode profilNode : profilsNode) {
                profils.add(profilNode.get("nom").asText());
            }

            return profils;

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible de charger les profils");
        }
    }

    // ======================== Méthodes de sauvegarde ========================
    /**
     * Sauvegarde un objet Profile dans le fichier de profils.
     *
     * @param profile Profil à sauvegarder
     * @throws RuntimeException si le profil ne peut pas être sauvegardé
     *
     * @see Profile
     * @see ObjectMapper
     */
    protected void sauvegarderProfil(Profile profile) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Path cheminFichier = SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve(nomFichierProfils);

            // Création ou lecture du fichier JSON
            JsonNode rootNode;
            if (!cheminFichier.toFile().exists()) {
                Files.createFile(cheminFichier);
                rootNode = mapper.createObjectNode();
                ((ObjectNode) rootNode).putArray("profils");
            } else {
                rootNode = mapper.readTree(cheminFichier.toFile());
            }

            // Récupération du nœud des profils
            ArrayNode profilsNode = (ArrayNode) rootNode.get("profils");

            // Vérification si le profil existe déjà
            boolean profilExistant = false;
            for (JsonNode profilNode : profilsNode) {
                if (profilNode.get("nom").asText().equals(profile.getNom())) {
                    ((ObjectNode) profilNode).set("profil", mapper.valueToTree(profile));
                    profilExistant = true;
                    break;
                }
            }

            // Ajout du nouveau profil si non existant
            if (!profilExistant) {
                ObjectNode nouveauProfilNode = mapper.createObjectNode();
                nouveauProfilNode.put("nom", profile.getNom());
                nouveauProfilNode.set("profil", mapper.valueToTree(profile));
                profilsNode.add(nouveauProfilNode);
            }

            // Écriture dans le fichier
            mapper.writeValue(cheminFichier.toFile(), rootNode);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible de sauvegarder le profil");
        }
    }

    /**
     * Charge un profil à partir du fichier de profils.
     *
     * @param nom Nom du profil à charger
     * @return Le profil chargé
     * @throws RuntimeException si le profil ne peut pas être chargé
     *
     * @see Profile
     * @see ObjectMapper
     */
    protected Profile chargerProfil(String nom) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Path cheminFichier = SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve(nomFichierProfils);
            if (!cheminFichier.toFile().exists()) {
                return null;
            }
            JsonNode rootNode = mapper.readTree(cheminFichier.toFile());

            ArrayNode profilsNode = (ArrayNode) rootNode.get("profils");
            if (profilsNode == null) {
                return null;
            }

            JsonNode profilNode = null;
            for (JsonNode node : profilsNode) {
                if (node.get("nom").asText().equals(nom)) {
                    profilNode = node.get("profil");
                    break;
                }
            }
            if (profilNode == null) {
                return null;
            }

            return mapper.treeToValue(profilNode.get(nom), Profile.class);

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible de charger le profil");
        }
    }

    /**
     * Supprime un profil du fichier de profils.
     *
     * @param nom Nom du profil à supprimer
     * @throws RuntimeException si le profil ne peut pas être supprimé
     */
    protected void supprimerProfil(String nom) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Path cheminFichier = SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve(nomFichierProfils);
            JsonNode rootNode = mapper.readTree(cheminFichier.toFile());

            ArrayNode profilsNode = (ArrayNode) rootNode.get("profils");
            if (profilsNode == null) {
                return;
            }

            int index = 0;
            for (JsonNode profilNode : profilsNode) {
                if (profilNode.get("nom").asText().equals(nom)) {
                    profilsNode.remove(index);
                    break;
                }
                index++;
            }

            mapper.writeValue(cheminFichier.toFile(), rootNode);

            // Supprime le fichier de sauvegarde de la partie
            Path cheminFichierPartie = SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve(nomFichierPartie);
            if (cheminFichierPartie.toFile().exists()) {
                JsonNode rootNodePartie = mapper.readTree(cheminFichierPartie.toFile());
                if (rootNodePartie.has(nom)) {
                    ((ObjectNode) rootNodePartie).remove(nom);
                    mapper.writeValue(cheminFichierPartie.toFile(), rootNodePartie);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible de supprimer le profil");
        }
    }

    /**
     * Sauvegarde une partie dans le fichier de parties. l'organisation du
     * fichier est la suivante : { "nomProfil1": { "difficulte1": {Infos de la
     * partie}, "difficulte2": {Infos de la partie} }, "nomProfil2": {
     * "difficulte1": {Infos de la partie}, "difficulte2": {Infos de la partie},
     * "difficulte3": {Infos de la partie} } }
     *
     *
     * @param partie Partie à sauvegarder
     * @param nomProfil Nom du profil associé à la partie
     *
     * @throws RuntimeException si la partie ne peut pas être sauvegardée
     *
     * @see Partie
     */
    protected void sauvegardePartie(Partie partie, String nomProfil) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Path cheminFichier = SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve(nomFichierPartie);

            // Création ou lecture du fichier JSON
            JsonNode rootNode;
            if (!cheminFichier.toFile().exists()) {
                Files.createFile(cheminFichier);
                rootNode = mapper.createObjectNode();
                mapper.writeValue(cheminFichier.toFile(), rootNode);
            }
            rootNode = mapper.readTree(cheminFichier.toFile());

            // Création ou récupération du nœud du profil
            if (!rootNode.has(nomProfil)) {
                ((ObjectNode) rootNode).putObject(nomProfil);
            }

            // ajoute ou met à jour le nœud de la partie dans le profil
            JsonNode profilNode = rootNode.get(nomProfil);
            ((ObjectNode) profilNode).set(partie.getDifficulte().toString(), mapper.valueToTree(partie));

            mapper.writeValue(cheminFichier.toFile(), rootNode);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible de sauvegarder la partie");
        }
    }

    /**
     * Charge une partie à partir du fichier de parties.
     * <p>
     * si la partie n'existe pas, retourne null
     * </p>
     *
     * @param nomProfil Nom du profil associé à la partie
     * @param difficulte Difficulté de la partie
     * @return La partie chargée sous forme de JsonNode
     * @throws RuntimeException si la partie ne peut pas être chargée
     *
     * @see Partie
     * @see JsonNode
     */
    protected JsonNode chargerPartie(String nomProfil, Difficulte difficulte) {
        try {
            ObjectMapper mapper = new ObjectMapper();

            Path cheminFichier = SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve(nomFichierPartie);
            if (!cheminFichier.toFile().exists()) {
                return null;
            }
            JsonNode rootNode = mapper.readTree(cheminFichier.toFile());
            if (Objects.isNull(rootNode) || !rootNode.has(nomProfil)) {
                return null;
            }
            JsonNode profilNode = rootNode.get(nomProfil);
            if (profilNode == null) {
                return null;
            }
            JsonNode partieNode = profilNode.get(difficulte.toString());
            if (Objects.isNull(partieNode)) {
                return null;
            }
            return partieNode.get("partie");

        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible de charger la partie");
        }
    }

    /**
     * Supprime une partie du fichier de parties.
     *
     * @param nomProfil Nom du profil associé à la partie
     * @param difficulte Difficulté de la partie
     * @throws RuntimeException si la partie ne peut pas être supprimée
     */
    protected void supprimerPartie(String nomProfil, Difficulte difficulte)
            throws IllegalArgumentException, RuntimeException {
        try {
            ObjectMapper mapper = new ObjectMapper();
            Path cheminFichier = SauvegardeManager.getInstance().getRepertoireSauvegarde().resolve(nomFichierPartie);
            JsonNode rootNode = mapper.readTree(cheminFichier.toFile());
            if (rootNode.has(nomProfil)) {
                JsonNode profilNode = rootNode.get(nomProfil);
                if (profilNode.has(difficulte.toString())) {
                    ((com.fasterxml.jackson.databind.node.ObjectNode) profilNode).remove(difficulte.toString());
                    mapper.writeValue(cheminFichier.toFile(), rootNode);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Impossible de supprimer la partie");
        }
    }
}
