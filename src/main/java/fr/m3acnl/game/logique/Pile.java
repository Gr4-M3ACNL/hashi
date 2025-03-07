package fr.m3acnl.game.logique;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Classe Pile représentant une Pile d'objets.
 *
 * @author MABIRE Aymeric
 * @version 1.0
 */
public class Pile implements JsonSerializable {

    /**
     * Tableau contenant la pile.
     */
    private final ArrayList<Lien> tab;

    /**
     * Constructeur de la classe Pile.
     */
    public Pile() {
        tab = new ArrayList<>();
    }

    /**
     * Méthode pour retourner la taille d'une pile.
     *
     * @return : la taille de la pile
     */
    public int taille() {
        return tab.size();
    }

    /**
     * Méthode pour savoir si une pile est vide.
     *
     * @return : true si la pile est vide, false sinon
     */
    public boolean estVide() {
        return tab.isEmpty();
    }

    /**
     * Méthode pour empiler un objet dans une pile.
     *
     * @param o : objet à empiler
     */
    public void empiler(Lien o) {
        tab.add(o);

    }

    /**
     * Méthode pour dépiler un objet d'une pile.
     *
     * @return : l'objet dépilé
     */
    public Lien depiler() {
        if (!estVide()) {
            Lien value = tab.get(this.taille() - 1);
            tab.remove(this.taille() - 1);
            return value;
        }
        return null;
    }

    /**
     * Méthode pour retourner l'objet au sommet de la pile.
     *
     * @return : l'objet au sommet de la pile
     */
    public Lien sommet() {
        if (!estVide()) {
            return tab.get(this.taille() - 1);
        }
        return null;
    }

    /**
     * Méthode pour vider entièrement une pile.
     */
    public void vidange() {
        while (!estVide()) {
            depiler();
        }
    }

    /**
     * Méthode pour afficher le contenu d'une pile.
     *
     * @return : le contenu de la pile
     */
    public String to_s() {
        String s = "";
        for (int i = 0; i < this.taille(); i++) {
            s += tab.get(i).toString() + " ";
        }
        return s;
    }

    /**
     * Méthode pour sérialiser une pile de liens.
     * 
     * @param gen générateur de JSON
     * @param serializers fournisseur de sérialisation
     * 
     * @throws IOException si une erreur d'entrée/sortie survient
     * 
     * @see JsonSerializable#serialize
     */
    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartArray();
        for (Lien lien : tab) {
            gen.writeNumber(lien.getIndex());
        }
        gen.writeEndArray();
    }

    /**
     * Méthode pour sérialiser une pile de liens avec le type.
     * 
     * @param gen générateur de JSON
     * @param serializers fournisseur de sérialisation
     * @param typeSer sérialiseur de type
     * 
     * @throws IOException si une erreur d'entrée/sortie survient
     * 
     * @see #serialize
     * @see JsonSerializable#serializeWithType
     */
    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }
}
