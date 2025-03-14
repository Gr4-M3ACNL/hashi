package fr.m3acnl;

import java.util.List;
import java.util.Optional;

import fr.m3acnl.game.Difficulte;
import fr.m3acnl.game.affichage.PartieAffichage;
import fr.m3acnl.managers.ProfileManager;
import fr.m3acnl.profile.Profile;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.Slider;
import javafx.scene.control.TextInputDialog;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe principale de l'application HashiParmentier.
 */
public class HashiParmentier extends Application {

    /**
     * Stage principal de l'application.
     */
    private Stage primaryStage;
    /**
     * Scène principale de l'application.
     */
    private Scene mainScene;
    /**
     * Scènes de reglages.
     */
    private Scene settingsScene;
    /**
     * Scène de sélection de niveau.
     */
    private Scene levelSelectionScene;
    /**
     * Scène de confirmation de quitter.
     */
    private Scene confirmQuitScene;
    /**
     * Scène d'aide.
     */
    private Scene aideScene;
    /**
     * Scène de sélection de profil.
     */
    private Scene profileSelectionScene; // Nouvelle scène pour la sélection de profil
    /**
     * Indique si une partie est en cours.
     */
    private boolean isInGame = false;  // Suivi de l'état du jeu (en cours ou non)

    /**
     * Constructeur vide de la classe HashiParmentier.
     */
    public HashiParmentier() {
        // Constructeur vide
    }

    /**
     * Méthode start de l'application JavaFX.
     *
     * @param primaryStage Stage principal de l'application.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Jeu - Menu Principal");

        // 📌 Charger l'image de fond
        Image backgroundImage = new Image(getClass().getResource("/META-INF/assetsGraphiques/back/backMenu.jpeg").toExternalForm());
        BackgroundImage background = new BackgroundImage(backgroundImage, BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));

        // 📌 SCÈNE PRINCIPALE
        BorderPane root = new BorderPane();
        root.setBackground(new Background(background));

        // 📌 Icône utilisateur à gauche
        ImageView userIcon = new ImageView(new Image(getClass().getResource("/META-INF/assetsGraphiques/icon/utilisateur.png").toExternalForm()));
        userIcon.setFitWidth(50);
        userIcon.setFitHeight(50);

        // 📌 Icône réglages en haut à droite (⚙️)
        ImageView settingsIcon = new ImageView(new Image(getClass().getResource("/META-INF/assetsGraphiques/icon/parametre.png").toExternalForm()));
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
        playButton.setOnAction(e -> showProfileSelectionPage()); // Changer pour afficher la page de sélection de profil

        Button quitButton = createStyledButton("Quitter le jeu");
        quitButton.setOnAction(e -> showConfirmQuitPage());

        centerBox.getChildren().addAll(playButton, quitButton);
        root.setCenter(centerBox);

        mainScene = new Scene(root, 500, 400);

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
                    buttonParamAffichage, buttonNiveauAide, buttonQuitterJeu, buttonRetour);
        }

        settingsScene = new Scene(vboxSettings, 500, 400);

        // 📌 PAGE DE SÉLECTION DE NIVEAU
        VBox vboxLevels = new VBox(15);
        vboxLevels.setAlignment(Pos.CENTER);
        vboxLevels.setBackground(new Background(background));

        Button level1 = createStyledButton("Facile");
        level1.setOnAction(e -> lancerPartieAffichage(Difficulte.facile));

        Button level2 = createStyledButton("Moyen");
        level2.setOnAction(e -> lancerPartieAffichage(Difficulte.moyen));

        Button level3 = createStyledButton("Difficile");
        level3.setOnAction(e -> lancerPartieAffichage(Difficulte.difficile));

        Button level4 = createStyledButton("expert");
        level4.setOnAction(e -> lancerPartieAffichage(Difficulte.expert));

        Button levelRetour = createStyledButton("Retour");
        levelRetour.setOnAction(e -> primaryStage.setScene(mainScene));

        Label levelTitle = createStyledLabel("Choisissez votre niveau de jeu :");
        vboxLevels.getChildren().addAll(levelTitle, level1, level2, level3, level4, levelRetour);
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

        // 📌 PAGE DE SÉLECTION DE PROFIL
        VBox vboxProfileSelection = new VBox(15);
        vboxProfileSelection.setAlignment(Pos.CENTER);
        vboxProfileSelection.setBackground(new Background(background));

        final Label profileLabel = createStyledLabel("Veuillez vous connecter pour jouer");

        Button loadProfileButton = createStyledButton("Charger un profil");
        loadProfileButton.setOnAction(e -> loadProfile());

        Button createProfileButton = createStyledButton("Créer un profil");
        createProfileButton.setOnAction(e -> createProfile());

        Button returnToMainMenu = createStyledButton("Retour");
        returnToMainMenu.setOnAction(e -> primaryStage.setScene(mainScene));

        vboxProfileSelection.getChildren().addAll(profileLabel, loadProfileButton, createProfileButton, returnToMainMenu);
        profileSelectionScene = new Scene(vboxProfileSelection, 500, 400);

        // 📌 Lancer l'application avec la scène principale
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // 📌 Méthodes de navigation
    /**
     * Affiche le menu des réglages.
     */
    private void showSettingsMenu() {
        primaryStage.setScene(settingsScene);
    }

