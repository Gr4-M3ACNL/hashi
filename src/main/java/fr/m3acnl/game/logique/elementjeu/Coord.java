package fr.m3acnl.game.logique.elementjeu;

/**
 * Classe Coord pour une représentation de coordonnée.
 * <br>
 * Cette classe représente les coordonnées d'un élément au sein de la matrice du jeu.
 *
 * @author COGNARD Luka
 * @version 1.0
 */
public class Coord implements Comparable<Coord> {

    // ==================== Attributs ====================
    /**
     * Coordonnée x de la coordonnée.
     */
    private final int coordX;

    /**
     * Coordonnée y de la coordonnée.
     */
    private final int coordY;

    /**
     * Constructeur pour créer une nouvelle instance de Coord.
     *
     * @param px coordonnée x de la coordonnée
     * @param py coordonnée y de la coordonnée
     */
    public Coord(int px, int py) {
        coordX = px;
        coordY = py;
    }

    // ==================== Getter ====================
    /**
     * Récupère la coordonnée x de l'instance.
     *
     * @return la coordonnée x de l'instance
     */
    public int getCoordX() {
        return coordX;
    }

    /**
     * Récupère la coordonnée y de l'instance.
     *
     * @return la coordonnée y de l'instance
     */
    public int getCoordY() {
        return coordY;
    }

    // ==================== Méthodes ====================
    /**
     * Comparaison entre 2 Coord.
     *
     * @param co2 le Coord avec qui comparé
     * @return le résultat de la comparaison
     */
    @Override
    public int compareTo(Coord co2) {
        int res = Integer.compare(this.coordX, co2.getCoordX());
        if (res == 0) {
            return Integer.compare(this.coordY, co2.getCoordY());
        }
        return res;
    }

    /**
     * Permet de faire l'affichage de la classe.
     */
    @Override
    public String toString() {
        return "(" + coordX + "," + coordY + ")";
    }
}
