package fr.m3acnl.managers;

import com.fasterxml.jackson.databind.JsonNode;
import fr.m3acnl.game.Difficulte;
import fr.m3acnl.game.Partie;
import fr.m3acnl.game.logique.Jeu;
import fr.m3acnl.managers.JsonManager.GrilleInfo;
import java.util.Objects;


/**
 * Classe permettant de gérer la sauvegarde et le chargement des parties.
 * Implémente le singleton.
 * 
 * @author PUREN Mewen
 * 
 * @see JsonManager
 * @see Jeu
 * @see Partie
 */
public class SauvegardePartieManager {

    /**
     * Instance de la classe JsonManager.
     * utilisée pour charger et sauvegarder les partie par rapport au profile.
     */
    private static final JsonManager jsonManager = new JsonManager();

    /**
     * Instance de la classe SauvegardePartieManager.
     * permet d'implémenter le singleton.
     */
    private static final SauvegardePartieManager instance = new SauvegardePartieManager();

    /**
     * Constructeur de la classe SauvegardePartieManager.
     */
    private SauvegardePartieManager() {}

    /**
     * Classe interne permettant de stocker une partie en cours avec son chrono.
     * utilisée pour charger une partie.
     * @param chrono le chrono de la partie
     * @param jeu le jeu en cours
     */
    public record JeuEnCour(long chrono, Jeu jeu) {}

    /**
     * Retourne l'instance de la classe SauvegardePartieManager.
     *
     * @return l'instance de la classe SauvegardePartieManager
     */
    public static SauvegardePartieManager getInstance() {
        return instance;
    }

    /**
     * Sauvegarde une partie.
     *
     * @param partie la partie à sauvegarder
     */
    public void sauvegarde(Partie partie) {
        jsonManager.sauvegardePartie(partie, ProfileManager.getInstance().getProfileActif().getNom());
    }

    /**
     * Charge une partie.
     * si une partie est deja en cours elle est relancée.
     * sinon une nouvelle partie est créée.
     *
     * @param difficulte la difficulté de la partie à charger
     * @return la partie chargée
     */
    public JeuEnCour charger(Difficulte difficulte) {
        GrilleInfo grilleInfo = jsonManager.getGrilleInfo(difficulte, ProfileManager.getInstance()
                                .getProfileActif().getHistoriquePartieProfile().getIndex(difficulte));
        Jeu jeu = new Jeu(grilleInfo.taille(), grilleInfo.serialise());
        
        JsonNode partie = jsonManager.chargerPartie(ProfileManager.getInstance().getProfileActif().getNom(), difficulte);
        
        if (Objects.isNull(partie)) { // Si la partie n'était pas dans le fichier de sauvegarde
            return new JeuEnCour(0, jeu); // On retourne une nouvelle partie
        }

        // On charge rempli les différentes piles de coups

        partie.get("CoupJouer").forEach(coup -> jeu.rejouer(coup.asInt()));
        partie.get("CoupJouerBuff").forEach(coup -> jeu.getCoupsJouerBuff().empiler(jeu.getPlateau().getListeLien().get(coup.asInt())));
        partie.get("SauvegardeAutomatique").forEach(coup -> jeu.getSauvegardeAutomatique().add(jeu.getPlateau().getListeLien().get(coup.asInt())));
        partie.get("PointDeSauvegarde").forEach(coup -> jeu.getPointDeSauvegarde().add(jeu.getPlateau().getListeLien().get(coup.asInt())));
        
        return new JeuEnCour(partie.get("Chrono").asLong(), jeu);
        
    }

    /**
     * Supprime la sauvegarde d'une partie.
     *
     * @param difficulte la difficulté de la partie à supprimer
     */
    public void supprimer(Difficulte difficulte) {
        jsonManager.supprimerPartie(ProfileManager.getInstance().getProfileActif().getNom(), difficulte);
    }

}
