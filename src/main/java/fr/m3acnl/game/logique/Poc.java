/**
 * Classe Poc.
 * @autor MABIRE Aymeric
 * @date 05-02-2025
 * @version 1.0
 * @description Contient la classe Proof of Concept pour la matrice du jeu
 *
 */

package fr.m3acnl.game.logique;

import java.util.ArrayList;

public class Poc {

    private ArrayList<ArrayList<ElementJeu>> matrice; //Matrice avec les objets
    private ArrayList<ArrayList<Integer>> matrice2; //Matrice par default
    private ArrayList<Lien> listeLien; //Ligne par default

    /**
     * Constructeur pour une nouvelle instance de Lien.
     *
     * @param lignes Nombre de lignes de la matrice
     * @param cols Nombre de colonnes de la matrice
     */
    public Poc(int lignes, int cols, int[][] mat, Jeu jeu) {
        matrice = new ArrayList<>();
        listeLien = new ArrayList<Lien>();
        for (int i = 0; i < lignes; i++) {
            ArrayList<ElementJeu> ligne = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                ligne.add(null); // Initialize with null or any default value
            }
            matrice.add(ligne);
        }
        matrice2 = new ArrayList<>();
        for (int i = 0; i < lignes; i++) {
            ArrayList<Integer> ligne = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                ligne.add(mat[i][j]); // Initialize with null or any default value
            }
            matrice2.add(ligne);
        }

        this.genMatrice(jeu);

    }

    /**
     * Récupère un élément de la matrice a la position donnée.
     *
     * @param ligne La ligne de l'élément
     * @param col La colonne de l'élément
     * @return L'élément a la position donnée
     */
    public ElementJeu getElement(int ligne, int col) {
        return matrice.get(ligne).get(col);
    }

    /**
     * Modifie un élément de la matrice a la position donnée.
     *
     * @param ligne La ligne de l'élément
     * @param col La colonne de l'élément
     * @param element L'élément a mettre a la position donnée
     */
    public void setElement(int ligne, int col, ElementJeu element) {
        matrice.get(ligne).set(col, element);
    }

    /**
     * Dessine la matrice.
     */
    public void draw() {
        for (ArrayList<ElementJeu> ligne : matrice) {
            for (ElementJeu element : ligne) {
                if (element != null) {
                    element.draw();
                } else {
                    System.out.print("_ ");
                }
            }
            System.out.println();
        }
    }

    /**
     * Génère la matrice avec les noeuds et les liens.
     */
    public void genMatrice(Jeu jeu) {
        // Generate the nodes
        for (int i = 0; i < matrice2.size(); i++) {
            for (int j = 0; j < matrice2.get(i).size(); j++) {
                if (matrice2.get(i).get(j) < 0) {
                    matrice.get(i).set(j, new Noeud(i, j, -matrice2.get(i).get(j)));
                }
            }
        }

        genLink(jeu);
    }

    /**
     * Génère les liens de la matrice.
     */
    public void genLink(Jeu jeu) {
        for (int i = 0; i < matrice.size(); i++) {
            for (int j = 0; j < matrice.get(i).size() - 1; j++) {
                if (matrice.get(i).get(j) instanceof Noeud) {
                    verifHorizontale(j, i, jeu);
                    verifVerticale(j, i, jeu);
                }
            }
        }
    }

    /**
     * Vérifie si il y a un noeud a droite et crée un lien entre les deux noeuds.
     *
     * @param y La colonne de l'élément
     * @param x La ligne de l'élément
     */
    private void verifHorizontale(int y, int x, Jeu jeu) {
        //verif si il y a un noeud a droite
        //Si il y a un noeud, crée un lien entre les deux noeuds et le rajoute dans la matrice
        ElementJeu current = matrice.get(x).get(y);
        for (int i = y + 1; i < matrice.get(x).size(); i++) {
            ElementJeu right = matrice.get(x).get(i);
            if (current instanceof Noeud && right instanceof Noeud) {
                // Create a link between the two nodes
                Lien lien = new Lien((Noeud) current, (Noeud) right, matrice2.get(x).get(i - 1), jeu, 1);
                // Add the link to the matrix
                for (int k = y + 1; k < i; k++) {
                    if (matrice.get(x).get(k) == null) {
                        matrice.get(x).set(k, lien);
                    } else if (matrice.get(x).get(k) instanceof Lien) {
                        DoubleLien dl = new DoubleLien(lien, (Lien) matrice.get(x).get(k));
                        matrice.get(x).set(k, dl);
                    }
                }
                listeLien.add(lien);
                return;
            }
        }
        /*ElementJeu current = matrice.get(y).get(x);
        for (int i = y; i < matrice.size(); i++) {
            for (int j = x; j < matrice.get(i).size() - 1; j++) {
                ElementJeu right = matrice.get(i).get(j + 1);

                if (current instanceof Noeud && right instanceof Noeud) {
                    // Create a link between the two nodes
                    Lien lien = new Lien((Noeud) current, (Noeud) right, matrice2.get(i).get(j - 1), jeu, 1);
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
                    listeLien.add(lien);
                    return;
                }
            }
        }*/
    }

    /**
     * Vérifie si il y a un noeud en dessous et crée un lien entre les deux
     * noeuds.
     *
     * @param y La colonne de l'élément
     * @param x La ligne de l'élément
     */
    private void verifVerticale(int y, int x, Jeu jeu) {
        //verif si il y a un noeud en bas
        //Si il y a un noeud, crée un lien entre les deux noeuds et le rajoute dans la matrice
        ElementJeu current = matrice.get(x).get(y);
        for (int i = x + 1; i < matrice.size(); i++) {
            ElementJeu bot = matrice.get(i).get(y);

            if (current instanceof Noeud && bot instanceof Noeud) {
                // Create a link between the two nodes
                Lien lien = new Lien((Noeud) current, (Noeud) bot, matrice2.get(i - 1).get(y), jeu, 0);
                // Add the link to the matrix
                for (int k = x + 1; k < i; k++) {
                    if (matrice.get(k).get(y) == null) {
                        matrice.get(k).set(y, lien);
                    } else if (matrice.get(k).get(y) instanceof Lien) {
                        DoubleLien dl = new DoubleLien(lien, (Lien) matrice.get(k).get(y));
                        matrice.get(k).set(y, dl);
                    }

                }
                listeLien.add(lien);
                return;
            }
        }
        
        /*ElementJeu current = matrice.get(y).get(x);
        for (int i = y; i < matrice.size(); i++) {
            for (int j = x; j < matrice.get(i).size() - 1; j++) {
                ElementJeu bot = matrice.get(i + 1).get(j);

                if (current instanceof Noeud && bot instanceof Noeud) {
                    // Create a link between the two nodes
                    Lien lien = new Lien((Noeud) current, (Noeud) bot, matrice2.get(i - 1).get(j), jeu, 1);
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
                    listeLien.add(lien);
                    return;
                }
            }
        }*/
    }

    /**
     * Main pour tester la génération de la matrice.
     * @param args argumet en commande
     */
    public static void main(String[] args) {
        int[][] mat = {{-4, 2, -4, 2, -2, 0, 0},
                       {2, -3, 1, -3, 2, 2, -3},
                       {-3, 2, 0, 0, 0, 0, 1},
                       {1, -6, 2, -4, 2, -3, 1},
                       {0, 2, 0, 0, 0, 1, -1},
                       {1, -4, 2, 2, -2, 1, 0},
                       {-2, 1, 1, -2, 1, -2, 0}};
        Poc test = new Poc(7, 7, mat, new Jeu(5, mat));
        test.draw();

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
