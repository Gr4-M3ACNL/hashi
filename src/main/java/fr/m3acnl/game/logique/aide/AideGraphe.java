package fr.m3acnl.game.logique.aide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;

import fr.m3acnl.game.logique.elementjeu.Coord;
import fr.m3acnl.game.logique.elementjeu.DoubleLien;
import fr.m3acnl.game.logique.elementjeu.ElementJeu;
import fr.m3acnl.game.logique.elementjeu.Lien;
import fr.m3acnl.game.logique.elementjeu.Noeud;
import fr.m3acnl.game.logique.Jeu;
import fr.m3acnl.game.logique.Matrice;

/**
 * Classe AideGraphe pour aider à travers les graphes. Cette classe permet de
 * trouver les voisins d'un nœud, les voisins accessibles et de vérifier si un
 * sous-graphe est connexe. Elle permet également de trouver les voisins
 * accessibles et de vérifier si un lien est impossible. Elle hérite de la
 * classe Aide.
 *
 * @author Lelandais Clément
 * @version 1.0
 */
public class AideGraphe extends Aide {

    /**
     * Jeu duquel l'aide est issue.
     */
    private final Jeu jeu;

    /**
     * Liste des aides graphes.
     */
    private static final List<AideGraphe> aidesGraphes;

    /**
     * Constructeur de la classe AideGraphe.
     *
     * @param matrice Matrice du jeu
     * @param description Description de l'aide
     * @param nom Nom de l'aide
     * @param jeu Jeu duquel l'aide est issue
     * @param c Coordonnée de l'aide
     */
    public AideGraphe(Matrice matrice, String description, String nom, Jeu jeu, Coord c) {
        super(matrice, nom, c);
        this.jeu = jeu;
        this.description = description;
    }

    static {
        aidesGraphes = new ArrayList<>();
    }

    /**
     * Affiche la description de l'aide.
     */
    @Override
    public void afficherAide(int index) {
        System.out.println("Aide: " + aidesGraphes.get(index).nom);
        System.out.println("Description: " + aidesGraphes.get(index).description);
        System.out.println("Coût: " + aidesGraphes.get(index).cout);

    }

        /**
     * Trouve les voisins d'un nœud.
     *
     * @param noeud Le noeud sur lequel trouver les voisins.
     * @return la liste des noeuds
     */
    public List<Noeud> trouverVoisins(Noeud noeud) {
        List<Noeud> voisins = new ArrayList<>();
        int x = noeud.getPosition().getCoordX();
        int y = noeud.getPosition().getCoordY();

        // Parcours en ligne droite dans chaque direction
        if (estValide(x + 1, y)) {
            ajouterVoisin(voisins, x, y, 1, 0);  // Droite
        }
        if (estValide(x - 1, y)) {
            ajouterVoisin(voisins, x, y, -1, 0); // Gauche
        }
        if (estValide(x, y + 1)) {
            ajouterVoisin(voisins, x, y, 0, 1);  // Bas
        }
        if (estValide(x, y - 1)) {
            ajouterVoisin(voisins, x, y, 0, -1); // Haut
        }

        return voisins;
    }

    /**
     * Ajoute un voisin à la liste des voisins.
     *
     * @param voisins liste des voisins
     * @param x coordonnée x
     * @param y coordonnée y
     * @param dx déplacement x
     * @param dy déplacement y
     */
    private void ajouterVoisin(List<Noeud> voisins, int x, int y, int dx, int dy) {
        x += dx;
        y += dy;
        if (matrice.getElement(x, y) instanceof Lien l) {
            if (l.noeudDansLien((Noeud) matrice.getElement(x - dx, y - dy)) == 0) {
                if (((Noeud) matrice.getElement(x - dx, y - dy)).compareTo(l.getNoeud1()) == 0) {
                    voisins.add(l.getNoeud2());
                } else {
                    voisins.add(l.getNoeud1());
                }
            }
        } else if (matrice.getElement(x, y) instanceof DoubleLien l1) {
            Lien l2 = l1.getLienDuNoeud((Noeud) matrice.getElement(x - dx, y - dy));
            if (l2 != null) {
                if (((Noeud) matrice.getElement(x - dx, y - dy)).compareTo(l2.getNoeud1()) == 0) {
                    voisins.add(l2.getNoeud2());
                } else {
                    voisins.add(l2.getNoeud1());
                }
            }
        }

    }

