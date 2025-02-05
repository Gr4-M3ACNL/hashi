package fr.m3acnl.game.logique;

import java.util.ArrayList;

public class Poc {

    private ArrayList<ArrayList<GameElement>> matrice;

    public Poc(int lignes, int cols) {
        matrice = new ArrayList<>();
        for (int i = 0; i < lignes; i++) {
            ArrayList<GameElement> ligne = new ArrayList<>();
            for (int j = 0; j < cols; j++) {
                ligne.add(null); // Initialize with null or any default value
            }
            matrice.add(ligne);
        }
    }

    public GameElement getElement(int ligne, int col) {
        return matrice.get(ligne).get(col);
    }

    public void setElement(int ligne, int col, GameElement element) {
        matrice.get(ligne).set(col, element);
    }

    //public void genMatrice(); //Genere la matrice grâce au fichier Json
    /*public void genLien() {
        //Genere les liens entre les noeuds
        for (ArrayList<GameElement> ligne : matrice) {
            for (GameElement element : ligne) {
                if (element != null) {
                    element.draw();
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }*/
    public void draw() {
        for (ArrayList<GameElement> ligne : matrice) {
            for (GameElement element : ligne) {
                if (element != null) {
                    element.draw();
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    public void genLink() {
        for (int i = 0; i < matrice.size(); i++) {
            for (int j = 0; j < matrice.get(i).size() - 1; j++) {
                if (matrice.get(i).get(j) instanceof Noeud) {
                    //verifHorizontale
                    //verifVertiale
                }
            }
        }
    }

    private void verifHorizontale(int y, int x) {
        //verif si il y a un noeud a droite

        //Si il y a un noeud, crée un lien entre les deux noeuds et le rajoute dans la matrice
        GameElement current = matrice.get(y).get(x);
        for (int i = y; i < matrice.size(); i++) {
            for (int j = x; j < matrice.get(i).size() - 1; j++) {
                GameElement right = matrice.get(i).get(j + 1);
                if (current instanceof Noeud && right instanceof Noeud) {
                    // Create a link between the two nodes
                    Lien lien = new Lien(current, right, 1);
                    // Add the link to the matrix
                    for (int k = x; k < j; k++) {
                        matrice.get(i).set(k, lien);
                    }
                }
            }
        }
    }

    private void verifVerticale(int y, int x) {
        //verif si il y a un noeud en bas

        //Si il y a un noeud, crée un lien entre les deux noeuds et le rajoute dans la matrice
        GameElement current = matrice.get(y).get(x);
        for (int i = y; i < matrice.size(); i++) {
            for (int j = x; j < matrice.get(i).size() - 1; j++) {
                GameElement bot = matrice.get(i + 1).get(j);
                if (current instanceof Noeud && bot instanceof Noeud) {
                    // Create a link between the two nodes
                    Lien lien = new Lien(current, bot, 1);
                    // Add the link to the matrix
                    for (int k = y; k < j; k++) {
                        matrice.get(k).set(j, lien);
                    }
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
