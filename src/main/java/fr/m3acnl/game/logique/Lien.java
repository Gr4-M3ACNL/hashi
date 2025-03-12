package fr.m3acnl.game.logique;

import java.util.ArrayList;

/**
 * Classe Lien, afin de les utilisés pour lié 2 noeuds.
 *
 * @author COGNARD Luka
 * @version 1.0
 */
public class Lien implements ElementJeu {

    /**
     * Le jeu pour récupérer le plateau.
     */
    private final Jeu jeu;

    /**
     * 1er noeud du lien.
     */
    private final Noeud noeud1;

    /**
     * 2ème noeud du lien.
     */
    private final Noeud noeud2;

    /**
     * Nombre de lien actuelle.
     */
    private int nbLien;

    /**
     * Le nombre soluce du lien.
     */
    private final int nbLienSoluce;

    /**
     * La surbrillance du lien.
     */
    private Boolean surbrillance;

    /**
     * L'orientation du lien.
     */
    private final int orientation;

    /**
     * L'index du lien dans la liste de lien.
     */
    private int index;

    private boolean modifie = false;

    private int cmptVerif = 0;

    private int cmptVerifMax;

    private ArrayList<DoubleLien> listeDl;

    /**
     * Constructeur pour une nouvelle instance de Lien.
     *
     * @param n1 premier Noeud
     * @param n2 deuxième Noeud
     * @param sol le nombre de lien de la solution
     * @param orient L'orientation du Lien
     * @param j Le jeu d'on-t-il fait parti
     */
    public Lien(Noeud n1, Noeud n2, int sol, Jeu j, int orient) {
        noeud1 = n1;
        noeud2 = n2;
        nbLienSoluce = sol;
        nbLien = 0;

        surbrillance = false;
        jeu = j;
        orientation = orient;
        listeDl = new ArrayList<>();

        cmptVerifMax = tailleLien();
    }

