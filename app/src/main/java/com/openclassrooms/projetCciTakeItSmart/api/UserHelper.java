package com.openclassrooms.projetCciTakeItSmart.api;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.openclassrooms.projetCciTakeItSmart.models.User;



public class UserHelper {

    private static final String COLLECTION_NAME = "users";

    // --- COLLECTION REFERENCE ---

    public static CollectionReference getUsersCollection(){
        return FirebaseFirestore.getInstance().collection(COLLECTION_NAME);
    }

    // --- CREATE ---

    public static Task<Void> createUser(String uid, String username, String urlPicture, String vehicule, String adresse, String tel, String identite, String matricule, String annee) {

        // 1 - Creation d'Obj
        User userToCreate = new User(uid, username, urlPicture, vehicule, adresse, tel, identite, matricule, annee);

        return UserHelper.getUsersCollection().document(uid).set(userToCreate);
    }

    // --- GET ---

    public static Task<DocumentSnapshot> getUser(String uid){
        return UserHelper.getUsersCollection().document(uid).get();
    }

    // --- UPDATE ---

    public static Task<Void> updateUsername(String username, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("username", username);
    }

    public static Task<Void> updateVehicule(String vehicule, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("vehicule", vehicule);
    }

    public static Task<Void> updateMatricule(String matricule, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("matricule", matricule);
    }

    public static Task<Void> updateAnnee(String annee, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("annee", annee);
    }

    public static Task<Void> updateTel(String tel, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("tel", tel);
    }

    public static Task<Void> updateIdentite(String identite, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("identite", identite);
    }

    public static Task<Void> updateAdresse(String adresse, String uid) {
        return UserHelper.getUsersCollection().document(uid).update("adresse", adresse);
    }

    public static Task<Void> updateIsMentor(String uid, Boolean isMentor) {
        return UserHelper.getUsersCollection().document(uid).update("isMentor", isMentor);
    }

    // --- DELETE ---

    public static Task<Void> deleteUser(String uid) {
        return UserHelper.getUsersCollection().document(uid).delete();
    }

}
