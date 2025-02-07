/**
 * @autor COGNARD Luka
 * @date 24-01-2025
 * @version 1.0
 * @description Contient la classe Noeud
 *
 */
package fr.m3acnl.game.logique;

import java.util.Hashtable;

/**
 * Cette classe représente un Noeud dans le jeu du hachi. Un noeud connait: sa
 * position, son degré actuelle, le degré de sa solution et une liste
 * d'adjacence stockant les noeuds auquel il est relié directement.
 */

public class Noeud implements ElementJeu, Comparable<Noeud> {

    /**
     * La position en coordonnée du noeud.
     */
    private Coord position;

    /**
     * Le degré solution du noeud.
     */
    private int degreSoluce;

    /**
     * Le degré actuelle du noeud.
     */
    private int degreActuelle;

    /**
     * La liste d'adjacence de la matrice.
     */
    private Hashtable listeAdjacence;

    /**
     * Si le noeud est en surbrillance ou non.
     */
    private Boolean surbrillance;

    

    /**
     * Constructeur pour créer une nouvelle instance d'un Noeud.
     *
     * @param x la coordonnée x du noeud
     * @param y la coordonnée y du noeud
     * @param degS le degré solution du noeud
     */
    public Noeud(int x, int y, int degS) {
        position = new Coord(x, y);
        degreSoluce = degS;
        degreActuelle = 0;
        listeAdjacence = new Hashtable<>();
        surbrillance = false;
    }

    /**
     * Active la surbrillance du noeud.
     */
    public void surbrillanceOn() {
        surbrillance = true;
    }

    /**
     * Désactive la surbrillance du noeud.
     */
    public void surbrillanceOff() {
        surbrillance = false;
    }

    /**
     * Incrémente le degré actuelle du noeud.
     */
    public void ajouterDegre() {
        degreActuelle += 1;
    }

    /**
     * Décrémente le degré actuelle du noeud.
     */
    public void enleverDegre() {
        degreActuelle -= 2;
    }

    /**
     * Vérifie si le noeud est valide.
     *
     * @return 0 si valide
     */
    public int estValide() {
        return (degreSoluce - degreActuelle);
    }

    /**
     * Récupère la position du noeud.
     *
     * @return les coordonnées du noeud
     */
    public Coord getPosition() {
        return position;
    }

    /**
     * Récupère le degré solution.
     *
     * @return le degré solution
     */
    public int getDegreSoluce() {
        return degreSoluce;
    }
    
    /**
     * Récupère la surbrillance.
     * @return la surbrillance
     */
    public Boolean getSurbrillance() {
        return surbrillance;
    }

    /**
     * Comparaison entre deux noeuds Les noeuds sont comparé par leur position.
     *
     * @param n2 le noeud avec qui comparé
     * @return le résultat de la comparaison
     */
    @Override
    public int compareTo(Noeud n2) {
        return this.position.compareTo(n2.position);
    }


    /**
     * Affiche le Noeud.
     */
    @Override
    public void draw() {
        System.out.println("N");

    }
}
