
/**
 * Class Coord.
 *
 * @autor COGNARD Luka
 * @date 24-01-2025
 * @version 1.0
 * @description Contient la classe Coord
 *
 */

//package fr.m3acnl.game.logique;
/**
 * Cette classe représente les coordonnés en x et y, et permet la création ainsi
 * que la récupération du x et y.
 */
public class Coord implements Comparable<Coord> {

    /**
     * Coordonnée x de la coordonnée.
     */
    private int coordX;

    /**
     * Coordonnée y de la coordonnée.
     */
    private int coordY;

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

    /**
     * Comparaison entre 2 Coord.
     *
     * @param co2 le Coord avec qui comparé
     * @return le résultat de la comparaison
     */
    public int compareTo(Coord co2) {
        int res = Integer.compare(this.coordX, co2.getCoordX());
        if (res == 0) {
            return Integer.compare(this.coordY, co2.getCoordY());
        }
        return res;
    }
}
