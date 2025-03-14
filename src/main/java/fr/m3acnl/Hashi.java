package fr.m3acnl;

import javafx.application.Application;

/**
 * Main class of the Hashi game.
 */
public class Hashi {

    /**
     * Constructeur privé de la classe Hashi.
     */
    private Hashi() {
        // Constructeur vide
    }

    /**
     * Méthode main de la classe Hashi.
     *
     * @param args Arguments passés en ligne de commande.
     */
    public static void main(String[] args) {
        System.out.println("Hello World!");
        Application.launch(HashiParmentier.class, args);
    }
}