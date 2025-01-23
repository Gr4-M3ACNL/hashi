package fr.m3acnl;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 * Main class of the Hashi game.
 */
public class HashiParmentier extends Application {

    /**
     * Constructeur privé de la classe Hashi.
     */
    public HashiParmentier() {

    }

    /**
     * Méthode de lancement de l'application graphique.
     * 
     * @param stagePrimaire Stage principal de l'application.
     * @throws Exception Exception levée en cas d'erreur lors du lancement de l'application.
     */
    @Override
    public void start(Stage stagePrimaire) throws Exception {
        Label label = new Label("Bienvenue dans JavaFX!");
        StackPane root = new StackPane(label);
        Scene scene = new Scene(root, 400, 300);
        stagePrimaire.setTitle("Hello JavaFX!");
        stagePrimaire.setScene(scene);
        stagePrimaire.show();
    }
    
    /**
     * Méthode d'initialisation de l'application.
     * 
     * @throws Exception Exception levée en cas d'erreur lors de l'initialisation de l'application.
     */
    @Override
    public void init() throws Exception {
        System.out.println("Initialisation de l'application Hashi.");        
    }

    /**
     * Méthode d'arrêt de l'application.
     * 
     * @throws Exception Exception levée en cas d'erreur lors de l'arrêt de l'application.
     */
    @Override
    public void stop() throws Exception {
        
    }
}