    /**
     * Regarde si le noeud est lié.
     *
     * @param elem le noeud du jeu à analyser
     * @param n le noeud du jeu à analyser
     * @return true s'il est lié, false sinon
     */
    private boolean checkLier(ElementJeu elem, Noeud n) {
        Lien l;
        if (elem instanceof DoubleLien dl) {
            l = dl.getLienDuNoeud(n);
        } else {
            l = (Lien) elem;
        }
        return l != null && l.getNbLien() > 0;
    }

    /**
     * Retourne une liste de voisins uniquement accessibles : pas de lien qui
     * coupe.
     *
     * @param noeud Le nœud dont on cherche les voisins accessibles.
     * @return Liste des voisins accessibles.
     */
    public List<Noeud> trouverVoisinsDispo(Noeud noeud) {
        List<Noeud> voisins = trouverVoisins(noeud);
        for (int i = voisins.size() - 1; i >= 0; i--) {
            Noeud voisin = voisins.get(i);
            if (noeud.getPosition().getCoordX() == voisin.getPosition().getCoordX()) {
                // Vérifie si le voisin est à gauche ou à droite
                if (noeud.getPosition().getCoordY() > voisin.getPosition().getCoordY()) {
                    // Vérifie l'isolement horizontal du voisin à gauche
                    if (!checkLier(matrice.getElement(noeud.getPosition().getCoordX(), voisin.getPosition().getCoordY() + 1), noeud)) {
                        if (jeu.verificationHorizontal(voisin, noeud, 1, true) == 1) {
                            voisins.remove(i);
                        }
                    }
                } else {
                    // Vérifie l'isolement horizontal du voisin à droite
                    if (!checkLier(matrice.getElement(noeud.getPosition().getCoordX(), noeud.getPosition().getCoordY() + 1), noeud)) {
                        // Aide à true pour une utilisation pour une aide
                        if (jeu.verificationHorizontal(noeud, voisin, 1, true) == 1) {
                            voisins.remove(i);
                        }
                    }
                }
            } else {
                // Vérifie si le voisin est au-dessus ou en dessous
                if (noeud.getPosition().getCoordX() > voisin.getPosition().getCoordX()) {
                    // Vérifie l'isolement vertical du voisin en haut
                    if (!checkLier(matrice.getElement(voisin.getPosition().getCoordX() + 1, voisin.getPosition().getCoordY()), noeud)) {
                        // Aide à true pour une utilisation pour une aide
                        if (jeu.verificationVertical(voisin, noeud, 1, true) == 1) {
                            voisins.remove(i);
                        }
                    }
                } else {
                    // Vérifie l'isolement vertical du voisin en bas
                    if (!checkLier(matrice.getElement(noeud.getPosition().getCoordX() + 1, voisin.getPosition().getCoordY()), noeud)) {
                        // Aide à true pour une utilisation pour une aide
                        if (jeu.verificationVertical(noeud, voisin, 1, true) == 1) {
                            voisins.remove(i);
                        }
                    }
                }
            }
        }

        return voisins;
    }

    /**
     * Retourne une liste de voisins uniquement accessibles et complets : pas de
     * lien qui coupe et de degré complet.
     *
     * @param n Noeud dont on cherche les voisins accessibles et complets.
     * @return Liste des voisins accessibles et comple
     */
    public List<Noeud> trouverVoisinsDispoComplet(Noeud n) {
        List<Noeud> voisins = trouverVoisinsDispo(n);
        List<Noeud> voisinsComplet = new ArrayList<>();
        //System.out.println("Voisins : " + voisins);
        for (Noeud voisin : voisins) {
            if (voisin.getDegreActuelle() != voisin.getDegreSoluce()) {
                voisinsComplet.add(voisin);
            }
        }
        return voisinsComplet;
    }


    /**
     * Fait une liste de tous les noeuds.
     *
     * @return liste de noeuds.
     */
    public List<Noeud> getListeNoeuds() {
        List<Noeud> noeuds = new ArrayList<>();
        for (int i = 0; i < jeu.getTaille(); i++) {
            for (int j = 0; j < jeu.getTaille(); j++) {
                ElementJeu element = matrice.getElement(i, j);
                // Si l'élément est un Noeud, on l'ajoute à la liste
                if (element instanceof Noeud noeud) {
                    noeuds.add(noeud);
                }
            }
        }
        return noeuds;
    }

