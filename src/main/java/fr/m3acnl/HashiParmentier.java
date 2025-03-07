package fr.m3acnl;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class HashiParmentier extends Application {

    private Stage primaryStage;
    private Scene mainScene;
    private Scene settingsScene;
    private Scene levelSelectionScene;
    private Scene confirmQuitScene;
    private Scene aideScene;
    private boolean isInGame = false;  // Suivi de l'état du jeu (en cours ou non)

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Jeu - Menu Principal");
        
        // 📌 Charger l'image de fond
        String imagePath = "file:src/main/img/DessinFondMenu.jpeg";
        Image backgroundImage = new Image(imagePath);
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, 
                new BackgroundSize(100, 100, true, true, false, true));
        
        // 📌 SCÈNE PRINCIPALE
        BorderPane root = new BorderPane();
        root.setBackground(new Background(background));

        // 📌 Icône utilisateur à gauche
        ImageView userIcon = new ImageView(new Image("file:src/main/img/logo_user.png"));
        userIcon.setFitWidth(50);
        userIcon.setFitHeight(50);

        // 📌 Icône réglages en haut à droite (⚙️)
        ImageView settingsIcon = new ImageView(new Image("file:src/main/img/logo_pause.png"));
        settingsIcon.setFitWidth(50);
        settingsIcon.setFitHeight(50);
        Button settingsButton = new Button("", settingsIcon);
        settingsButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
        settingsButton.setOnAction(e -> showSettingsMenu());

        // 📌 Placer les icônes dans un HBox en haut à droite
        HBox topBox = new HBox(10, userIcon, settingsButton);  // 10px d'écart entre les icônes
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.TOP_RIGHT);
        root.setTop(topBox);

        // 📌 Centre avec boutons "Jouer" et "Quitter le jeu"
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);

        Button playButton = createStyledButton("Jouer");
        playButton.setOnAction(e -> startGame());

        Button quitButton = createStyledButton("Quitter le jeu");
        quitButton.setOnAction(e -> showConfirmQuitPage());

        centerBox.getChildren().addAll(playButton, quitButton);
        root.setCenter(centerBox);
        
        mainScene = new Scene(root, 500, 400);
        mainScene.getStylesheets().add("file:src/main/resources/style.css");

        // 📌 PAGE DES RÉGLAGES
        VBox vboxSettings = new VBox(15);
        vboxSettings.setAlignment(Pos.CENTER);
        vboxSettings.setBackground(new Background(background));

        Label settingsTitle = createStyledLabel("Réglages");

        // 📌 Volume sonore
        Label volumeLabel = createStyledLabel("Volume des effets sonores");
        Slider volumeSlider = new Slider(0, 100, 50);
        volumeSlider.setMaxWidth(150);
        
        // 📌 Boutons divers
        Button buttonParamAffichage = createStyledButton("Paramètres d'affichage");
        Button buttonNiveauAide = createStyledButton("Niveau d'aide");
        buttonNiveauAide.setOnAction(e -> showAidePage());

        Button buttonQuitterPartie = createStyledButton("Quitter la partie");
        buttonQuitterPartie.setOnAction(e -> System.out.println("Quitter la partie et sauvegarder..."));

        Button buttonQuitterJeu = createStyledButton("Quitter le jeu");
        buttonQuitterJeu.setOnAction(e -> showConfirmQuitPage());

        Button buttonRetour = createStyledButton("Retour");
        buttonRetour.setOnAction(e -> primaryStage.setScene(mainScene));

        // Afficher ou masquer le bouton "Quitter la partie" en fonction de l'état du jeu
        if (isInGame) {
            vboxSettings.getChildren().addAll(settingsTitle, volumeLabel, volumeSlider, 
                                              buttonParamAffichage, buttonNiveauAide, 
                                              buttonQuitterPartie, buttonQuitterJeu, buttonRetour);
        } else {
            vboxSettings.getChildren().addAll(settingsTitle, volumeLabel, volumeSlider, 
                                              buttonParamAffichage, buttonNiveauAide, 
                                              buttonQuitterJeu, buttonRetour);
        }

        settingsScene = new Scene(vboxSettings, 500, 400);

        // 📌 PAGE DE SÉLECTION DE NIVEAU
        VBox vboxLevels = new VBox(15);
        vboxLevels.setAlignment(Pos.CENTER);
        vboxLevels.setBackground(new Background(background));

        Button level1 = createStyledButton("Facile");
        level1.setOnAction(e -> System.out.println("Lancement du Facile..."));

        Button level2 = createStyledButton("Moyen");
        level2.setOnAction(e -> System.out.println("Lancement du Moyen..."));

        Button level3 = createStyledButton("Difficile");
        level3.setOnAction(e -> System.out.println("Lancement du Difficile..."));

        Button levelRetour = createStyledButton("Retour");
        levelRetour.setOnAction(e -> primaryStage.setScene(mainScene));

        Label levelTitle = createStyledLabel("Choisissez votre niveau de jeu :");
        vboxLevels.getChildren().addAll(levelTitle, level1, level2, level3, levelRetour);
        levelSelectionScene = new Scene(vboxLevels, 500, 400);

        // 📌 PAGE CONFIRMATION QUITTER
        VBox vboxConfirmQuit = new VBox(15);
        vboxConfirmQuit.setAlignment(Pos.CENTER);
        vboxConfirmQuit.setBackground(new Background(background));

        Label confirmQuitLabel = createStyledLabel("Voulez-vous vraiment quitter le jeu ?");

        Button buttonOuiQuitter = createStyledButton("Oui");
        buttonOuiQuitter.setOnAction(e -> Platform.exit());

        Button buttonNonQuitter = createStyledButton("Non");
        buttonNonQuitter.setOnAction(e -> primaryStage.setScene(mainScene));

        vboxConfirmQuit.getChildren().addAll(confirmQuitLabel, buttonOuiQuitter, buttonNonQuitter);
        confirmQuitScene = new Scene(vboxConfirmQuit, 500, 400);

        // 📌 PAGE AIDE
        VBox vboxAide = new VBox(15);
        vboxAide.setAlignment(Pos.CENTER);
        vboxAide.setBackground(new Background(background));

        Label aideTitle = createStyledLabel("Choisissez un niveau d'aide :");

        Button niveau0 = createStyledButton("Facile");
        Button niveau1 = createStyledButton("Moyen");
        Button niveau3 = createStyledButton("Difficile");

        Button retourAide = createStyledButton("Retour");
        retourAide.setOnAction(e -> primaryStage.setScene(settingsScene));

        vboxAide.getChildren().addAll(aideTitle, niveau0, niveau1, niveau3, retourAide);
        aideScene = new Scene(vboxAide, 500, 400);

        // 📌 Lancer l'application avec la scène principale
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // 📌 Méthodes de navigation
    private void showSettingsMenu() {
        primaryStage.setScene(settingsScene);
    }

    private void showLevelSelection() {
        primaryStage.setScene(levelSelectionScene);
    }

    private void showConfirmQuitPage() {
        primaryStage.setScene(confirmQuitScene);
    }

    private void showAidePage() {
        primaryStage.setScene(aideScene);
    }

    // 📌 Méthode pour démarrer une partie
    private void startGame() {
        isInGame = true;  // Mettre à jour l'état pour indiquer que nous sommes en jeu
        primaryStage.setScene(levelSelectionScene);  // Aller à la page de sélection du niveau
    }

    // 📌 Création d'un bouton stylisé
    private Button createStyledButton(String text) {
        Button button = new Button(text);
        button.setStyle("-fx-font-family: 'Arial'; " 
                        + "-fx-font-size: 16px; " 
                        + "-fx-text-fill: black; " 
                        + "-fx-background-color: linear-gradient(#f5e6b8, #e4c98f); " 
                        + "-fx-background-radius: 25; "  
                        + "-fx-padding: 10px 20px; "
                        + "-fx-border-color: transparent; " 
                        + "-fx-border-width: 0;");
        return button;
    }

    // 📌 Création d'un label stylisé
    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-family: 'Arial'; " 
                       + "-fx-font-size: 18px; " 
                       + "-fx-text-fill: black; " 
                       + "-fx-background-color: rgba(255, 255, 255, 0.6); " 
                       + "-fx-padding: 5px 10px; " 
                       + "-fx-background-radius: 10px;");
        return label;
    }

    public static void main(String[] args) {
        launch(args);
        return;
    }
}