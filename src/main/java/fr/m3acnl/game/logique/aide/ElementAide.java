package fr.m3acnl.game.logique.aide;

import java.util.ArrayList;
import java.util.List;

import fr.m3acnl.game.logique.elementjeu.Noeud;

/**
 * Classe représentant un élément d'aide à afficher.
 *
 * @see Aide
 * @see AideVoisin
 * @see AideGraphe
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
        }
    }

    /**
     * Ajoute un texte à l'élément d'aide.
     *
     * @param texte Texte à ajouter.
     */
    public void addTexte(int i, String texte) {
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
