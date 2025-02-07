/**
 * @autor COGNARD Luka
 * @date 03-02-2025
 * @version 1.0
 * @description Contient la classe Jeu
 *
 */
package fr.m3acnl.game.logique;

import java.time.Instant;
import java.util.ArrayList;

/**
 * Cette classe gère le jeu.
 */
public class Jeu {

    /**
     * Le plateau de jeu.
     */
    private Poc plateau;

    /**
     * Temp au début.
     */
    private Instant instantDebut;

    /**
     * Temp fin de partie.
     */
    private long tempFinal;

    /**
     * Pile des coup jouer.
     */
    private Pile coupJouer;

    /**
     * Pile des coup jouer en buffeur pour le retour.
     */
    private Pile coupJouerBuff;

    /**
     * Clef de la matrice.
     */
    private int clefFichier;

    /**
     * Constructeur pour une instance d'objet Jeu.
     * 
     * @param clef clef de la matrice dans le fichier json
     */
    public Jeu(int clef) {
        clefFichier = clef;
        instantDebut = Instant.now();
        coupJouer = new Pile();
        coupJouerBuff = new Pile();
        plateau = new Poc(7,7);
        tempFinal = 0;
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
        if (y1 < y2) {
            for (int i = y1; i < y2; i++) {
                ElementJeu elem = plateau.getElement(x, i);
                if (elem instanceof DoubleLien) {
                    if (((DoubleLien) elem).getInterupteur() && nbLien == 0) {
                        return 1;
                    }
                }
            }
        } else {
            for (int i = y1; i > y2; i--) {
                ElementJeu elem = plateau.getElement(x, i);
                if (elem instanceof DoubleLien) {
                    if (((DoubleLien) elem).getInterupteur() && nbLien == 0) {
                        return 1;
                    }
                }
            }
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
        if (x1 < x2) {
            for (int i = x1; i < x2; i++) {
                ElementJeu elem = plateau.getElement(i, y);
                if (elem instanceof DoubleLien) {
                    if (((DoubleLien) elem).getInterupteur() && nbLien == 0) {
                        return 1;
                    }
                }
            }
        } else {
            for (int i = x1; i > x2; i--) {
                ElementJeu elem = plateau.getElement(i, y);
                if (elem instanceof DoubleLien) {
                    if (((DoubleLien) elem).getInterupteur() && nbLien == 0) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    /**
     * Méthode de récupération du plateau.
     * 
     * @return Le plateau
     */
    public Poc getPlateau() {
        return plateau;
    }
}
