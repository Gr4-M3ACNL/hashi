package fr.m3acnl.game.logique.elementjeu;

/**
 * Interface ElementJeu pour représenter les éléments du jeu.
 *
 * @author Aymeric MABIRE
 * @version 1.0
 */
public interface ElementJeu {

    // ==================== Getter ====================
    /**
     * Permet d'indiquer qu'il faut consulter l'élément.
     *
     * @return true si l'élément a été modifié et necessite consultation
     */
    public boolean modifie();

    /**
     * Permet de savoir si l'élément a été modifié.
     *
     * @return true si l'élément a été modifié, false sinon
     */
    public Boolean activer();

    // ==================== Setter ====================
    /**
     * Permet d'indiquer que l'élément a été consulter.
     */
    public void verifie();

    /**
     * Permet de dire que l'élément a été modifié.
     */
    public void averifie();

    /**
     * Méthode pour activer la surbrillance d'un élément de jeu.
     */
    public void surbrillanceOn();

    /**
     * Méthode pour désactiver la surbrillance d'un élément de jeu.
     */
    public void surbrillanceOff();

    // ==================== Affichage ====================
    /**
     * Méthode pour dessiner un élément de jeu.
     *
     * @return retourne le chemin d'accès de la texture de l'élément
     */
    public String draw();

    /**
     * Méthode pour dessiner un élément de jeu dans le terminal.
     */
    public void drawTerm();

}
