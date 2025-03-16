package fr.m3acnl.game.logique;

public abstract class Aide {
    String nom;
    String description;
    int cout;
    Matrice matrice;
    Coord coord;

    public Aide(Matrice matrice, String nom, Coord c) {
        this.coord = c;
        this.matrice = matrice;
        this.nom = nom;
    }

    public void afficherAide(int index) {
        //System.out.println("Aide");
    }

}