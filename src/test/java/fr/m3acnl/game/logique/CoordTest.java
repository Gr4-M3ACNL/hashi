package fr.m3acnl.game.logique;

public class CoordTest {
    public static void main(String[] args) {
        Coord c1 =new Coord(5, 2);
        System.out.println("c1 X : "+c1.getCoordX());
        System.out.println("c1 Y : "+c1.getCoordY());
        Coord c2 = new Coord(5, 1);
        System.out.println("résultat comparaison point c1 a c2 : "+c1.compareTo(c2));
        c2 = new Coord(5, 2);
        System.out.println("résultat comparaison point c1 a c2 : "+c1.compareTo(c2));
        c2 = new Coord(5, 3);
        System.out.println("résultat comparaison point c1 a c2 : "+c1.compareTo(c2));
    }
}
