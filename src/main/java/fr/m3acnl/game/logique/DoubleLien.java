
/**
 * Class DoubleLien.
 *
 * @autor COGNARD Luka
 * @date 28-01-2025
 * @version 1.0
 * @description Contient la classe DoubleLien
 *
 */
//package fr.m3acnl.game.logique;

/**
 * Cette classe gère les double lien.
 */
public class DoubleLien implements ElementJeu {

    /**
     * Le 1er lien.
     */
    private Lien lien1;

    /**
     * Le 2ème lien.
     */
    private Lien lien2;

    /**
     * Interrupteur quand un lien est activer.
     */
    private Boolean interupteur;

    /**
     * Constructeur pour créer une instance de DoubleLien.
     *
     * @param l1 Le 1er lien
     * @param l2 Le 2ème lien
     */
    public DoubleLien(Lien l1, Lien l2) {
        lien1 = l1;
        lien2 = l2;
        interupteur = false;
    }

    /**
     * Active le lien si il est activable lié au noeud donné.
     *
     * @param n Le noeud lié
     */
    public void activer(Noeud n) {
        if (!interupteur) {
            this.activeInterrupteur();
            if (lien1.noeudDansLien(n) == 0) {
                lien1.activer();
            } else {
                lien2.activer();
            }
        } else {
            if (lien1.noeudDansLien(n) == 0) {
                if (lien1.getNbLien() != 0) {
                    lien1.activer();
                }
            } else if (lien2.getNbLien() != 0) {
                lien2.activer();
            }
            this.desactiveInterrupteur();
        }
    }

    /**
     * Méthode non utiliser.
     */
    @Override
    public void activer() {
        return;
    }

    /**
     * Active l'interrupteur.
     */
    public void activeInterrupteur() {
        interupteur = true;
    }

    /**
     * Désactive l'interrupteur si les lien1 et lien2 sont à 0 donc égaux.
     */
    public void desactiveInterrupteur() {
        if (lien1.getNbLien() == lien2.getNbLien()) {
            interupteur = false;
        }
    }

    /**
     * Récupère l'état de l'interrupteur.
     *
     *
     * @return L'état de l'interrupteur
     */
    public Boolean getInterupteur() {
        return interupteur;
    }

    /**
     * Affiche le DoubleLien.
     */
    @Override
    public void draw() {
        System.out.print("D     ");
    }
}
