package fr.m3acnl.game.logique;

/**
 * Cette classe représente les coordonnés en x et y,
 * et permet la création ainsi que la récupération du x et y
 */
public class Coord implements Comparable<Coord>{
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
    
    /**
     * Comparaison entre 2 Coord
     * 
     * @param co2 le Coord avec qui comparé
     * @return le résultat de la comparaison
     */
    public int compareTo(Coord co2){
        return (Integer.compare(this.x, co2.x) & Integer.compare(this.y, co2.y));
    }
}
