package fr.m3acnl.profile;

/**
 * Profil d'un utilisateur.
 * 
 * @author PUREN Mewen
 */
public class ParametreProfile {

    /**
     * Limite du niveau d'aide.
     */
    private Integer niveauAide;
    /**
     * Volume des effets sonores.
     * Valeur entre 0 et 1.
     */
    private float volumeEffetsSonore;
    /**
     * Activation des effets visuels.
     */
    private Boolean effetVisuel;
    
    /**
     * Constructeur des paramètres de profil par défaut.
     */
    protected ParametreProfile() {
        this.niveauAide = 0;
        this.volumeEffetsSonore = 0.5f;
        this.effetVisuel = true;
    }

    /**
     * Méthode pour connaître la limite du niveau d'aide.
     *
     * @return le niveau d'aide
     */
    protected Integer getNiveauAide() {
        return this.niveauAide;
    }

    /**
     * Méthode pour connaître le volume des effets sonores.
     *
     * @return le volume des effets sonores
     */
    protected float getVolumeEffetsSonore() {
        return this.volumeEffetsSonore;
    }

    /**
     * Méthode pour connaître l'état des effets visuels.
     *
     * @return l'état des effets visuels
     */
    protected Boolean getEffetVisuel() {
        return this.effetVisuel;
    }

    /**
     * Méthode pour modifier la limite du niveau d'aide.
     * 
     * @param niveauAide le niveau d'aide
     */
    protected void setNiveauAide(Integer niveauAide) {
        if (niveauAide < 0 || niveauAide > 2) {
            throw new IllegalArgumentException("Le niveau d'aide doit être compris entre 0 et 2");
        }
        this.niveauAide = niveauAide;
    }

    /**
     * Méthode pour modifier le volume des effets sonores.
     * 
     * @param volumeEffetsSonore le volume des effets sonores
     */
    protected void setVolumeEffetsSonore(float volumeEffetsSonore) {
        if (volumeEffetsSonore < 0 || volumeEffetsSonore > 1) {
            throw new IllegalArgumentException("Le volume des effets sonores doit être compris entre 0 et 1");
        }
        this.volumeEffetsSonore = volumeEffetsSonore;
    }

    /**
     * Méthode pour modifier l'état des effets visuels.
     * 
     * @param effetVisuel l'état des effets visuels
     */
    protected void setEffetVisuel(Boolean effetVisuel) {
        this.effetVisuel = effetVisuel;
    }
}