    /**
     * Récupère l'index du lien.
     *
     * @return L'index du lien
     */
    public int getIndex() {
        return index;
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
     * Récupère l'orientation du lien.
     *
     * @return l'orientation du lien
     */
    public int getOrientation() {
        return orientation;
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
     *
     * @return la surbrillance
     */
    public Boolean getSurbrillance() {
        return surbrillance;
    }

    /**
     * Récupère le nombre soluce du lien.
     *
     * @return le nombre soluce
     */
    public int getNbLienSoluce() {
        return nbLienSoluce;
    }

    /**
     * Défini l'index du lien.
     *
     * @param i L'index du lien
     */
    public void setIndex(int i) {
        index = i;
    }

    public void addDoubleLien(DoubleLien dl) {
        listeDl.add(dl);
    }

    public int tailleLien() {
        int tot = 0;
        if (orientation == 1) {
            for (int i = noeud1.getPosition().getCoordY() + 1; i < noeud2.getPosition().getCoordY(); i++) {
                tot++;
            }
        } else {
            for (int i = noeud1.getPosition().getCoordX() + 1; i < noeud2.getPosition().getCoordX(); i++) {
                tot++;
            }
        }
        return tot;
    }

    /**
     * Active la surbrillance du lien.
     */
    @Override
    public void surbrillanceOn() {
        surbrillance = true;
        averifié();
    }

    /**
     * Désactive la surbrillance du lien.
     */
    @Override
    public void surbrillanceOff() {
        surbrillance = false;
        averifié();
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
     * Active le lien le faisant passer à son état suivant et met à jour le
     * degré actuelle des noeud liés.
     *
     * @return true si le lien a été activer false sinon
     */
    @Override
    public Boolean activer() {
        System.out.println("Lien activer");
        averifié();
        nbLien = (nbLien + 1) % 3;
        if (nbLien != 2) {
            if (orientation == 1) {
                if (jeu.verificationHorizontal(noeud1, noeud2, nbLien) == 1) {
                    nbLien -= 1;

                    return false;
                }
            } else {
                if (jeu.verificationVertical(noeud1, noeud2, nbLien) == 1) {
                    nbLien -= 1;
                    return false;
                }
            }
        }
        if (nbLien == 0) {
            noeud1.suppressionDegre();
            noeud2.suppressionDegre();

            noeud1.retirerNoeudAdjacence(noeud2);
            noeud2.retirerNoeudAdjacence(noeud1);
        } else {
            noeud1.ajouterDegre();
            noeud2.ajouterDegre();
            if (nbLien == 1) {
                noeud1.ajouterNoeudAdjacence(noeud2);
                noeud2.ajouterNoeudAdjacence(noeud1);
                if (noeud1.getSurbrillance() || noeud2.getSurbrillance()) {
                    noeud1.setActiver(true);
                    noeud2.setActiver(true);
                    noeud1.surbrillanceOn();
                    noeud2.surbrillanceOn();
                    noeud1.setActiver(false);
                    noeud2.setActiver(false);
                }
            }

        }

        System.out.println(nbLien + "\n-------------------");
        return true;
    }

    /**
     * Effectue le retour a l'état précédent du Lien et des noeuds.
     */
    public void retourArriere() {
        averifié();
        nbLien = (nbLien + 2) % 3;
        if (nbLien < 2) {
            if (nbLien == 0) {
                if (orientation == 1) {
                    jeu.verificationHorizontal(noeud1, noeud2, nbLien);
                } else {
                    jeu.verificationVertical(noeud1, noeud2, nbLien);
                }
            }
            noeud1.diminuerDegre();
            noeud2.diminuerDegre();
        } else {
            if (orientation == 1) {
                jeu.verificationHorizontal(noeud1, noeud2, nbLien);
            } else {
                jeu.verificationVertical(noeud1, noeud2, nbLien);
            }
            noeud1.ajouterDegre();
            noeud2.ajouterDegre();
            noeud1.ajouterDegre();
            noeud2.ajouterDegre();
        }

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

    /**
     * Remet le lien à zéro .
     */
    public void remiseAzero() {
        if (nbLien == 1) {
            this.retourArriere();
        }
        if (nbLien == 2) {
            this.activer();
        }
        averifié();
    }

    @Override
    public boolean modifié() {
        return modifie;
    }

    @Override
    public void verifié() {
        if (cmptVerif == cmptVerifMax) {
            modifie = false;
            cmptVerif = 0;
        } else {
            cmptVerif++;
        }
    }

    @Override
    public void averifié() {
        modifie = true;
        cmptVerif = 0;
        for (DoubleLien dl : listeDl) {
            dl.averifié();
        }
    }

    /**
     * Affiche le Lien.
     */
    @Override
    public String draw() {
        String path = "/META-INF/assetsGraphiques/link/";
        if (surbrillance) {
            path += "surbrillance/";
            if (orientation == 1) { //Horizontal
                path += "horizontal_";
            } else { //Vertical
                path += "vertical_";
            }

            switch (nbLien) {
                case 0:
                    return path + "uno.png";

                case 1:
                    return path + "duo.png";
                case 2:
                    return path + "duo.png";
                default:
                    return "/META-INF/assetsGraphiques/link/blank.png";

            }
        } else {
            path += "standard/";
            if (orientation == 1) { //Horizontal
                path += "horizontal_";
            } else { //Vertical
                path += "vertical_";
            }
            switch (nbLien) {
                case 1:
                    return path + "uno.png";

                case 2:
                    return path + "duo.png";

                default:
                    return "/META-INF/assetsGraphiques/link/blank.png";

            }
        }
    }

    /**
     * Affiche le Lien dans le terminal.
     */
    @Override
    public void drawTerm() {
        if (orientation == 1) {
            System.out.print("H{" + nbLien + "/" + nbLienSoluce + "}   ");
        } else {
            System.out.print("V{" + nbLien + "/" + nbLienSoluce + "}   ");
        }
    }

    /**
     * Permet de faire l'affichage de la classe.
     */
    @Override
    public String toString() {
        String toString = "Lien{" + "noeud1=" + noeud1 + ", noeud2=" + noeud2 + ", nbLien=" + nbLien + ", nbLienSoluce="
                + nbLienSoluce;
        toString += ", surbrillance=" + surbrillance + ", orientation=" + orientation + ", index=" + index + '}';
        return toString;
    }
}
