package fr.m3acnl.game.affichage;

import fr.m3acnl.game.logique.Jeu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;
import javafx.geometry.Pos;

public class PartieAffichage extends Application {
    private Jeu jeu;
    private Label timeLabel;
    private Button[][] buttons;
    private Timeline timeline;

    @Override
    public void start(Stage primaryStage) {
        System.out.println("Such a beautiful start!");
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

        BorderPane root = new BorderPane();

        // GridPane pour le plateau de jeu
        GridPane gridPane = new GridPane();
        buttons = new Button[7][7];
        System.out.println("Lets do the for Yppie!!");
        System.out.println("I'll do "+jeu.getTaille()+" iterations !!");
        for (int i = 0; i < jeu.getTaille(); i++) {
            System.out.println("Can I go in?");
            for (int j = 0; j < jeu.getTaille(); j++) {
                System.out.print("Alright,I'm in!");
                buttons[i][j] = new Button();
                int x = i;
                int y = j;
                buttons[i][j].setOnAction(e -> activerElement(x, y));

                // Définir l'image (bouton) en fonction de l'élément
                String imagePath;
                System.out.print("The Element is");
                if (jeu.getPlateau().getElement(i, j) != null) {
                    imagePath = jeu.getPlateau().getElement(i, j).draw();
                    System.out.println("Not Null");
                } else {
                    imagePath = getClass().getResource("META-INF/assetsGraphiques/link/blank.png").toExternalForm();
                    System.out.println("Null");
                }
                Image image = new Image(imagePath);
                ImageView imageView = new ImageView(image);
                imageView.setFitWidth(50);
                imageView.setFitHeight(50);
                buttons[i][j].setGraphic(imageView);
                
                gridPane.add(buttons[i][j], j, i);
            }
        }
        root.setCenter(gridPane);

        // Panneau de contrôle
        HBox controlPanel = new HBox(10);
        controlPanel.setAlignment(Pos.CENTER);

        Button undoButton = new Button("Retour");
        undoButton.setOnAction(e -> jeu.retour());
        
        Button redoButton = new Button("Avancer");
        redoButton.setOnAction(e -> jeu.avancer());
        
        Button checkWinButton = new Button("Vérifier victoire");
        checkWinButton.setOnAction(e -> checkWin());
        
        timeLabel = new Label("Temps: 0s");
        
        controlPanel.getChildren().addAll(undoButton, redoButton, checkWinButton, timeLabel);
        root.setBottom(controlPanel);

        // Timer pour la mise à jour du temps
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), e -> updateTime()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
        
        Scene scene = new Scene(root, 600, 600);
        primaryStage.setTitle("Jeu Interface");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void activerElement(int x, int y) {
        jeu.activeElemJeu(x, y, null);
        buttons[x][y].setText("X");
    }

    private void checkWin() {
        if (jeu.gagner()) {
            System.out.println("Vous avez gagné!");
        }
    }

    private void updateTime() {
        timeLabel.setText("Temps: " + jeu.getTempsEcouler() + "s");
        
    }

    public static void main(String[] args) {
        Application.launch(PartieAffichage.class, args);
    }

}