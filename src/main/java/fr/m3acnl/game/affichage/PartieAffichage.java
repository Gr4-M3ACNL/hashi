package fr.m3acnl.game.affichage;

import java.net.URL;
import java.util.Arrays;

import fr.m3acnl.game.Difficulte;
import fr.m3acnl.game.Partie;
import fr.m3acnl.game.logique.DoubleLien;
import fr.m3acnl.game.logique.Jeu;
import fr.m3acnl.game.logique.Noeud;
import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
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
import javafx.util.Duration;

/**
 * Classe PartieAffichage pour l'affichage du jeu.
 *
 * @author MABIRE Aymeric, PESANTEZ Maelig
 * @version 1.0
 */
public class PartieAffichage extends Application {

    /**
     * Le jeu.
     */
    private Partie partie;

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

    /**
     * Stocke la dernière taille de la fenêtre.
     */
    private double derniereTaille = -1;

    public PartieAffichage(Difficulte difficulte) {
        this.partie = new Partie(difficulte);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.getIcons().add(new Image(getClass().getResource("/META-INF/assetsGraphiques/logo.png").toExternalForm()));

        primaryStage.setFullScreen(true);

        gridPane = new GridPane();
        gridPane.setAlignment(Pos.CENTER);
        gridPane.setHgap(-10);
        gridPane.setVgap(-10);

        StackPane root = new StackPane();
        creerBackground();
        root.getChildren().addAll(backgroundPane, gridPane);

        boutons = new Button[partie.getJeu().getTaille()][partie.getJeu().getTaille()];
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
        for (int i = 0; i < partie.getJeu().getTaille(); i++) {
            for (int j = 0; j < partie.getJeu().getTaille(); j++) {
                boutons[i][j] = new Button();
                int x = i;
                int y = j;
                boutons[i][j].setOnMouseReleased(e -> activerElement(x, y));

                URL resource = getResourceElement(i, j);
                if (resource != null) {
                    boutons[i][j].setGraphic(creerImageView(resource, 100));
                }

                boutons[i][j].setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                boutons[i][j].setOnMouseEntered(e -> previsualiserEtat(x, y)); // Active le survol
                boutons[i][j].setOnMouseExited(e -> restaurerEtat(x, y)); // Désactive le survol

                gridPane.add(boutons[i][j], y, x);
            }
        }
    }

    /**
     * Prévisualise l'état d'un élément du jeu. Permet de pouvoir voir les
     * connections avant de cliquer.
     *
     * @param x La ligne de l'élément
     * @param y La colonne de l'élément
     */
    private void previsualiserEtat(int x, int y) {
        if (partie.getJeu().getPlateau().getElement(x, y) == null) {
            return;
        }
        if (partie.getJeu().getPlateau().getElement(x, y) instanceof DoubleLien) {
            DoubleLien doubleLien = (DoubleLien) partie.getJeu().getPlateau().getElement(x, y);
            Noeud noeudReference = partie.getJeu().getPlateau().trouverNoeudLePlusProche(x, y);

            if (noeudReference != null) {
                Noeud noeud = trouverNoeudLePlusProche(doubleLien, noeudReference);
                if (noeud != null) {
                    doubleLien.activerSurbrillance(noeud);
                }
            }
        } else {
            partie.getJeu().getPlateau().getElement(x, y).surbrillanceOn();

        }
        actualiserAffichage();
    }

    /**
     * Restaure l'état initial du bouton lorsque la souris quitte.
     *
     * @param x La ligne de l'élément
     * @param y La colonne de l'élément
     */
    private void restaurerEtat(int x, int y) {
        if (partie.getJeu().getPlateau().getElement(x, y) == null) {
            return;
        }
        if (partie.getJeu().getPlateau().getElement(x, y) instanceof DoubleLien) {
            DoubleLien doubleLien = (DoubleLien) partie.getJeu().getPlateau().getElement(x, y);
            doubleLien.surbrillanceOff();
        } else {
            partie.getJeu().getPlateau().getElement(x, y).surbrillanceOff();
        }
        actualiserAffichage();
    }

