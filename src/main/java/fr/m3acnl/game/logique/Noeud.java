package fr.m3acnl.game.logique;

import java.util.Hashtable;

/**
 * Cette classe représente un Noeud dans le jeu du hachi
 * un noeud connait ça position, son degré actuelle, le degré de ça solution
 * et une liste d'adjacence pour la connexité.
 * Il permet l'incrémentation et la decrémentation de son degré actuelle,
 * si il est valide ainsi que de récupérer ça position et son degré solution
 */
public class Noeud{
    private Coord position;
    private int degreSoluce;
    private int degreActuelle;
    private Hashtable listeAdjacence;

    /**
     * Constructeur pour créer une nouvelle instance d'un Noeud
     * 
     * @param x la coordonnée x du noeud
     * @param y la coordonnée y du noeud
     * @param degS le degré solution du noeud
     */
    public Noeud(int x, int y, int degS){
        position=new Coord(x, y);
        degreSoluce=degS;
        degreActuelle=0;
        listeAdjacence=new Hashtable<>();
    }

    /**
     * Incrémente le degré actuelle du noeud
     */
    public void ajouterDegre(){
        degreActuelle+=1;
    }

    /**
     * Décrémente le degré actuelle du noeud
     */
    public void enleverDegre(){
        degreActuelle-=2;
    }

    /**
     * Vérifie si le noeud est valide
     * 
     * @return 0 si valide
     */
    public int estValide(){
        return (degreSoluce-degreActuelle);
    }

    /**
     * Récupère la position du noeud
     * 
     * @return les coordonnées du noeud
     */
    public Coord getPosition() {
        return position;
    }

    /**
     * Récupère le degré solution
     * 
     * @return le degré solution
     */
    public int getDegreSoluce() {
        return degreSoluce;
    }

}
