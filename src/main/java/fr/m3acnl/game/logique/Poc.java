/**
 * @autor MABIRE Aymeric
 * @date 05-02-2025
 * @version 1.0
 * @description Contient la classe Proof of Concept pour la matrice du jeu
 *
 */
package fr.m3acnl.game.logique;

import java.util.ArrayList;

public class Poc {

    private ArrayList<ArrayList<ElementJeu>> matrice;

    /**
     * Constructeur pour une nouvelle instance de Lien
     *
     * @param lignes Nombre de lignes de la matrice
     * @param cols Nombre de colonnes de la matrice
     */
    public Poc(int lignes, int cols) {
        matrice = new ArrayList<>();
        for (int i = 0; i < lignes; i++) {
            ArrayList<ElementJeu> ligne = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                ligne.add(null); // Initialize with null or any default value
            }
            matrice.add(ligne);
        }
    }

    /**
     * Récupère un élément de la matrice a la position donnée
     *
     * @param ligne La ligne de l'élément
     * @param col La colonne de l'élément
     * @return L'élément a la position donnée
     */
    public ElementJeu getElement(int ligne, int col) {
        return matrice.get(ligne).get(col);
    }

    /**
     * Modifie un élément de la matrice a la position donnée
     *
     * @param ligne La ligne de l'élément
     * @param col La colonne de l'élément
     * @param element L'élément a mettre a la position donnée
     */
    public void setElement(int ligne, int col, ElementJeu element) {
        matrice.get(ligne).set(col, element);
    }

    //public void genMatrice(); //Genere la matrice grâce au fichier Json
    /**
     * Dessine la matrice
     */
    public void draw() {
        for (ArrayList<ElementJeu> ligne : matrice) {
            for (ElementJeu element : ligne) {
                if (element != null) {
                    element.draw();
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Génère les liens de la matrice
     */
    public void genLink() {
        for (int i = 0; i < matrice.size(); i++) {
            for (int j = 0; j < matrice.get(i).size() - 1; j++) {
                if (matrice.get(i).get(j) instanceof Noeud) {
                    verifHorizontale(j, i);
                    verifVerticale(j, i);
                }
            }
        }
    }

    /**
     * Vérifie si il y a un noeud a droite et crée un lien entre les deux noeuds
     *
     * @param y La colonne de l'élément
     * @param x La ligne de l'élément
     */
    private void verifHorizontale(int y, int x) {
        //verif si il y a un noeud a droite
        //Si il y a un noeud, crée un lien entre les deux noeuds et le rajoute dans la matrice
        ElementJeu current = matrice.get(y).get(x);
        for (int i = y; i < matrice.size(); i++) {
            for (int j = x; j < matrice.get(i).size() - 1; j++) {
                ElementJeu right = matrice.get(i).get(j + 1);

                if (current instanceof Noeud && right instanceof Noeud) {
                    // Create a link between the two nodes
                    Lien lien = new Lien((Noeud) current, (Noeud) right, 1, new Jeu(5), 1);
                    // Add the link to the matrix
                    for (int k = x; k < j; k++) {
                        if (matrice.get(i).get(k) == null) {
                            matrice.get(i).set(k, lien);
                        } else if (matrice.get(i).get(k) instanceof Lien) {
                            Lien lien2 = (Lien) matrice.get(i).get(k);
                            DoubleLien dl = new DoubleLien(lien, lien2);
                            matrice.get(i).set(k, dl);
                        }

                    }
                    return;
                }
            }
        }
    }

    /**
     * Vérifie si il y a un noeud en dessous et crée un lien entre les deux
     * noeuds
     *
     * @param y La colonne de l'élément
     * @param x La ligne de l'élément
     */
    private void verifVerticale(int y, int x) {
        //verif si il y a un noeud en bas
        //Si il y a un noeud, crée un lien entre les deux noeuds et le rajoute dans la matrice
        ElementJeu current = matrice.get(y).get(x);
        for (int i = y; i < matrice.size(); i++) {
            for (int j = x; j < matrice.get(i).size() - 1; j++) {
                ElementJeu bot = matrice.get(i + 1).get(j);

                if (current instanceof Noeud && bot instanceof Noeud) {
                    // Create a link between the two nodes
                    Lien lien = new Lien((Noeud) current, (Noeud) bot, 0, new Jeu(5), 1);
                    // Add the link to the matrix
                    for (int k = y; k < i; k++) {
                        if (matrice.get(k).get(j) == null) {
                            matrice.get(k).set(j, lien);
                        } else if (matrice.get(k).get(j) instanceof Lien) {
                            Lien lien2 = (Lien) matrice.get(k).get(j);
                            DoubleLien dl = new DoubleLien(lien, lien2);
                            matrice.get(j).set(j, dl);
                        }

                    }
                    return;
                }
            }
        }
    }
}

/*
 * Variables:
 * matrice (contient noeuds et liens)
 * 
 * 
 * 
 * Méthodes:
 * Initalisation-start
 * seek-possibilité
 * check-double
 * 
 * 
 * 
 */
