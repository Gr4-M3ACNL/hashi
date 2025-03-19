package fr.m3acnl.game.logique.aide;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.m3acnl.game.logique.Jeu;
import fr.m3acnl.game.logique.Matrice;
import fr.m3acnl.game.logique.elementjeu.Coord;
import fr.m3acnl.game.logique.elementjeu.DoubleLien;
import fr.m3acnl.game.logique.elementjeu.ElementJeu;
import fr.m3acnl.game.logique.elementjeu.Lien;
import fr.m3acnl.game.logique.elementjeu.Noeud;

/**
 * Classe d'aide.
 *
 * @author Gaumont mael
 * @version 1.0
 */
public class AideVoisin extends Aide {

    private final Jeu jeu;
    private static List<AideVoisin> aidesVoisins;

    /**
     * Constructeur pour créer une instance de l'aide sur les voisins.
     *
     * @param matrice la matrice représentant la grille du jeu
     * @param description la description de l'aide
     * @param nom le nom de l'aide
     */
    public AideVoisin(Matrice matrice, String description, String nom, Jeu jeu, Coord c) {
        super(matrice, nom, c);
        this.description = description;
        this.jeu = jeu;
        //aidesVoisin = new ArrayList<>();
    }

    static {
        aidesVoisins = new ArrayList<>();
    }

    /**
     * Affiche l'aide spécifique sur les voisins d'un noeud.
     *
     * @param noeud le noeud du jeu à analyser
     */
    public boolean afficherAideNoeud(Noeud noeud) {
        int poidsNoeud = noeud.getDegreSoluce();
        List<Noeud> voisins = trouverVoisins(noeud);
        int sommeVoisins = 0;
        int nbvoisin = voisins.size();

        // Calcul de la somme des poids des voisins
        for (Noeud voisin : voisins) {
            sommeVoisins += voisin.getDegreSoluce();
        }

        // Vérifie si le poids du noeud divisé par 2 est égal à la somme de ses voisins
        if ((poidsNoeud / 2 == nbvoisin) && (poidsNoeud <= sommeVoisins)) {
            aidesVoisins.add(new AideVoisin(jeu.getPlateau(), "Le poids du noeud divisé par 2 est égal à la somme de ses voisins.",
                    "Voisinage", jeu, noeud.getPosition()));
            return true;
        }
        return false;
    }

