package fr.m3acnl.game.affichage;

import java.time.Duration;
import java.util.Arrays;

import fr.m3acnl.affichage.GenererAsset;
import fr.m3acnl.game.Difficulte;
import fr.m3acnl.game.Partie;
import fr.m3acnl.game.logique.Jeu;
import fr.m3acnl.game.logique.elementjeu.DoubleLien;
import fr.m3acnl.game.logique.elementjeu.ElementJeu;
import fr.m3acnl.game.logique.elementjeu.Lien;
import fr.m3acnl.game.logique.elementjeu.Noeud;
import fr.m3acnl.managers.ProfileManager;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Classe PartieAffichage pour l'affichage du jeu.
 *
 * @author MABIRE Aymeric, PESANTEZ Maelig
 * @version 1.0
 */
public class PartieAffichage extends Application {

    // ======================== Attributs ========================
    /**
     * La partie en cours.
     * <p>
     * contient le jeu et le chrono. gère la sauvegarde et la fin de la partie.
     * </p>
     *
     * @see Partie
     */
    private final Partie partie;

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

    /**
     * Accès à la timeline pour l'actualisation du chrono.
     */
    private Timeline timeline;

    /**
     * Constructeur de la classe PartieAffichage.
     *
     * @param difficulte La difficulté de la partie
     * @see Partie#Partie(Difficulte)
     */
    public PartieAffichage(Difficulte difficulte) {
        this.partie = new Partie(difficulte);
    }

    /**
     * Generateur de menu.
     */
    private final GenererAsset genererMenu = new GenererAsset("/META-INF/assetsGraphiques/back/backPartie.png");

    /**
     * La fenêtre principale.
     */
    Stage primaryStage;

    /**
     * La scène principale.
     */
    Scene mainScene;

    /**
     * Méthode start pour lancer l'application.
     *
     * @param prim La scène principale
     */
    @Override
    public void start(Stage prim) {
        primaryStage = prim;
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

        mainScene = new Scene(mainLayout, 1920, 1080);
        primaryStage.setTitle("Jeu Interface");
        primaryStage.setScene(mainScene);
        primaryStage.setOnCloseRequest(event -> {
            genererMenu.demandeSortie(event);
        });

        primaryStage.show();

        mainScene.widthProperty().addListener((obs, oldVal, newVal) -> ajusterTailleImages());
        mainScene.heightProperty().addListener((obs, oldVal, newVal) -> ajusterTailleImages());
        demarrerChrono();
        actualiserAffichage();
    }

