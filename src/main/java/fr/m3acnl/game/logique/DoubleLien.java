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
     * Active le lien lié au noeud donné.
     * @param n Le noeud lié
     */
    public void activeLien(Noeud n) {
        this.activeInterrupteur();
        if (lien1.noeudDansLien(n) == 0 && lien2.getNbLien() == 0) {
            lien1.lienActiver();
        } else if (lien1.getNbLien() == 0) {
            lien2.lienActiver();
        }
        
    }
    
    /**
     * Active l'interrupteur en inversant son état.
     */
    public void activeInterrupteur(){
        interupteur= !interupteur;
    }

    /**
     * Récupère l'état de l'interrupteur.
     * @return L'état de l'interrupteur
     */
    public Boolean getInterupteur() {
        return interupteur;
    }
}
