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
     *
     * @return Retourne un string représentant l'élément de jeu.
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
