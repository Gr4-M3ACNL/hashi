package fr.m3acnl.game.logique;

public class Noeud {

    private Coord position;
    private int degreSoluce;
    private int degreActuelle;
    private int listeAdjacence[][];

    public Noeud(int x, int y, int degS) {
        position = new Coord(x, y);
        degreSoluce = degS;
        degreActuelle = 0;
    }

    public void ajouterDegre() {
        degreActuelle += 1;
        return;
    }

    public void enleverDegre() {
        degreActuelle -= 2;
    }

    public int estValide() {
        return (degreSoluce - degreActuelle);
    }

    public Coord getPosition() {
        return position;
    }

    public int getDegreSoluce() {
        return degreSoluce;
    }
}