    // ======================== Creation de l'interface ========================
    /**
     * Crée le fond de la fenêtre.
     *
     */
    private void creerBackground() {

        ImageView imageBackgroundView = genererMenu.creerImageView("/META-INF/assetsGraphiques/back/backPartie.png", 1920, 1080);
        ImageView imageFondView = genererMenu.creerImageView("/META-INF/assetsGraphiques/back/table.png", 640, 640);
        imageFondView.setPreserveRatio(true);
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

        Button buttonPause = new Button("Pause");
        Button buttonRetour = new Button("Annuler coups");
        Button buttonAvancer = new Button("Rétablir coups");
        Button buttonCheck = new Button("Vérifier grille");
        Button buttonSave = new Button("Sauvegarde manuelle");
        Button buttonCheckpoint = new Button("Retour checkpoint");

        Button[] boutonsControle = {buttonRetour, buttonAvancer, buttonCheck, buttonSave, buttonCheckpoint, buttonPause};
        for (Button bouton : boutonsControle) {
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
        // ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore()
        buttonRetour.setOnAction(e -> {
            retour();
            genererMenu.jouerSon("bouton.wav",
                    ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
        });
        buttonAvancer.setOnAction(e -> {
            avancer();
            genererMenu.jouerSon("bouton.wav",
                    ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
        });
        buttonCheck.setOnAction(e -> {
            check();
            genererMenu.jouerSon("bouton.wav",
                    ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
        });
        buttonSave.setOnAction(e -> {
            sauvegarde();
            genererMenu.jouerSon("bouton.wav",
                    ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
        });
        buttonCheckpoint.setOnAction(e -> {
            retourSauvegarde();
            genererMenu.jouerSon("bouton.wav",
                    ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
        });
        buttonPause.setOnAction(e -> {
            pause();
            genererMenu.jouerSon("bouton.wav",
                    ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
        });
        labelTemps = new Label("Temps: " + partie.getChronoDuration().toMinutes() + " min " + partie.getChronoDuration().toSecondsPart() + " sec");
        labelTemps.setStyle("-fx-background-color: linear-gradient(#7a5230, #4a2c14);"
                + "-fx-background-radius: 10;"
                + "-fx-border-color: #3d1e10;"
                + "-fx-border-width: 2px;"
                + "-fx-border-radius: 10;"
                + "-fx-text-fill: white;"
                + "-fx-font-size: 14px;"
                + "-fx-font-family: 'Georgia'; -fx-padding: 5px;");
        actualiserLabelTemps();

        // Mise à jour de la taille des boutons lorsqu'on redimensionne la fenêtre
        Platform.runLater(() -> {
            Scene scene = controlPanel.getScene();
            if (scene != null) {
                scene.widthProperty().addListener((obs, oldVal, newVal) -> ajusterTailleBoutons(boutonsControle));
                scene.heightProperty().addListener((obs, oldVal, newVal) -> ajusterTailleBoutons(boutonsControle));
                ajusterTailleBoutons(boutonsControle);
            }
        });

        controlPanel.getChildren().addAll(buttonRetour, buttonAvancer, buttonCheck, buttonSave, buttonCheckpoint, buttonPause, labelTemps);
        return controlPanel;
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
                boutons[i][j].setOnMouseClicked(event -> activerElement(event, x, y));

                String resource = getResourceElementJeu(i, j);
                if (resource != null) {
                    boutons[i][j].setGraphic(genererMenu.creerImageView(resource, 100, 100));
                }

                boutons[i][j].setStyle("-fx-background-color: transparent; -fx-padding: 0;");
                boutons[i][j].setOnMouseEntered(e -> previsualiserEtat(e, x, y));

                boutons[i][j].setOnMouseExited(e -> restaurerEtat(x, y)); // Désactive le survol

                gridPane.add(boutons[i][j], y, x);
            }
        }
    }


    // ======================== Actualisation de l'interface ========================
    /**
     * Met à jour l'affichage.
     */
    private void actualiserAffichage() {
        ajusterTailleImages();
    }

    /**
     * Actualise le label du temps.
     */
    private void actualiserLabelTemps() {
        if (partie != null) {
            Duration chrono = partie.getChronoDuration();
            long minutes = chrono.toMinutes();
            long secondes = chrono.toSecondsPart();
            labelTemps.setText("Temps: " + minutes + " min " + secondes + " sec");
        }
    }

    /**
     * Ajuste la taille des images en fonction de la taille de la scène.
     */
    private void ajusterTailleImages() {
        if (mainScene == null) {
            return;
        }

        double largeurScene = mainScene.getWidth();
        double hauteurScene = mainScene.getHeight();
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

                bouton.setGraphic(genererMenu.creerImageView(getResourceElementJeu(i, j),
                        tailleCellule * SUPERPOSITION_RATIO,
                        tailleCellule * SUPERPOSITION_RATIO));
                partie.getJeu().getPlateau().getElement(i, j).verifie();
            }

            bouton.setMinSize(tailleCellule, tailleCellule);
            bouton.setMaxSize(tailleCellule, tailleCellule);
        });
        derniereTaille = tailleCellule;
    }

    /**
     * Prévisualise l'état d'un élément du jeu. Permet de pouvoir voir les
     * connections avant de cliquer.
     *
     * @param event L'événement de souris
     * @param x La ligne de l'élément
     * @param y La colonne de l'élément
     */
    private void previsualiserEtat(MouseEvent event, int x, int y) {
        ElementJeu element = partie.getJeu().getPlateau().getElement(x, y);

        if (element == null) {
            return; // Rien à faire si la case est vide
        }

        if (element instanceof DoubleLien doubleLien) {
            // Trouver le nœud le plus proche de la souris
            Noeud noeudProche = trouverNoeudLePlusProche(doubleLien, event);

            if (noeudProche != null) {
                doubleLien.activerSurbrillance(noeudProche);
            }
        } else {
            element.surbrillanceOn();
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
     * Ajuste la taille des boutons en fonction de la taille de la scène.
     *
     * @param boutons Les boutons à ajuster
     */
    private void ajusterTailleBoutons(Button[] boutons) {
        Scene scene = boutons[0].getScene();
        if (scene == null) {
            return;
        }

        double largeurFenetre = scene.getWidth();
        double hauteurFenetre = scene.getHeight();

        double nouvelleTailleBouton = Math.max(hauteurFenetre * 0.05, 40); // 40px de hauteur minimum
        double nouvelleLargeur = Math.max(largeurFenetre * 0.15, 150); // 150px de largeur minimum

        for (Button bouton : boutons) {
            bouton.setMinSize(nouvelleLargeur, nouvelleTailleBouton);
            bouton.setMaxSize(nouvelleLargeur, nouvelleTailleBouton);
        }
    }

    /**
     * Permet d'afficher l'overlay de victoire.i
     */
    private void victoire() {
        Arrays.stream(boutons).flatMap(Arrays::stream).forEach(b -> b.setDisable(true));

        // Afficher l'image "up.png" temporairement
        ImageView winImageView = genererMenu.creerImageView("/META-INF/assetsGraphiques/character/up.png", 300, 300);

        // Utiliser un StackPane mais sans fond opaque
        StackPane upPane = new StackPane(winImageView);
        upPane.setAlignment(Pos.CENTER);
        upPane.setOpacity(1);

        backgroundPane.setEffect(null);
        backgroundPane.setOpacity(1);

        backgroundPane.getChildren().add(upPane);
        backgroundPane.getChildren().get(backgroundPane.getChildren().size() - 1).toFront();

        // Pause de 3 secondes avant d'afficher l'overlay final
        PauseTransition pause = new PauseTransition(javafx.util.Duration.seconds(3));
        pause.setOnFinished(event -> {
            backgroundPane.getChildren().remove(upPane); // Retirer l'image temporaire
            afficherOverlayVictoire(); // Afficher l'overlay après la pause
        });
        genererMenu.jouerSon("victoire.wav",
                ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
        pause.play();
    }

    /**
     * Affiche l'overlay du menu de victoire.
     */
    private void afficherOverlayVictoire() {
        if (!(mainScene != null && mainScene.getRoot() instanceof BorderPane mainLayout)) {
            System.out.println("ERREUR : Scène ou BorderPane invalide !");
            return;
        }

        // Création des éléments de l'overlay
        ImageView winImageView = genererMenu.creerImageView("/META-INF/assetsGraphiques/character/win.png", 500, 500);

        Label labelWin = new Label("Temps : " + labelTemps.getText());
        labelWin.setStyle("-fx-text-fill: white; -fx-font-size: 20px; -fx-font-weight: bold;");

        Button btnSuivant = genererMenu.createStyledButton("Suivant");
        Button btnQuitter = genererMenu.createStyledButton("Quitter");

        btnSuivant.setOnAction(e -> {
            relancerPartie(mainLayout);  // Passer mainLayout à relancerPartie
            genererMenu.jouerSon("bouton.wav",
                    ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());
            cacherOverlay(mainLayout);  // Cacher l'overlay
        });
        btnQuitter.setOnAction(e -> {
            genererMenu.jouerSon("bouton.wav",
                    ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore());

            // Fermer la fenêtre principale
            primaryStage.close();

            genererMenu.relancerPartie();
        });

        VBox winBox = new VBox(20, winImageView, labelWin, btnSuivant, btnQuitter);
        winBox.setAlignment(Pos.CENTER);
        winBox.setStyle("-fx-background-image: url('/META-INF/assetsGraphiques/back/backWin.png');"
                + "-fx-background-size: cover; -fx-padding: 20px; -fx-border-radius: 10px;");
        StackPane overlayPane = new StackPane(winBox);
        overlayPane.setAlignment(Pos.CENTER);
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5);");

        // Affichage de l'overlay
        mainLayout.setCenter(overlayPane);
    }

    /**
     * Méthode pour cacher l'overlay.
     *
     * @param mainLayout Le layout principal
     */
    private void cacherOverlay(BorderPane mainLayout) {
        // Retirer l'overlay en enlevant le StackPane du centre
        mainLayout.setCenter(null);
    }

    // ======================== Generation des ressources ========================
    /**
     * Récupère la ressource associée à un élément du jeu.
     *
     * @param i L'indice de ligne
     * @param j L'indice de colonne
     * @return L'URL de la ressource
     */
    private String getResourceElementJeu(int i, int j) {
        return partie.getJeu().getPlateau().getElement(i, j) != null
                ? (String) partie.getJeu().getPlateau().getElement(i, j).draw()
                : "/META-INF/assetsGraphiques/link/blank.png";
    }

    // ======================== Gestion des actions ========================
    /**
     * Active un élément du jeu.
     *
     * @param event L'événement de souris
     * @param x La ligne de l'élément
     * @param y La colonne de l'élément
     */
    private void activerElement(MouseEvent event, int x, int y) {
        restaurerEtat(x, y);

        // Vérifier si l'élément du plateau est un DoubleLien
        ElementJeu element = partie.getJeu().getPlateau().getElement(x, y);

        if (element instanceof DoubleLien doubleLien) {
            // Trouver le nœud le plus proche de la souris
            Noeud noeudProche = trouverNoeudLePlusProche(doubleLien, event);

            if (noeudProche != null) {
                doubleLien.activer(noeudProche);
                genererMenu.jouerSon("lien.wav",
                        ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore()); // Jouer le son de lien
            }
        } else {
            partie.getJeu().activeElemJeu(x, y, null);
            if (element instanceof Noeud noeud) {
                genererMenu.jouerSon("noeud.wav",
                        ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore()); // Jouer le son du nœud
            } else if (element instanceof Lien lien) {
                genererMenu.jouerSon("lien.wav",
                        ProfileManager.getInstance().getProfileActif().getParametre().getVolumeEffetsSonore()); // Jouer le son du lien
            }
        }

        // Vérifier si la partie est gagnée
        if (partie.getJeu().gagner()) {
            partie.finPartie();
            victoire();
        } else {
            // on ne doit pas sauvegarder la partie si elle est gagnée
            partie.sauvegarde();
        }
        // Mettre à jour l'affichage
        actualiserAffichage();
    }

    /**
     * Trouve le nœud le plus proche du bouton DoubleLien.
     *
     * @param doubleLien Le DoubleLien à vérifier
     * @param event L'événement de souris
     * @return Le nœud le plus proche
     */
    private Noeud trouverNoeudLePlusProche(DoubleLien doubleLien, MouseEvent event) {
        if (!(event.getSource() instanceof Node source)) {
            System.out.println("L'élément source n'est pas un Node !");
            return null;
        }

        // Convertir la position de la souris en coordonnées locales du bouton
        Point2D mouseLocal = source.sceneToLocal(event.getSceneX(), event.getSceneY());

        // Récupérer les nœuds des liens
        Noeud noeudHaut = doubleLien.getLien1().getNoeud1();
        Noeud noeudBas = doubleLien.getLien1().getNoeud2();
        Noeud noeudGauche = doubleLien.getLien2().getNoeud1();
        Noeud noeudDroit = doubleLien.getLien2().getNoeud2();

        // Dimensions du bouton
        double boutonWidth = ((Button) source).getWidth();
        double boutonHeight = ((Button) source).getHeight();

        // Déterminer la position relative de la souris
        double centerX = boutonWidth / 2;
        double centerY = boutonHeight / 2;
        double dx = mouseLocal.getX() - centerX;
        double dy = mouseLocal.getY() - centerY;

        // Comparer les distances pour choisir entre horizontal et vertical
        if (Math.abs(dx) > Math.abs(dy)) {
            // Mouvement majoritairement horizontal
            return (dx < 0) ? noeudGauche : noeudDroit;
        } else {
            // Mouvement majoritairement vertical
            return (dy < 0) ? noeudHaut : noeudBas;
        }
    }

    /**
     * Permet de mettre le jeu en pause et de lancer le menu.
     */
    private void pause() {

        primaryStage.setWidth(primaryStage.getWidth());
        primaryStage.setHeight(primaryStage.getHeight());

        genererMenu.showSettingsMenu(primaryStage, genererMenu.creerMenuPause(primaryStage, mainScene, partie));

        // Forcer l'ajustement après un petit délai
    }

    /**
     * Démarre le chrono de la partie pour l'actualisation du temps.
     */
    private void demarrerChrono() {
        timeline = new Timeline(new KeyFrame(javafx.util.Duration.seconds(1), event -> actualiserLabelTemps()));
        timeline.setCycleCount(Animation.INDEFINITE); // Répéter indéfiniment
        timeline.play(); // Démarrer le chrono
    }

    /**
     * Relance une nouvelle partie. la difficulté reste la même.
     *
     * @param mainLayout Le layout principal
     * @see PartieAffichage#PartieAffichage(Difficulte)
     * @see PartieAffichage#start(Stage)
     */
    private void relancerPartie(BorderPane mainLayout) {
        // Réinitialiser la partie, mais ne pas oublier d'ajouter les éléments de jeu au mainLayout
        PartieAffichage partieAffichage = new PartieAffichage(partie.getDifficulte());
        partieAffichage.start((Stage) mainLayout.getScene().getWindow());
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
     * Sauvegarde manuelle.
     *
     * @see Jeu#sauvegarderManuellement()
     */
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
