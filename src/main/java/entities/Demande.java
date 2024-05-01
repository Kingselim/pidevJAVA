/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author hassa
 */
public class Demande {
    private Long id;
    private String state;
    private String typePret;
    private String motifPret;
    private User idUser;
    private Pret idPret;


    public Demande() {
    }

    public Demande(String state, String typePret, String motifPret, User idUser, Pret idPret) {
        this.state = state;
        this.typePret = typePret;
        this.motifPret = motifPret;
        this.idUser = idUser;
        this.idPret = idPret;
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTypePret() {
        return typePret;
    }

    public void setTypePret(String typePret) {
        this.typePret = typePret;
    }

    public String getMotifPret() {
        return motifPret;
    }

    public void setMotifPret(String motifPret) {
        this.motifPret = motifPret;
    }

    public User getIdUser() {
        return idUser;
    }

    public void setIdUser(User idUser) {
        this.idUser = idUser;
    }

    public Pret getIdPret() {
        return idPret;
    }

    public void setIdPret(Pret idPret) {
        this.idPret = idPret;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Demande :")
                .append("\n   State: ").append(state)
                .append("\n   Type Pret: ").append(typePret)
                .append("\n   Motif Pret: ").append(motifPret)
               /// .append("\n   User ID: ").append(idUser)
                //.append("\n   Pret ID: ").append(idPret)
                .append("\n");
        return stringBuilder.toString();
    }

}
