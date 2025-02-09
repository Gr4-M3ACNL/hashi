
/**
 * Classe jeu.
 *
 * @autor COGNARD Luka
 * @date 03-02-2025
 * @version 1.0
 * @description Contient la classe Jeu
 *
 */
//package fr.m3acnl.game.logique;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Cette classe gère le jeu.
 */
public class Jeu {

    /**
     * Le plateau de jeu.
     */
    private final Matrice plateau;

    /**
     * Temps au début.
     */
    private final Instant instantDebut;

    /**
     * Temps fin de partie.
     */
    private final long tempsFinal;

    /**
     * Pile des coup jouer.
     */
    private final Pile coupsJouer;

    /**
     * Pile des coup jouer en buffeur pour le retour.
     */
    private final Pile coupsJouerBuff;

    /**
     * Clef de la matrice.
     */
    private final int clefFichier;

    /**
     * Constructeur pour une instance d'objet Jeu.
     *
     * @param clef clef de la matrice dans le fichier json
     * @param mat La matrice du jeu
     */
    public Jeu(int clef, Double[][] mat) {
        clefFichier = clef;
        instantDebut = Instant.now();
        coupsJouer = new Pile();
        coupsJouerBuff = new Pile();
        plateau = new Matrice(7, 7, mat, this);
        tempsFinal = 0;
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
     * Active l'élement de jeu selectionner.
     *
     * @param x Coordonnée en x
     * @param y Coordonnée en y
     * @param n Le noeud du lien a activer dans le doubleLien
     */
    public void activeElem(int x, int y, Noeud n) {
        ElementJeu elem = plateau.getElement(x, y);
        if (elem instanceof DoubleLien) {
            Lien lienActiver = ((DoubleLien) elem).activer(n);
            if (lienActiver != null) {
                coupsJouer.empiler(lienActiver);
            }
        } else if (elem != null) {
            if (elem instanceof Lien && elem.activer()) {
                coupsJouer.empiler(elem);
            }
        }
    }

    /**
     * Reviens en arrière en revenant au coup précédent.
     */
    public void retour() {
        if (!coupsJouer.estVide()) {
            ((Lien) coupsJouer.depiler()).retourArriere();
        }
    }

    /**
     * Vérifie si le jeu est gagner.
     *
     * @return true si le joueur a gagner
     */
    public Boolean gagner() {
        return plateau.validationMatrice();
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
}
