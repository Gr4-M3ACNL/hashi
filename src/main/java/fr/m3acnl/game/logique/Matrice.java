package fr.m3acnl.game.logique;

import java.util.ArrayList;

/**
 * Classe matrice du jeu pour gérer ça création et ça validité.
 *
 * @author MABIRE Aymeric
 * @version 1.0
 */
public class Matrice {
    //Variables de la classe Matrice.

    /**
     * Contient la matrice du jeu avec les objets de type ElementJeu.
     */
    private final ArrayList<ArrayList<ElementJeu>> matrice;

    /**
     * Contient la matrice de départ du jeu avec les valeurs de type Double.
     */
    private final ArrayList<ArrayList<Double>> matrice2;

    /**
     * Contient la liste des liens de la matrice.
     */
    private final ArrayList<Lien> listeLien;

    /**
     * Constructeur pour une nouvelle instance de Lien.
     *
     * @param lignes Nombre de lignes de la matrice
     * @param cols Nombre de colonnes de la matrice
     * @param mat La matrice pour générer le jeu
     * @param jeu Le jeu au quel la matrice appartient
     */
    public Matrice(int lignes, int cols, Double[][] mat, Jeu jeu) {
        matrice = new ArrayList<>();
        listeLien = new ArrayList<>();
        for (int i = 0; i < lignes; i++) {
            ArrayList<ElementJeu> ligne = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                ligne.add(null);
            }
            matrice.add(ligne);
        }
        matrice2 = new ArrayList<>();
        for (int i = 0; i < lignes; i++) {
            ArrayList<Double> ligne = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                ligne.add(mat[i][j]);
            }
            matrice2.add(ligne);
        }

        this.genMatrice(jeu);

    }

    /**
     * Récupère la partie entière d'un nombre. La partie entière représente la
     * solution vertical du lien.
     *
     * @param val Le nombre a traiter
     * @return La partie entière du nombre
     */
    private double horiz(double val) {
        return Math.round((val - Math.floor(val)) * 10);
    }

    /**
     * Récupère la partie décimale d'un nombre. La partie décimale représente la
     * solution horizontal du lien.
     *
     * @param val Le nombre a traiter
     * @return La partie décimale du nombre
     */
    private double vertic(double val) {
        return Math.floor(val);
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
        int count = 0;
        System.out.println("     0       1       2       3       4       5       6");
        for (ArrayList<ElementJeu> ligne : matrice) {
            System.out.print(count++ + " ");
            for (ElementJeu element : ligne) {

                if (element != null) {
                    element.draw();
                } else {
                    System.out.print(" NONE   ");
                }
            }
            System.out.println();
        }
        System.out.println("V/H[nbLien](nbLienSoluce)");
        System.out.println("N[nbSoluce](nbActuelle)");
    }

    /**
     * Génère la matrice avec les noeuds et les liens.
     *
     * @param jeu Le jeu au quel la matrice appartient
     */
    public void genMatrice(Jeu jeu) {
        // Generate the nodes
        for (int i = 0; i < matrice2.size(); i++) {
            for (int j = 0; j < matrice2.get(i).size(); j++) {
                if (matrice2.get(i).get(j) < 0) {
                    matrice.get(i).set(j, new Noeud(i, j, -matrice2.get(i).get(j).intValue()));
                }
            }
        }

        genLink(jeu);
    }

    /**
     * Génère les liens de la matrice.
     *
     * @param jeu le jeu au quel la matrice et les liens appartiennent
     */
    private void genLink(Jeu jeu) {
        for (int i = 0; i < matrice.size(); i++) {
            for (int j = 0; j < matrice.get(i).size(); j++) {
                if (matrice.get(i).get(j) instanceof Noeud) {
                    verifHorizontale(j, i, jeu);
                    verifVerticale(j, i, jeu);
                }
            }
        }
    }

    /**
     * Vérifie si il y a un noeud a droite et crée un lien entre les deux
     * noeuds.
     *
     * @param y La colonne de l'élément
     * @param x La ligne de l'élément
     * @param jeu le jeu au quel la matrice et les liens appartiennent
     */
    private void verifHorizontale(int y, int x, Jeu jeu) {
        //verification si il y a un noeud a droite
        //Si il y a un noeud, crée un lien entre les deux noeuds et le rajoute dans la matrice
        ElementJeu actuel = matrice.get(x).get(y);
        Double sol;
        for (int i = y + 1; i < matrice.get(x).size(); i++) {
            ElementJeu droite = matrice.get(x).get(i);
            if (actuel instanceof Noeud && droite instanceof Noeud) {
                sol = horiz(matrice2.get(x).get(i - 1));
                Lien lien = new Lien((Noeud) actuel, (Noeud) droite, sol.intValue(), jeu, 1);
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
    }

    /**
     * Vérifie si il y a un noeud en dessous et crée un lien entre les deux
     * noeuds.
     *
     * @param y La colonne de l'élément
     * @param x La ligne de l'élément
     * @param jeu le jeu au quel la matrice et les liens appartiennent
     */
    private void verifVerticale(int y, int x, Jeu jeu) {
        //verification si il y a un noeud en bas
        //Si il y a un noeud, crée un lien entre les deux noeuds et le rajoute dans la matrice
        ElementJeu actuel = matrice.get(x).get(y);
        Double sol;
        for (int i = x + 1; i < matrice.size(); i++) {
            ElementJeu bas = matrice.get(i).get(y);
            if (actuel instanceof Noeud && bas instanceof Noeud) {
                // Créer un lien entre 2 noeuds
                sol = vertic(matrice2.get(i - 1).get(y));
                Lien lien = new Lien((Noeud) actuel, (Noeud) bas, sol.intValue(), jeu, 0);
                // Ajoute le lien dans la matrice
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
    }

    /**
     * Vérifie si la matrice est valide.
     *
     * @return true si la matrice est valide, false sinon
     */
    public Boolean validationMatrice() {
        for (Lien lien : listeLien) {
            if (!lien.estValide()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Main pour tester la génération de la matrice.
     *
     * @param args argument en commande
     */
    public static void main(String[] args) {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Jeu jeu = new Jeu(0, mat);
        jeu.drawJeu();

        System.out.println("\n\nTest activation du Lien 0,1 en état 1\n\n");
        jeu.activeElem(0, 1, null);
        jeu.drawJeu();
        jeu.activeElem(0, 0, null);
        System.out.println("\n\nTest activation du Lien 0,1 en état 2\n\n");
        jeu.activeElem(0, 1, null);
        jeu.drawJeu();

        System.out.println("\n\nTest activation du Lien 1,0 en état 2\n\n");
        jeu.activeElem(1, 0, null);
        jeu.activeElem(1, 0, null);
        jeu.drawJeu();
        jeu.activeElem(0, 0, null);
        System.out.println("\n\nTest activation du Lien 0,1 en état 0 (saturation) \n\n");
        jeu.activeElem(0, 1, null);
        jeu.drawJeu();

        System.out.println("\n\nTest completion de la matrice\n\n");
        //Lien 0,1 état 2
        jeu.activeElem(0, 1, null);
        jeu.activeElem(0, 1, null);
        //Lien 0,3 état 2
        jeu.activeElem(0, 3, null);
        jeu.activeElem(0, 3, null);
        //Lien 1,2 état 1
        jeu.activeElem(1, 2, null);
        //Lien 1,6 état 2
        jeu.activeElem(1, 5, null);
        jeu.activeElem(1, 5, null);
        //Lien 2,1 état 2
        jeu.activeElem(2, 1, null);
        jeu.activeElem(2, 1, null);
        //Lien 2,6 état 1
        jeu.activeElem(2, 6, null);
        //Lien 3,0 état 1
        jeu.activeElem(3, 0, null);
        //Lien 3,2 état 2
        jeu.activeElem(3, 2, null);
        jeu.activeElem(3, 2, null);
        //Lien 3,4 état 2
        jeu.activeElem(3, 4, (Noeud) jeu.getPlateau().getElement(3, 5));
        jeu.activeElem(3, 4, (Noeud) jeu.getPlateau().getElement(3, 5));
        //Lien 4,1 état 2
        jeu.activeElem(4, 1, null);
        jeu.activeElem(4, 1, null);
        //Lien 4,5 état 1
        jeu.activeElem(4, 5, null);
        //Lien 5,2 état 2
        jeu.activeElem(5, 2, null);
        jeu.activeElem(5, 2, null);
        //Lien 6,1 état 1
        jeu.activeElem(6, 1, null);
        //Lien 6,4 état 1
        jeu.activeElem(6, 4, null);

        jeu.drawJeu();
        System.out.println("Validation de la matrice: " + jeu.gagner() + "\n\n");

        System.out.println("\n\nTest activation du Lien 2,4 en état 2 : Normalement impossible, car DoubleLien et lien horizontal actif\n\n");
        jeu.activeElem(2, 4, null);
        jeu.activeElem(2, 4, null);
        jeu.drawJeu();
        System.out.println("Validation de la matrice: " + jeu.gagner() + "\n\n");

        /*Matrice test = new Matrice(7, 7, mat, new Jeu(5, mat));
        test.draw();

        System.out.println("\n\nTest activation du Lien 0,1 en état 1\n\n");
        test.matrice.get(0).get(1).activer();
        test.draw();

        System.out.println("\n\nTest activation du Lien 0,1 en état 2\n\n");
        test.matrice.get(0).get(1).activer();
        test.draw();

        System.out.println("\n\nTest activation du Lien 1,0 en état 2\n\n");
        test.matrice.get(1).get(0).activer();
        test.matrice.get(1).get(0).activer();
        test.draw();

        System.out.println("\n\nTest activation du Lien 0,1 en état 0 (saturation) \n\n");
        test.matrice.get(0).get(1).activer();
        test.draw();

        System.out.println("\n\nTest completion de la matrice\n\n");
        //Lien 0,1 état 2
        test.matrice.get(0).get(1).activer();
        test.matrice.get(0).get(1).activer();
        //Lien 0,3 état 2
        test.matrice.get(0).get(3).activer();
        test.matrice.get(0).get(3).activer();
        //Lien 1,2 état 1
        test.matrice.get(1).get(2).activer();
        //Lien 1,6 état 2
        test.matrice.get(1).get(5).activer();
        test.matrice.get(1).get(5).activer();
        //Lien 2,1 état 2
        test.matrice.get(2).get(1).activer();
        test.matrice.get(2).get(1).activer();
        //Lien 2,6 état 1
        test.matrice.get(2).get(6).activer();
        //Lien 3,0 état 1
        test.matrice.get(3).get(0).activer();
        //Lien 3,2 état 2
        test.matrice.get(3).get(2).activer();
        test.matrice.get(3).get(2).activer();
        //Lien 3,4 état 2
        ((DoubleLien) test.matrice.get(3).get(4)).activer((Noeud) test.matrice.get(3).get(5));
        ((DoubleLien) test.matrice.get(3).get(4)).activer((Noeud) test.matrice.get(3).get(5));
        //Lien 4,1 état 2
        test.matrice.get(4).get(1).activer();
        test.matrice.get(4).get(1).activer();

        //Lien 4,5 état 1
        test.matrice.get(4).get(5).activer();
        //Lien 5,2 état 2
        test.matrice.get(5).get(2).activer();
        test.matrice.get(5).get(2).activer();
        //Lien 6,1 état 1
        test.matrice.get(6).get(1).activer();
        //Lien 6,4 état 1
        test.matrice.get(6).get(4).activer();

        test.draw();
        System.out.println("Validation de la matrice: " + test.validationMatrice() + "\n\n");

        //Lien 2,4 état 2
        System.out.println("\n\nTest activation du Lien 2,4 en état 2 : Normalement impossible, car DoubleLien et lien horizontal actif\n\n");
        test.matrice.get(2).get(4).activer();
        test.matrice.get(2).get(4).activer();
        test.draw();
        System.out.println("Validation de la matrice: " + test.validationMatrice() + "\n\n");*/
    }
}

/*
 * Variables:
 * matrice (contient noeuds et liens)
 * 
 * 
 * 
 * Méthodes:
 * Initialisation-start
 * seek-possibilité
 * check-double
 * 
 * 
 * 
 */
