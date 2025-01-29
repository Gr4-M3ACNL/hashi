package fr.m3acnl.game.logique;

/**
 * Cette classe gère les double lien.
 * 
 * @author COGNARD Luka
 */
public class DoubleLien {

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
     * @param n Le noeud lié
     */
    public void activeLien(Noeud n) {
        this.activeInterrupteur();
        int lienactivable = this.lienActivable(n);
        if (lienactivable == 1) {
            lien1.lienActiver();
        } else if (lienactivable == 2) {
            lien2.lienActiver();
        }
        this.desactiveInterrupteur();
    }
    
    /**
     * Active l'interrupteur si il est désactiver.
     */
    public void activeInterrupteur() {
        if (!interupteur) {
            interupteur = true;
        }
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
     * @return L'état de l'interrupteur
     */
    public Boolean getInterupteur() {
        return interupteur;
    }

    /**
     * Regarde si le lien contenant le noeud n est activable.
     * @param n Le noeud lié
     * @return le numéro de noeud a activer si pas activable 0
     */
    public int lienActivable(Noeud n) {
        if (lien1.noeudDansLien(n) == 0) {
            if (lien2.getNbLien() == 0) {
                return 1;
            }
        } else if (lien1.getNbLien() == 0) {
            return 2;
        }
        return 0;
    }
}