    /**
     * Affiche l'aide spécifique sur les voisins d'un noeud.
     *
     * @param noeud le noeud du jeu à analyser
     */
    public boolean afficherAideNoeud2(Noeud noeud) {
        int poidsNoeud = noeud.getDegreSoluce();
        List<Noeud> voisins = trouverVoisins(noeud);
        int sommeVoisins = 0;
        int nbvoisin = voisins.size();

        // Calcul de la somme des poids des voisins
        for (Noeud voisin : voisins) {
            sommeVoisins += voisin.getDegreSoluce();
        }

        // Vérifie si le poids du noeud divisé par 2 est égal à la somme de ses voisins
        if ((poidsNoeud / 2 == nbvoisin) && (poidsNoeud <= sommeVoisins)) {
            aidesVoisins.add(new AideVoisin(jeu.getPlateau(), "Le poids du noeud divisé par 2 est égal à la somme de ses voisins.",
                    "Voisinage", jeu, noeud.getPosition()));
            return true;
        }
        return false;
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

    private boolean estValide(int x, int y) {
        return x >= 0 && x < jeu.getTaille() && y >= 0 && y < jeu.getTaille();
    }

    /**
     * Sort une liste de voisin.
     *
     * @param noeud Le nœud dont on cherche les voisins.
     * @return Liste des voisins.
     * @see Noeud
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
     * Retourne une liste de voisins uniquement accessibles.
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
                            aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));
                            voisins.remove(i);
                        }
                    }
                } else {
                    // Vérifie l'isolement horizontal du voisin à droite
                    if (!checkLier(matrice.getElement(noeud.getPosition().getCoordX(), noeud.getPosition().getCoordY() + 1), noeud)) {
                        // Aide à true pour une utilisation pour une aide
                        if (jeu.verificationHorizontal(noeud, voisin, 1, true) == 1) {
                            aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));
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
                            aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));
                            voisins.remove(i);
                        }
                    }
                } else {
                    // Vérifie l'isolement vertical du voisin en bas
                    if (!checkLier(matrice.getElement(noeud.getPosition().getCoordX() + 1, voisin.getPosition().getCoordY()), noeud)) {
                        // Aide à true pour une utilisation pour une aide
                        if (jeu.verificationVertical(noeud, voisin, 1, true) == 1) {
                            aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));
                            voisins.remove(i);
                        }
                    }
                }
            }
        }

        return voisins;
    }

    /**
     * Ajoute les voisin a une liste .
     *
     * @param voisins voisin
     * @param x coord x
     * @param y coord y
     * @param dx coord dx
     * @param dy coord dy
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
     * Calcule le poids total des voisins d'un noeud.
     *
     * @param noeud le noeud du jeu à analyser
     * @return le poids total des voisins
     */
    public int poidsTotalVoisins(Noeud noeud) {
        int poidsTotal = 0;
        List<Noeud> voisins = trouverVoisins(noeud);

        // Calcul de la somme des poids des voisins
        for (Noeud voisin : voisins) {
            poidsTotal += voisin.getDegreSoluce();
        }

        return poidsTotal;
    }

    /**
     * Getter pour la description.
     */
    public String getDescription() {
        return description;
    }

    /**
     * Prend les poids autour actuelle des voisin pour check si il lui reste de
     * la place.
     *
     * @param noeud le noeud du jeu à analyser
     * @return si il y asser de poid pour se connecter
     */
    public boolean poidRestantVoisin(Noeud noeud) {
        List<Noeud> voisins = trouverVoisinsDispo(noeud);
        int pa = 0;
        int ps = 0;
        int pl = noeud.getDegreSoluce() - noeud.getDegreActuelle();
        for (Noeud voisin : voisins) {
            pa += voisin.getDegreActuelle();
        }
        for (Noeud voisin : voisins) {
            ps += voisin.getDegreSoluce();
        }
        if (pa < pl) {
            aidesVoisins.add(new AideVoisin(jeu.getPlateau(), "il n y a plus de place pour cher les voisin actuellement .",
                    "poidRestantVoisin", jeu, noeud.getPosition()));
            return true;
        }
        return false;
    }

    /**
     * Regarde si le noeud est lié.
     *
     * @return true si il est lié, false sinon
     */
    private boolean checkLier(ElementJeu elem, Noeud n) {
        Lien l;
        if (elem instanceof DoubleLien dl) {
            l = dl.getLienDuNoeud(n);
        } else {
            l = (Lien) elem;
        }

        if (l.getNbLien() > 0) {
            return true;
        }
        return false;
    }

    /**
     * Observe si ce noeud peut se relier à un autre noeud.
     *
     * @param noeud le noeud du jeu à analyser
     * @return true si le noeud peut se relier à un voisin, sinon false
     */
    public boolean checkIsolement(Noeud noeud) {
        List<Noeud> voisins = trouverVoisins(noeud);
        if (voisins.isEmpty()) {
            aidesVoisins.add(new AideVoisin(matrice, "Aucun voisin accessible", "Isolement", jeu, noeud.getPosition()));

            return false;
        }

        for (Noeud voisin : voisins) {

            if (noeud.getPosition().getCoordX() == voisin.getPosition().getCoordX()) {
                /*
                 * Regarde si le voisin est a ça gauche si oui verifhorizontal du voisin vers le noeud
                 */
                if (noeud.getPosition().getCoordY() > voisin.getPosition().getCoordY()) {
                    /*
                     * Regarde si le noeud ets lié a son voisin si oui n'est pas isolé.
                     */
                    if (checkLier(matrice.getElement(noeud.getPosition().getCoordX(), voisin.getPosition().getCoordY() + 1), noeud)) {
                        aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));

                        return true;
                    }
                    /*Aide a true car c'est une utilisation pour une aide */
                    if (jeu.verificationHorizontal(voisin, noeud, 1, true) == 0) {
                        aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));

                        return true;
                    }
                } else {
                    /* Sinon le voisin est a droite verifhorizontal noeud vers voisin
                     * Regarde si le noeud ets lié a son voisin si oui n'est pas isolé.
                     */
                    if (checkLier(matrice.getElement(noeud.getPosition().getCoordX(), noeud.getPosition().getCoordY() + 1), noeud)) {
                        aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                    /* Aide a true car c'est une utilisation pour une aide */
                    if (jeu.verificationHorizontal(noeud, voisin, 1, true) == 0) {
                        aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                }
            } else {
                /*
                 * Regarde si le voisin est au dessus de lui si oui verifvertical du voisn vers le noeud.
                 */
                if (noeud.getPosition().getCoordX() > voisin.getPosition().getCoordX()) {
                    /*
                     * Regarde si le noeud ets lié a son voisin si oui n'est pas isolé.
                     */
                    if (checkLier(matrice.getElement(voisin.getPosition().getCoordX() + 1, voisin.getPosition().getCoordY()), noeud)) {
                        aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                    /* Aide a true car c'est une utilisation pour une aide */
                    if (jeu.verificationVertical(voisin, noeud, 1, true) == 0) {
                        aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                } else {
                    /* Sinon verifvertical du noeud vers le voisin
                     * Regarde si le noeud ets lié a son voisin si oui n'est pas isolé.
                     */
                    if (checkLier(matrice.getElement(noeud.getPosition().getCoordX() + 1, voisin.getPosition().getCoordY()), noeud)) {
                        aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                    /* Aide a true car c'est une utilisation pour une aide */
                    if (jeu.verificationVertical(noeud, voisin, 1, true) == 0) {
                        aidesVoisins.add(new AideVoisin(matrice, "Trouvés", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Fait une verification par zone du jeu .
     *
     * @return nb d aide dispo
     */
    public int checkzone() {
        // Création des 9 listes pour les zones
        Map<String, List<Noeud>> zones = new HashMap<>();
        zones.put("Centre", new ArrayList<>());
        zones.put("Centre Droite", new ArrayList<>());
        zones.put("Centre Gauche", new ArrayList<>());
        zones.put("Haut", new ArrayList<>());
        zones.put("Bas", new ArrayList<>());
        zones.put("Haut Droite", new ArrayList<>());
        zones.put("Haut Gauche", new ArrayList<>());
        zones.put("Bas Droite", new ArrayList<>());
        zones.put("Bas Gauche", new ArrayList<>());

        int taille = jeu.getTaille();
        int tiers = taille / 3;  // Permet de découper en 3 parties égales

        List<Noeud> tousLesNoeuds = getListeNoeuds();

        // Classer les nœuds dans les bonnes listes en fonction de leur position
        for (Noeud noeud : tousLesNoeuds) {
            int x = noeud.getPosition().getCoordX();
            int y = noeud.getPosition().getCoordY();

            if (x < tiers) {
                if (y < tiers) {
                    zones.get("Haut Gauche").add(noeud);
                } else if (y < 2 * tiers) {
                    zones.get("Centre Gauche").add(noeud);
                } else {
                    zones.get("Bas Gauche").add(noeud);
                }
            } else if (x < 2 * tiers) {
                if (y < tiers) {
                    zones.get("Haut").add(noeud);
                } else if (y < 2 * tiers) {
                    zones.get("Centre").add(noeud);
                } else {
                    zones.get("Bas").add(noeud);
                }
            } else {
                if (y < tiers) {
                    zones.get("Haut Droite").add(noeud);
                } else if (y < 2 * tiers) {
                    zones.get("Centre Droite").add(noeud);
                } else {
                    zones.get("Bas Droite").add(noeud);
                }
            }
        }

        int totalAides = 0;
        Map<String, Integer> aidesParZone = new HashMap<>();

        // Vérifier si une aide est disponible dans chaque zone
        for (Map.Entry<String, List<Noeud>> entry : zones.entrySet()) {
            String nomZone = entry.getKey();
            List<Noeud> noeuds = entry.getValue();
            int aidesDansCetteZone = 0;

            for (Noeud noeud : noeuds) {
                if (poidRestantVoisin(noeud) || checkIsolement(noeud) || afficherAideNoeud(noeud)) {
                    aidesDansCetteZone++;
                }
            }

            if (aidesDansCetteZone > 0) {
                aidesParZone.put(nomZone, aidesDansCetteZone);
                totalAides += aidesDansCetteZone;
            }
        }

        // Affichage des résultats
        for (Map.Entry<String, Integer> aideEntry : aidesParZone.entrySet()) {

        }

        return totalAides;
    }

    public ElementAide aideGlobale() {
        List<Noeud> tousLesNoeuds = getListeNoeuds();
        ElementAide elementAide = new ElementAide();  // Créer un nouvel élément d'aide

        for (Noeud noeud : tousLesNoeuds) {
            // Test 1: Aide sur les voisins
            if (afficherAideNoeud(noeud)) {
                elementAide.addTexte(1, " Il y a des noeud qui peuve etre connecter.Tecnique :si une ile a un chiffre qui "
                        + "si il se divise par 2 alors est egale a son nombre de voisin alors il peut se connecter en double pont a tous ses voisin");
                // Ajouter le noeud à surligner (exemple pour l'index 0)
            }

            // Test 2: Aide sur l'isolement
            if (poidRestantVoisin(noeud)) {
                elementAide.addTexte(2, "un noeud ne possede plus assez de place pour se completer.conseille:liberer lui des voisin");
                // Ajouter le noeud à surligner (exemple pour l'index 1)
            }

            // Test 3: Aide sur le poids restant
            if (checkIsolement(noeud)) {
                elementAide.addTexte(3,
                        "une ile est completement isolé et ne peut plus etre connecter a aucune ile.conseille:"
                        + "liberer lui des voisin");
                // Ajouter le noeud à surligner (exemple pour l'index 2)
            }
        }

        return elementAide;  // Retourner l'élément d'aide complet
    }

    /**
     * Affiche la description de l'aide.
     */
    @Override
    public void afficherAide(int index) {
        System.out.println("Aide: " + aidesVoisins.get(index).nom);
        System.out.println("Description: " + aidesVoisins.get(index).description);
        System.out.println("Coût: " + aidesVoisins.get(index).cout);

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

        AideVoisin aideVoisin = new AideVoisin(jeu.getPlateau(), "Aide sur les voisins", "Voisinage", jeu, new Coord(0, 0));

        // Test de la méthode afficherAideNoeud
        Noeud noeudTest = (Noeud) jeu.getPlateau().getElement(0, 0);  // Exemple de noeud

        aideVoisin.afficherAideNoeud(noeudTest); // Cette méthode affiche dans la console

        aideVoisin.checkIsolement(noeudTest);

        // Test de la méthode poidsTotalVoisins
        int poidsVoisins = aideVoisin.poidsTotalVoisins(noeudTest);

        // Test de la méthode trouverVoisins
        List<Noeud> voisins = aideVoisin.trouverVoisins(noeudTest);

        // Test de la méthode getListeNoeuds
        List<Noeud> tousLesNoeuds = aideVoisin.getListeNoeuds();

        // Test des voisins disponibles par rapport aux liens
        List<Noeud> voisinsDispo2 = aideVoisin.trouverVoisinsDispo(noeudTest);

        // Test d'isolement 
        Noeud noeudTest2 = (Noeud) jeu.getPlateau().getElement(5, 4);
        aideVoisin.checkIsolement(noeudTest2);
        jeu.activeElemJeu(3, 4, ((Noeud) jeu.getPlateau().getElement(3, 3)));
        jeu.activeElemJeu(4, 3, null);
        aideVoisin.checkIsolement(noeudTest2);

        // Test du poids restant chez les voisins
        boolean poidsRestant = aideVoisin.poidRestantVoisin(noeudTest);

        // Test des voisins disponibles par rapport aux liens
        List<Noeud> voisinsDispo = aideVoisin.trouverVoisinsDispo(noeudTest);

        // Test des voisins disponibles par rapport aux liens
        voisinsDispo2 = aideVoisin.trouverVoisinsDispo(noeudTest2);

        // Test du poids restant chez les voisins
        boolean poidsRestant2 = aideVoisin.poidRestantVoisin(noeudTest2);

    }

}
