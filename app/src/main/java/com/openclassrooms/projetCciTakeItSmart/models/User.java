package com.openclassrooms.projetCciTakeItSmart.models;

import android.support.annotation.Nullable;



public class User {

    private String uid;
    private String username;
    private String vehicule;
    private String matricule;
    private String annee;
    private String tel;
    private String identite;
    private String adresse;
    private Boolean isMentor;
    private Boolean poids1;
    private Boolean poids2;
    @Nullable private String urlPicture;

    public User() { }

    public User(String uid, String username, String urlPicture, String vehicule, String adresse, String tel, String identite, String matricule, String annee) {
        this.uid = uid;
        this.username = username;
        this.vehicule = vehicule;
        this.matricule = matricule;
        this.annee = annee;
        this.tel = tel;
        this.identite = identite;
        this.adresse = adresse;
        this.urlPicture = urlPicture;
        this.isMentor = false;
    }

    // --- GETTERS ---
    public String getUid() { return uid; }
    public String getUsername() { return username; }
    public String getVehicule() { return vehicule; }
    public String getMatricule() { return matricule; }
    public String getAnnee() { return annee; }
    public String getTel() { return tel; }
    public String getIdentite() { return identite; }
    public String getAdresse() { return adresse; }
    public String getUrlPicture() { return urlPicture; }
    public Boolean getIsMentor() { return isMentor; }


    // --- SETTERS ---
    public void setUsername(String username) { this.username = username; }
    public void setVehicule(String vehicule) { this.vehicule = vehicule; }
    public void setMatricule(String matricule) { this.matricule = matricule; }
    public void setAnnee(String annee) { this.annee = annee; }
    public void setTel(String tel) { this.tel = tel; }
    public void setIdentite(String identite) { this.identite = identite; }
    public void setAdresse(String adresse) { this.adresse = adresse; }
    public void setUid(String uid) { this.uid = uid; }
    public void setUrlPicture(String urlPicture) { this.urlPicture = urlPicture; }
    public void setIsMentor(Boolean mentor) { isMentor = mentor; }
}
