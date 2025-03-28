package fr.m3acnl.game.logique.aide;

import java.util.ArrayList;
import java.util.List;

import fr.m3acnl.game.logique.elementjeu.Noeud;

/**
 * Classe représentant un élément d'aide à afficher.
 *
 * @see Aide
 * @see AideVoisin
 * @see Noeud
 *
 * @author MABIRE Aymeric
 */
public class ElementAide {

    /**
     * Les différents texte d'aides à afficher.
     */
    private List<String> texte;

    /**
     * Les noeuds à mettre en surbrillance.
     */
    private List<Noeud>[] noeudsSurbrillance;

    /**
     * Constructeur de la classe ElementAide.
     */
    @SuppressWarnings("unchecked") // Nécessaire car Java ne permet pas la création directe de tableaux génériques
    public ElementAide() {
        texte = new ArrayList<>();
        noeudsSurbrillance = new List[3];

        for (int i = 0; i < 3; i++) {
            noeudsSurbrillance[i] = new ArrayList<>();
            this.addTexte(i, "");
        }
    }

    /**
     * Ajoute un texte à l'élément d'aide.
     *
     * @param i Index du texte à ajouter.
     * @param texte Texte à ajouter.
     */
    public void addTexte(int i, String texte) {
        // Vérifier si l'index est valide
        if (i < 0) {
            throw new IndexOutOfBoundsException("Index négatif : " + i);
        }

        // Remplir la liste avec des valeurs par défaut jusqu'à atteindre l'index `i`
        while (this.texte.size() <= i) {
            this.texte.add(""); // Ajoute des chaînes vides pour éviter l'erreur d'index
        }

        // Maintenant, on peut utiliser `set`
        this.texte.set(i, texte);
    }

    /**
     * Ajoute un noeud à l'élément d'aide.
     *
     * @param index Index de la liste de noeuds à ajouter.
     * @param n Noeud à ajouter.
     */
    public void addNoeud(int index, Noeud n) {
        if (index >= 0 && index < 3) {
            noeudsSurbrillance[index].add(n);
        } else {
            throw new IndexOutOfBoundsException("Index hors limite : " + index);
        }
    }

    /**
     * Retourne les textes de l'élément d'aide.
     *
     * @return Les textes de l'élément d'aide.
     */
    public List<String> getTexte() {
        return texte;
    }

    /**
     * Retourne les noeuds à mettre en surbrillance.
     *
     * @return Les noeuds à mettre en surbrillance.
     */
    public List<Noeud>[] getNoeudsSurbrillance() {
        return noeudsSurbrillance;
    }
}
