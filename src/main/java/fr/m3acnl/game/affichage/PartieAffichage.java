package fr.m3acnl.game.affichage;

import java.net.URL;

import fr.m3acnl.game.logique.Jeu;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PartieAffichage extends Application {

    private Jeu jeu;
    private Label labelTemps;
    private Button[][] bouttons;
    private GridPane gridPane;
    private static final double SUPERPOSITION_RATIO = 1.15;
    private static final Integer TAILLE_FOND = 800;
    private static final double ASSOMBRISSEMENT = 0.65; // 65% de luminosité

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

        ImageView imageFondView = new ImageView(new Image(getClass().getResource("/META-INF/assetsGraphiques/background.jpeg").toExternalForm()));
        imageFondView.setFitWidth(TAILLE_FOND);
        imageFondView.setFitHeight(TAILLE_FOND);
        imageFondView.setPreserveRatio(false);
        imageFondView.setSmooth(false);

        Rectangle overlay = new Rectangle(TAILLE_FOND, TAILLE_FOND, Color.BLACK);
        overlay.setOpacity(1 - ASSOMBRISSEMENT);

        StackPane root = new StackPane();
        root.getChildren().addAll(imageFondView, overlay, gridPane);

        bouttons = new Button[7][7];
        for (int i = 0; i < jeu.getTaille(); i++) {
            for (int j = 0; j < jeu.getTaille(); j++) {
                bouttons[i][j] = new Button();
                int x = i, y = j;
                bouttons[i][j].setOnAction(e -> activerElement(x, y));

                URL resource = getResourceElement(i, j);
                if (resource != null) {
                    bouttons[i][j].setGraphic(creerImageView(resource, 100));
                }

                bouttons[i][j].setStyle("-fx-background-color: transparent; -fx-padding: 0; -fx-border-width: 0; -fx-background-insets: 0;");
                gridPane.add(bouttons[i][j], j, i);
            }
        }

        HBox controlPanel = new HBox(10);
        controlPanel.setAlignment(Pos.CENTER);

        Button buttonRetour = new Button("Retour");
        buttonRetour.setOnAction(e -> jeu.retour());

        Button bouttonAvancer = new Button("Avancer");
        bouttonAvancer.setOnAction(e -> jeu.avancer());

        Button bouttonCheck = new Button("Vérifier victoire");
        bouttonCheck.setOnAction(e -> check());

        labelTemps = new Label("Temps: 0s");

        controlPanel.getChildren().addAll(buttonRetour, bouttonAvancer, bouttonCheck, labelTemps);
        BorderPane mainLayout = new BorderPane();
        mainLayout.setCenter(root);
        mainLayout.setBottom(controlPanel);

        Scene scene = new Scene(mainLayout, 900, 900);
        primaryStage.setTitle("Jeu Interface");
        primaryStage.setScene(scene);
        primaryStage.show();

        scene.widthProperty().addListener((obs, oldVal, newVal) -> ajusterTailleImages());
        scene.heightProperty().addListener((obs, oldVal, newVal) -> ajusterTailleImages());
    }

    /**
     * Ajuste la taille des images des iles et des liens en fonction de la
     * taille du background.
     */
    private void ajusterTailleImages() {
        double taille = Math.min(TAILLE_FOND, TAILLE_FOND) / jeu.getTaille();
        double tailleImage = taille * SUPERPOSITION_RATIO;
        for (int i = 0; i < jeu.getTaille(); i++) {
            for (int j = 0; j < jeu.getTaille(); j++) {
                ImageView imageView = creerImageView(getResourceElement(i, j), tailleImage);
                bouttons[i][j].setGraphic(imageView);
                bouttons[i][j].setMinSize(taille, taille);
                bouttons[i][j].setMaxSize(taille, taille);
            }
        }
    }

    /**
     * Crée une image view à partir d'une ressource et d'une taille.
     *
     * @param resource L'URL de la ressource
     * @param size La taille de l'image
     * @return L'image view
     */
    private ImageView creerImageView(URL resource, double size) {
        Image image = new Image(resource.toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(size);
        imageView.setFitHeight(size);
        imageView.setPreserveRatio(false);
        return imageView;
    }

    /**
     * Récupère l'URL de la ressource pour un élément du jeu.
     *
     * @param i L'indice de la ligne
     * @param j L'indice de la colonne
     * @return L'URL de la ressource
     */
    private URL getResourceElement(int i, int j) {
        if (jeu.getPlateau().getElement(i, j) != null) {
            return getClass().getResource((String) jeu.getPlateau().getElement(i, j).draw());
        }
        return getClass().getResource("/META-INF/assetsGraphiques/link/blank.png");
    }

    /**
     * Active un élément du jeu.
     *
     * @param x L'indice de la ligne
     * @param y L'indice de la colonne
     */
    private void activerElement(int x, int y) {
        jeu.activeElemJeu(x, y, null);
        mettreAJourAffichage();
    }

    /**
     * Met à jour l'affichage du jeu.
     */
    private void mettreAJourAffichage() {
        ajusterTailleImages();
    }

    /**
     * Vérifie si le joueur a gagné.
     */
    private void check() {
        if (jeu.gagner()) {
            System.out.println("Vous avez gagné!");
        }
    }

    public static void main(String[] args) {
        Application.launch(PartieAffichage.class, args);
    }
}
