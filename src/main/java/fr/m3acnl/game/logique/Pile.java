/**
 * Class Pile.
 *
 * @autor MABIRE Aymeric
 * @date 24-01-2025
 * @version 1.0
 * @description Contient la classe Pile
 *
 */

//package fr.m3acnl.game.logique;

import java.util.ArrayList;

/**
 * La classe Pile représentant une pile d'objets.
 */
public class Pile {

    /**
     * Tableau contenant la pile.
     */
    private final ArrayList<Object> tab;

    /**
     * Constructeur de la classe Pile.
     */
    public Pile() {
        tab = new ArrayList<>();
    }

    /**
     * Méthode pour retourner la taille d'une pile.
     *
     * @return : la taille de la pile
     */
    public int taille() {
        return tab.size();
    }

    /**
     * Méthode pour savoir si une pile est vide.
     *
     * @return : true si la pile est vide, false sinon
     */
    public boolean estVide() {
        return tab.isEmpty();
    }

    /**
     * Méthode pour empiler un objet dans une pile.
     *
     * @param o : objet à empiler
     */
    public void empiler(Object o) {
        tab.add(o);

    }

    /**
     * Méthode pour dépiler un objet d'une pile.
     *
     * @return : l'objet dépilé
     */
    public Object depiler() {
        if (!estVide()) {
            Object value = tab.get(this.taille() - 1);
            tab.remove(this.taille() - 1);
            return value;
        }
        return null;
    }

    /**
     * Méthode pour retourner l'objet au sommet de la pile.
     *
     * @return : l'objet au sommet de la pile
     */
    public Object sommet() {
        if (!estVide()) {
            return tab.get(this.taille() - 1);
        }
        return null;
    }

    /**
     * Méthode pour vider entièrement une pile.
     */
    public void vidange() {
        while (!estVide()) {
            depiler();
        }
    }

    /**
     * Méthode pour afficher le contenu d'une pile.
     *
     * @return : le contenu de la pile
     */
    public String to_s() {
        String s = "";
        for (int i = 0; i < this.taille(); i++) {
            s += tab.get(i).toString() + " ";
        }
        return s;
    }

}
