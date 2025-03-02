package fr.m3acnl.managers;

import fr.m3acnl.profile.Profile;
import java.util.List;

/**
 * Classe singleton permettant de gérer les profils.
 * ce mangeur permet de définir le profil actif, de le sauvegarder, de le supprimer et de le créer.
 */
public class ProfileManager {
    
    /**
     * Instance de la classe JsonManager.
     * utilisée pour charger et sauvegarder les profils.
     */
    private static final JsonManager jsonManager = new JsonManager();
    
    /**
     * Instance de la classe ProfileManager.
     * permet d'implémenter le singleton.
     */
    private static final ProfileManager instance = new ProfileManager();

    /**
     * Profil actif.
     */
    private Profile profileActif;

    /**
     * Constructeur de la classe ProfileManager.
     */
    private ProfileManager() {
    }

    /**
     * Retourne l'instance de la classe ProfileManager.
     *
     * @return l'instance de la classe ProfileManager
     */
    public static ProfileManager getInstance() {
        return instance;
    }

    /**
     * Retourne le profil actif.
     *
     * @return le profil actif
     */
    public Profile getProfileActif() {
        return profileActif;
    }

    /**
     * Définir le profil actif.
     * charge le profil en fonction de son nom.
     *
     * @param profile le profil actif
     */
    public void setProfileActif(String profile) {
        sauvegarder(); //sauvegarde le profil actif au cas ou.
        this.profileActif = jsonManager.chargerProfil(profile);
    }

    /**
     * Retourne la liste de nom des profils.
     *
     * @return la liste des profils
     */
    public List<String> listeProfils() {
        return jsonManager.getListeProfils();
    }
    
    /**
     * Sauvegarde le profil actif.
     */
    public void sauvegarder() {
        if (profileActif != null) {
            jsonManager.sauvegarderProfil(profileActif);
        }
    }

    /**
     * Supprime un profil.
     * a partir de son nom
     *
     * @param nom nom du profil à supprimer
     */
    public void supprimerProfil(String nom) {
        jsonManager.supprimerProfil(nom);
    }
    
    /**
     * Supprime un profil.
     * a partir de son objet
     *
     * @param profile profil à supprimer
     */
    public void supprimerProfil(Profile profile) {
        jsonManager.supprimerProfil(profile.getNom());
    }

    /**
     * Crée un profil.
     *
     * @param nom nom du profil à créer
     */
    public void creerProfil(String nom) {
        //TODO: vérifier si un profil existe déjà avec ce nom
        profileActif = new Profile(nom);
        sauvegarder();
    }

}
