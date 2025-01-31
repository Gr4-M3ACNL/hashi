package fr.m3acnl.profile;


/**
 * Profil d'un utilisateur.
 * 
 * @author PUREN Mewen
 */
public class Profile {
    
    /**
     * Nom du profil.
     */
    private String name;

    /**
     * Paramètres du profil.
     */
    private final ParametreProfile parametre;

    /**
     * Constructeur d'un profil.
     *
     * @param name nom du profil
     */
    public Profile(String name) {
        this.name = name;
        this.parametre = new ParametreProfile();
    }

    /**
     * Méthode pour connaître le nom du profil.
     *
     * @return le nom du profil
     */
    public String getName() {
        return this.name;
    }

    /**
     * Méthode pour connaître les paramètres du profil.
     *
     * @return les paramètres du profil
     */
    public ParametreProfile getParametre() {
        return this.parametre;
    }
}