    /**
     * Affiche la page de sélection de niveau.
     */
    private void showLevelSelection() {
        primaryStage.setScene(levelSelectionScene);
    }

    /**
     * Affiche la page de confirmation de quitter.
     */
    private void showConfirmQuitPage() {
        primaryStage.setScene(confirmQuitScene);
    }

    /**
     * Affiche la page d'aide.
     */
    private void showAidePage() {
        primaryStage.setScene(aideScene);
    }

    /**
     * Affiche la page de sélection de profil.
     */
    private void showProfileSelectionPage() {
        primaryStage.setScene(profileSelectionScene);  // Changement pour afficher la page de sélection de profil
    }

    // 📌 Méthode pour créer un profil
    /**
     * Crée un profil.
     */
    private void createProfile() {
        // Créer une instance de TextInputDialog
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Créer un profil");
        //dialog.setHeaderText("Entrez votre nom de profil :");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setHeader(null);

        dialog.setGraphic(null);
        // Appliquer le style CSS au DialogPane et aux éléments internes
        dialog.setContentText("Entrez votre nom de profil :  ");

        dialog.getDialogPane().setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: #3d1e10; -fx-background-color: #f8f1e1;");
        // Appliquer un style spécifique à l'input text
        dialog.getEditor().setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: #3d1e10;");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            ProfileManager.getInstance().creerProfil(name); // Crée et sauvegarde le profil
            ProfileManager.getInstance().setProfileActif(name); // Définit le profil actif
            System.out.println("Profil créé et sauvegardé : " + name);

            // Recharge la liste des profils pour que le nouveau apparaisse
            showProfileSelectionPage();
        });
    }

    /**
     * Charge un profil.
     */
    private void loadProfile() {
        List<String> profileNames = ProfileManager.getInstance().listeProfils();

        if (profileNames.isEmpty()) {
            alerteProfile();
            return;
            /*// Création d'un profil par défaut
            String defaultProfile = "profil_par_defaut";
            ProfileManager.getInstance().creerProfil(defaultProfile);
            ProfileManager.getInstance().setProfileActif(defaultProfile);
            System.out.println("Aucun profil existant, création du profil par défaut : " + defaultProfile);

            // Recharger la liste après création du profil
            profileNames = ProfileManager.getInstance().listeProfils();*/
        }

        // Création de la fenêtre modale
        Stage dialogStage = new Stage();
        dialogStage.initModality(Modality.APPLICATION_MODAL);
        dialogStage.setTitle("Sélection du profil");

        VBox root = new VBox(15);
        root.setAlignment(Pos.CENTER);
        root.setStyle("-fx-background-color: #f8f1e1; -fx-padding: 20px; -fx-border-radius: 10px;");

        Label titleLabel = new Label("Choisissez un profil :");
        titleLabel.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 18px; -fx-text-fill: black;");

        // Menu déroulant (ComboBox)
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.getItems().addAll(profileNames);
        comboBox.setValue(profileNames.get(0)); // Sélection par défaut
        comboBox.setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: #3d1e10;");
        comboBox.setStyle(comboBox.getStyle() + "-fx-background-color: #f0f0f0;"); // Fond de la ComboBox

        // Personnalisation du fond de la liste déroulante (popup)
        comboBox.setCellFactory(lv -> {
            ListCell<String> cell = new ListCell<>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setStyle("-fx-background-color: #ffffff;"); // Fond de chaque élément
                    } else {
                        setText(item);
                        setStyle("-fx-background-color: #ffffff; -fx-font-size: 16px; -fx-text-fill: #3d1e10"); // Fond de chaque élément
                    }
                }
            };
            return cell; // Il faut renvoyer le cell ici
        });

        // Bouton de validation
        Button confirmButton = createStyledButton("Valider");
        confirmButton.setOnAction(event -> {
            String selectedProfile = comboBox.getValue();
            if (selectedProfile != null) {
                ProfileManager.getInstance().setProfileActif(selectedProfile);
                Profile profile = ProfileManager.getInstance().getProfileActif();
                System.out.println("Profil chargé : " + profile.getNom());
                dialogStage.close();
                startGame();
            }
        });

        // Ajout des éléments à la fenêtre
        root.getChildren().addAll(titleLabel, comboBox, confirmButton);

        Scene scene = new Scene(root, 300, 200);
        dialogStage.setScene(scene);
        dialogStage.showAndWait();
    }

    private void alerteProfile() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Attention !");
        alert.setHeaderText(null);

        // Style du DialogPane avec image de fond
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-image: url('/META-INF/assetsGraphiques/back/backExit.png');"
                + "-fx-background-size: cover;");

        // Image de gauche (agrandie de 30%)
        ImageView exitImage = new ImageView(new Image(getClass().getResource("/META-INF/assetsGraphiques/character/goodbye.png").toExternalForm()));
        exitImage.setFitWidth(130);
        exitImage.setFitHeight(130);

        // Texte de confirmation
        Label message = new Label("Il est necessaire de créer un profil pour jouer.");
        message.setWrapText(true);
        message.setStyle("-fx-font-size: 14px; -fx-font-family: 'Georgia'; -fx-text-fill: black;");

        // Conteneur principal (Image + Texte)
        HBox content = new HBox(20, exitImage, message);
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
    // 📌 Méthode pour démarrer une partie

    /**
     * Démarre une partie.
     */
    private void startGame() {
        isInGame = true;  // Mettre à jour l'état pour indiquer que nous sommes en jeu
        primaryStage.setScene(levelSelectionScene);  // Aller à la page de sélection du niveau
    }

    // 📌 Création d'un bouton stylisé
    /**
     * Crée un bouton stylisé.
     *
     * @param text Texte du bouton.
     * @return Le bouton créé.
     */
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
    /**
     * Crée un label stylisé.
     *
     * @param text Texte du label.
     * @return Le label créé.
     */
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

    /**
     * Lance une partie en mode affichage.
     *
     * @param difficulte Difficulté de la partie.
     * @see PartieAffichage
     */
    private void lancerPartieAffichage(Difficulte difficulte) {
        PartieAffichage partieAffichage = new PartieAffichage(difficulte);
        Stage stage = new Stage();
        try {
            partieAffichage.start(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Méthode main de l'application.
     *
     * @param args Arguments passés en ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
        return;
    }
}
