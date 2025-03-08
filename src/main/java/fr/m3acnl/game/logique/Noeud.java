package fr.m3acnl.game.logique;

import java.util.ArrayList;

/**
 * Classe Noeud a lié dans le jeu.
 *
 * @author MABIRE Aymeric
 * @version 1.0
 */
public class Noeud implements ElementJeu, Comparable<Noeud> {

    /**
     * La position en coordonnée du noeud.
     */
    private final Coord position;

    /**
     * Le degré solution du noeud.
     */
    private final int degreSoluce;

    /**
     * Le degré actuelle du noeud.
     */
    private int degreActuelle;

    /**
     * La liste d'adjacence de la matrice.
     */
    private final ArrayList<Noeud> listeAdjacence;

    /**
     * Si le noeud est en surbrillance ou non.
     */
    private Boolean surbrillance;

    /**
     * Permet de savoir si le noeud est actif ou non.
     */
    private Boolean activer = false;

    /**
     * Constructeur pour créer une nouvelle instance d'un Noeud.
     *
     * @param x la coordonnée x du noeud
     * @param y la coordonnée y du noeud
     * @param degS le degré solution du noeud
     */
    public Noeud(int x, int y, int degS) {
        position = new Coord(x, y);
        degreSoluce = degS;
        degreActuelle = 0;
        listeAdjacence = new ArrayList<>();
        surbrillance = false;
    }

    /**
     * Récupère la position du noeud.
     *
     * @return les coordonnées du noeud
     */
    public Coord getPosition() {
        return position;
    }

    /**
     * Récupère le degré actuelle.
     *
     * @return le degré actuelle
     */
    public int getDegreActuelle() {
        return degreActuelle;
    }

    /**
     * Récupère le degré solution.
     *
     * @return le degré solution
     */
    public int getDegreSoluce() {
        return degreSoluce;
    }

    /**
     * Récupère la surbrillance.
     *
     * @return la surbrillance
     */
    public Boolean getSurbrillance() {
        return surbrillance;
    }

    /**
     * Récupère la liste d'adjacence.
     *
     * @return la liste d'adjacence
     */
    public ArrayList<Noeud> getListeAdjacence() {
        return listeAdjacence;
    }

    public void setActiver(Boolean activer) {
        this.activer = activer;
    }

    /**
     * Incrémente le degré actuelle du noeud.
     */
    public void ajouterDegre() {
        degreActuelle += 1;
    }

    /**
     * Décrémente le degré actuelle du noeud de 2 pour enlever le lien.
     */
    public void suppressionDegre() {
        degreActuelle -= 2;
    }

    /**
     * décrémente le degré actuelle du noeud de 1 pour le retour arrière.
     */
    public void diminuerDegre() {
        degreActuelle -= 1;
    }

    /**
     * Vérifie si le noeud est valide.
     *
     * @return 0 si valide
     */
    public int estValide() {
        return (degreSoluce - degreActuelle);
    }

    /**
     * Ajoute un noeud à la liste d'adjacence.
     *
     * @param n le noeud à ajouter
     */
    public void ajouterNoeudAdjacence(Noeud n) {
        listeAdjacence.add(n);
    }

    /**
     * Retire un noeud de la liste d'adjacence.
     *
     * @param n le noeud à retirer
     */
    public void retirerNoeudAdjacence(Noeud n) {
        listeAdjacence.remove(n);
    }

    /**
     * Retourne la liste des noeuds connectés à ce noeud.
     *
     * @return Liste des noeuds connectés.
     */
    public ArrayList<Noeud> afficherReseau() {
        ArrayList<Noeud> listeNoeuds = new ArrayList<>();
        afficherReseauRecursif(this, listeNoeuds);
        return listeNoeuds;
    }

    /**
     * Parcours récursif du réseau à partir d'un noeud.
     *
     * @param noeud Noeud actuel
     * @param visites Liste des noeuds déjà visités
     */
    private void afficherReseauRecursif(Noeud noeud, ArrayList<Noeud> visites) {
        if (visites.contains(noeud)) {
            return;
        }
        visites.add(noeud);

        for (Noeud voisin : noeud.listeAdjacence) {
            afficherReseauRecursif(voisin, visites);
        }
    }

    /**
     * Affiche le réseaux de connection du noeud.
     *
     * @return false pour l'instant
     */
    @Override
    public Boolean activer() {
        ArrayList<Noeud> noeuds = afficherReseau();
        for (Noeud noeud : noeuds) {
            noeud.setActiver(true);
            if (noeud.getSurbrillance()) {
                noeud.surbrillanceOff();
            } else {
                noeud.surbrillanceOn();
            }
            noeud.setActiver(false);
        }
        return true;
    }

    /**
     * Active la surbrillance du noeud.
     */
    @Override
    public void surbrillanceOn() {
        if (activer) {
            surbrillance = true;
        }
    }

    /**
     * Désactive la surbrillance du noeud.
     */
    @Override
    public void surbrillanceOff() {
        if (activer) {
            surbrillance = false;
        }
    }

    /**
     * Affiche le Noeud.
     */
    @Override
    public String draw() {
        if (getSurbrillance()) {
            return "/META-INF/assetsGraphiques/pie/surbrillance/pie" + degreSoluce + ".png";
        } else if (degreActuelle < degreSoluce) {
            return "/META-INF/assetsGraphiques/pie/standard/pie" + degreSoluce + ".png";
        } else if (degreActuelle == degreSoluce) {
            return "/META-INF/assetsGraphiques/pie/good/pie" + degreSoluce + ".png";
        } else {
            return "/META-INF/assetsGraphiques/pie/satured/pie" + degreSoluce + ".png";
        }
    }

    /**
     * Permet de faire l'affichage de la classe.
     */
    @Override
    public void drawTerm() {
        System.out.print("N{" + degreActuelle + "/" + degreSoluce + "}   ");
    }

    /**
     * Comparaison entre deux noeuds Les noeuds sont comparé par leur position.
     *
     * @param n2 le noeud avec qui comparé
     * @return le résultat de la comparaison
     */
    @Override
    public int compareTo(Noeud n2) {
        return this.position.compareTo(n2.position);
    }

    /**
     * Permet de faire l'affichage de la classe.
     */
    @Override
    public String toString() {
        return "Noeud{" + "position=" + position + ", degreSoluce=" + degreSoluce + ", degreActuelle=" + degreActuelle
                + ", Surbrillance= " + surbrillance + "}";
    }
}
