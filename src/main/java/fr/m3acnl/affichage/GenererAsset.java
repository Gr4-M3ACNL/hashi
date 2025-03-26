package fr.m3acnl.affichage;

import java.net.URL;
import java.util.Optional;

import fr.m3acnl.HashiParmentier;
import fr.m3acnl.game.Partie;
import fr.m3acnl.managers.ProfileManager;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * Classe pour générer les menus partager entres les affichages.
 *
 * @author MABIRE Aymeric, TOUISSI Nassim
 */
public class GenererAsset {

    // ======================== Attributs ========================
    /**
     * Image de fond des menus.
     */
    BackgroundImage background;
    /**
     * Image de fond de l'alerte.
     */
    String backAlerte = "/META-INF/assetsGraphiques/back/backAlerte.png";

    /**
     * Constructeur de la classe GenererAsset.
     *
     * @param backgroundPath Chemin de l'image de fond.
     */
    public GenererAsset(String backgroundPath) {
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

        Label confirmQuitLabel = creerLabelStyle("Voulez-vous vraiment quitter le jeu ?");

        Button buttonOuiQuitter = creerBoutonStyle("Oui");
        buttonOuiQuitter.setOnAction(e -> Platform.exit());

        Button buttonNonQuitter = creerBoutonStyle("Non");
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

        Label settingsTitle = creerLabelStyle("Réglages");

        Label volumeLabel = creerLabelStyle("Volume des effets sonores");
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

        Button buttonParamAffichage = creerBoutonStyle("Tutoriel");
        buttonParamAffichage.setOnAction(e -> {
            showAidePage(primaryStage, creerSlideshow(primaryStage, mainScene));
            jouerSon("bouton.wav",
                    ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
        });
        Button buttonNiveauAide = creerBoutonStyle("Niveau d'aide");
        buttonNiveauAide.setOnAction(e -> {
            showAidePage(primaryStage, creerMenuAide(primaryStage, mainScene));
            jouerSon("bouton.wav",
                    ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
        });

        Button buttonQuitterPartie = creerBoutonStyle("Quitter la partie");
        buttonQuitterPartie.setOnAction(e -> {
            jouerSon("bouton.wav", ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
            primaryStage.close();
            relancerPartie();
        });

        Button buttonQuitterJeu = creerBoutonStyle("Quitter le jeu");
        buttonQuitterJeu.setOnAction(e -> {
            showConfirmQuitPage(primaryStage, creerMenuQuitter(primaryStage, mainScene));
            jouerSon("bouton.wav", ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
        }
        );

        Button buttonRetour = creerBoutonStyle("Retour");
        buttonRetour.setOnAction(e -> {
            if (partie != null) {
                partie.startChrono();
            }
            jouerSon("bouton.wav", ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
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
        VBox vboxAide = new VBox(15);
        vboxAide.setAlignment(Pos.CENTER);
        vboxAide.setBackground(new Background(background));

        Label aideTitle = creerLabelStyle("Choisissez un niveau d'aide :");

        Button niveau0 = creerBoutonStyle("Facile");
        Button niveau1 = creerBoutonStyle("Moyen");
        Button niveau3 = creerBoutonStyle("Difficile");

        niveau0.setOnAction(e -> {
            ProfileManager.getInstance().getProfileActif().getParametre().setNiveauAide(0);
            jouerSon("bouton.wav", ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
            primaryStage.setScene(settingsScene);
        });
        niveau1.setOnAction(e -> {
            ProfileManager.getInstance().getProfileActif().getParametre().setNiveauAide(1);
            jouerSon("bouton.wav", ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
            primaryStage.setScene(settingsScene);
        });
        niveau3.setOnAction(e -> {
            ProfileManager.getInstance().getProfileActif().getParametre().setNiveauAide(2);
            jouerSon("bouton.wav", ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
            primaryStage.setScene(settingsScene);
        });

        Button retourAide = creerBoutonStyle("Retour");
        retourAide.setOnAction(e -> {
            primaryStage.setScene(settingsScene);
            jouerSon("bouton.wav", ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
        });

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

    /**
     * Affiche l'alerte si aucun profil n'existe.
     */
    public void alerteProfile() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Attention !");
        alert.setHeaderText(null);

        // Style du DialogPane avec image de fond
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-image: url('" + backAlerte + "');"
                + "-fx-background-size: cover;");

        // Image de gauche (agrandie de 30%)
        ImageView attention = creerImageView("/META-INF/assetsGraphiques/character/sky.png", 150, 150);

        // Texte de confirmation
        Label message = new Label("Il est necessaire de créer un profil pour jouer.");
        message.setWrapText(true);
        message.setStyle("-fx-font-size: 14px; -fx-font-family: 'Georgia'; -fx-text-fill: black;");

        // Conteneur principal (Image + Texte)
        HBox content = new HBox(20, attention, message);
        content.setAlignment(Pos.CENTER_LEFT);

        // Création des boutons
        ButtonType boutonQuitter = new ButtonType("Ok", ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(boutonQuitter);

        // Style des boutons
        String buttonStyle = "-fx-background-color: linear-gradient(#7a5230, #4a2c14);"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: #3d1e10;"
                + "-fx-border-width: 2px;"
                + "-fx-border-radius: 10;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-family: 'Georgia';";

        // Centrer les boutons
        HBox buttonBox = new HBox(10, dialogPane.lookupButton(boutonQuitter));
        buttonBox.setAlignment(Pos.CENTER);

        // Appliquer le style aux boutons
        buttonBox.getChildren().forEach(button -> button.setStyle(buttonStyle));

        // Organisation du layout général
        VBox root = new VBox(20, content, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        dialogPane.setContent(root);

        // Affichage de l'alerte et récupération de la réponse
        alert.showAndWait();
    }

    /**
     * Demande à l'utilisateur s'il veut vraiment quitter. Si oui, ferme
     * l'application.
     *
     * @param event L'événement de fermeture
     */
    public void demandeSortie(javafx.stage.WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation");
        alert.setHeaderText(null);

        // Style du DialogPane avec image de fond
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-image: url('" + backAlerte + "');"
                + "-fx-background-size: cover;");

        // Image de gauche (agrandie de 30%)
        ImageView exitImage = creerImageView("/META-INF/assetsGraphiques/character/goodbye.png", 130, 130);

        // Texte de confirmation
        Label message = new Label("Voulez-vous vraiment quitter ?");
        message.setWrapText(true);
        message.setStyle("-fx-font-size: 14px; -fx-font-family: 'Georgia'; -fx-text-fill: black;");

        // Conteneur principal (Image + Texte)
        HBox content = new HBox(20, exitImage, message);
        content.setAlignment(Pos.CENTER_LEFT);

        // Création des boutons
        ButtonType boutonQuitter = new ButtonType("Oui", ButtonBar.ButtonData.OK_DONE);
        ButtonType boutonAnnuler = new ButtonType("Non", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(boutonQuitter, boutonAnnuler);

        // Style des boutons
        String buttonStyle = "-fx-background-color: linear-gradient(#7a5230, #4a2c14);"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: #3d1e10;"
                + "-fx-border-width: 2px;"
                + "-fx-border-radius: 10;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-family: 'Georgia';";

        // Centrer les boutons
        HBox buttonBox = new HBox(10, dialogPane.lookupButton(boutonQuitter), dialogPane.lookupButton(boutonAnnuler));
        buttonBox.setAlignment(Pos.CENTER);

        // Appliquer le style aux boutons
        buttonBox.getChildren().forEach(button -> button.setStyle(buttonStyle));

        // Organisation du layout général
        VBox root = new VBox(20, content, buttonBox);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(20));
        dialogPane.setContent(root);

        // Affichage de l'alerte et récupération de la réponse
        Optional<ButtonType> result = alert.showAndWait();

        // Vérification de la réponse
        if (result.isEmpty() || result.get() == boutonAnnuler) {
            event.consume(); // Annule la fermeture de l'application
        } else {
            Platform.exit();
        }
    }

    /**
     * Relance une nouvelle appli.
     */
    public void relancerPartie() {
        Platform.runLater(() -> {
            try {
                Stage newStage = new Stage();
                HashiParmentier app = new HashiParmentier();
                app.start(newStage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });
    }

    /**
     * Crée un diaporama pour le tutoriel.
     *
     * @param primaryStage La fenêtre principale.
     * @param mainScene La scène principale.
     * @return La scène créée.
     */
    public Scene creerSlideshow(Stage primaryStage, Scene mainScene) {
        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setBackground(new Background(background));

        String[] imagePaths = {
            "/META-INF/assetsTuto/1.png",
            "/META-INF/assetsTuto/2.png",
            "/META-INF/assetsTuto/3.png",
            "/META-INF/assetsTuto/4.png",
            "/META-INF/assetsTuto/5.png",
            "/META-INF/assetsTuto/6.png",
            "/META-INF/assetsTuto/7.png",
            "/META-INF/assetsTuto/8.png",
            "/META-INF/assetsTuto/9.png",
            "/META-INF/assetsTuto/10.png",
            "/META-INF/assetsTuto/11.png",
            "/META-INF/assetsTuto/12.png",
            "/META-INF/assetsTuto/13.png",
            "/META-INF/assetsTuto/14.png",
            "/META-INF/assetsTuto/15.png",
            "/META-INF/assetsTuto/16.png"
        };

        int[] currentIndex = {0};

        ImageView imageView = creerImageView(imagePaths[currentIndex[0]], 1100, 1000);
        imageView.setPreserveRatio(true);
        imageView.fitWidthProperty().bind(root.widthProperty());
        imageView.fitHeightProperty().bind(root.heightProperty().subtract(50)); // Laisser de la place aux boutons

        Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(6), event -> {
            currentIndex[0] = (currentIndex[0] + 1) % imagePaths.length;
            imageView.setImage(new Image(getClass().getResource(imagePaths[currentIndex[0]]).toExternalForm()));
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();

        // Bouton "Précédent"
        Button buttonPrecedent = creerBoutonStyle("Précédent");
        buttonPrecedent.setOnAction(e -> {
            currentIndex[0] = (currentIndex[0] - 1 + imagePaths.length) % imagePaths.length;
            imageView.setImage(new Image(getClass().getResource(imagePaths[currentIndex[0]]).toExternalForm()));
            timeline.stop();
            timeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(900), evt -> {
                currentIndex[0] = (currentIndex[0] + 1) % imagePaths.length;
                imageView.setImage(new Image(getClass().getResource(imagePaths[currentIndex[0]]).toExternalForm()));
            }));
            timeline.play();
        });

        // Bouton "Suivant"
        Button buttonSuivant = creerBoutonStyle("Suivant");
        buttonSuivant.setOnAction(e -> {
            currentIndex[0] = (currentIndex[0] + 1) % imagePaths.length;
            imageView.setImage(new Image(getClass().getResource(imagePaths[currentIndex[0]]).toExternalForm()));
            timeline.stop();
            timeline.getKeyFrames().setAll(new KeyFrame(Duration.seconds(900), evt -> {
                currentIndex[0] = (currentIndex[0] + 1) % imagePaths.length;
                imageView.setImage(new Image(getClass().getResource(imagePaths[currentIndex[0]]).toExternalForm()));
            }));
            timeline.play();
        });

        // Bouton "Retour"
        Button buttonRetour = creerBoutonStyle("Retour");
        buttonRetour.setOnAction(e -> primaryStage.setScene(mainScene));

        HBox buttonBox = new HBox(10, buttonPrecedent, buttonRetour, buttonSuivant);
        buttonBox.setAlignment(Pos.CENTER);

        root.getChildren().addAll(imageView, buttonBox);

        return new Scene(root, 1100, 1000);
    }

    // ======================== Création des éléments graphiques =========================
    /**
     * Crée un bouton stylisé.
     *
     * @param text Texte du bouton.
     * @return Le bouton créé.
     */
    public Button creerBoutonStyle(String text) {
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
    public Label creerLabelStyle(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-family: 'Arial'; "
                + "-fx-font-size: 18px; "
                + "-fx-text-fill: black; "
                + "-fx-background-color: rgba(255, 255, 255, 0.6); "
                + "-fx-padding: 5px 10px; "
                + "-fx-background-radius: 10px;");
        return label;
    }

    /**
     * Crée un fond d'écran à partir d'une image.
     *
     * @param backgroundPath Chemin de l'image de fond.
     * @return Le fond créé.
     */
    public Background createBackground(String backgroundPath) {
        BackgroundImage background = new BackgroundImage(
                new Image(getClass().getResource(backgroundPath).toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));
        return new Background(background);
    }

    /**
     * Crée un ImageView à partir d'une ressource et d'une taille.
     *
     * @param path Le chemin de la ressource
     * @param hauteur Hauteur de l'image
     * @param largeur Largeur de l'image
     * @return L'ImageView créé
     */
    public ImageView creerImageView(String path, double hauteur, double largeur) {
        URL resource = getClass().getResource(path);
        ImageView imageView = new ImageView(new Image(resource.toExternalForm(), 500 * 0.5, 500 * 0.5, true, true));
        imageView.setFitWidth(largeur);
        imageView.setFitHeight(hauteur);
        imageView.setCache(true);
        imageView.setSmooth(true);

        return imageView;
    }

    // ======================== Gestion des sons ========================
    /**
     * Joue un son à partir d'un fichier audio.
     *
     * @param fichierAudio Le fichier audio à jouer
     * @param volume Le volume du son (0.0 à 1.0)
     * @see AudioClip
     */
    public void jouerSon(String fichierAudio, double volume) {
        String chemin = getClass().getResource("/META-INF/assetsAudio/" + fichierAudio).toExternalForm();
        AudioClip son = new AudioClip(chemin);
        son.setVolume(volume); // Appliquer le volume
        if (volume > 0) {
            son.play();
        }
    }

}
