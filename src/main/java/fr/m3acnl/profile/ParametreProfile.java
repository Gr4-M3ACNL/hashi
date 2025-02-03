package fr.m3acnl.profile;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import java.io.IOException;

/**
 * Profil d'un utilisateur.
 * 
 * @author PUREN Mewen
 */
public class ParametreProfile implements JsonSerializable {

    /**
     * Limite du niveau d'aide.
     */
    private Integer niveauAide;
    /**
     * Volume des effets sonores.
     * Valeur entre 0 et 1.
     */
    private float volumeEffetsSonore;
    /**
     * Activation des effets visuels.
     */
    private Boolean effetVisuel;
    
    /**
     * Constructeur des paramètres de profil par défaut.
     */
    protected ParametreProfile() {
        this.niveauAide = 0;
        this.volumeEffetsSonore = 0.5f;
        this.effetVisuel = true;
    }

    /**
     * Méthode pour connaître la limite du niveau d'aide.
     *
     * @return le niveau d'aide
     */
    protected Integer getNiveauAide() {
        return this.niveauAide;
    }

    /**
     * Méthode pour connaître le volume des effets sonores.
     *
     * @return le volume des effets sonores
     */
    protected float getVolumeEffetsSonore() {
        return this.volumeEffetsSonore;
    }

    /**
     * Méthode pour connaître l'état des effets visuels.
     *
     * @return l'état des effets visuels
     */
    protected Boolean getEffetVisuel() {
        return this.effetVisuel;
    }

    /**
     * Méthode pour modifier la limite du niveau d'aide.
     * 
     * @param niveauAide le niveau d'aide
     */
    protected void setNiveauAide(Integer niveauAide) {
        if (niveauAide < 0 || niveauAide > 2) {
            throw new IllegalArgumentException("Le niveau d'aide doit être compris entre 0 et 2");
        }
        this.niveauAide = niveauAide;
    }

    /**
     * Méthode pour modifier le volume des effets sonores.
     * 
     * @param volumeEffetsSonore le volume des effets sonores
     */
    protected void setVolumeEffetsSonore(float volumeEffetsSonore) {
        if (volumeEffetsSonore < 0 || volumeEffetsSonore > 1) {
            throw new IllegalArgumentException("Le volume des effets sonores doit être compris entre 0 et 1");
        }
        this.volumeEffetsSonore = volumeEffetsSonore;
    }

    /**
     * Méthode pour modifier l'état des effets visuels.
     * 
     * @param effetVisuel l'état des effets visuels
     */
    protected void setEffetVisuel(Boolean effetVisuel) {
        this.effetVisuel = effetVisuel;
    }

    /**
     * Serialize les paramètres de profil pour un format JSON.
     * 
     * @param gen générateur de JSON
     * @param serializers fournisseur de sérialisation
     * 
     * @throws IOException si une erreur d'entrée/sortie survient
     * @see JsonSerializable#serialize
     */
    @Override
    public void serialize(JsonGenerator gen, SerializerProvider serializers) throws IOException {
        gen.writeStartObject();
        gen.writeNumberField("niveauAide", this.niveauAide);
        gen.writeNumberField("volumeEffetsSonore", this.volumeEffetsSonore);
        gen.writeBooleanField("effetVisuel", this.effetVisuel);
        gen.writeEndObject();
    }

    /**
     * Serialize les paramètres de profil pour un format JSON avec un type.
     * 
     * @param gen générateur de JSON
     * @param serializers fournisseur de sérialisation
     * @param typeSer sérialiseur de type
     * 
     * @throws IOException si une erreur d'entrée/sortie survient
     * @see #serialize
     * @see JsonSerializable#serializeWithType
     */
    @Override
    public void serializeWithType(JsonGenerator gen, SerializerProvider serializers, TypeSerializer typeSer) throws IOException {
        serialize(gen, serializers);
    }
}
