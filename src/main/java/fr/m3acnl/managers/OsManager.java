package fr.m3acnl.managers;

/**
 * Classe utilisée pour obtenir le type de système d'exploitation actuel.
 * 
 * @author PUREN Mewen
 * @see OsType
 */
public class OsManager {

    /**
     * Le singleton de la classe OsManager.
     */
    private static final OsManager instance = new OsManager();

    /**
     * Le type de système d'exploitation actuel.
     */
    private final OsType osType;

    /**
     * Constructeur de la classe OsManager.
     */
    private OsManager() {
        String os = System.getProperty("os.name").toLowerCase();
        osType = OsType.getOsParNom(os);
    }

    /**
     * Obtenir l'instance de OsManager.
     *
     * @return l'instance de OsManager.
     */
    public static OsManager getInstance() {
        return instance;
    }

    /**
     * Obtenir le type de système d'exploitation actuel.
     *
     * @return le type de système d'exploitation actuel.
     */
    public OsType getOsType() {
        return osType;
    }

    /**
     * Les types de système d'exploitation.
     */
    public enum OsType {

        /**
         * Windows OS.
         */
        WINDOWS("windows"),
        /**
         * Mac OS.
         */
        MAC("mac", "darwin"),
        /**
         * Linux OS.
         */
        LINUX("unix", "linux", "debian", "ubuntu", "centos", "fedora", "arch");

        /**
         * Constructeur de la classe OsType.
         *
         * @param cles la clé du type de système d'exploitation.
         */
        OsType(String... cles) {
            this.cles = cles;
        }

        /**
         * La clé du type de système d'exploitation.
         */
        private final String[] cles;

        /**
         * Obtenir le type de système d'exploitation par son nom.
         *
         * @param nom le nom du système d'exploitation.
         * @return le type de système d'exploitation.
         */
        private static OsType getOsParNom(String nom) {
            for (OsType os : OsType.values()) {
                for (String cle : os.cles) {
                    if (nom.contains(cle)) {
                        return os;
                    }
                }
            }
            return null;
        }
    }
}
