package fr.m3acnl;

import javafx.application.Application;

/**
 * Main class of the Hashi game.
 *
 * @author COGNARD Luka, GAUMONT Maël, LELANDAIS Clément, MABIRE Aymeric, PESANTEZ Maëlig, PUREN Mewen, TOUISSI Nassim
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
        Application.launch(HashiParmentier.class, args);
    }
}
