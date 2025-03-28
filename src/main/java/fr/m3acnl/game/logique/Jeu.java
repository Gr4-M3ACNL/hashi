package fr.m3acnl.game.logique;

import fr.m3acnl.game.logique.elementjeu.Noeud;
import fr.m3acnl.game.logique.elementjeu.Lien;
import fr.m3acnl.game.logique.elementjeu.DoubleLien;
import fr.m3acnl.game.logique.elementjeu.ElementJeu;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;

/**
 * Classe Jeu pour la logique et la gestion du jeu.
 *
 * @author COGNARD Luka
 * @version 1.0
 */
public class Jeu {

    // ==================== Attributs ====================
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
     * @deprecated le chronomètre est calculé dans la partie
     */
    @Deprecated
    private long tempsFinal;

    /**
     * Pile des coups joués.
     */
    private final Pile coupsJouer;

    /**
     * Pile des coup joués en buffer pour le retour.
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
     * La pile des coups de la sauvegarde manuelle par le joueur.
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
        sauvegardeAutomatique = new ArrayList<Lien>();
        pointDeSauvegarde = new ArrayList<Lien>();
    }

    // ==================== Getter ====================
    /**
     * Récupère la taille.
     *
     * @return La taille du plateau.
     */
    public int getTaille() {
        return taille;
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
     * Récupère la pile des coups joués.
     *
     * @return La pile des coups joués
     */
    public Pile getCoupsJouer() {
        return coupsJouer;
    }

    /**
     * Récupère le tableau de la sauvegarde automatique.
     *
     * @return Le tableau de la sauvegarde automatique
     */
    public ArrayList<Lien> getSauvegardeAutomatique() {
        return sauvegardeAutomatique;
    }

    /**
     * Récupère le tableau de la sauvegarde manuelle.
     *
     * @return Le tableau de la sauvegarde manuelle
     */
    public ArrayList<Lien> getPointDeSauvegarde() {
        return pointDeSauvegarde;
    }

    /**
     * Récupère la pile des coups joués en buffer.
     *
     * @return La pile des coups joués en buffer
     */
    public Pile getCoupsJouerBuff() {
        return coupsJouerBuff;
    }

    /**
     * Done le temp écoulé actuellement en secondes.
     *
     * @return le temp écoulé
     * 
     * @deprecated le chronomètre est maintent calculé dans la partie
     */
    @Deprecated
    public Long getTempsEcouler() {
        return tempsFinal + Duration.between(instantDebut, Instant.now()).toSeconds();
    }

    /**
     * Récupère le temps final.
     *
     * @return le temps final
     * 
     * @deprecated le chronomètre est maintenant calculé dans la partie
     */
    @Deprecated
    public Long getTempsFinal() {
        return tempsFinal;
    }

    /**
     * Vérifie si le jeu est gagné.
     *
     * @return true si le joueur à gagné
     */
    public Boolean gagner() {
        if (plateau.validationMatrice()) {
            tempsFinal = getTempsEcouler();
            return true;
        }
        return false;
    }

    // ==================== Setter ====================
    /**
     * Modification du temps final.
     *
     * @param temps Le temp final
     * 
     * @deprecated le chronomètre est maintenant calculé dans la partie
     */
    @Deprecated
    public void setTempsFinal(long temps) {
        tempsFinal = temps;
    }

    // ==================== Action ====================
    /**
     * Renvoie une copie du jeu.
     * 
     * @return La copie du jeu.
     */
    public Jeu copieJeu() {
        Jeu newJeu = new Jeu(taille, plateau.getMatrice2Array());
        Noeud n1 = null;
        for (Lien l : coupsJouer.copieTab()) {
            n1 = l.getNoeud1();
            if (l.getOrientation() == 1) {
                newJeu.activeElemJeu(n1.getPosition().getCoordX(), n1.getPosition().getCoordY() + 1, n1);
            } else {
                newJeu.activeElemJeu(n1.getPosition().getCoordX() + 1, n1.getPosition().getCoordY(), n1);
            }
        }
        return newJeu;
    }
    
    /**
     * Vérification si le lien horizontal n'est pas coupé sur son chemin.
     *
     * @param noeud1 Le 1er noeud du lien
     * @param noeud2 Le 2eme noeud du lien
     * @param nbLien Le nombre de liens actuel
     * @param aide Si la fonction est appelée pour une aide true, false sinon
     * @return 1 si il est coupé 0 sinon
     */
    public int verificationHorizontal(Noeud noeud1, Noeud noeud2, int nbLien, boolean aide) {
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
        if (!aide) {
            for (int i = 0; i < doubleLienPossible.size(); i++) {
                doubleLienPossible.get(i).activeInterrupteur();
            }
        }
        return 0;
    }

    /**
     * Vérification si le lien vertical n'est pas coupé sur son chemin.
     *
     * @param noeud1 Le 1er noeud du lien
     * @param noeud2 Le 2eme noeud du lien
     * @param nbLien Le nombre de liens actuel
     * @param aide Si la fonction est appelée pour une aide true, false sinon
     * @return 1 si il est coupé 0 sinon
     */
    public int verificationVertical(Noeud noeud1, Noeud noeud2, int nbLien, boolean aide) {
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
        if (!aide) {
            for (int i = 0; i < doubleLienPossible.size(); i++) {
                doubleLienPossible.get(i).activeInterrupteur();
            }
        }
        return 0;
    }

    /**
     * Sauvegarde automatiquement seulement si le graphe est valide.
     * <br>
     * utiliser pour les validation de la matrice.
     */
    private void sauvegardeAuto() {
        if (plateau.liensValide()) {
            sauvegardeAutomatique = coupsJouer.copieTab();
        }
    }

    /**
     * Sauvegarde manuellement peu importe l'état du graphe.
     * <br>
     * Utiliser pour les sauvegardes manuelles.
     */
    public void sauvegarderManuellement() {
        pointDeSauvegarde = coupsJouer.copieTab();
    }

    /**
     * Active l’élément de jeu selectionné.
     *
     * @param x Coordonnée en x
     * @param y Coordonnée en y
     * @param n Le noeud du lien à activer dans le doubleLien
     * @return Renvoie le lien activé, si il n'a pas été activé renvoie null
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
     * Active l'élément du jeu sélectionné par le joueur.
     *
     * @param x Coordonnée en x
     * @param y Coordonnée en y
     * @param n Le noeud du lien à activer dans le doubleLien
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
     * Active l'élément du jeu pour les aides.
     *
     * @param x Coordonnée en x
     * @param y Coordonnée en y
     * @param n Le noeud du lien a activer dans le doubleLien
     * @return Renvoie null si le lien n'as pas été activé sinon renvoie le lien.
     */
    public Lien activeElemAide(int x, int y, Noeud n) {
        return activeElem(x, y, n);
    }

    /**
     * Retour au coup précédent.
     */
    public void retour() {
        if (!coupsJouer.estVide()) {
            coupsJouerBuff.empiler(coupsJouer.sommet());
            ((Lien) coupsJouer.depiler()).retourArriere();
        }
    }

    /**
     * Avance au coup suivant.
     * <br>
     * Uniquement si il y a un coup dans le buffer.
     */
    public void avancer() {
        if (!coupsJouerBuff.estVide()) {
            coupsJouer.empiler(coupsJouerBuff.sommet());
            ((Lien) coupsJouerBuff.depiler()).activer();
        }
    }

    /**
     * Méthode qui met le chronomètre en pause.
     * 
     * @deprecated le chronomètre est maintenant calculé dans la partie
     */
    @Deprecated
    public void stopChrono() {
        tempsFinal += Duration.between(instantDebut, Instant.now()).toSeconds();
    }

    /**
     * Méthode qui remet le chronomètre en marche.
     * 
     * @deprecated le chronomètre est maintenant calculé dans la partie
     */
    @Deprecated
    public void reprendreChrono() {
        instantDebut = Instant.now();
    }

    /**
     * Charge la sauvegarde donnée.
     *
     * @param sauvegarde la sauvegarde à charger.
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
     * <br>
     * Utiliser pour les validation de la matrice.
     */
    public void chargerSauvegardeAuto() {
        chargerSauvegarde(sauvegardeAutomatique);
    }

    /**
     * Charge la sauvegarde manuelle.
     */
    public void chargerSauvegardeManuel() {
        chargerSauvegarde(pointDeSauvegarde);
    }

    /**
     * Rejoue un coup du lien à l'index donné lors du chargement.
     * <br>
     * L'index est celui de la liste des liens.
     * 
     * @param index L'index du lien à ajouter et empiler.
     * @throws RuntimeException si le lien ne s'est pas activé.
     */
    public void rejouer(int index) {
        if (plateau.getCopListeLien().get(index).activer()) {
            coupsJouer.empiler(plateau.getCopListeLien().get(index));
        } else {
            throw new RuntimeException("Le lien n'a pas pu s'activer");
        }
    }

    // ==================== Affichage ====================
    /**
     * Affiche le jeu.
     * <br>
     * Utilisé pour le terminal.
     */
    public void drawJeuTerm() {
        plateau.drawTerm();
    }
}
