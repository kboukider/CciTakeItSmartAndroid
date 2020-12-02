package com.openclassrooms.projetCciTakeItSmart.trajet;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.openclassrooms.projetCciTakeItSmart.R;
import com.openclassrooms.projetCciTakeItSmart.api.TrajetHelper;
import com.openclassrooms.projetCciTakeItSmart.api.UserHelper;
import com.openclassrooms.projetCciTakeItSmart.base.BaseActivity;
import com.openclassrooms.projetCciTakeItSmart.models.Trajet;
import com.openclassrooms.projetCciTakeItSmart.models.User;

import butterknife.BindView;
import butterknife.OnClick;



public class TrajetActivity extends BaseActivity {

    //POUR LE DESIGN
    @BindView(R.id.profile_activity_imageview_profile) ImageView imageViewProfile;
    @BindView(R.id.profile_activity_edit_text_depart) TextInputEditText textInputEditTextDepart;
    @BindView(R.id.profile_activity_edit_text_arrivee) TextInputEditText textInputEditTextArrivee;
    @BindView(R.id.profile_activity_edit_text_date) TextInputEditText textInputEditTextDate;
    @BindView(R.id.profile_activity_edit_text_heure) TextInputEditText textInputEditTextHeure;
    @BindView(R.id.profile_activity_text_view_email) TextView textViewEmail;
    @BindView(R.id.profile_activity_progress_bar) ProgressBar progressBar;
    @BindView(R.id.profile_activity_edit_text_commentaire) TextInputEditText textInputEditTextCommentaire;
    @BindView(R.id.profile_activity_edit_text_prix) TextInputEditText textInputEditTextPrix;
    @BindView(R.id.profile_activity_check_box_poids1) CheckBox checkBoxPoids1;
    @BindView(R.id.profile_activity_check_box_poids2) CheckBox checkBoxPoids2;
    //POUR LES DONNEES
    private static final int SIGN_OUT_TASK = 10;
    private static final int DELETE_USER_TASK = 20;
    private static final int UPDATE_DEPART = 34;
    private static final int UPDATE_ARRIVEE = 35;
    private static final int UPDATE_DATE = 36;
    private static final int UPDATE_HEURE = 33;
    private static final int UPDATE_COMMENTAIRE = 50;
    private static final int UPDATE_PRIX = 32;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureToolbar();
        this.updateUIWhenCreating();
    }

    @Override
    public int getFragmentLayout() { return R.layout.activity_trajet; }

    // --------------------
    // ACTIONS
    // --------------------

    @OnClick(R.id.profile_activity_button_update)
    public void onClickUpdateButton() { this.updateDepartInFirebase(); this.updateArriveeInFirebase(); this.updateDateInFirebase(); this.updateHeureInFirebase(); this.updateCommentaireInFirebase();  this.updatePrixInFirebase(); }

    @OnClick(R.id.profile_activity_button_sign_out)
    public void onClickSignOutButton() { this.signOutTrajetFromFirebase(); }

    @OnClick(R.id.profile_activity_button_delete)
    public void onClickDeleteButton() {
        new AlertDialog.Builder(this)
                .setMessage("êtes-vous sûr de vouloir supprimer ce trajet ?")
                .setPositiveButton(R.string.popup_message_choice_yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        deleteTrajetFromFirebase();
                    }
                })
                .setNegativeButton(R.string.popup_message_choice_no, null)
                .show();
    }

    @OnClick(R.id.profile_activity_check_box_poids1)
    public void onClickCheckBoxPoids1() { this.updateTrajetPoids1(); }

    @OnClick(R.id.profile_activity_check_box_poids2)
    public void onClickCheckBoxPoids2() { this.updateTrajetPoids2(); }

    // --------------------
    // REST REQUESTS
    // --------------------

    private void signOutTrajetFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
    }

    private void deleteTrajetFromFirebase(){
        if (this.getCurrentUser() != null) {

            //4 - nous avons aussi delete user depuis firestore storage
            TrajetHelper.deleteTrajet(this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener());

            AuthUI.getInstance()
                    .delete(this)
                    .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));
        }
    }


    //  Update Depart
    private void updateDepartInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String depart = this.textInputEditTextDepart.getText().toString();

        if (this.getCurrentUser() != null){
            if (!depart.isEmpty() &&  !depart.equals("")){
                TrajetHelper.updateDepart(depart, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_DEPART));
            }
        }
    }

    //  Update Date
    private void updateDateInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String date = this.textInputEditTextDate.getText().toString();

        if (this.getCurrentUser() != null){
            if (!date.isEmpty() &&  !date.equals("")){
                TrajetHelper.updateDate(date, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_DATE));
            }
        }
    }

    //  Update Heure
    private void updateHeureInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String heure = this.textInputEditTextHeure.getText().toString();

        if (this.getCurrentUser() != null){
            if (!heure.isEmpty() &&  !heure.equals("")){
                TrajetHelper.updateHeure(heure, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_HEURE));
            }
        }
    }
    // 3 - Update Arrivee
    private void updateArriveeInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String arrivee = this.textInputEditTextArrivee.getText().toString();

        if (this.getCurrentUser() != null){
            if (!arrivee.isEmpty() &&  !arrivee.equals("")){
                TrajetHelper.updateArrivee(arrivee, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_ARRIVEE));
            }
        }
    }

    //  Update Commentaire
    private void updateCommentaireInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String commentaire = this.textInputEditTextCommentaire.getText().toString();

        if (this.getCurrentUser() != null){
            if (!commentaire.isEmpty() &&  !commentaire.equals("")){
                TrajetHelper.updateCommentaire(commentaire, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_COMMENTAIRE));
            }
        }
    }

    //  Update Prix
    private void updatePrixInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String prix = this.textInputEditTextPrix.getText().toString();

        if (this.getCurrentUser() != null){
            if (!prix.isEmpty() &&  !prix.equals("")){
                TrajetHelper.updatePrix(prix, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_PRIX));
            }
        }
    }

    // 2 - Update Trajet Poids1 (oui ou non)
    private void updateTrajetPoids1(){
        if (this.getCurrentUser() != null) {
            TrajetHelper.updatePoids1(this.getCurrentUser().getUid(), this.checkBoxPoids1.isChecked()).addOnFailureListener(this.onFailureListener());
        }
    }

    // 2 - Update Trajet Poids2 (oui ou non)
    private void updateTrajetPoids2(){
        if (this.getCurrentUser() != null) {
            TrajetHelper.updatePoids2(this.getCurrentUser().getUid(), this.checkBoxPoids2.isChecked()).addOnFailureListener(this.onFailureListener());
        }
    }

    // --------------------
    // UI
    // --------------------

    private void updateUIWhenCreating(){

        if (this.getCurrentUser() != null){

            //Get picture URL depuis Firebase
            if (this.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                        .load(this.getCurrentUser().getPhotoUrl())
                        .apply(RequestOptions.circleCropTransform())
                        .into(imageViewProfile);
            }

            //Get email & username depuis Firebase
            String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ? getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();

            //Update views avec donnée
            this.textViewEmail.setText(email);

            // 5 - Get données additionelles depuis Firestore
            TrajetHelper.getTrajet(this.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Trajet currentUser = documentSnapshot.toObject(Trajet.class);
                    String depart = TextUtils.isEmpty(currentUser.getDepart()) ? "" : currentUser.getDepart();
                    String arrivee = TextUtils.isEmpty(currentUser.getArrivee()) ? "" : currentUser.getArrivee();
                    String date = TextUtils.isEmpty(currentUser.getDate()) ? "" : currentUser.getDate();
                    String heure = TextUtils.isEmpty(currentUser.getHeure()) ? "" : currentUser.getHeure();
                    String commentaire = TextUtils.isEmpty(currentUser.getCommentaire()) ? "" : currentUser.getCommentaire();
                    String prix = TextUtils.isEmpty(currentUser.getPrix()) ? "" : currentUser.getPrix();
                    checkBoxPoids1.setChecked(currentUser.getPoids1());
                    checkBoxPoids2.setChecked(currentUser.getPoids2());
                    textInputEditTextDepart.setText(depart);
                    textInputEditTextArrivee.setText(arrivee);
                    textInputEditTextDate.setText(date);
                    textInputEditTextHeure.setText(heure);
                    textInputEditTextCommentaire.setText(commentaire);
                    textInputEditTextPrix.setText(prix);
                }
            });
        }
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    case UPDATE_DEPART:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case UPDATE_ARRIVEE:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case UPDATE_DATE:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case UPDATE_HEURE:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case UPDATE_COMMENTAIRE:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case UPDATE_PRIX:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case SIGN_OUT_TASK:
                        finish();
                        break;
                    case DELETE_USER_TASK:
                        finish();
                        break;
                    default:
                        break;
                }
            }
        };
    }
}
