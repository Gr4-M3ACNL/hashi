package fr.m3acnl.game.logique;

import fr.m3acnl.game.logique.elementjeu.Coord;

/**
 * Classe représentant une aide pour le graphe dans le jeu.
 * Elle hérite de la classe Aide.
 */
public class AideGraphe extends Aide {
    /**
     * Constructeur de la classe AideGraphe.
     *
     * @param matrice Matrice du jeu.
     * @param nom     Nom de l'aide.
     * @param c       Coordonnée de l'aide.
     */
    public AideGraphe(Matrice matrice, String nom, Coord c) {
        super(matrice, nom, c);
    }

    /**
     * Méthode pour afficher l'aide spécifique au graphe.
     *
     * @param index Index de l'aide à afficher.
     */
    @Override
    public void afficherAide(int index) {
        System.out.println("Affichage de l'aide pour le graphe (index " + index + ")");
    }
}
