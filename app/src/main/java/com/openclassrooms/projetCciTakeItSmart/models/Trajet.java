package com.openclassrooms.projetCciTakeItSmart.models;

import android.support.annotation.Nullable;

public class Trajet {

    private String uid;
    private String depart;
    private String arrivee;
    private String date;
    private String heure;
    private String commentaire;
    private String prix;
    private Boolean poids1;
    private Boolean poids2;

    @Nullable private String urlPicture;

    public Trajet() { }

    public Trajet(String uid, String urlPicture, String depart, String arrivee, String date, String heure, String commentaire, String prix) {
        this.uid = uid;
        this.depart = depart;
        this.arrivee = arrivee;
        this.date = date;
        this.heure = heure;
        this.urlPicture = urlPicture;
        this.commentaire = commentaire;
        this.prix = prix;
        this.poids1 = false;
        this.poids2 = false;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getDepart() { return depart; }
    public String getArrivee() { return arrivee; }
    public String getDate() { return date; }
    public String getHeure() { return heure; }
    public String getUrlPicture() { return urlPicture; }
    public String getCommentaire() { return commentaire; }
    public String getPrix() { return prix; }
    public Boolean getPoids1() { return poids1; }
    public Boolean getPoids2() { return poids2; }

    // --- SETTERS ---
    public void setDepart(String depart) { this.depart= depart; }
    public void setArrivee(String arrivee) { this.arrivee = arrivee; }
    public void setDate(String date) { this.date= date; }
    public void setHeure(String heure) { this.heure= heure; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setCommentaire(String commentaire) { this.commentaire = commentaire; }
    public void setPrix(String prix) { this.prix = prix; }
    public void setPoids1(Boolean poid1) {poids1 = poid1; }
    public void setPoids2(Boolean poid2) { poids2 = poid2; }
}
