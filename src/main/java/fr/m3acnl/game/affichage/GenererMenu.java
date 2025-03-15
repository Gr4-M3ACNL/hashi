package fr.m3acnl.game.affichage;

import fr.m3acnl.game.Partie;
import fr.m3acnl.managers.ProfileManager;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe pour générer les menus partager entres les affichages.
 */
public class GenererMenu {

    // ======================== Attributs ========================
    /**
     * Image de fond des menus.
     */
    BackgroundImage background;

    /**
     * Constructeur de la classe GenererMenu.
     *
     * @param backgroundPath Chemin de l'image de fond.
     */
    public GenererMenu(String backgroundPath) {
        background = new BackgroundImage(
                new Image(getClass().getResource(backgroundPath).toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));
    }

    // ======================== Creation des menus ========================
    /**
     * Crée un menu de confirmation pour quitter.
     *
     * @param primaryStage Stage principal.
     * @param mainScene Scene principale.
     * @return La scène créée.
     */
    public Scene creerMenuQuitter(Stage primaryStage, Scene mainScene) {
        VBox vboxConfirmQuit = new VBox(15);
        vboxConfirmQuit.setAlignment(Pos.CENTER);
        vboxConfirmQuit.setBackground(new Background(background));

        Label confirmQuitLabel = createStyledLabel("Voulez-vous vraiment quitter le jeu ?");

        Button buttonOuiQuitter = createStyledButton("Oui");
        buttonOuiQuitter.setOnAction(e -> Platform.exit());

        Button buttonNonQuitter = createStyledButton("Non");
        buttonNonQuitter.setOnAction(e -> primaryStage.setScene(mainScene));

        vboxConfirmQuit.getChildren().addAll(confirmQuitLabel, buttonOuiQuitter, buttonNonQuitter);
        return new Scene(vboxConfirmQuit, 500, 400);
    }

    /**
     * Crée un menu de pause.
     *
     * @param primaryStage Stage principal.
     * @param mainScene Scene principale.
     * @param partie Partie en cours.
     * @return La scène créée.
     */
    public Scene creerMenuPause(Stage primaryStage, Scene mainScene, Partie partie) {
        if (partie != null) {
            partie.stopChrono();
        }
        VBox vboxSettings = new VBox(15);
        vboxSettings.setAlignment(Pos.CENTER);
        vboxSettings.setBackground(new Background(background));

        Label settingsTitle = createStyledLabel("Réglages");

        Label volumeLabel = createStyledLabel("Volume des effets sonores");
        Slider volumeSlider = new Slider(0, 100, ProfileManager.getInstance().getProfileActif() == null
                ? 50
                : ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore() * 100);
        volumeSlider.setMaxWidth(150);

        // Ajouter un listener pour suivre les changements
        volumeSlider.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                ProfileManager.getInstance().getProfileActif().getParametre().setVolumeEffetsSonore(newValue.floatValue() / 100);
            }
        });

        Button buttonParamAffichage = createStyledButton("Paramètres d'affichage");
        Button buttonNiveauAide = createStyledButton("Niveau d'aide");
        buttonNiveauAide.setOnAction(e -> showAidePage(primaryStage, creerMenuAide(primaryStage, mainScene)));

        Button buttonQuitterPartie = createStyledButton("Quitter la partie");
        buttonQuitterPartie.setOnAction(e -> System.out.println("Quitter la partie et sauvegarder..."));

        Button buttonQuitterJeu = createStyledButton("Quitter le jeu");
        buttonQuitterJeu.setOnAction(e -> showConfirmQuitPage(primaryStage, creerMenuQuitter(primaryStage, mainScene)));

        Button buttonRetour = createStyledButton("Retour");
        buttonRetour.setOnAction(e -> {
            if (partie != null) {
                partie.startChrono();
            }
            primaryStage.setScene(mainScene);

        });

        // Afficher ou masquer le bouton "Quitter la partie" en fonction de l'état du jeu
        vboxSettings.getChildren().addAll(settingsTitle, volumeLabel, volumeSlider,
                buttonParamAffichage, buttonNiveauAide,
                buttonQuitterPartie, buttonQuitterJeu, buttonRetour);

        return new Scene(vboxSettings, 500, 400);
    }

    /**
     * Crée un menu de selection de niveau d'aide.
     *
     * @param primaryStage La fenêtre principale.
     * @param settingsScene La scène d'affichage des réglages.
     * @return La scène créée.
     */
    public Scene creerMenuAide(Stage primaryStage, Scene settingsScene) {
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
        return new Scene(vboxAide, 500, 400);

    }

    /**
     * Affiche la page de confirmation de quitter.
     *
     * @param primaryStage La fenêtre principale.
     * @param confirmQuitScene La scène de confirmation de quitter.
     */
    public void showConfirmQuitPage(Stage primaryStage, Scene confirmQuitScene) {
        primaryStage.setScene(confirmQuitScene);
    }

    /**
     * Affiche la page d'aide.
     *
     * @param primaryStage La fenêtre principale.
     * @param aideScene La scène d'aide.
     */
    public void showAidePage(Stage primaryStage, Scene aideScene) {
        primaryStage.setScene(aideScene);
    }

    /**
     * Affiche le menu des réglages.
     *
     * @param primaryStage La fenêtre principale.
     * @param settingsScene La scène des réglages.
     */
    public void showSettingsMenu(Stage primaryStage, Scene settingsScene) {
        primaryStage.setScene(settingsScene);
    }

    // ======================== Création des éléments graphiques ========================
    /**
     * Crée un bouton stylisé.
     *
     * @param text Texte du bouton.
     * @return Le bouton créé.
     */
    public Button createStyledButton(String text) {
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

    /**
     * Crée un label stylisé.
     *
     * @param text Texte du label.
     * @return Le label créé.
     */
    public Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-family: 'Arial'; "
                + "-fx-font-size: 18px; "
                + "-fx-text-fill: black; "
                + "-fx-background-color: rgba(255, 255, 255, 0.6); "
                + "-fx-padding: 5px 10px; "
                + "-fx-background-radius: 10px;");
        return label;
    }

}
