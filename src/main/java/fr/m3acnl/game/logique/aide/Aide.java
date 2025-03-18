package fr.m3acnl.game.logique.aide;

import fr.m3acnl.game.logique.Matrice;
import fr.m3acnl.game.logique.elementjeu.Coord;

/**
 * Classe abstraite représentant une aide dans le jeu.
 */
public abstract class Aide {

    /**
     * Nom de l'aide.
     */
    String nom;
    /**
     * Description de l'aide.
     */
    String description;
    /**
     * coupt en temps de l'aide.
     */
    int cout;
    /**
     * Matrice du jeu.
     */
    Matrice matrice;
    /**
     * Coordonnée de l'aide.
     */
    Coord coord;

    /**
     * Constructeur de la classe Aide.
     *
     * @param matrice Matrice du jeu.
     * @param nom Nom de l'aide.
     * @param c Coordonnée de l'aide.
     */
    public Aide(Matrice matrice, String nom, Coord c) {
        this.coord = c;
        this.matrice = matrice;
        this.nom = nom;
    }

    /**
     * Méthode abstraite pour afficher l'aide.
     *
     * @param index Index de l'aide à afficher.
     */
    public void afficherAide(int index) {
        //System.out.println("Aide");
    }

}
