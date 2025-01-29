package fr.m3acnl.game.logique;

/**
 * Cette classe représente un lien entre deux noeuds. Il connait les deux noeuds
 * lié, son nombre de lien et son nombre de lien de la solution.
 * 
 * @author COGNARD Luka
 */
public class Lien {

    /**
     * 1er noeud du lien.
     */
    private Noeud noeud1;

    /**
     * 2ème noeud du lien.
     */
    private Noeud noeud2;

    /**
     * Nombre de lien actuelle.
     */
    private int nbLien;

    /**
     * Le nombre soluce du lien.
     */
    private int nbLienSoluce;

    /**
     * La surbrillance du lien.
     */
    private Boolean surbrillance;

    /**
     * Constructeur pour une nouvelle instance de Lien.
     *
     * @param n1 premier Noeud
     * @param n2 deuxième Noeud
     * @param sol le nombre de lien de la solution
     */
    public Lien(Noeud n1, Noeud n2, int sol) {
        noeud1 = n1;
        noeud2 = n2;
        nbLienSoluce = sol;
        nbLien = 0;
        surbrillance = false;
    }

    /**
     * Active la surbrillance du lien.
     */
    public void surbrillanceOn() {
        surbrillance = true;
    }

    /**
     * Désactive la surbrillance du lien.
     */
    public void surbrillanceOff() {
        surbrillance = false;
    }

    /**
     * Vérifie si le lien est valide.
     *
     * @return True si le lien est valide sinon false
     */
    public boolean estValide() {
        return (nbLien == nbLienSoluce);
    }

    /**
     * Active le lien le faisant passer a son état suivant et met a jour le
     * degré actuelle des noeud liés.
     */
    public void lienActiver() {
        if ((nbLien = (nbLien + 1) % 3) == 0) {
            noeud1.enleverDegre();
            noeud2.enleverDegre();
        } else {
            noeud1.ajouterDegre();
            noeud2.ajouterDegre();
        }
    }

    /**
     * Récupère le premier noeud.
     *
     * @return le premier noeud
     */
    public Noeud getNoeud1() {
        return noeud1;
    }

    /**
     * Récupère le deuxième noeud.
     *
     * @return le deuxième noeud
     */
    public Noeud getNoeud2() {
        return noeud2;
    }

    /**
     * Récupère le nombre de lien.
     *
     * @return le nombre de lien
     */
    public int getNbLien() {
        return nbLien;
    }

    /**
     * Récupère la surbrillance.
     * @return la surbrillance
     */
    public Boolean getSurbrillance() {
        return surbrillance;
    }

    /**
     * Vérifie si un noeud n est présent dans ce lien.
     *
     * @param n Le noeud a vérifier
     * @return Le résultat de la vérification
     */
    public int noeudDansLien(Noeud n) {
        int res = noeud1.compareTo(n);
        if (res != 0) {
            return noeud2.compareTo(n);
        }
        return res;
    }
}
