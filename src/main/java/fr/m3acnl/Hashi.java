package fr.m3acnl;

import fr.m3acnl.managers.ProfileManager;
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
        try {
            ProfileManager.getInstance().creerProfil("DummyProfile");
        } catch (Exception e) {
            ProfileManager.getInstance().setProfileActif("DummyProfile");
        }
        Application.launch(HashiParmentier.class, args);
    }
}