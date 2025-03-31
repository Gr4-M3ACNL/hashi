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
 * @author Gaumont mael, MABIRE Aymeric
 * @version 1.0
 */
public class AideVoisin extends Aide {

    /**
     * le jeu sur lequel on recherche de l'aide.
     */
    private final Jeu jeu;
    /**
     * liste d'aide sur les voisins.
     */
    private static List<AideVoisin> aidesVoisins = new ArrayList<>();

    /**
     * la zonne dans la quelle se trouve l'aide.
     */
    private Map<String, List<Noeud>> zones;

    /**
     * Constructeur pour créer une instance de l'aide sur les voisins.
     *
     * @param matrice la matrice représentant la grille du jeu
     * @param description la description de l'aide
     * @param nom le nom de l'aide
     * @param jeu le jeu sur lequel on recherche de l'aide
     * @param c la coordonnée du noeud
     */
    public AideVoisin(Matrice matrice, String description, String nom, Jeu jeu, Coord c) {
        super(matrice, nom, c);
        this.description = description;
        this.jeu = jeu;
        cout = 1;

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
     * Vérifie si les coordonnées sont valides.
     *
     * @param x coordonnée x
     * @param y coordonnée y
     * @return true si les coordonnées sont valides, sinon false
     */
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
                            //aidesVoisins.add(new AideVoisin(matrice, "//ible", "Isolement", jeu, noeud.getPosition()));
                            voisins.remove(i);
                        }
                    }
                } else {
                    // Vérifie l'isolement horizontal du voisin à droite
                    if (!checkLier(matrice.getElement(noeud.getPosition().getCoordX(), noeud.getPosition().getCoordY() + 1), noeud)) {
                        // Aide à true pour une utilisation pour une aide
                        if (jeu.verificationHorizontal(noeud, voisin, 1, true) == 1) {
                            //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
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
                            //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                            voisins.remove(i);
                        }
                    }
                } else {
                    // Vérifie l'isolement vertical du voisin en bas
                    if (!checkLier(matrice.getElement(noeud.getPosition().getCoordX() + 1, voisin.getPosition().getCoordY()), noeud)) {
                        // Aide à true pour une utilisation pour une aide
                        if (jeu.verificationVertical(noeud, voisin, 1, true) == 1) {
                            //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
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
     * Getter pour la description.
     *
     * @return la description de l'aide
     */
    public String getDescription() {
        return description;
    }

    /**
     * Prend les poids autour actuels des voisins pour checker s'il lui reste de
     * la place. Ajout des aides correspondantes dans la liste d'AideVoisins
     * selon l'aide correspondante. Conseil : comprendre les ifs par leur
     * description.
     *
     * @param noeud Le noeud du jeu à analyser
     * @return Aide correspondante avec un numéro selon la priorité. UTILISER
     * TROUVERVOISINS DISPO ?
     */
    public int poidsRestantVoisins(Noeud noeud) {
        List<Noeud> voisins = trouverVoisinsDispoComplet(noeud);
        int poidsVoisins = 0;
        int poidsVoisinsReels = 0;
        int poidsRestant = (noeud.getDegreSoluce() - noeud.getDegreActuelle());

        for (Noeud voisin : voisins) {
            poidsVoisins += voisin.getDegreActuelle();
            poidsVoisinsReels += voisin.getDegreSoluce();
        }
        System.out.println("Noeud : " + noeud + "Poids restant : " + poidsRestant);
        if (poidsRestant != 0) {
            System.out.println(
                    "Noeud : " + noeud + "Pr : " + poidsRestant + " noeud : " + noeud.getPosition() + "Voisins : "
                    + poidsVoisinsReels);

            if (!checkIsolement(noeud)) { // Le noeud est isolé   /**/ 
                /*aidesVoisins.add(new AideVoisin(matrice, "Noeud isolé" + noeud.getPosition(), "Isolement", jeu,
                        noeud.getPosition()));*/
                return 4;
            } else if (poidsRestant > 0 && poidsVoisinsReels == poidsVoisins) { // Ses voisins sont connecté aux mauvais noeuds
                aidesVoisins.add(new AideVoisin(matrice, "Voisin trop connecté" + noeud.getPosition(), "Voisinage", jeu,
                        noeud.getPosition()));
                return 0;
            } else if (poidsRestant > 0 && poidsVoisinsReels == noeud.getDegreSoluce()) { // Le noeud peut se connecter à tous ses voisins
                aidesVoisins.add(new AideVoisin(matrice, "Connexion totale" + noeud.getPosition(), "Voisinage",
                        jeu, noeud.getPosition()));
                return 1;
            } else if (poidsRestant > 0) { //Le noeud a de la place pour se connecter
                aidesVoisins.add(new AideVoisin(matrice, "Connexion partielle" + noeud.getPosition(), "Voisinage",
                        jeu, noeud.getPosition()));
                return 2;
            } else if (poidsRestant < 0) { //Le noeud est surchargé
                aidesVoisins.add(new AideVoisin(matrice, "Surcharge" + noeud.getPosition(), "Voisinage", jeu,
                        noeud.getPosition()));
                return 3;
            }
        }
        return -1;
    }

    /**
     * Permet de fournir un conseil en fonction du noeud.
     *
     * @param noeud Le noeud du jeu à analyser
     * @return Le conseil à afficher
     */
    private String conseil(Noeud noeud) {
        String conseil = "";
        conseil = switch (noeud.getDegreSoluce()) {
            case 3, 4 -> {
                if ((noeud.getPosition().getCoordX() == 0 && noeud.getPosition().getCoordY() == 0)
                        || (noeud.getPosition().getCoordX() == 0 && noeud.getPosition().getCoordY() == jeu.getTaille() - 1)
                        || (noeud.getPosition().getCoordX() == jeu.getTaille() - 1 && noeud.getPosition().getCoordY() == 0)
                        || (noeud.getPosition().getCoordX() == jeu.getTaille() - 1 && noeud.getPosition().getCoordY() == jeu.getTaille() - 1)) {
                    yield "\nConseil:\nUn noeud de degré 3 ou 4 dans un coin se connecte à tous ses voisins\n";
                }
                yield "";
            }
            case 5, 6 -> {
                if ((noeud.getPosition().getCoordX() == 0 || noeud.getPosition().getCoordX() == jeu.getTaille() - 1)
                        || (noeud.getPosition().getCoordY() == 0 || noeud.getPosition().getCoordY() == jeu.getTaille() - 1)) {
                    yield "\nConseil:\nUn noeud de degré 5 ou 6 sur une bordure se connecte au moins une fois à tous ses voisins\n";
                }
                yield "";
            }
            case 7, 8 ->
                "\nConseil:\nUn noeud de degré 7 ou 8 se connecte au moins une fois avec tous ses voisins\n";
            default ->
                "";
        };
        return conseil;
    }

    /**
     * Regarde si le noeud est lié.
     *
     * @param elem le noeud du jeu à analyser
     * @param n le noeud à vérifier
     * @return true si il est lié, false sinon
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
     * Observe si ce noeud peut se relier à un autre noeud.
     *
     * @param noeud le noeud du jeu à analyser
     * @return true si le noeud peut se relier à un voisin, sinon false
     */
    private boolean checkIsolement(Noeud noeud) {
        List<Noeud> voisins = trouverVoisins(noeud);
        if (voisins.isEmpty()) {
            aidesVoisins.add(new AideVoisin(matrice, "Aucun voisin accessible", "Isolement", jeu, noeud.getPosition()));

            return false;
        }

        for (Noeud voisin : voisins) {
            if (noeud.getPosition().getCoordX() == voisin.getPosition().getCoordX()) {

                // Regarde si le voisin est a ça gauche si oui verifhorizontal du voisin vers le noeud
                if (noeud.getPosition().getCoordY() > voisin.getPosition().getCoordY()) {

                    //  Regarde si le noeud ets lié a son voisin si oui n'est pas isolé.
                    if (checkLier(
                            matrice.getElement(noeud.getPosition().getCoordX(), voisin.getPosition().getCoordY() + 1),
                            noeud)) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                    //Aide a true car c'est une utilisation pour une aide 
                    if (jeu.verificationHorizontal(voisin, noeud, 1, true) == 0) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                } else {

                    // Regarde si le noeud ets lié a son voisin si oui n'est pas isolé.
                    if (checkLier(
                            matrice.getElement(noeud.getPosition().getCoordX(), noeud.getPosition().getCoordY() + 1),
                            noeud)) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                    // Aide a true car c'est une utilisation pour une aide 
                    if (jeu.verificationHorizontal(noeud, voisin, 1, true) == 0) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                }
            } else {

                //Regarde si le voisin est au dessus de lui si oui verifvertical du voisn vers le noeud.
                if (noeud.getPosition().getCoordX() > voisin.getPosition().getCoordX()) {

                    // Regarde si le noeud ets lié a son voisin si oui n'est pas isolé.
                    if (checkLier(
                            matrice.getElement(voisin.getPosition().getCoordX() + 1, voisin.getPosition().getCoordY()),
                            noeud)) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                    // Aide a true car c'est une utilisation pour une aide
                    if (jeu.verificationVertical(voisin, noeud, 1, true) == 0) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                } else {

                    //  Regarde si le noeud ets lié a son voisin si oui n'est pas isolé.
                    if (checkLier(
                            matrice.getElement(noeud.getPosition().getCoordX() + 1, voisin.getPosition().getCoordY()),
                            noeud)) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                    // Aide a true car c'est une utilisation pour une aide 
                    if (jeu.verificationVertical(noeud, voisin, 1, true) == 0) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                }
            }
        }
        aidesVoisins.add(new AideVoisin(matrice, "Aucun voisin accessible", "Isolement", jeu, noeud.getPosition()));
        return false;
    }

    /**
     * Découpe la matrice en 9 zones distinctes.
     */
    public void checkzone() {
        // Création des 9 listes pour les zones
        zones = new HashMap<>();
        zones.put("Centre", new ArrayList<>());
        zones.put("Centre Droite", new ArrayList<>());
        zones.put("Centre Gauche", new ArrayList<>());
        zones.put("Gauche", new ArrayList<>());
        zones.put("Droite", new ArrayList<>());
        zones.put("Haut Droite", new ArrayList<>());
        zones.put("Haut Gauche", new ArrayList<>());
        zones.put("Bas Droite", new ArrayList<>());
        zones.put("Bas Gauche", new ArrayList<>());

        int taille = jeu.getTaille();
        int tiers = taille / 3; // Permet de découper en 3 parties égales

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
                    zones.get("Haut Droite").add(noeud);
                }
            } else if (x < 2 * tiers) {
                if (y < tiers) {
                    zones.get("Gauche").add(noeud);
                } else if (y < 2 * tiers) {
                    zones.get("Centre").add(noeud);
                } else {
                    zones.get("Droite").add(noeud);
                }
            } else {
                if (y < tiers) {
                    zones.get("Bas Gauche").add(noeud);
                } else if (y < 2 * tiers) {
                    zones.get("Centre Droite").add(noeud);
                } else {
                    zones.get("Bas Droite").add(noeud);
                }
            }
        }
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
     * Méthode pour créer un élément d'aide globale. Vérifie si le noeud est
     * isolé.
     *
     * @return l'element d'aide a afficher
     */
    public ElementAide aideGlobale() {
        checkzone();
        List<Noeud> tousLesNoeuds = getListeNoeuds();
        ElementAide elementAide = new ElementAide();  // Créer un nouvel élément d'aide
        for (Noeud noeud : tousLesNoeuds) {
            // Test 2: Aide sur l'isolement
            System.out.println("Poids restant : " + poidsRestantVoisins(noeud));
            if (elementAide.getTexte().get(0).equals("")) {
                switch (poidsRestantVoisins(noeud)) {
                    case 0 -> {
                        System.out.println("0");
                        elementAide.addTexte(0, "Un noeud ne possède plus assez de place pour se relier.");
                        elementAide.addTexte(1, "Un noeud ne possède plus assez de place pour se relier. Il se trouve dans la zone "
                                + zones.entrySet().stream().filter(e -> e.getValue().contains(noeud)).findFirst().get()
                                        .getKey());
                        zones.entrySet().stream().filter(
                                e -> e.getValue().contains(noeud)).findFirst().get().getValue().forEach(
                                        n -> elementAide.addNoeud(1, n));
                        elementAide.addTexte(2, "Un noeud ne possède plus assez de place pour se relier."
                                + " Conseil : libérez lui de ses voisins "
                                + aidesVoisins.get(aidesVoisins.size() - 1).getDescription() + conseil(noeud));
                        elementAide.addNoeud(2, noeud);
                    }
                    case 1 -> {
                        System.out.println("1");
                        elementAide.addTexte(0, "Un noeud peut complètement se relier avec ses voisins ");
                        elementAide.addTexte(1, "Un noeud peut complètement se relier avec ses voisins . Il se trouve dans la zone "
                                + zones.entrySet().stream().filter(e -> e.getValue().contains(noeud)).findFirst().get()
                                        .getKey());
                        zones.entrySet().stream().filter(
                                e -> e.getValue().contains(noeud)).findFirst().get().getValue()
                                .forEach(
                                        n -> elementAide.addNoeud(1, n));
                        elementAide.addTexte(2, "Un noeud peut complètement se relier avec ses voisins "
                                + aidesVoisins.get(aidesVoisins.size() - 1).getDescription() + conseil(noeud));
                        elementAide.addNoeud(2, noeud);
                    }
                    case 2 -> {
                        System.out.println("2");
                        elementAide.addTexte(0, "Un noeud peut se relier avec ses voisins ");
                        System.out.println("2 - 0");
                        elementAide.addTexte(1, "Un noeud peut se relier avec ses voisins . Il se trouve dans la zone "
                                + zones.entrySet().stream().filter(e -> e.getValue().contains(noeud)).findFirst().get()
                                        .getKey());
                        zones.entrySet().stream().filter(
                                e -> e.getValue().contains(noeud)).findFirst().get().getValue().forEach(
                                        n -> elementAide.addNoeud(1, n));
                        System.out.println("2 - 1 Size : " + aidesVoisins.size());
                        elementAide.addTexte(2, "Un noeud peut se relier avec ses voisins "
                                + aidesVoisins.get(aidesVoisins.size() - 1).getDescription() + conseil(noeud));
                        elementAide.addNoeud(2, noeud);
                        System.out.println("End 2");
                    }
                    case 3 -> {
                        System.out.println("3");
                        elementAide.addTexte(0, "Un noeud est saturé, liberer le un peu ");
                        elementAide.addTexte(1, "Un noeud est saturé, liberer le un peu. Il se trouve dans la zone "
                                + zones.entrySet().stream().filter(e -> e.getValue().contains(noeud)).findFirst().get()
                                        .getKey());
                        zones.entrySet().stream().filter(
                                e -> e.getValue().contains(noeud)).findFirst().get().getValue().forEach(
                                        n -> elementAide.addNoeud(1, n));

                        elementAide.addTexte(2, "Un noeud est saturé, liberer le un peu "
                                + aidesVoisins.get(aidesVoisins.size() - 1).getDescription() + conseil(noeud));
                        elementAide.addNoeud(2, noeud);
                    }
                    case 4 -> {
                        System.out.println("4");
                        elementAide.addTexte(0,
                                "Un noeud est isolé");
                        elementAide.addTexte(1, "Un noeud est isolé. Il se trouve dans la zone "
                                + zones.entrySet().stream().filter(e -> e.getValue().contains(noeud)).findFirst().get()
                                        .getKey());
                        zones.entrySet().stream().filter(
                                e -> e.getValue().contains(noeud)).findFirst().get().getValue().forEach(
                                        n -> elementAide.addNoeud(1, n));
                        elementAide.addTexte(2,
                                "Un noeud est isolé" + aidesVoisins.get(aidesVoisins.size() - 1).getDescription()
                                + conseil(noeud));
                        elementAide.addNoeud(2, noeud);
                    }
                    default -> {
                        break;
                    }
                }
            }
        }
        return elementAide;  // Retourner l'élément d'aide complet
    }

    /**
     * Affiche la description de l'aide.
     *
     * @param index l'index de l'aide à afficher
     * @see Aide#afficherAide(int)
     */
    @Override
    public void afficherAide(int index) {
        System.out.println("Aide: " + aidesVoisins.get(index).nom);
        System.out.println("Description: " + aidesVoisins.get(index).description);
        System.out.println("Coût: " + aidesVoisins.get(index).cout + "\n");
    }
}
