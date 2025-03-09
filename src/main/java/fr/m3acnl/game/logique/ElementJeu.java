package fr.m3acnl.game.logique;

/**
 * Interface ElementJeu pour représenter les éléments du jeu.
 *
 * @author Aymeric MABIRE
 * @version 1.0
 */
public interface ElementJeu {

    /**
     * Méthode pour dessiner un élément de jeu.
     * @return retourne le chemin d'accès de la texture de l'élément
     */
    public String draw();

    /**
     * Méthode pour dessiner un élément de jeu dans le terminal.
     */
    public void drawTerm();

    /**
     * Méthode pour activer un élément de jeu.
     *
     * @return true si l'élément est activé, false sinon.
     */
    public Boolean activer();
}
