package fr.m3acnl.game.logique;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

/**
 * Classe de test de la classe Matrice.
 *
 * @see Matrice
 * @see Lien
 * @see DoubleLien
 *
 * @author MABIRE AYMERIC
 */
public class MatriceTest {

    /**
     * Test de la méthode getElement
     *
     * @see Matrice#getElement(int, int)
     */
    @Test
    void testGetElement() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Matrice mat1 = new Matrice(7, 7, mat, new Jeu(7, mat));
        assertEquals(Noeud.class, mat1.getElement(6, 0).getClass(), "Matrice générer correctement Lien en 6,0");
    }

    /**
     * Test de la méthode setElement
     *
     * @see Matrice#setElement(int, int, ElementJeu)
     * @see Matrice#getElement(int, int)
     */
    @Test
    void testSetElement() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Matrice mat1 = new Matrice(7, 7, mat, new Jeu(7, mat));
        mat1.setElement(0, 0, null);
        assertEquals(null, mat1.getElement(0, 0), "Matrice modifier en 0,0 a null");
    }

    /**
     * Test de la méthode afficherReseau
     *
     * @see Noeud#afficherReseau()
     * @see Matrice#getElement(int, int)
     * @see Lien#activer()
     */
    @Test
    void testAfficherReseau() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Matrice mat1 = new Matrice(7, 7, mat, new Jeu(7, mat));
        ArrayList<Noeud> noeuds = ((Noeud) mat1.getElement(0, 0)).afficherReseau();
        assertEquals(((Noeud) mat1.getElement(0, 0)), noeuds.get(0), "Noeud correctement selectionner");
        mat1.getElement(0, 1).activer();
        noeuds = ((Noeud) mat1.getElement(0, 0)).afficherReseau();
        ArrayList<Noeud> noeudsAttendu = new ArrayList<>();
        noeudsAttendu.add((Noeud) mat1.getElement(0, 0));
        noeudsAttendu.add((Noeud) mat1.getElement(0, 2));
        for (int i = 0; i < noeuds.size(); i++) {
            assertEquals(noeudsAttendu.get(i), noeuds.get(i), "Noeud correctement selectionner");
        }

        mat1.remiseAzero();
        noeudsAttendu = new ArrayList<>();
        //Complétion de la matrice
        //Lien 0,1 état 2
        mat1.getElement(0, 1).activer();
        mat1.getElement(0, 1).activer();

        //Lien 1,0 état 2
        mat1.getElement(1, 0).activer();
        mat1.getElement(1, 0).activer();

        //Lien 0,3 état 2
        mat1.getElement(0, 3).activer();
        mat1.getElement(0, 3).activer();

        //Lien 1,2 état 1
        mat1.getElement(1, 2).activer();

        //Lien 1,5 état 2
        mat1.getElement(1, 5).activer();
        mat1.getElement(1, 5).activer();

        //Lien 2,1 état 2
        mat1.getElement(2, 1).activer();
        mat1.getElement(2, 1).activer();

        //Lien 2,6 état 1
        mat1.getElement(2, 6).activer();

        //Lien 3,0 état 1
        mat1.getElement(3, 0).activer();

        //Lien 3,2 état 2
        mat1.getElement(3, 2).activer();
        mat1.getElement(3, 2).activer();
        //DoubleLien 3,4 Lien horizontal état 2
        ((DoubleLien) mat1.getElement(3, 4)).activer((Noeud) mat1.getElement(3, 5));
        ((DoubleLien) mat1.getElement(3, 4)).activer((Noeud) mat1.getElement(3, 5));
        //Lien 4,1 état 2
        mat1.getElement(4, 1).activer();
        mat1.getElement(4, 1).activer();
        //Lien 4,5 état 1
        mat1.getElement(4, 5).activer();
        //Lien 5,2 état 2
        mat1.getElement(5, 2).activer();
        mat1.getElement(5, 2).activer();
        //Lien 6,1 état 1
        mat1.getElement(6, 1).activer();
        //Lien 6,4 état 1
        mat1.getElement(6, 4).activer();

        noeudsAttendu.add(((Noeud) mat1.getElement(0, 0)));
        noeudsAttendu.add(((Noeud) mat1.getElement(0, 2)));
        noeudsAttendu.add(((Noeud) mat1.getElement(0, 4)));
        noeudsAttendu.add(((Noeud) mat1.getElement(2, 0)));
        noeudsAttendu.add(((Noeud) mat1.getElement(6, 0)));
        noeudsAttendu.add(((Noeud) mat1.getElement(6, 3)));
        noeudsAttendu.add(((Noeud) mat1.getElement(6, 5)));
        noeudsAttendu.add(((Noeud) mat1.getElement(3, 5)));
        noeudsAttendu.add(((Noeud) mat1.getElement(3, 3)));
        noeudsAttendu.add(((Noeud) mat1.getElement(3, 1)));
        noeudsAttendu.add(((Noeud) mat1.getElement(1, 1)));
        noeudsAttendu.add(((Noeud) mat1.getElement(1, 3)));
        noeudsAttendu.add(((Noeud) mat1.getElement(1, 6)));
        noeudsAttendu.add(((Noeud) mat1.getElement(4, 6)));
        noeudsAttendu.add(((Noeud) mat1.getElement(5, 1)));
        noeudsAttendu.add(((Noeud) mat1.getElement(5, 4)));
        noeuds = ((Noeud) mat1.getElement(0, 0)).afficherReseau();
        for (int i = 0; i < noeuds.size(); i++) {
            assertEquals(noeudsAttendu.get(i), noeuds.get(i), "Noeud correctement selectionner");
        }

    }

    /**
     * Test de la méthode validationMatrice
     *
     * @see Matrice#validationMatrice()
     * @see Matrice#getElement(int, int)
     * @see Lien#activer()
     * @see DoubleLien#activer(Noeud)
     */
    @Test
    void testValidationMatrice() {
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };
        Matrice mat1 = new Matrice(7, 7, mat, new Jeu(7, mat));
        assertEquals(false, mat1.validationMatrice(), "Matrice non résolu false");

        //Complétion de la matrice
        //Lien 0,1 état 2
        mat1.getElement(0, 1).activer();
        mat1.getElement(0, 1).activer();
        //Lien 1,0 état 2
        mat1.getElement(1, 0).activer();
        mat1.getElement(1, 0).activer();
        //Lien 0,3 état 2
        mat1.getElement(0, 3).activer();
        mat1.getElement(0, 3).activer();
        //Lien 1,2 état 1
        mat1.getElement(1, 2).activer();
        //Lien 1,5 état 2
        mat1.getElement(1, 5).activer();
        mat1.getElement(1, 5).activer();
        //Lien 2,1 état 2
        mat1.getElement(2, 1).activer();
        mat1.getElement(2, 1).activer();
        //Lien 2,6 état 1
        mat1.getElement(2, 6).activer();
        //Lien 3,0 état 1
        mat1.getElement(3, 0).activer();
        //Lien 3,2 état 2
        mat1.getElement(3, 2).activer();
        mat1.getElement(3, 2).activer();
        //DoubleLien 3,4 Lien horizontal état 2
        ((DoubleLien) mat1.getElement(3, 4)).activer((Noeud) mat1.getElement(3, 5));
        ((DoubleLien) mat1.getElement(3, 4)).activer((Noeud) mat1.getElement(3, 5));
        //Lien 4,1 état 2
        mat1.getElement(4, 1).activer();
        mat1.getElement(4, 1).activer();
        //Lien 4,5 état 1
        mat1.getElement(4, 5).activer();
        //Lien 5,2 état 2
        mat1.getElement(5, 2).activer();
        mat1.getElement(5, 2).activer();
        //Lien 6,1 état 1
        mat1.getElement(6, 1).activer();
        //Lien 6,4 état 1
        mat1.getElement(6, 4).activer();
        assertEquals(true, mat1.validationMatrice(), "Matrice résolu true");
    }
}
