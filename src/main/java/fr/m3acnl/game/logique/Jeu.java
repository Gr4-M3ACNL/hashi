package fr.m3acnl.game.logique;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Classe Jeu pour une représentation du jeu et gérer le jeu.
 *
 * @author COGNARD Luka
 * @version 1.0
 */
public class Jeu {

    /**
     * Le plateau de jeu.
     */
    private final Matrice plateau;

    /**
     * Temps au début.
     */
    private Instant instantDebut;

    /**
     * Temps fin de partie.
     */
    private long tempsFinal;

    /**
     * Pile des coup jouer.
     */
    private final Pile coupsJouer;

    /**
     * Pile des coup jouer en buffeur pour le retour.
     */
    private final Pile coupsJouerBuff;

    /**
     * Taille du plateau.
     */
    private final int taille;

    /**
     * La pile des coups de la sauvegarde automatique quand le graphe est bon.
     */
    private ArrayList<Lien> sauvegardeAutomatique;

    /**
     * La pile des coups de la sauvegarde manuel par le joueur.
     */
    private ArrayList<Lien> pointDeSauvegarde;

    /**
     * Constructeur pour une instance d'objet Jeu.
     *
     * @param taille La taille de la matrice
     * @param mat La matrice du jeu
     */
    public Jeu(int taille, Double[][] mat) {
        this.taille = taille;
        instantDebut = Instant.now();
        coupsJouer = new Pile();
        coupsJouerBuff = new Pile();
        plateau = new Matrice(this.taille, this.taille, mat, this);
        tempsFinal = 0;
        sauvegardeAutomatique = null;
        pointDeSauvegarde = null;
    }

    /**
     * Vérification si le lien horizontal n'est pas couper sur son chemin.
     *
     * @param noeud1 Le 1er noeud du lien
     * @param noeud2 Le 2eme noeud du lien
     * @param nbLien Le nombre de lien actuel
     * @return 1 si il est couper 0 sinon
     */
    public int verificationHorizontal(Noeud noeud1, Noeud noeud2, int nbLien) {
        int y1 = noeud1.getPosition().getCoordY();
        int y2 = noeud2.getPosition().getCoordY();
        int x = noeud1.getPosition().getCoordX();
        ArrayList<DoubleLien> doubleLienPossible = new ArrayList<>();
        for (int i = y1; i < y2; i++) {
            ElementJeu elem = plateau.getElement(x, i);
            if (elem instanceof DoubleLien) {
                if (((DoubleLien) elem).getInterrupteur()) {
                    if (nbLien == 1) {
                        return 1;
                    } else if (nbLien == 0) {
                        ((DoubleLien) elem).desactiveInterrupteur();
                    }
                } else {
                    doubleLienPossible.add(((DoubleLien) elem));
                }
            }
        }
        for (int i = 0; i < doubleLienPossible.size(); i++) {
            doubleLienPossible.get(i).activeInterrupteur();
        }
        return 0;
    }

    /**
     * Récupère la taille.
     * 
     * @return La taille du plateau.
     */
    public int getTaille() {
        return taille;
    }
    /**
     * Vérification si le lien vertical n'est pas couper sur son chemin.
     *
     * @param noeud1 Le 1er noeud du lien
     * @param noeud2 Le 2eme noeud du lien
     * @param nbLien Le nombre de lien actuel
     * @return 1 si il est couper 0 sinon
     */
    public int verificationVertical(Noeud noeud1, Noeud noeud2, int nbLien) {
        int x1 = noeud1.getPosition().getCoordX();
        int x2 = noeud2.getPosition().getCoordX();
        int y = noeud1.getPosition().getCoordY();
        ArrayList<DoubleLien> doubleLienPossible = new ArrayList<>();
        for (int i = x1; i < x2; i++) {
            ElementJeu elem = plateau.getElement(i, y);
            if (elem instanceof DoubleLien) {
                if (((DoubleLien) elem).getInterrupteur()) {
                    if (nbLien == 1) {
                        return 1;
                    } else if (nbLien == 0) {
                        ((DoubleLien) elem).desactiveInterrupteur();
                    }
                } else {
                    doubleLienPossible.add(((DoubleLien) elem));
                }
            }
        }
        for (int i = 0; i < doubleLienPossible.size(); i++) {
            doubleLienPossible.get(i).activeInterrupteur();
        }
        return 0;
    }

    /**
     * Sauvegarde automatiquement seulement si le graphe est valide.
     */
    private void sauvegardeAuto() {
        if (plateau.liensValide()) {
            sauvegardeAutomatique = coupsJouer.copieTab();
        }
    }

