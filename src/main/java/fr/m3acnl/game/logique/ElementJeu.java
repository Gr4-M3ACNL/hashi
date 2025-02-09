/**
 * Interface ElementJeu.
 *
 * @autor Aymeric MABIRE
 * @date 05-02-2025
 * @version 1.0
 * @description Contient l'interface ElementJeu
 *
 */

//package fr.m3acnl.game.logique;

/**
 * Interface des éléments du jeu.
 */
public interface ElementJeu {

    /**
     * Méthode pour dessiner un élément de jeu.
     */
    public void draw();

    /**
     * Méthode d'activation d'un élément de jeu.
     * @return true si il a été activer false sinon
     */
    public Boolean activer();
}
