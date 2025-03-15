package fr.m3acnl.managers;

import java.nio.file.Path;

/**
 * Cette classe est utilisée pour gérer l'emplacement des sauvegardes.
 *
 * @see OsManager
 * @author PUREN Mewen
 */
public class SauvegardeManager {

    // ======================== Attributs ========================
    /**
     * Le singleton de la classe SauvegardeManager.
     */
    private static final SauvegardeManager instance = new SauvegardeManager();
    /**
     * Le dossier de sauvegarde.
     */
    private Path repertoireSauvegarde;

    /**
     * Constructeur de la classe SauvegardeManager.
     */
    private SauvegardeManager() {
        setSaveDir();
        initialiseRepertoire();
    }

    // ======================== Getter ========================
    /**
     * Retourne l'instance de la classe SauvegardeManager.
     *
     * @return l'instance de la classe SauvegardeManager
     */
    protected static SauvegardeManager getInstance() {
        return instance;
    }

    /**
     * Retourne le dossier de sauvegarde.
     *
     * @return le dossier de sauvegarde
     */
    protected Path getRepertoireSauvegarde() {
        return repertoireSauvegarde;
    }

    // ======================== Setter ========================
    /**
     * Définir le dossier de sauvegarde en fonction du système d'exploitation.
     */
    private void setSaveDir() {
        this.repertoireSauvegarde = switch (OsManager.getInstance().getOsType()) {
            case WINDOWS ->
                Path.of(System.getenv("APPDATA"), "HashiParmentier");
            case MAC ->
                Path.of(System.getProperty("user.home"), "Library", "Application Support", "HashiParmentier");
            default ->
                Path.of(System.getProperty("user.home"), ".game", "HashiParmentier");
        };
    }

    // ======================== Méthodes ========================
    /**
     * Initialiser le dossier de sauvegarde.
     *
     * @throws RuntimeException si le dossier de sauvegarde ne peut pas être
     * créé
     */
    private void initialiseRepertoire() {
        if (!this.repertoireSauvegarde.toFile().exists()) {
            if (!this.repertoireSauvegarde.toFile().mkdirs()) {
                throw new RuntimeException("Impossible de créer le dossier de sauvegarde.");
            }
        }
    }

}
