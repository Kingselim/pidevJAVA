package entities;

import javafx.beans.property.*;

import java.util.Objects;

public class Pret {

    private Long id;
    private Float montantPret;
    private Float tauxInteret;
    private String duree;
    private String nomAssociation;

    public Pret() {
    }

    public Pret(Float montantPret, Float tauxInteret, String duree, String nomAssociation) {
        this.montantPret = montantPret;
        this.tauxInteret = tauxInteret;
        this.duree = duree;
        this.nomAssociation = nomAssociation;
    }

    public Pret(Long id, float montantPret, float tauxInteret, String duree, String nomAssociation) {
        this.id = id;
        this.montantPret = montantPret;
        this.tauxInteret = tauxInteret;
        this.duree = duree;
        this.nomAssociation = nomAssociation;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Float getMontantPret() {
        return montantPret;
    }

    public void setMontantPret(Float montantPret) {
        this.montantPret = montantPret;
    }

    public Float getTauxInteret() {
        return tauxInteret;
    }

    public void setTauxInteret(Float tauxInteret) {
        this.tauxInteret = tauxInteret;
    }

    public String getDuree() {
        return duree;
    }

    public void setDuree(String duree) {
        this.duree = duree;
    }

    public String getNomAssociation() {
        return nomAssociation;
    }

    public void setNomAssociation(String nomAssociation) {
        this.nomAssociation = nomAssociation;
    }

    // Equals and hashCode methods
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pret pret = (Pret) o;
        return Objects.equals(id, pret.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Pret :")
              //  .append("\n   ID: ").append(id)
                .append("\n   Montant: ").append(montantPret)
                .append("\n   Taux d'intérêt: ").append(tauxInteret)
                .append("\n   Durée: ").append(duree)
                .append("\n   Nom de l'association: ").append(nomAssociation)
                .append("\n");
        return stringBuilder.toString();
    }
}
