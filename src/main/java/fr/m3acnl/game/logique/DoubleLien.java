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
     * Constructeur pour créer une instance de DoubleLien.
     * @param l1 Le 1er lien
     * @param l2 Le 2ème lien
     */
    public DoubleLien(Lien l1, Lien l2) {
        lien1 = l1;
        lien2 = l2;
    }

    /**
     * Active le lien lié au noeud donné.
     * @param n Le noeud lié
     */
    public void activeLien(Noeud n) {
        if (lien1.noeudDansLien(n) == 0) {
            lien1.lienActiver();
        } else {
            lien2.lienActiver();
        }
        
    }
}
