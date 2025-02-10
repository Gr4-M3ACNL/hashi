
//package fr.m3acnl.game.logique;
/**
 * Interface ElementJeu.
 *
 * @autor Aymeric MABIRE
 * @date 05-02-2025
 * @version 1.0
 * @description Contient l'interface ElementJeu
 *
 */
public interface ElementJeu {

    /**
     * Méthode pour dessiner un élément de jeu.
     */
    public void draw();

    /**
     * Méthode pour activer un élément de jeu.
     *
     * @return true si l'élément est activé, false sinon.
     */
    public Boolean activer();
}