    /**
     * Méthode pour créer un élément d'aide globale. PENSER A PRIORISER L'AIDE
     * DE LIEN IMPOSSIBLE : AIDE SUR MM NOEUD, PRIORISER LIEN IMPOSSIBLE
     *
     * @return Un élément aide.
     */
    public ElementAide aideGlobale() {
        List<Noeud> tousLesNoeuds = getListeNoeuds();
        ElementAide elementAide = new ElementAide();  // Créer un nouvel élément d'aide
        for (Noeud noeud : tousLesNoeuds) {
            // Test 1: Aide sur les voisins
            if (lienImpossible(noeud)) {
                elementAide.addTexte(4, " 4 Description: Cette aide vous montre les voisins possibles.");
                elementAide.addNoeud(0, ((Noeud) matrice.getElement(0, 0)));
                // Ajouter le noeud à surligner (exemple pour l'index 0)
            }

            // Test 2: Aide sur l'isolement
            /*if (rendSousGrapheConnexe(noeud)) {
                elementAide.addTexte(5, "5 Description: Cette aide met en évidence si un noeud rend un sous-reseau connexe.");
                elementAide.addNoeud(0, ((Noeud) matrice.getElement(0, 0)));
            }*/
        }

        return elementAide;  // Retourner l'élément d'aide complet
    }

    public List<Integer> getSommeVoisins(List<Noeud> voisins, Noeud n) {
        List<Integer> somme = new ArrayList<>();
        for (Noeud voisin : voisins) {
            if (!voisin.equals(n)) {
                somme.add(voisin.getDegreSoluce() - voisin.getDegreActuelle());
            }
        }
        return somme;
    }

    public int getNbVoisinsDispos(List<Noeud> voisins, Noeud n) {
        int nb = 0;
        for (Noeud voisin : voisins) {
            if (voisin.getDegreActuelle() != voisin.getDegreSoluce()) {
                nb++;
            }
        }
        return nb;
    }

    /**
     * Vérifie si la somme des nbvoisins - 1 = poidsRestant et que les voisins
     * ont un réseau où ils sont le dernier qui peuvent se connecter. Noeud
     * absent enregistré pour solution ?
     *
     * @param n Noeud dont on vérifie les liens.
     * @return true si la condition est remplie, false sinon
     */
    public boolean lienImpossible(Noeud n) {
        List<Noeud> voisins = new ArrayList<>(trouverVoisinsDispoComplet(n));
        List<Integer> somme = new ArrayList<>();
        for (int i = 1; i < voisins.size(); i++) {
            Noeud temp = voisins.remove(voisins.size() - 1);
            if (!n.getListeAdjacence().contains(temp)) {

                somme = getSommeVoisins(voisins, n);
                boolean somm = somme.stream().allMatch(m -> m == 1 || m == 2);
                int som = somme.stream().mapToInt(Integer::intValue).sum();
                if (somm && som == (n.getDegreSoluce() - n.getDegreActuelle())
                        && getNbVoisinsDispos(voisins.get(Math.max(0, i - 1)).afficherReseau(),
                                voisins.get(Math.max(0, i - 1))) == 1 && getNbVoisinsDispos(voisins.get(Math.min(voisins.size() - 1, i)).afficherReseau(),
                        voisins.get(Math.min(voisins.size() - 1, i))) == 1) {
                    voisins.add(0, temp);
                    aidesGraphes.add(new AideGraphe(matrice, "Le nœud " + n.getPosition() + " peut rendre un sous-graphe connexe à cause "
                            + "de la somme de certains de ses voisins.\n", "Liens impossibles", jeu, n.getPosition()));
                    //afficherAide(aidesGraphes.size() - 1);
                    return true;
                }
            }
            voisins.add(0, temp);
        }
        return false;
    }

    /**
     * Vérifie si les coordonnées sont valides.
     *
     * @param x Coordonnée x
     * @param y Coordonnée y
     * @return true si les coordonnées sont valides, false sinon
     */
    private boolean estValide(int x, int y) {
        return x >= 0 && x < jeu.getTaille() && y >= 0 && y < jeu.getTaille();
    }

    

    public static void main(String[] args) {
        // Création d'une matrice pour tester
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -6.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };

        // Création du jeu
        Jeu jeu = new Jeu(7, mat);
        AideGraphe aideGraphe = new AideGraphe(jeu.getPlateau(), "Aide sur les voisins", "Voisinage", jeu, new Coord(0, 0));
        // Test de la méthode afficherAideNoeud
        //Noeud noeudTest = (Noeud) jeu.getPlateau().getElement(0, 0);
        //jeu.activeElemAide(1, 0, null);
        System.out.println("\nTest sous-graphe connexe");
        aideGraphe.aideGlobale();
        jeu.drawJeuTerm();
        //System.out.println("Element Aide : " + ea);
    }
}