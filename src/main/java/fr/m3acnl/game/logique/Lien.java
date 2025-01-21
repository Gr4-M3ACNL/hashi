package fr.m3acnl.game.logique

public class Lien {
    private Noeud noeud1;
    private Noeud noeud2;
    private int etat;
    private int etatSoluce;

    public Lien(Noeud n1, Noeud n2, int sol){
        noeud1=n1;
        noeud2=n2;
        etatSoluce=sol;
        etat=0;
    }

    public boolean estValide(){
        return (etat==etatSoluce);
    }
    public void lienActiver(){
        if((etat+=1)%3==0){
            noeud1.enleverDegre();
            noeud2.enleverDegre();
        }
        else{
            noeud1.ajouterDegre();
            noeud2.ajouterDegre();
        }
    }

    public Noeud getNoeud1() {
        return noeud1;
    }
    public Noeud getNoeud2() {
        return noeud2;
    }
    public int getEtat() {
        return etat;
    }
}
