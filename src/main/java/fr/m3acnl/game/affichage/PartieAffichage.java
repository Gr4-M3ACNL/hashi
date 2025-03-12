package fr.m3acnl.game.affichage;

import java.net.URL;

import fr.m3acnl.game.logique.DoubleLien;
import fr.m3acnl.game.logique.Jeu;
import fr.m3acnl.game.logique.Noeud;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe PartieAffichage pour l'affichage du jeu.
 *
 * @author PESANTEZ Maelig
 * @version 1.0
 */
public class PartieAffichage extends Application {

    /**
     * Le jeu.
     */
    private Jeu jeu;

    /**
     * Label pour afficher le temps.
     */
    private Label labelTemps;

    /**
     * Tableau de boutons.
     */
    private Button[][] boutons;

    /**
     * GridPane pour afficher les boutons.
     */
    private GridPane gridPane;

    /**
     * Le ratio de superpostion des images.
     */
    private static final double SUPERPOSITION_RATIO = 1.15;

    /**
     * Le StackPane pour le fond.
     */
    private StackPane backgroundPane;

    private double derniereTaille = -1;

    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(new Image(getClass().getResource("/META-INF/assetsGraphiques/logo.png").toExternalForm()));
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        jeu = new Jeu(7, mat);
        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(-10);
        gridPane.setVgap(-10);

        StackPane root = new StackPane();
        creerBackground();
        root.getChildren().addAll(backgroundPane, gridPane);

        boutons = new Button[jeu.getTaille()][jeu.getTaille()];
        initialiserBoutons();

        VBox controlPanel = creerPanneauDeControle();
        controlPanel.setAlignment(Pos.CENTER_LEFT);
        controlPanel.setPadding(new Insets(0, 0, 0, 90));
        controlPanel.setPickOnBounds(false);
        root.getChildren().add(controlPanel);
        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(root);

