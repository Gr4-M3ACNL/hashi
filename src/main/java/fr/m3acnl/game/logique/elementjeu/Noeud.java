package fr.m3acnl.game.logique.elementjeu;

import java.util.ArrayList;

/**
 * Classe Noeud a lié dans le jeu.
 *
 * @author MABIRE Aymeric
 * @version 1.0
 */
public class Noeud implements ElementJeu, Comparable<Noeud> {

    // ==================== Attributs ====================
    /**
     * La position en coordonnée du noeud.
     */
    private final Coord position;

    /**
     * Le degré solution du noeud.
     */
    private final int degreSoluce;

    /**
     * Le degré actuel du noeud.
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
     * Permet de savoir si l'élément a été modifié.
     */
    private Boolean modifie = true;

    /**
     * Constructeur pour créer une nouvelle instance d'un Noeud.
     *
     * @param x La ligne de l'élément
     * @param y La colonne de l'élément
     * @param degS le degré solution du noeud
     */
    public Noeud(int x, int y, int degS) {
        position = new Coord(x, y);
        degreSoluce = degS;
        degreActuelle = 0;
        listeAdjacence = new ArrayList<>();
        surbrillance = false;
    }

    // ==================== Getter ====================
    /**
     * Récupère la position du noeud.
     *
     * @return les coordonnées du noeud
     */
    public Coord getPosition() {
        return position;
    }

    /**
     * Récupère le degré actuel.
     *
     * @return le degré actuel
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

    /**
     * Vérifie si le noeud est valide.
     *
     * @return 0 si valide
     */
    public int estValide() {
        return (degreSoluce - degreActuelle);
    }

    // ==================== Setter ====================
    /**
     * Permet de définir l'activation du noeud. (Pour la surbrillance pour
     * eviter que le noeud ne s'active en survol)
     *
     * @param activer Boolean pour activer ou non le noeud
     */
    public void setActiver(Boolean activer) {
        this.activer = activer;
    }

    /**
     * Incrémente le degré actuel du noeud.
     */
    public void ajouterDegre() {
        degreActuelle += 1;
        averifie();
    }

    /**
     * Décrémente le degré actuel du noeud de 2 pour enlever le lien.
     */
    public void suppressionDegre() {
        degreActuelle -= 2;
        averifie();
    }

    /**
     * décrémente le degré actuel du noeud de 1 pour le retour arrière.
     */
    public void diminuerDegre() {
        degreActuelle -= 1;
        averifie();
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

    // ==================== Action ====================
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

    // ==================== Override ====================
    /**
     * Permet de savoir si l'élément a été modifié.
     *
     * @return true si l'élément a été modifié, false sinon
     */
    @Override
    public Boolean modifie() {
        return modifie;
    }

    /**
     * Permet d'indiquer que l'élément a été consulté.
     */
    @Override
    public void verifie() {
        modifie = false;
    }

    /**
     * Permet de dire que l'élément a été modifié.
     */
    @Override
    public void averifie() {
        modifie = true;
    }

    /**
     * Affiche le réseau de connection du noeud.
     *
     * @return false pour l'instant
     */
    @Override
    public Boolean activer() {
        ArrayList<Noeud> noeuds = afficherReseau();

        // Vérifier si au moins un nœud a la surbrillance activée
        boolean etatSurbrillance = noeuds.stream().anyMatch(Noeud::getSurbrillance);

        // Appliquer l'état opposé à tous les nœuds
        for (Noeud noeud : noeuds) {
            noeud.setActiver(true);
            if (etatSurbrillance) {
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
            averifie();
        }
    }

    /**
     * Désactive la surbrillance du noeud.
     */
    @Override
    public void surbrillanceOff() {
        if (activer) {
            surbrillance = false;
            averifie();
        }
    }

    /**
     * Comparaison entre deux noeuds Les noeuds sont comparés par leur position.
     *
     * @param n2 le noeud avec qui comparé
     * @return le résultat de la comparaison
     */
    @Override
    public int compareTo(Noeud n2) {
        return this.position.compareTo(n2.position);
    }

    // ==================== Affichage ====================
    /**
     * Affiche le Noeud.
     * <br>
     * Renvoie le chemin d'accès de la texture du noeud.
     * Selon le degré solution et le degré actuel.
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
     * Permet de faire l'affichage de la classe.
     */
    @Override
    public String toString() {
        return "Noeud{" + "position=" + position + ", degreSoluce=" + degreSoluce + ", degreActuelle=" + degreActuelle
                + ", Surbrillance= " + surbrillance + "}";
    }
}