    /**
     * Active un élément du jeu.
     *
     * @param x La ligne de l'élément
     * @param y La colonne de l'élément
     */
    private void activerElement(int x, int y) {
        restaurerEtat(x, y);
        if (partie.getJeu().getPlateau().getElement(x, y) instanceof DoubleLien) {
            DoubleLien doubleLien = (DoubleLien) partie.getJeu().getPlateau().getElement(x, y);

            Noeud noeudReference = partie.getJeu().getPlateau().trouverNoeudLePlusProche(x, y);

            if (noeudReference != null) {
                Noeud noeud = trouverNoeudLePlusProche(doubleLien, noeudReference);
                if (noeud != null) {
                    doubleLien.activer(noeud);
                }
            }
        } else {
            partie.getJeu().activeElemJeu(x, y, null);
        }
        if (partie.getJeu().gagner()) {
            partie.finPartie();
            victoire();
        } else {
            partie.sauvegarde();
        }
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

        // Style des boutons
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
                    + "-fx-font-family: 'Georgia';");
        }

        // Ajout des actions aux boutons
        buttonRetour.setOnAction(e -> retour());
        buttonAvancer.setOnAction(e -> avancer());
        buttonCheck.setOnAction(e -> check());
        buttonSave.setOnAction(e -> sauvegarde());
        buttonCheckpoint.setOnAction(e -> retourSauvegarde());
        labelTemps = new Label("Temps: 0s");
        labelTemps.setStyle("-fx-text-fill: white; -fx-font-size: 14px; -fx-font-family: 'Georgia';");

        controlPanel.getChildren().addAll(buttonRetour, buttonAvancer, buttonCheck, buttonSave, buttonCheckpoint,
                labelTemps);
        return controlPanel;
    }

    /**
     * Permet d'afficher l'overlay de victoire.i
     */
    private void victoire() {
        Arrays.stream(boutons).flatMap(Arrays::stream).forEach(b -> b.setDisable(true));

        // Afficher l'image "up.png" temporairement
        ImageView winImageView = new ImageView(new Image(getClass().getResource("/META-INF/assetsGraphiques/up.png").toExternalForm()));
        winImageView.setFitWidth(300);
        winImageView.setFitHeight(300);

        // Utiliser un StackPane mais sans fond opaque
        StackPane upPane = new StackPane(winImageView);
        upPane.setAlignment(Pos.CENTER);

        backgroundPane.getChildren().add(upPane);

        // Pause de 3 secondes avant d'afficher l'overlay final
        PauseTransition pause = new PauseTransition(Duration.seconds(3));
        pause.setOnFinished(event -> {
            backgroundPane.getChildren().remove(upPane); // Retirer l'image temporaire
            afficherOverlayVictoire(); // Afficher l'overlay après la pause
        });
        pause.play();
    }

    /**
     * Affiche l'overlay du menu de victoire.
     */
    private void afficherOverlayVictoire() {
        Scene scene = gridPane.getScene();
        if (!(scene != null && scene.getRoot() instanceof BorderPane mainLayout)) {
            System.out.println("ERREUR : Scène ou BorderPane invalide !");
            return;
        }

        // Création des éléments de l'overlay
        ImageView winImageView = new ImageView(new Image(getClass().getResource("/META-INF/assetsGraphiques/win.png").toExternalForm()));
        winImageView.setFitWidth(500);
        winImageView.setFitHeight(500);

        Label labelWin = new Label("Temps : " + labelTemps.getText());
        labelWin.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        Button btnSuivant = new Button("Grille Suivante");
        Button btnQuitter = new Button("Quitter");

        btnSuivant.setOnAction(e -> System.out.println("Grille suivante"));
        btnQuitter.setOnAction(e -> Platform.exit());

        VBox winBox = new VBox(20, winImageView, labelWin, btnSuivant, btnQuitter);
        winBox.setAlignment(Pos.CENTER);
        winBox.setStyle("-fx-background-color: rgba(0, 0, 0, 0.7); -fx-padding: 20px; -fx-border-radius: 10px;");

        StackPane overlayPane = new StackPane(winBox);
        overlayPane.setAlignment(Pos.CENTER);
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        mainLayout.setCenter(overlayPane);
    }

    /**
     * Ajuste la taille des images en fonction de la taille de la scène.
     */
    private void ajusterTailleImages() {
        Scene scene = gridPane.getScene();
        if (scene == null) {
            return;
        }

        double largeurScene = scene.getWidth();
        double hauteurScene = scene.getHeight();
        int taille = partie.getJeu().getTaille();

        double tailleCellule = Math.min(largeurScene * 0.92, hauteurScene * 0.92) / taille;

        // Ajuster le GridPane
        gridPane.setPrefSize(taille * tailleCellule, taille * tailleCellule);
        gridPane.setMaxSize(taille * tailleCellule, taille * tailleCellule);

        // Ajuster chaque bouton avec un Stream
        Arrays.stream(boutons).flatMap(Arrays::stream).forEach(bouton -> {
            int i = GridPane.getRowIndex(bouton);
            int j = GridPane.getColumnIndex(bouton);

            if (partie.getJeu().getPlateau().getElement(i, j) != null
                    && (partie.getJeu().getPlateau().getElement(i, j).modifie() || derniereTaille != tailleCellule)) {

                derniereTaille = tailleCellule;
                bouton.setGraphic(creerImageView(getResourceElement(i, j), tailleCellule * SUPERPOSITION_RATIO));
                partie.getJeu().getPlateau().getElement(i, j).verifie();
            }

            bouton.setMinSize(tailleCellule, tailleCellule);
            bouton.setMaxSize(tailleCellule, tailleCellule);
        });
    }

    /**
     * Récupère la ressource associée à un élément du jeu.
     *
     * @param i L'indice de ligne
     * @param j L'indice de colonne
     * @return L'URL de la ressource
     */
    private URL getResourceElement(int i, int j) {
        return partie.getJeu().getPlateau().getElement(i, j) != null
                ? getClass().getResource((String) partie.getJeu().getPlateau().getElement(i, j).draw())
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
        partie.getJeu().retour();
        partie.sauvegarde();
        actualiserAffichage();
    }

    /**
     * Utilise la méthode avancer et met a jour l'affichage.
     *
     * @see Jeu#avancer()
     */
    private void avancer() {
        partie.getJeu().avancer();
        partie.sauvegarde();
        actualiserAffichage();
    }

    /**
     * Vérifier la grille et revenir à la dernière grille correcte.
     *
     * @see Jeu#chargerSauvegardeAuto()
     */
    private void check() {
        partie.getJeu().chargerSauvegardeAuto();
        partie.sauvegarde();
        actualiserAffichage();
    }

    /**
     * Met à jour l'affichage.
     */
    private void actualiserAffichage() {
        ajusterTailleImages();
    }

    private void sauvegarde() {
        partie.getJeu().sauvegarderManuellement();
        partie.sauvegarde();
    }

    /**
     * Retour au checkpoint manuel.
     *
     * @see Jeu#chargerSauvegardeManuel()
     */
    private void retourSauvegarde() {
        partie.getJeu().chargerSauvegardeManuel();
        partie.sauvegarde();
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