        Scene scene = new Scene(mainLayout, 1920, 1080);
        primaryStage.setTitle("Jeu Interface");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.widthProperty().addListener((obs, oldVal, newVal) -> ajusterTailleImages());
        scene.heightProperty().addListener((obs, oldVal, newVal) -> ajusterTailleImages());
        actualiserAffichage();
    }

    /**
     * Initialise les boutons du jeu.
     */
    private void initialiserBoutons() {
        for (int i = 0; i < jeu.getTaille(); i++) {
            for (int j = 0; j < jeu.getTaille(); j++) {
                boutons[i][j] = new Button();
                int x = i;
                int y = j;
                boutons[i][j].setOnAction(e -> activerElement(x, y));

                URL resource = getResourceElement(i, j);
                if (resource != null) {
                    boutons[i][j].setGraphic(creerImageView(resource, 100));
                }

                boutons[i][j].setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                boutons[i][j].setOnMouseEntered(e -> previsualiserEtat(x, y));
                boutons[i][j].setOnMouseExited(e -> restaurerEtat(x, y));

                gridPane.add(boutons[i][j], y, x);
            }
        }
    }

    /**
     * Prévisualise l'état d'un élément du jeu.
     *
     * @param x La coordonnée x
     * @param y La coordonnée y
     */
    private void previsualiserEtat(int x, int y) {
        if (jeu.getPlateau().getElement(x, y) == null) {
            return;
        }
        if (jeu.getPlateau().getElement(x, y) instanceof DoubleLien) {
            DoubleLien doubleLien = (DoubleLien) jeu.getPlateau().getElement(x, y);
            Noeud noeudReference = jeu.getPlateau().trouverNoeudLePlusProche(x, y);

            if (noeudReference != null) {
                Noeud noeud = trouverNoeudLePlusProche(doubleLien, noeudReference);
                if (noeud != null) {
                    // Simuler l'activation sans modifier l'état du jeu
                    doubleLien.activerSurbrillance(noeud);
                    doubleLien.averifié();

                }
            }
        } else {
            jeu.getPlateau().getElement(x, y).surbrillanceOn();
            jeu.getPlateau().getElement(x, y).averifié();

        }
        actualiserAffichage();
    }

    /**
     * Restaure l'état initial du bouton lorsque la souris quitte.
     *
     * @param x La coordonnée x
     * @param y La coordonnée y
     */
    private void restaurerEtat(int x, int y) {
        if (jeu.getPlateau().getElement(x, y) == null) {
            return;
        }
        if (jeu.getPlateau().getElement(x, y) instanceof DoubleLien) {
            DoubleLien doubleLien = (DoubleLien) jeu.getPlateau().getElement(x, y);
            doubleLien.surbrillanceOff();
            doubleLien.averifié();
        } else {
            jeu.getPlateau().getElement(x, y).surbrillanceOff();
            jeu.getPlateau().getElement(x, y).averifié();
        }
        actualiserAffichage();
    }

    /**
     * Active un élément du jeu.
     *
     * @param x La coordonnée x
     * @param y La coordonnée y
     */
    private void activerElement(int x, int y) {
        restaurerEtat(x, y);
        if (jeu.getPlateau().getElement(x, y) instanceof DoubleLien) {
            DoubleLien doubleLien = (DoubleLien) jeu.getPlateau().getElement(x, y);

            // On suppose que le plateau stocke les Noeuds, ici on récupère un Noeud voisin pour référence
            Noeud noeudReference = jeu.getPlateau().trouverNoeudLePlusProche(x, y);

            if (noeudReference != null) {
                Noeud noeud = trouverNoeudLePlusProche(doubleLien, noeudReference);
                if (noeud != null) {
                    doubleLien.activer(noeud).averifié();
                    doubleLien.averifié();

                }
            }
        } else {
            jeu.activeElemJeu(x, y, null);
            jeu.getPlateau().getElement(x, y).averifié();
        }
        jeu.chargerSauvegardeAuto();
        actualiserAffichage();
    }

    /**
     * Trouve le nœud le plus proche d'un double lien.
     *
     * @param doubleLien Le double lien
     * @param reference Le nœud de référence
     * @return Le nœud le plus proche
     */
    private Noeud trouverNoeudLePlusProche(DoubleLien doubleLien, Noeud reference) {
        Noeud n1Lien1 = doubleLien.getLien1().getNoeud1();
        Noeud n2Lien1 = doubleLien.getLien1().getNoeud2();
        Noeud n1Lien2 = doubleLien.getLien2().getNoeud1();
        Noeud n2Lien2 = doubleLien.getLien2().getNoeud2();

        Noeud[] noeudsPossibles = {n1Lien1, n2Lien1, n1Lien2, n2Lien2};
        Noeud noeudProche = noeudsPossibles[0];
        double distanceMin = calculerDistance(reference, noeudProche);

        for (Noeud noeud : noeudsPossibles) {
            double distance = calculerDistance(reference, noeud);
            if (distance < distanceMin) {
                distanceMin = distance;
                noeudProche = noeud;
            }
        }

        return noeudProche;
    }

    /**
     * Calcule la distance entre deux nœuds.
     *
     * @param n1 Le premier nœud
     * @param n2 Le deuxième nœud
     * @return La distance entre les deux nœuds
     */
    private double calculerDistance(Noeud n1, Noeud n2) {
        double dx = n1.getPosition().getCoordX() - n2.getPosition().getCoordX();
        double dy = n1.getPosition().getCoordY() - n2.getPosition().getCoordY();
        return Math.sqrt(dx * dx + dy * dy);
    }

    /**
     * Crée le fond de la fenêtre.
     *
     */
    private void creerBackground() {

        Image imageBackground = new Image(getClass().getResource("/META-INF/assetsGraphiques/background.png").toExternalForm());
        ImageView imageBackgroundView = new ImageView(imageBackground);
        imageBackgroundView.setPreserveRatio(false);
        imageBackgroundView.setCache(true);

        Image imageFond = new Image(getClass().getResource("/META-INF/assetsGraphiques/table.png").toExternalForm());
        ImageView imageFondView = new ImageView(imageFond);
        imageFondView.setPreserveRatio(true);
        imageFondView.setCache(true);

        backgroundPane = new StackPane(imageBackgroundView, imageFondView);

        // Attendre que la scène soit disponible avant de lier les propriétés
        backgroundPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                imageBackgroundView.fitWidthProperty().bind(newScene.widthProperty());
                imageBackgroundView.fitHeightProperty().bind(newScene.heightProperty());

                imageFondView.fitWidthProperty().bind(newScene.widthProperty().multiply(0.92));
                imageFondView.fitHeightProperty().bind(newScene.heightProperty().multiply(0.92));
            }
        });
    }

    /**
     * Crée le panneau de contrôle.
     *
     * @return Le panneau de contrôle
     */
    private VBox creerPanneauDeControle() {
        VBox controlPanel = new VBox(10);
        controlPanel.setAlignment(Pos.CENTER);

        Button buttonRetour = new Button("Retour");
        Button buttonAvancer = new Button("Avancer");
        Button buttonCheck = new Button("Vérifier grille");
        Button buttonSave = new Button("Sauvegarde manuelle");
        Button buttonCheckpoint = new Button("Retour checkpoint");

        // Appliquer un style uniforme aux boutons
        for (Button bouton : new Button[]{buttonRetour, buttonAvancer, buttonCheck, buttonSave, buttonCheckpoint}) {
            bouton.setMinSize(150, 50);
            bouton.setStyle(
                    "-fx-background-color: linear-gradient(#7a5230, #4a2c14);"
                    + "-fx-background-radius: 10;"
                    + "-fx-border-color: #3d1e10;"
                    + "-fx-border-width: 2px;"
                    + "-fx-border-radius: 10;"
                    + "-fx-text-fill: white;"
                    + "-fx-font-size: 14px;"
                    + "-fx-font-family: 'Georgia';"
            );
        }

        // Ajouter les actions
        buttonRetour.setOnAction(e -> retour());
        buttonAvancer.setOnAction(e -> avancer());
        buttonCheck.setOnAction(e -> check());
        buttonSave.setOnAction(e -> jeu.sauvegarderManuellement());
        buttonCheckpoint.setOnAction(e -> retourSauvegarde());
        labelTemps = new Label("Temps: 0s");
        labelTemps.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: 'Georgia';");

        controlPanel.getChildren().addAll(buttonRetour, buttonAvancer, buttonCheck, buttonSave, buttonCheckpoint, labelTemps);
        return controlPanel;
    }

    /**
     * Ajuste la taille des images en fonction de la taille de la scène.
     */
    private void ajusterTailleImages() {
        if (gridPane.getScene() == null) {
            // Ajoute un listener pour s'exécuter dès que la scène est disponible
            gridPane.sceneProperty().addListener((obs, oldScene, newScene) -> {
                if (newScene != null) {
                    ajusterTailleImages(); // Appelle à nouveau la fonction avec la bonne taille
                }
            });
            return; // Attend que la scène soit prête
        }

        double largeurScene = gridPane.getScene().getWidth();
        double hauteurScene = gridPane.getScene().getHeight();

        // Déterminer la taille des cellules en gardant une marge de 5% sur la table
        double tailleTable = Math.min(largeurScene * 0.92, hauteurScene * 0.92);
        double tailleCellule = tailleTable / jeu.getTaille();

        // Ajuster la taille du GridPane pour correspondre à la table
        gridPane.setPrefSize(jeu.getTaille() * tailleCellule, jeu.getTaille() * tailleCellule);
        gridPane.setMaxSize(jeu.getTaille() * tailleCellule, jeu.getTaille() * tailleCellule);

        // Ajuster chaque bouton
        for (int i = 0; i < jeu.getTaille(); i++) {
            for (int j = 0; j < jeu.getTaille(); j++) {
                if (jeu.getPlateau().getElement(i, j) != null) {
                    if (jeu.getPlateau().getElement(i, j).modifié() || derniereTaille != tailleCellule) {
                        derniereTaille = tailleCellule;
                        ImageView imageView = creerImageView(getResourceElement(i, j), tailleCellule * SUPERPOSITION_RATIO);
                        imageView.setCache(true);
                        imageView.setSmooth(true);
                        boutons[i][j].setGraphic(imageView);
                        jeu.getPlateau().getElement(i, j).verifié();
                    }
                }
                boutons[i][j].setMinSize(tailleCellule, tailleCellule);
                boutons[i][j].setMaxSize(tailleCellule, tailleCellule);
            }
        }
    }

    /**
     * Récupère la ressource associée à un élément du jeu.
     *
     * @param i L'indice de ligne
     * @param j L'indice de colonne
     * @return L'URL de la ressource
     */
    private URL getResourceElement(int i, int j) {
        return jeu.getPlateau().getElement(i, j) != null
                ? getClass().getResource((String) jeu.getPlateau().getElement(i, j).draw())
                : getClass().getResource("/META-INF/assetsGraphiques/link/blank.png");
    }

    /**
     * Crée un ImageView à partir d'une ressource et d'une taille.
     *
     * @param resource La ressource à exploiter
     * @param size La taille de l'image
     * @return L'ImageView créé
     */
    private ImageView creerImageView(URL resource, double size) {
        ImageView imageView = new ImageView(new Image(resource.toExternalForm(), 500 * 0.5, 500 * 0.5, true, true));
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setCache(true);
        imageView.setSmooth(true);
        return imageView;
    }

    /**
     * Utilise la méthode retour et met a jour l'affichage.
     *
     * @see Jeu#retour()
     */
    private void retour() {
        jeu.retour();
        actualiserAffichage();
    }

    /**
     * Utilise la méthode avancer et met a jour l'affichage.
     *
     * @see Jeu#avancer()
     */
    private void avancer() {
        jeu.avancer();
        actualiserAffichage();
    }

    /**
     * Vérifier la grille et revenir à la dernière grille correcte.
     *
     */
    private void check() {
        jeu.chargerSauvegardeAuto();
        actualiserAffichage();
    }

    /**
     * Met à jour l'affichage.
     */
    private void actualiserAffichage() {
        ajusterTailleImages();
    }

    /**
     * Retour au checkpoint manuel
     */
    private void retourSauvegarde() {
        jeu.chargerSauvegardeManuel();
        actualiserAffichage();
    }

    /**
     * Méthode main pour lancer l'application.
     *
     * @param args Les arguments de la ligne de commande
     */
    public static void main(String[] args) {
        Application.launch(PartieAffichage.class, args);
    }
}