    /**
     * Sauvegarde manuellement peut importe l'état du graphe.
     */
    public void sauvegarderManuellement() {
        pointDeSauvegarde = coupsJouer.copieTab();
    }

    /**
     * Active l’élément de jeu selectionner.
     *
     * @param x Coordonnée en x
     * @param y Coordonnée en y
     * @param n Le noeud du lien a activer dans le doubleLien
     * @return Renvoie le lien activer si il n'a pas été activer renvoie null
     */
    private Lien activeElem(int x, int y, Noeud n) {
        ElementJeu elem = plateau.getElement(x, y);
        if (elem instanceof DoubleLien) {
            Lien lienActiver = ((DoubleLien) elem).activer(n);
            if (lienActiver != null) {
                return lienActiver;
            }
        } else if (elem != null) {
            if (elem instanceof Lien && elem.activer()) {
                return (Lien) elem;
            } else {
                elem.activer();
                return null;
            }
        }
        return null;
    }

    /**
     * Active Element du jeu sélectionner par le joueur.
     *
     * @param x Coordonnée en x
     * @param y Coordonnée en y
     * @param n Le noeud du lien a activer dans le doubleLien
     */
    public void activeElemJeu(int x, int y, Noeud n) {
        Lien elem = activeElem(x, y, n);
        if (elem != null) {
            coupsJouerBuff.vidange();
            coupsJouer.empiler(elem);
            sauvegardeAuto();
        }
    }

    /**
     * Active Element du jeu pour les aide.
     *
     * @param x Coordonnée en x
     * @param y Coordonnée en y
     * @param n Le noeud du lien a activer dans le doubleLien
     */
    public void activeElemAide(int x, int y, Noeud n) {
        activeElem(x, y, n);
    }

    /**
     * Reviens en arrière en revenant au coup précédent.
     */
    public void retour() {
        if (!coupsJouer.estVide()) {
            coupsJouerBuff.empiler(coupsJouer.sommet());
            ((Lien) coupsJouer.depiler()).retourArriere();
        }
    }

    /**
     * Avance en effectuant le coup suivant.
     */
    public void avancer() {
        if (!coupsJouerBuff.estVide()) {
            coupsJouer.empiler(coupsJouerBuff.sommet());
            ((Lien) coupsJouerBuff.depiler()).activer();
        }
    }

    /**
     * Vérifie si le jeu est gagner.
     *
     * @return true si le joueur a gagner
     */
    public Boolean gagner() {
        if (plateau.validationMatrice()) {
            tempsFinal = getTempsEcouler();
            return true;
        }
        return false;
    }

    /**
     * Méthode stoppant le chrono.
     */
    public void stopChrono() {
        tempsFinal += Duration.between(instantDebut, Instant.now()).toSeconds();
    }

    /**
     * Méthode remettant le chrono en marche.
     */
    public void reprendreChrono() {
        instantDebut = Instant.now();
    }

    /**
     * Done le temp écouler actuellement en seconde.
     *
     * @return le temp écouler
     */
    public Long getTempsEcouler() {
        return tempsFinal + Duration.between(instantDebut, Instant.now()).toSeconds();
    }

    /**
     * Récupère le temps final.
     *
     * @return le temp final
     */
    public Long getTempsFinal() {
        return tempsFinal;
    }

    /**
     * Modification du temps final.
     *
     * @param temps Le temp final
     */
    public void setTempsFinal(long temps) {
        tempsFinal = temps;
    }

    /**
     * Affiche le jeu.
     */
    public void drawJeu() {
        plateau.draw();
    }

    /**
     * Récupère le plateau.
     *
     * @return Le plateau
     */
    public Matrice getPlateau() {
        return plateau;
    }

    /**
     * Charge la sauvegarde donner.
     * @param sauvegarde la sauvegarde a charger.
     */
    private void chargerSauvegarde(ArrayList<Lien> sauvegarde) {
        if (sauvegarde == null) {
            return;
        }
        plateau.remiseAzero();
        coupsJouer.vidange();
        coupsJouerBuff.vidange();
        for (Lien lien : sauvegarde) {
            lien.activer();
        }
        coupsJouer.setTab(sauvegarde);
    }

    /**
     * Charge la sauvegarde automatique.
     */
    public void chargerSauvegardeAuto() {
        chargerSauvegarde(sauvegardeAutomatique);
    }

    /**
     * Charge la sauvegarde manuel.
     */
    public void chargerSauvegardeManuel() {
        chargerSauvegarde(pointDeSauvegarde);
    }
}
