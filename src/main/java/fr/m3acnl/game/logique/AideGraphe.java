package fr.m3acnl.game.logique;

public class AideGraphe extends Aide {
    public AideGraphe(Matrice matrice, String nom, Coord c) {
        super(matrice, nom, c);
    }

    @Override
    public void afficherAide(int index) {
        System.out.println("Affichage de l'aide pour le graphe (index " + index + ")");
    }
}
