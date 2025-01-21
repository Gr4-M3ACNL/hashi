package fr.m3acnl.game.logique;

/**
 * Cette classe représente un lien entre 2 noeud,
 * il connait les 2 noeud lié, son nombre de lien
 * et son nombre de lien soluce
 */
public class Lien {
    private Noeud noeud1;
    private Noeud noeud2;
    private int nbLien;
    private int nbLienSoluce;

    /**
     * Constructeur pour une nouvelle instance de Lien
     * 
     * @param n1 premier Noeud
     * @param n2 deuxième Noeud
     * @param sol le nombre de lien soluce
     */
    public Lien(Noeud n1, Noeud n2, int sol){
        noeud1=n1;
        noeud2=n2;
        nbLienSoluce=sol;
        nbLien=0;
    }

    /**
     * Vérifie si le lien est valide
     * 
     * @return True si le lien est valide sinon false
     */
    public boolean estValide(){
        return (nbLien==nbLienSoluce);
    }

    /**
     * Active le lien le faisant passer a son état suivant
     * et met a jour le degré actuelle des noeud liés
     */
    public void lienActiver(){
        if((nbLien+=1)%3==0){
            noeud1.enleverDegre();
            noeud2.enleverDegre();
        }
        else{
            noeud1.ajouterDegre();
            noeud2.ajouterDegre();
        }
    }
    /**
     * Récupère le premier noeud
     * 
     * @return le premier noeud
     */
    public Noeud getNoeud1() {
        return noeud1;
    }

    /**
     * Récupère le deuxième noeud
     * 
     * @return le deuxième noeud
     */
    public Noeud getNoeud2() {
        return noeud2;
    }

    /**
     * Récupère le nombre de lien
     * 
     * @return le nombre de lien
     */
    public int getnbLien() {
        return nbLien;
    }

    
}
