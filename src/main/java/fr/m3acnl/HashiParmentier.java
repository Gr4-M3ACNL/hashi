package fr.m3acnl;

import java.util.List;
import java.util.Optional;

import fr.m3acnl.game.Difficulte;
import fr.m3acnl.game.affichage.GenererMenu;
import fr.m3acnl.game.affichage.PartieAffichage;
import fr.m3acnl.managers.ProfileManager;
import fr.m3acnl.profile.Profile;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe principale de l'application HashiParmentier.
 */
public class HashiParmentier extends Application {

    // ======================== Attributs ========================
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
     * Scène de sélection de profil.
     */
    private Scene profileSelectionScene;

    /**
     * Générateur de menu.
     */
    private final GenererMenu genererMenu = new GenererMenu("/META-INF/assetsGraphiques/back/backMenu.jpeg");

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
        BackgroundImage background = new BackgroundImage(
                new Image(getClass().getResource("/META-INF/assetsGraphiques/back/backMenu.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));
        // 📌 SCÈNE PRINCIPALE
        BorderPane root = new BorderPane();
        root.setBackground(new Background(background));
        /*
        // 📌 Icône utilisateur à gauche
        ImageView userIcon = new ImageView(
                new Image(getClass().getResource("/META-INF/assetsGraphiques/icon/utilisateur.png").toExternalForm()));
        userIcon.setFitWidth(50);
        userIcon.setFitHeight(50);

        // 📌 Icône réglages en haut à droite (⚙️)
        ImageView settingsIcon = new ImageView(
                new Image(getClass().getResource("/META-INF/assetsGraphiques/icon/parametre.png").toExternalForm()));
        settingsIcon.setFitWidth(50);
        settingsIcon.setFitHeight(50);
        Button settingsButton = new Button("", settingsIcon);
        settingsButton.setStyle("-fx-background-color: transparent; -fx-border-width: 0;");
        settingsButton.setOnAction(e -> genererMenu.showSettingsMenu(primaryStage, settingsScene));
        
        // 📌 Placer les icônes dans un HBox en haut à droite
        HBox topBox = new HBox(10, userIcon, settingsButton); // 10px d'écart entre les icônes
        topBox.setPadding(new Insets(10));
        topBox.setAlignment(Pos.TOP_RIGHT);
        root.setTop(topBox);*/
        // 📌 Centre avec boutons "Jouer" et "Quitter le jeu"
        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        Button playButton = genererMenu.createStyledButton("Jouer");
        playButton.setOnAction(e -> showProfileSelectionPage()); // Changer pour afficher la page de sélection de profil

        Button quitButton = genererMenu.createStyledButton("Quitter le jeu");
        quitButton.setOnAction(e -> genererMenu.showConfirmQuitPage(primaryStage, confirmQuitScene));

        centerBox.getChildren().addAll(playButton, quitButton);
        root.setCenter(centerBox);

        mainScene = new Scene(root, 500, 400);
        creerSelectionNiveau(background);
        confirmQuitScene = genererMenu.creerMenuQuitter(primaryStage, mainScene);
        settingsScene = genererMenu.creerMenuPause(primaryStage, mainScene, null);
        creerSelectionProfil(background);

        // 📌 Lancer l'application avec la scène principale
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // ======================== Gestion des scènes ========================
    private void creerSelectionProfil(BackgroundImage background) {
        VBox vboxProfileSelection = new VBox(15);
        vboxProfileSelection.setAlignment(Pos.CENTER);
        vboxProfileSelection.setBackground(new Background(background));

        final Label profileLabel = genererMenu.createStyledLabel("Veuillez vous connecter pour jouer");

        Button loadProfileButton = genererMenu.createStyledButton("Charger un profil");
        loadProfileButton.setOnAction(e -> loadProfile());

        Button createProfileButton = genererMenu.createStyledButton("Créer un profil");
        createProfileButton.setOnAction(e -> createProfile());

        Button returnToMainMenu = genererMenu.createStyledButton("Retour");
        returnToMainMenu.setOnAction(e -> primaryStage.setScene(mainScene));

        vboxProfileSelection.getChildren().addAll(profileLabel, loadProfileButton, createProfileButton,
                returnToMainMenu);
        profileSelectionScene = new Scene(vboxProfileSelection, 500, 400);
    }

    private void creerSelectionNiveau(BackgroundImage background) {
        VBox vboxLevels = new VBox(15);
        vboxLevels.setAlignment(Pos.CENTER);
        vboxLevels.setBackground(new Background(background));

        Button level1 = genererMenu.createStyledButton("Facile");
        level1.setOnAction(e -> lancerPartieAffichage(Difficulte.facile));

        Button level2 = genererMenu.createStyledButton("Moyen");
        level2.setOnAction(e -> lancerPartieAffichage(Difficulte.moyen));

        Button level3 = genererMenu.createStyledButton("Difficile");
        level3.setOnAction(e -> lancerPartieAffichage(Difficulte.difficile));

        Button level4 = genererMenu.createStyledButton("expert");
        level4.setOnAction(e -> lancerPartieAffichage(Difficulte.expert));

        Button levelRetour = genererMenu.createStyledButton("Retour");
        levelRetour.setOnAction(e -> primaryStage.setScene(mainScene));

        Label levelTitle = genererMenu.createStyledLabel("Choisissez votre niveau de jeu :");
        vboxLevels.getChildren().addAll(levelTitle, level1, level2, level3, level4, levelRetour);
        levelSelectionScene = new Scene(vboxLevels, 500, 400);

    }

    /**
     * Affiche la page de sélection de niveau.
     */
    private void showLevelSelection() {
        primaryStage.setScene(levelSelectionScene);
    }

    /**
     * Affiche la page de sélection de profil.
     */
    private void showProfileSelectionPage() {
        primaryStage.setScene(profileSelectionScene); // Changement pour afficher la page de sélection de profil
    }

    // ======================== Gestion des profils ========================
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

        dialog.getDialogPane().setStyle(
                "-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: #3d1e10; -fx-background-color: #f8f1e1;");
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
            genererMenu.alerteProfile();
            return;
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
        Button confirmButton = genererMenu.createStyledButton("Valider");
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

    // ======================== Gestion des parties ========================
    /**
     * Démarre une partie.
     */
    private void startGame() {
        primaryStage.setScene(levelSelectionScene); // Aller à la page de sélection du niveau
    }

    /**
     * Lance une partie en mode affichage.
     *
     * @param difficulte Difficulté de la partie.
     * @see PartieAffichage
     */
    public void lancerPartieAffichage(Difficulte difficulte) {
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
