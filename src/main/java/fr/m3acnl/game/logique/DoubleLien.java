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
    private final Lien lien1;

    /**
     * Le 2ème lien.
     */
    private final Lien lien2;

    /**
     * Interrupteur quand un lien est activer.
     */
    private Boolean interrupteur;

    /**
     * Constructeur pour créer une instance de DoubleLien.
     *
     * @param l1 Le 1er lien
     * @param l2 Le 2ème lien
     */
    public DoubleLien(Lien l1, Lien l2) {
        lien1 = l1;
        lien2 = l2;
        interrupteur = false;
    }

    /**
     * Active le lien si il est activable lié au noeud donné.
     *
     * @param n Le noeud lié
     * @return Le lien qui a été activer return null si pas de lien activer
     */
    public Lien activer(Noeud n) {
        if (!interrupteur) {
            if (lien1.noeudDansLien(n) == 0) {
                if (lien1.activer()) {
                    this.activeInterrupteur();
                    return lien1;
                }
            } else {
                if (lien2.activer()) {
                    this.activeInterrupteur();
                    return lien2;
                }
            }
        } else {
            if (lien1.noeudDansLien(n) == 0) {
                if (lien1.getNbLien() != 0) {
                    lien1.activer();
                    return lien1;
                }
            } else if (lien2.getNbLien() != 0) {
                lien2.activer();
                return lien2;
            }
        }
        return null;
    }

    /**
     * Méthode non utilisée.
     *
     * @return false
     */
    @Override
    public Boolean activer() {
        return false;
    }

    /**
     * Active l'interrupteur.
     */
    public void activeInterrupteur() {
        interrupteur = true;
    }

    /**
     * Désactive l'interrupteur si les lien1 et lien2 sont à 0 donc égaux.
     */
    public void desactiveInterrupteur() {
        if (lien1.getNbLien() == lien2.getNbLien()) {
            interrupteur = false;
        }
    }

    /**
     * Récupère l'état de l'interrupteur.
     *
     *
     * @return L'état de l'interrupteur
     */
    public Boolean getInterrupteur() {
        return interrupteur;
    }

    /**
     * Affiche le DoubleLien.
     */
    @Override
    public void draw() {
        System.out.print(" D" + "(" + lien1.getNbLienSoluce() + "|" + lien2.getNbLienSoluce() + ") ");
    }
}
