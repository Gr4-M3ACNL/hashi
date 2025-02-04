package fr.m3acnl.profile;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializable;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.jsontype.TypeSerializer;
import com.fasterxml.jackson.databind.node.ObjectNode;
import java.io.IOException;

/**
 * Profil d'un utilisateur.
 * 
 * @author PUREN Mewen
 */
public class Profile implements JsonSerializable {
    
    /**
     * Nom du profil.
     */
    private String pseudo;

    /**
     * Paramètres du profil.
     */
    private final ParametreProfile parametre;

    /**
     * Historique des parties jouées par un joueur.
     */
    private final HistoriquePartieProfile historiquePartieProfile;

    /**
     * Constructeur d'un profil.
     *
     * @param pseudo nom du profil
     */
    public Profile(String pseudo) {
        this.pseudo = pseudo;
        this.parametre = new ParametreProfile();
        this.historiquePartieProfile = new HistoriquePartieProfile();
    }

    /**
     * Méthode pour connaître le nom du profil.
     *
     * @return le nom du profil
     */
    public String getNom() {
        return this.pseudo;
    }

    /**
     * Méthode pour connaître les paramètres du profil.
     *
     * @return les paramètres du profil
     * 
     * @see ParametreProfile
     */
    public ParametreProfile getParametre() {
        return this.parametre;
    }

    /**
     * Méthode pour connaître l'historique des parties jouées par un joueur.
     *
     * @return l'historique des parties jouées par un joueur
     * 
     * @see HistoriquePartieProfile
     */
    public HistoriquePartieProfile getHistoriquePartieProfile() {
        return this.historiquePartieProfile;
    }

    /**
     * Méthode pour sérialiser un profil.
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
        ObjectMapper mapper = new ObjectMapper();
        ObjectNode profilNode = mapper.createObjectNode();
        profilNode.put("pseudo", this.pseudo);
        profilNode.set("parametre", mapper.valueToTree(this.parametre));
        profilNode.set("historiquePartieProfile", mapper.valueToTree(this.historiquePartieProfile));

        gen.writeStartObject();
        gen.writeObjectField(this.pseudo, profilNode);
        gen.writeEndObject();
    }

    /**
     * Méthode pour sérialiser un profil avec un type.
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

    /**
     * Méthode pour modifier les paramètres du profil.
     * 
     * @param parametre les paramètres du profil
     */
    protected void setParametre(ParametreProfile parametre) {
        this.parametre.setEffetVisuel(parametre.getEffetVisuel());
        this.parametre.setVolumeEffetsSonore(parametre.getVolumeEffetsSonore());
        this.parametre.setNiveauAide(parametre.getNiveauAide());
    }

    /**
     * Méthode pour modifier l'historique des parties jouées par un joueur.
     * 
     * @param historiquePartieProfile l'historique des parties jouées par un joueur
     */
    protected void setHistoriquePartieProfile(HistoriquePartieProfile historiquePartieProfile) {
        this.historiquePartieProfile.setFacile(historiquePartieProfile.getFacile());
        this.historiquePartieProfile.setMoyen(historiquePartieProfile.getMoyen());
        this.historiquePartieProfile.setDifficile(historiquePartieProfile.getDifficile());
        this.historiquePartieProfile.setExpert(historiquePartieProfile.getExpert());
    }

    /**
     * Méthode pour modifier le nom du profil.
     * 
     * @param pseudo le nom du profil
     */
    protected void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    /**
     * Constructeur vide pour la sérialisation.
     */
    protected Profile() {
        this.parametre = new ParametreProfile();
        this.historiquePartieProfile = new HistoriquePartieProfile();
    }
}
