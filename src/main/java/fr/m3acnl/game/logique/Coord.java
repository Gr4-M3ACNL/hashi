package fr.m3acnl.game.logique;

/**
 * Cette classe représente les coordonnés en x et y,
 * et permet la création ainsi que la récupération du x et y
 */
public class Coord{
    private int x;
    private int y;

    /**
     * Constructeur pour créer une nouvelle instance de Coord
     * 
     * @param px coordonnée x de la coordonnée 
     * @param py coordonnée y de la coordonnée
     */
    public Coord(int px,int py){
        x=px;
        y=py;
    }
    /**
     * Récupère la coordonnée x de l'instance
     * 
     * @return la coordonnée x de l'instance
     */
    public int getX() {
        return x;
    }

    /**
     * Récupère la coordonnée y de l'instance
     * 
     * @return la coordonnée y de l'instance
     */
    public int getY() {
        return y;
    }

}
