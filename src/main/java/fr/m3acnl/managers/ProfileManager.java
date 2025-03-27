package fr.m3acnl.managers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.time.Duration;

import fr.m3acnl.game.Difficulte;
import fr.m3acnl.profile.Profile;

/**
 * Classe singleton permettant de gérer les profils. ce mangeur permet de
 * définir le profil actif, de le sauvegarder, de le supprimer et de le créer.
 */
public class ProfileManager {

    // ======================== Attributs ========================
    /**
     * Instance de la classe JsonManager. utilisée pour charger et sauvegarder
     * les profils.
     */
    private static final JsonManager jsonManager = new JsonManager();

    /**
     * Instance de la classe ProfileManager. permet d'implémenter le singleton.
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

    // ======================== Classes internes ========================
    /**
     * Classe interne permettant de stocker le nom du profil et la durée de la partie.
     * 
     * @param nomProfil nom du profil
     * @param duree     durée de la partie
     */
    public record TempsPartie(String nomProfil, Duration duree) {

    }

    // ======================== Getter ========================
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
     * Retourne la liste des profils sous forme d'objets Profile.
     * 
     * @return la liste des profils sous forme d'objets Profile
     */
    private List<Profile> getListeProfilsObjet() {
        List<String> listeProfils = jsonManager.getListeProfils();
        List<Profile> listeProfilsObjet = new ArrayList<>();
        // On parcourt la liste des noms de profils et on les charge
        for (String nom : listeProfils) {
            Profile profile = jsonManager.chargerProfil(nom);
            if (profile != null) {
                listeProfilsObjet.add(profile);
            }
        }
        return listeProfilsObjet;
    }

    // ======================== Setter ========================
    /**
     * Définir le profil actif. charge le profil en fonction de son nom.
     *
     * @param profile le profil actif
     */
    public void setProfileActif(String profile) {
        sauvegarder(); //sauvegarde le profil actif au cas ou.
        this.profileActif = jsonManager.chargerProfil(profile);
    }

    // ======================== Méthodes ========================
    /**
     * Décharge le profil actif.
     */
    public void desactiverProfileActif() {
        sauvegarder();
        this.profileActif = null;
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
        if (Objects.nonNull(profileActif)) {
            jsonManager.sauvegarderProfil(profileActif);
        }
    }

    /**
     * Sauvegarde un profil.
     * 
     * @param profile le profil à sauvegarder
     */
    public void sauvegarder(Profile profile) {
        if (Objects.isNull(profile)) {
            throw new IllegalArgumentException("Le profil ne peut pas être null");
        }
        jsonManager.sauvegarderProfil(profile);
    }

    /**
     * Supprime un profil. A partir de son nom.
     *
     * @param nom nom du profil à supprimer
     */
    public void supprimerProfil(String nom) {
        if (Objects.nonNull(profileActif) && profileActif.getNom().equals(nom)) {
            profileActif = null;
        }
        jsonManager.supprimerProfil(nom);
    }

    /**
     * Supprime un profil. A partir de son objet.
     *
     * @param profile profil à supprimer
     */
    public void supprimerProfil(Profile profile) {
        if (Objects.nonNull(profileActif) && profileActif.equals(profile)) {
            profileActif = null;
        }
        jsonManager.supprimerProfil(profile.getNom());
    }

    /**
     * Crée un profil.
     * Cette méthode vérifie d'abord si le profil existe déjà. Si c'est le cas,
     * une exception est levée. Sinon, elle crée un nouveau profil et le sauvegarde.
     * Le profil crée devien le profil actif.
     *
     * @param nom nom du profil à créer
     * @throws IllegalArgumentException si le profil existe déjà
     */
    public void creerProfil(String nom) {
        List<String> listeProfils = listeProfils();

        // Vérifie si le profil existe déjà
        if (Objects.nonNull(listeProfils) && listeProfils.contains(nom)) {
            throw new IllegalArgumentException("Le profil existe déjà");
        }

        // Crée un nouveau profil
        Profile nouveauProfil = new Profile(nom);

        // Sauvegarde le nouveau profil via jsonManager
        jsonManager.sauvegarderProfil(nouveauProfil);

        // Charge la liste mise à jour
        listeProfils = listeProfils();

        // Définit le profil actif
        setProfileActif(nom);
    }

    /**
     * Retourne le classement des temps de jeu pour une difficulté donnée.
     * 
     * @param difficulte difficulté pour laquelle on veut le classement
     * @return une liste ordonée de tuple (nomProfil, tempsPartie)
     */
    public List<TempsPartie> getClassementTemps(Difficulte difficulte) {

        List<Profile> listeProfils = getListeProfilsObjet();
        List<TempsPartie> classement = new ArrayList<>();
        List<Duration> tempsParties;

        // On parcourt la liste des profils et on récupère les temps de jeu
        for (Profile profile : listeProfils) {
            tempsParties = profile.getHistoriquePartieProfile().getTemps(difficulte);
            for (Duration temps : tempsParties) {
                classement.add(new TempsPartie(profile.getNom(), temps));
            }
        }

        // On trie la liste des tuples (nomProfil, tempsPartie) par ordre croissant des temps
        // on utilse un ordre croissant car le temps de jeu le plus court est le meilleur
        classement.sort((o1, o2) -> {
            if (o1.duree.toMillis() == o2.duree.toMillis()) {
                return 0;
            } else if (o1.duree.toMillis() < o2.duree.toMillis()) {
                return -1;
            } else {
                return 1;
            }
        });

        // On garde les 5 meilleurs temps
        if (classement.size() > 5) {
            classement = classement.subList(0, 5);
        }
        // On remplit la liste avec des null si elle fait moins de 5
        while (classement.size() < 5) {
            classement.add(new TempsPartie("", null));
        }
        return classement;
    }

}
