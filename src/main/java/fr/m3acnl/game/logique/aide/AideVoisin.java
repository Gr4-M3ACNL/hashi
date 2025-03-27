package fr.m3acnl.game.logique.aide;

import java.util.ArrayList;
import java.util.Collections;
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

    /**
     * le jeu sur lequel on recherche de l'aide.
     */
    private final Jeu jeu;
    /**
     * liste d'aide sur les voisins.
     */
    private static List<AideVoisin> aidesVoisins;

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

    static {
        aidesVoisins = new ArrayList<>();
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
     * @return Aide correspondante avec un numéro selon la priorité.
     */
    public int poidsRestantVoisins(Noeud noeud) {
        List<Noeud> voisins = trouverVoisinsDispoComplet(noeud);
        List<Integer> poidsVoisins = new ArrayList<>();
        int poidsRestant = (noeud.getDegreSoluce() - noeud.getDegreActuelle());
        int poids = noeud.getDegreSoluce();
        for (Noeud voisin : voisins) {
            poidsVoisins.add((voisin.getDegreSoluce() - voisin.getDegreActuelle()));
        }
        if (noeud.getDegreSoluce() != noeud.getDegreActuelle()) {
            System.out.println("Pr : " + poidsRestant + " noeud : " + noeud.getPosition() + " Voisins : " + poidsVoisins);
            if (poids == 7 && poidsRestant > 3) {
                aidesVoisins.add(new AideVoisin(matrice, "7" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 0;
            } if (poidsRestant == 8) {
                aidesVoisins.add(new AideVoisin(matrice, "8" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 0;
            } if (poidsRestant == 5 && voisins.size() == 3 && poidsRestant > 2) {
                aidesVoisins.add(new AideVoisin(matrice, "Côté, 5 et 3 voisins" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 0;
            } if (voisins.size() == 1) {
                aidesVoisins.add(new AideVoisin(matrice, "Unique voisin" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 0;
            } if (voisins.size() == 2 && poidsRestant > 0 && poids == 4) {
                aidesVoisins.add(new AideVoisin(matrice, "Angle, 4" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 0;
            } if (poids == 6 && voisins.size() == 3 && poidsRestant > 0) {
                aidesVoisins.add(new AideVoisin(matrice, "Côté, 6" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 0;
            } if (poids == 3 && voisins.size() == 2 && poidsRestant > 1) {
                aidesVoisins.add(new AideVoisin(matrice, "Angle, 3" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 0;

            } if (poids == 4 && Collections.frequency(poidsVoisins, 1) == 1 && poidsRestant > 2 && voisins.size() == 3) {
                aidesVoisins.add(new AideVoisin(matrice, "Voisins 4-1-V-V" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 1;
            } if (poids == 6 && Collections.frequency(poidsVoisins, 1) == 1 && poidsRestant > 3) {
                aidesVoisins.add(new AideVoisin(matrice, "Côté, 6-1-V-V" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 1;
            } if (poids == 7 && poidsRestant == 3 && voisins.size() == 3) {
                aidesVoisins.add(new AideVoisin(matrice, "7-1-V" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 0;

            } if (poids == 2 && poidsRestant > 0 && voisins.size() == 2 && Collections.frequency(poidsVoisins, 2) == 2) {
                aidesVoisins.add(new AideVoisin(matrice, "2-2-2" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 1;
            } if (voisins.size() == 2 && poidsRestant == 3) {
                aidesVoisins.add(new AideVoisin(matrice, "Angle, 3" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 1;
            } if (poids == 1 && poidsRestant > 0 && voisins.size() == 2 && Collections.frequency(poidsVoisins, 1) == 1) {
                aidesVoisins.add(new AideVoisin(matrice, "1-1-V" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 1;
            } if (poids == 2 && poidsRestant > 1 && voisins.size() == 2 && Collections.frequency(poidsVoisins, 2) == 1) {
                aidesVoisins.add(new AideVoisin(matrice, "2-2-V" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 1;
            } if (poids == 2 && poidsRestant > 1 && Collections.frequency(poidsVoisins, 1) == 1 && voisins.size() == 2) {
                aidesVoisins.add(new AideVoisin(matrice, "2-1-V" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 1;
            } if (poids == 4 && poidsRestant > 3 && voisins.size() == 3 && Collections.frequency(poidsVoisins, 2) == 2) {
                aidesVoisins.add(new AideVoisin(matrice, "4-2-2-V" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 1;
            } if (poids == 3 && poidsRestant > 2 && voisins.size() == 3 && Collections.frequency(poidsVoisins, 1) == 2) {
                aidesVoisins.add(new AideVoisin(matrice, "Voisins : 3-1-1-V" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 1;
            } /*if (poids == 6 && poidsRestant > 3 && Collections.frequency(poidsVoisins, 1) == 1 && Collections.frequency(poidsVoisins, 2) == 1) {
                aidesVoisins.add(new AideVoisin(matrice, "Voisins : 6-1-2" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 1; PB : 1 DOIT ETRE BLOQUE, PLUS DE PONT PR LUI ET IDEM PR 1-V...
            }*/ if (poids == 2 && poidsRestant > 1 && voisins.size() == 3 && Collections.frequency(poidsVoisins, 1) == 2) {
                aidesVoisins.add(new AideVoisin(matrice, "2-1-1-V" + noeud.getPosition(), "Voisinage", jeu, noeud.getPosition()));
                return 1;
            }
        }
        return -1;
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
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                    /*Aide a true car c'est une utilisation pour une aide */
                    if (jeu.verificationHorizontal(voisin, noeud, 1, true) == 0) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                } else {
                    /* Sinon le voisin est a droite verifhorizontal noeud vers voisin */
 /*
                     * Regarde si le noeud ets lié a son voisin si oui n'est pas isolé.
                     */
                    if (checkLier(matrice.getElement(noeud.getPosition().getCoordX(), noeud.getPosition().getCoordY() + 1), noeud)) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                    /* Aide a true car c'est une utilisation pour une aide */
                    if (jeu.verificationHorizontal(noeud, voisin, 1, true) == 0) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
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
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                    /* Aide a true car c'est une utilisation pour une aide */
                    if (jeu.verificationVertical(voisin, noeud, 1, true) == 0) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                } else {
                    /* Sinon verifvertical du noeud vers le voisin */
 /*
                     * Regarde si le noeud ets lié a son voisin si oui n'est pas isolé.
                     */
                    if (checkLier(matrice.getElement(noeud.getPosition().getCoordX() + 1, voisin.getPosition().getCoordY()), noeud)) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
                        return true;
                    }
                    /* Aide a true car c'est une utilisation pour une aide */
                    if (jeu.verificationVertical(noeud, voisin, 1, true) == 0) {
                        //aidesVoisins.add(new AideVoisin(matrice, "Voisin disponible", "Isolement", jeu, noeud.getPosition()));
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
                if (poidsRestantVoisins(noeud) >= 0 && poidsRestantVoisins(noeud) < 3 || checkIsolement(noeud) /*|| afficherAideNoeud(noeud)*/) {
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
        List<Noeud> tousLesNoeuds = getListeNoeuds();
        ElementAide elementAide = new ElementAide();  // Créer un nouvel élément d'aide
        for (Noeud noeud : tousLesNoeuds) {
            AideGraphe aideGraphe = new AideGraphe(jeu.getPlateau(), "", "", jeu, noeud.getPosition());
            // Test 2: Aide sur l'isolement
            if (poidsRestantVoisins(noeud) == 0) {
                    elementAide.addTexte(0, "Technique debut de partie : " + aidesVoisins.get(aidesVoisins.size() - 1).getDescription());
                    elementAide.addNoeud(0, noeud);
            } if (poidsRestantVoisins(noeud) == 1) {
                    elementAide.addTexte(1, "Technique plus avancée : " + aidesVoisins.get(aidesVoisins.size() - 1).getDescription());
                    elementAide.addNoeud(1, noeud);
            } /*if (poidsRestantVoisins(noeud) == 2) {
                    elementAide.addTexte(0, "Un noeud peut se relier avec ses voisins " + aidesVoisins.get(aidesVoisins.size() - 1).getDescription());
                    elementAide.addNoeud(0, noeud);
            }*/

            if (!checkIsolement(noeud)) {
                elementAide.addTexte(1,
                        "Une île est complètement isolée et ne peut plus être connectée à aucune île. Conseil : "
                        + "libérez lui de ses voisins");
                elementAide.addNoeud(1, noeud);
            }

            if (aideGraphe.lienImpossible(noeud)) {
                elementAide.addTexte(2,
                        "Un noeud peut rendre un groupe d'îles complet. Conseil : "
                        + "libérez lui de ses voisins " + aidesVoisins.get(aidesVoisins.size() - 1).getDescription());
                elementAide.addNoeud(2, noeud);
            }
        }
        aidesVoisins.remove(aidesVoisins.size() - 1);

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

    /**
     * Méthode principale pour tester la classe AideVoisin.
     *
     * @param args arguments de la ligne de commande
     */
    public static void main(String[] args) {
        // Création d'une matrice pour tester
        Double[][] mat = {
            {-4.0, 0.2, -4.0, 0.2, -2.0, 0.0, 0.0},
            {2.0, -3.0, 0.1, -3.0, 0.2, 0.2, -3.0},
            {-3.0, 2.0, 0.0, 0.0, 0.0, 0.0, 1.0},
            {1.0, -2.0, 0.2, -4.0, 0.2, -3.0, 1.0},
            {0.0, 2.0, 0.0, 0.0, 0.0, 1.0, -1.0},
            {1.0, -4.0, 0.2, 0.2, -2.0, 1.0, 0.0},
            {-2.0, 0.1, 0.1, -2.0, 0.1, -2.0, 0.0}
        };

        // Création du jeu
        Jeu jeu = new Jeu(7, mat);
        //jeu.activeElemAide(1, 4, (Noeud) jeu.getPlateau().getElement(1, 3));
        //jeu.activeElemAide(1, 4, (Noeud) jeu.getPlateau().getElement(1, 3));

        AideVoisin aideVoisin = new AideVoisin(jeu.getPlateau(), "Aide sur les voisins", "Voisinage", jeu, new Coord(0, 0));

        jeu.drawJeuTerm();
        System.out.println("\nTest sous-graphe connexe");
        System.out.println("Aide globale : " + aideVoisin.aideGlobale().getTexte());
        List<Noeud>[] aide = aideVoisin.aideGlobale().getNoeudsSurbrillance();
        // affichage des noeuds qui correspondent à l'aide de niveau 2
        for (Noeud noeud : aide[0]) {
            System.out.println("NOEUD" + noeud.getPosition() + aide[0].size());
        }
        System.out.println("\n");
        for (Noeud noeud : aide[1]) {
            System.out.println("NOEUD" + noeud.getPosition() + aide[1].size());
        }
        System.out.println("\n");
        for (Noeud noeud : aide[2]) {
            System.out.println("NOEUD" + noeud.getPosition());
        }
    }
}
