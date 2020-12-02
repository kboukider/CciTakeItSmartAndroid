package com.openclassrooms.projetCciTakeItSmart.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.openclassrooms.projetCciTakeItSmart.models.Trajet;
import com.openclassrooms.projetCciTakeItSmart.models.User;

public class TrajetHelper {

    private static final String COLLECTION_NAME = "trajets";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getTrajetsCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createTrajet(String uid, String urlPicture, String depart, String arrivee, String date, String heure, String commentaire, String prix) {
        // 1 - Create Obj
        Trajet trajetToCreate = new Trajet(uid, urlPicture, depart, arrivee, date, heure, commentaire, prix);

        return TrajetHelper.getTrajetsCollection().document(uid).set(trajetToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getTrajet(String uid){
        return TrajetHelper.getTrajetsCollection().document(uid).get();
    }

    // --- UPDATE ---


    public static Task<Void> updateDepart(String depart, String uid) {
        return TrajetHelper.getTrajetsCollection().document(uid).update("depart", depart);
    }

    public static Task<Void> updateArrivee(String arrivee, String uid) {
        return TrajetHelper.getTrajetsCollection().document(uid).update("arrivee", arrivee);
    }
    public static Task<Void> updateDate(String date, String uid) {
        return TrajetHelper.getTrajetsCollection().document(uid).update("date", date);
    }
    public static Task<Void> updateHeure(String heure, String uid) {
        return TrajetHelper.getTrajetsCollection().document(uid).update("heure", heure);
    }
    public static Task<Void> updatePrix(String prix, String uid) {
        return TrajetHelper.getTrajetsCollection().document(uid).update("prix", prix);
    }

    public static Task<Void> updatePoids1(String uid, Boolean poids1) {
        return TrajetHelper.getTrajetsCollection().document(uid).update("poids1", poids1);
    }
    public static Task<Void> updatePoids2(String uid, Boolean poids2) {
        return TrajetHelper.getTrajetsCollection().document(uid).update("poids2", poids2);
    }

    public static Task<Void> updateCommentaire(String commentaire, String uid) {
        return TrajetHelper.getTrajetsCollection().document(uid).update("commentaire", commentaire);
    }

    // --- DELETE ---

    public static Task<Void> deleteTrajet(String uid) {
        return TrajetHelper.getTrajetsCollection().document(uid).delete();
    }

}
