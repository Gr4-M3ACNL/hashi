package fr.m3acnl.game.logique;

/**
 * Classe DoubleLien pour gérer les liens croisés.
 *
 * @author COGNARD Luka
 * @version 1.0
 */
public class DoubleLien implements ElementJeu {

    /**
     * Le 1er lien.
     */
    private final Lien lien1;

    /**
     * Le 2ème lien.
     */
    private final Lien lien2;

    /**
     * Interrupteur quand un lien est activé.
     */
    private Boolean interrupteur;

    /**
     * Lien qui est en surbrillance.
     */
    private int lienBrillance;

    private boolean modifie = false;

    /**
     * Constructeur pour créer une instance de DoubleLien.
     *
     * @param l1 Le 1er lien
     * @param l2 Le 2ème lien
     */
    public DoubleLien(Lien l1, Lien l2) {
        lien1 = l1;
        lien2 = l2;
        interrupteur = false;
        lienBrillance = 0;
    }

    /**
     * Récupère le 1er lien.
     *
     * @return Le 1er lien
     */
    public Lien getLien1() {
        return lien1;
    }

    /**
     * Récupère le 2ème lien.
     *
     * @return Le 2ème lien
     */
    public Lien getLien2() {
        return lien2;
    }

    /**
     * Récupère l'état de l'interrupteur.
     *
     * @return L'état de l'interrupteur
     */
    public Boolean getInterrupteur() {
        return interrupteur;
    }

    /**
     * Active l'interrupteur.
     */
    public void activeInterrupteur() {
        interrupteur = true;
    }

    /**
     * Désactive l'interrupteur si les liens sont égaux.
     */
    public void desactiveInterrupteur() {
        if (lien1.getNbLien() == lien2.getNbLien()) {
            interrupteur = false;
        }
    }

    /**
     * Renvoie le lien actif dans le double lien.
     *
     * @return Le lien actif, retourne null si aucun n'est actif.
     */
    public Lien lienActif() {
        if (interrupteur) {
            if (lien1.getNbLien() == 0) {
                if (lien2.getNbLien() == 0) {
                    return null;
                } else {
                    return lien2;
                }
            } else {
                return lien1;
            }
        } else {
            return null;
        }
    }

    /**
     * Active la surbrillance du lien en fonction du nœud donné.
     *
     * @param n Le nœud lié
     */
    public void activerSurbrillance(Noeud n) {
        if (!interrupteur) {
            if (lien1.noeudDansLien(n) == 0) {
                lien1.surbrillanceOn();
                lienBrillance = 1;

            } else {
                lien2.surbrillanceOn();
                lienBrillance = 2;
            }
        } else {
            if (lien1.noeudDansLien(n) == 0) {
                if (lien1.getNbLien() != 0) {
                    lien1.surbrillanceOn();
                    lienBrillance = 1;
                }
            } else if (lien2.getNbLien() != 0) {
                lien2.surbrillanceOn();
                lienBrillance = 2;

            }
        }
        averifié();
    }

    @Override
    public boolean modifié() {
        return modifie;
    }

    @Override
    public void verifié() {
        modifie = false;
    }

    @Override
    public void averifié() {
        modifie = true;
    }

    /**
     * Méthode non utilisée.
     *
     * @return false
     */
    @Override
    public Boolean activer() {
        return false;
    }

    /**
     * Active le lien s'il est activable en fonction du nœud donné.
     *
     * @param n Le nœud lié
     * @return Le lien qui a été activé, retourne null si aucun lien est activé
     */
    public Lien activer(Noeud n) {
        if (!interrupteur) {
            if (lien1.noeudDansLien(n) == 0) {
                if (lien1.activer()) {
                    this.activeInterrupteur();
                    return lien1;
                }
            } else {
                if (lien2.activer()) {
                    this.activeInterrupteur();
                    return lien2;
                }
            }
        } else {
            if (lien1.noeudDansLien(n) == 0) {
                if (lien1.getNbLien() != 0) {
                    lien1.activer();
                    this.desactiveInterrupteur();
                    return lien1;
                }
            } else if (lien2.getNbLien() != 0) {
                lien2.activer();
                this.desactiveInterrupteur();
                return lien2;
            }
        }
        averifié();
        return null;
    }

    @Override
    public void surbrillanceOn() {
        if (lienActif() != null) {
            lienActif().surbrillanceOn();
        }
        averifié();
    }

    @Override
    public void surbrillanceOff() {
        if (lienBrillance == 1) {
            lien1.surbrillanceOff();
            lienBrillance = 0;
        } else if (lienBrillance == 2) {
            lien2.surbrillanceOff();
            lienBrillance = 0;
        }
        averifié();
    }

    /**
     * Renvoie le chemin de l'image du DoubleLien.
     *
     * @return Le chemin de l'image correspondant à l'état du DoubleLien
     */
    @Override
    public String draw() {
        if (lien1.getSurbrillance() || lienBrillance == 1) {
            return lien1.draw();
        } else if (lien2.getSurbrillance() || lienBrillance == 2) {
            return lien2.draw();
        } else if (this.lienActif() == null) {
            return "/META-INF/assetsGraphiques/link/blank.png";
        }
        return this.lienActif().draw();
    }

    /**
     * Affiche le DoubleLien dans le terminal.
     */
    @Override
    public void drawTerm() {
        System.out.print("D{" + lien1.getNbLien() + "|" + lien2.getNbLien() + "}   ");
    }

    /**
     * Affiche une représentation textuelle du DoubleLien.
     *
     * @return Une chaîne de caractères représentant l'objet
     */
    @Override
    public String toString() {
        return " D" + "(" + lien1 + "|" + lien2 + ") ";
    }

}
