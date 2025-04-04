package fr.m3acnl.profile;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;

import fr.m3acnl.managers.ProfileManager;

/**
 * Paramère d'un profil utilisateur.
 *
 * @author PUREN Mewen
 */
public class ParametreProfile implements JsonSerializable {

    // ======================== Attributs ========================
    /**
     * Limite du niveau d'aide.
     */
    private Integer niveauAide;
    /**
     * Volume des effets sonores. Valeur entre 0 et 1.
     */
    private float volumeEffetsSonore;

    /**
     * Constructeur des paramètres de profil par défaut.
     */
    protected ParametreProfile() {
        this.niveauAide = 0;
        this.volumeEffetsSonore = 0.5f;
    }

    // ======================== Getter ========================
    /**
     * Méthode pour connaître la limite du niveau d'aide.
     *
     * @return le niveau d'aide
     */
    public Integer getNiveauAide() {
        return this.niveauAide;
    }

    /**
     * Méthode pour connaître le volume des effets sonores.
     *
     * @return le volume des effets sonores
     */
    public float getVolumeEffetsSonore() {
        return this.volumeEffetsSonore;
    }

    // ======================== Setter ========================
    /**
     * Méthode pour modifier la limite du niveau d'aide.
     *
     * @param niveauAide le niveau d'aide
     */
    public void setNiveauAide(Integer niveauAide) {
        if (niveauAide < 0 || niveauAide > 2) {
            throw new IllegalArgumentException("Le niveau d'aide doit être compris entre 0 et 2");
        }
        this.niveauAide = niveauAide;
        ProfileManager.getInstance().sauvegarder();
    }

    /**
     * Méthode pour modifier le volume des effets sonores.
     *
     * @param volumeEffetsSonore le volume des effets sonores
     */
    public void setVolumeEffetsSonore(float volumeEffetsSonore) {
        if (volumeEffetsSonore < 0 || volumeEffetsSonore > 1) {
            throw new IllegalArgumentException("Le volume des effets sonores doit être compris entre 0 et 1");
        }
        this.volumeEffetsSonore = volumeEffetsSonore;
        ProfileManager.getInstance().sauvegarder();
    }

    // ======================== Sérialisation ========================
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
