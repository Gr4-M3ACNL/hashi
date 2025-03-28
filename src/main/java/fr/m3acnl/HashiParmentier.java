package fr.m3acnl;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import fr.m3acnl.affichage.GenererAsset;
import fr.m3acnl.game.Difficulte;
import fr.m3acnl.game.affichage.PartieAffichage;
import fr.m3acnl.managers.ProfileManager;
import fr.m3acnl.profile.Profile;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * Classe principale de l'application HashiParmentier.
 *
 * @author TOUISSI Nassim
 *
 * @see Application
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
     * Scène de sélection de niveau.
     */
    private Scene selectionNiveau;
    /**
     * Scène de confirmation de quitter.
     */
    private Scene quitterAppli;
    /**
     * Scène de sélection de profil.
     */
    private Scene selectionProfile;

    /**
     * Générateur de menu.
     */
    private final GenererAsset genererMenu = new GenererAsset("/META-INF/assetsGraphiques/back/backMenu.jpeg");

    /**
     * Constructeur de la classe HashiParmentier.
     */
    public HashiParmentier() {
        // Constructeur par défaut
    }

    /**
     * Méthode start de l'application JavaFX.
     *
     * @param primaryStage Stage principal de l'application.
     */
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        primaryStage.getIcons().add(new Image(getClass().getResource("/META-INF/assetsGraphiques/logo.png").toExternalForm()));
        primaryStage.setTitle("Jeu - Menu Principal");

        BackgroundImage background = new BackgroundImage(
                new Image(getClass().getResource("/META-INF/assetsGraphiques/back/backMenu.jpeg").toExternalForm()),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, false, true));

        BorderPane root = new BorderPane();
        root.setBackground(new Background(background));

        VBox centerBox = new VBox(20);
        centerBox.setAlignment(Pos.CENTER);
        Button bouttonJouer = genererMenu.creerBoutonStyle("Jouer");
        bouttonJouer.setOnAction(e -> showProfileSelectionPage());
        Button bouttonClassement = genererMenu.creerBoutonStyle("Classement");
        bouttonClassement.setOnAction(e -> showClassement(background));
        Button bouttonQuitter = genererMenu.creerBoutonStyle("Quitter le jeu");
        bouttonQuitter.setOnAction(e -> genererMenu.showConfirmQuitPage(primaryStage, quitterAppli));

        centerBox.getChildren().addAll(bouttonJouer, bouttonClassement, bouttonQuitter);
        root.setCenter(centerBox);

        mainScene = new Scene(root, 500, 400);
        creerSelectionNiveau(background);
        quitterAppli = genererMenu.creerMenuQuitter(primaryStage, mainScene);
        creerSelectionProfil(background);

        //Lancement de l'application
        primaryStage.setScene(mainScene);
        primaryStage.show();
    }

    // ======================== Gestion des scènes ========================
    /**
     * Crée la scène de sélection de profil.
     *
     * @param background Image de fond.
     */
    private void creerSelectionProfil(BackgroundImage background) {
        VBox vboxProfileSelection = new VBox(15);
        vboxProfileSelection.setAlignment(Pos.CENTER);
        vboxProfileSelection.setBackground(new Background(background));

        final Label profileLabel = genererMenu.creerLabelStyle("Veuillez vous connecter pour jouer");

        Button loadProfileButton = genererMenu.creerBoutonStyle("Charger un profil");
        loadProfileButton.setOnAction(e -> loadProfile());

        Button creerProfileButton = genererMenu.creerBoutonStyle("Créer un profil");
        creerProfileButton.setOnAction(e -> creerProfile());

        Button supprimerProfilButton = genererMenu.creerBoutonStyle("Supprimer un profil");
        supprimerProfilButton.setOnAction(e -> supprimerProfile());

        Button returnToMainMenu = genererMenu.creerBoutonStyle("Retour");
        returnToMainMenu.setOnAction(e -> primaryStage.setScene(mainScene));

        vboxProfileSelection.getChildren().addAll(profileLabel, loadProfileButton, creerProfileButton, supprimerProfilButton,
                returnToMainMenu);
        selectionProfile = new Scene(vboxProfileSelection, 500, 400);
    }

    /**
     * Crée la scène de sélection de niveau.
     *
     * @param background Image de fond.
     */
    private void creerSelectionNiveau(BackgroundImage background) {
        VBox vboxLevels = new VBox(15);
        vboxLevels.setAlignment(Pos.CENTER);
        vboxLevels.setBackground(new Background(background));

        Button tuto = genererMenu.creerBoutonStyle("Tutoriel");
        tuto.setOnAction(e -> primaryStage.setScene(genererMenu.creerSlideshow(primaryStage, selectionNiveau)));

        Button level1 = genererMenu.creerBoutonStyle("Facile");
        level1.setOnAction(e -> lancerPartieAffichage(Difficulte.facile));

        Button level2 = genererMenu.creerBoutonStyle("Moyen");
        level2.setOnAction(e -> lancerPartieAffichage(Difficulte.moyen));

        Button level3 = genererMenu.creerBoutonStyle("Difficile");
        level3.setOnAction(e -> lancerPartieAffichage(Difficulte.difficile));

        Button level4 = genererMenu.creerBoutonStyle("expert");
        level4.setOnAction(e -> lancerPartieAffichage(Difficulte.expert));

        Button historique = genererMenu.creerBoutonStyle("Historique");
        historique.setOnAction(e -> creerHistorique(background));

        Button levelRetour = genererMenu.creerBoutonStyle("Retour");
        levelRetour.setOnAction(e -> primaryStage.setScene(mainScene));

        Label levelTitle = genererMenu.creerLabelStyle("Choisissez votre niveau de jeu :");
        vboxLevels.getChildren().addAll(levelTitle, tuto, level1, level2, level3, level4, historique, levelRetour);
        selectionNiveau = new Scene(vboxLevels, 700, 600);

    }

    /**
     * Permet de fournir l'historique des 5 dernieres parties jouer par
     * l'utilisateur sur chaque difficulté.
     *
     * @param background Le background de la fenetre
     */
    private void creerHistorique(BackgroundImage background) {
        VBox vboxContainer = new VBox(20);
        vboxContainer.setAlignment(Pos.CENTER);
        vboxContainer.setBackground(new Background(background));

        HBox hboxClassement = new HBox(20);
        hboxClassement.setAlignment(Pos.CENTER);

        java.util.Arrays.asList(Difficulte.facile, Difficulte.moyen, Difficulte.difficile, Difficulte.expert).forEach(difficulte -> {
            VBox vboxDifficulte = new VBox(10);
            vboxDifficulte.setAlignment(Pos.TOP_CENTER);

            Label difficultyLabel = genererMenu.creerLabelStyle("Historique " + difficulte);
            vboxDifficulte.getChildren().add(difficultyLabel);

            List<Duration> tempsList = new ArrayList<>(ProfileManager.getInstance()
                    .getProfileActif()
                    .getHistoriquePartieProfile()
                    .getTemps(difficulte));

            Collections.reverse(tempsList); // Inverse la liste temporairement

            tempsList.stream()
                    .limit(5)
                    .forEach(tempsPartie -> {
                        String formattedTime = (tempsPartie != null)
                                ? tempsPartie.toMinutesPart() + " min " + tempsPartie.toSecondsPart() + " sec"
                                : "";

                        Label profileLabel = genererMenu.creerLabelStyle(formattedTime);
                        vboxDifficulte.getChildren().add(profileLabel);
                    });

            hboxClassement.getChildren().add(vboxDifficulte);
        });

        Button retour = genererMenu.creerBoutonStyle("Retour");
        retour.setOnAction(e -> startGame());

        vboxContainer.getChildren().addAll(hboxClassement, retour);

        primaryStage.setScene(new Scene(vboxContainer, 1000, 600));
    }

    /**
     * Affiche la page de sélection de profil.
     */
    private void showProfileSelectionPage() {
        primaryStage.setScene(selectionProfile); // Changement pour afficher la page de sélection de profil
    }

    /**
     * Affiche la page de classement.
     *
     * @param background Image de fond.
     */
    private void showClassement(BackgroundImage background) {
        VBox vboxContainer = new VBox(20);
        vboxContainer.setAlignment(Pos.CENTER);
        vboxContainer.setBackground(new Background(background));

        HBox hboxClassement = new HBox(20);
        hboxClassement.setAlignment(Pos.CENTER);

        java.util.Arrays.asList(Difficulte.facile, Difficulte.moyen, Difficulte.difficile, Difficulte.expert).forEach(difficulte -> {
            VBox vboxDifficulte = new VBox(10);
            vboxDifficulte.setAlignment(Pos.TOP_CENTER);

            Label difficultyLabel = genererMenu.creerLabelStyle("Classement " + difficulte);
            vboxDifficulte.getChildren().add(difficultyLabel);

            ProfileManager.getInstance().getClassementTemps(difficulte).forEach(tempsPartie -> {
                String formattedTime = (tempsPartie.duree() != null)
                        ? tempsPartie.duree().toMinutesPart() + " min " + tempsPartie.duree().toSecondsPart() + " sec"
                        : "";

                Label profileLabel = genererMenu.creerLabelStyle(tempsPartie.nomProfil() + " : " + formattedTime);
                vboxDifficulte.getChildren().add(profileLabel);
            });

            hboxClassement.getChildren().add(vboxDifficulte);
        });

        Button retour = genererMenu.creerBoutonStyle("Retour");
        retour.setOnAction(e -> primaryStage.setScene(mainScene));

        vboxContainer.getChildren().addAll(hboxClassement, retour);

        primaryStage.setScene(new Scene(vboxContainer, 1000, 600));
    }

    // ======================== Gestion des profils ========================
    /**
     * Crée un profil.
     */
    private void creerProfile() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Créer un profil");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setHeader(null);

        dialog.setGraphic(null);
        dialog.setContentText("Entrez votre nom de profil :  ");

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);

        okButton.setStyle(genererMenu.creerBoutonStyle("OK").getStyle());
        cancelButton.setStyle(genererMenu.creerBoutonStyle("Annuler").getStyle());
        dialog.getDialogPane().setStyle(
                "-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: rgb(121, 45, 9); -fx-background-color:rgb(175, 140, 117);");
        dialog.getEditor().setPromptText("Nom de profil");
        dialog.getEditor().setStyle(
                "-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill:rgb(121, 45, 9);; -fx-background-color: #f8f1e1;");

        Optional<String> result = dialog.showAndWait();
        //Recupération du nom du profil
        result.ifPresent(name -> {
            ProfileManager.getInstance().creerProfil(name);
            ProfileManager.getInstance().setProfileActif(name);
            System.out.println("Profil créé et sauvegardé : " + name);

            startGame();
        });
    }

    /**
     * Permet de supprimer un profile.
     */
    private void supprimerProfile() {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Supprimer le profil");
        dialog.setHeaderText(null);
        dialog.getDialogPane().setHeader(null);

        dialog.setGraphic(null);
        dialog.setContentText("Le nom du profil à supprimer :  ");

        Button okButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.OK);
        Button cancelButton = (Button) dialog.getDialogPane().lookupButton(ButtonType.CANCEL);

        okButton.setStyle(genererMenu.creerBoutonStyle("OK").getStyle());
        cancelButton.setStyle(genererMenu.creerBoutonStyle("Annuler").getStyle());
        dialog.getDialogPane().setStyle(
                "-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill: rgb(121, 45, 9); -fx-background-color:rgb(175, 140, 117);");
        dialog.getEditor().setPromptText("Nom de profil");
        dialog.getEditor().setStyle("-fx-font-family: 'Arial'; -fx-font-size: 16px; -fx-text-fill:rgb(121, 45, 9);; -fx-background-color: #f8f1e1;");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(name -> {
            ProfileManager.getInstance().supprimerProfil(name);
            System.out.println("Profil supprimer : " + name);

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
        Button confirmButton = genererMenu.creerBoutonStyle("Valider");
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
        primaryStage.setScene(selectionNiveau); // Aller à la page de sélection du niveau
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
            primaryStage.close();
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
