package com.openclassrooms.projetCciTakeItSmart.auth;

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
import com.openclassrooms.projetCciTakeItSmart.api.UserHelper;
import com.openclassrooms.projetCciTakeItSmart.base.BaseActivity;
import com.openclassrooms.projetCciTakeItSmart.models.User;

import butterknife.BindView;
import butterknife.OnClick;



public class ProfileActivity extends BaseActivity {

    //POUR LE DESIGN
    @BindView(R.id.profile_activity_imageview_profile) ImageView imageViewProfile;
    @BindView(R.id.profile_activity_edit_text_username) TextInputEditText textInputEditTextUsername;
    @BindView(R.id.profile_activity_edit_text_vehicule) TextInputEditText textInputEditTextVehicule;
    @BindView(R.id.profile_activity_edit_text_matricule) TextInputEditText textInputEditTextMatricule;
    @BindView(R.id.profile_activity_edit_text_annee) TextInputEditText textInputEditTextAnnee;
    @BindView(R.id.profile_activity_edit_text_tel) TextInputEditText textInputEditTextTel;
    @BindView(R.id.profile_activity_edit_text_identite) TextInputEditText textInputEditTextIdentite;
    @BindView(R.id.profile_activity_edit_text_adresse) TextInputEditText textInputEditTextAdresse;
    @BindView(R.id.profile_activity_text_view_email) TextView textViewEmail;
    @BindView(R.id.profile_activity_progress_bar) ProgressBar progressBar;
    @BindView(R.id.profile_activity_check_box_is_mentor) CheckBox checkBoxIsMentor; // 1 - Ajouter CheckBox Mentor View

    //POUR LES DONNEES
    private static final int SIGN_OUT_TASK = 10;
    private static final int DELETE_USER_TASK = 20;
    private static final int UPDATE_USERNAME = 30;
    private static final int UPDATE_VEHICULE = 33;
    private static final int UPDATE_MATRICULE = 37;
    private static final int UPDATE_ANNEE = 38;
    private static final int UPDATE_TEL = 34;
    private static final int UPDATE_IDENTITE = 36;
    private static final int UPDATE_ADRESSE = 50;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.configureToolbar();
        this.updateUIWhenCreating();
    }

    @Override
    public int getFragmentLayout() { return R.layout.activity_profile; }

    // --------------------
    // ACTIONS
    // --------------------

    @OnClick(R.id.profile_activity_button_update)
    public void onClickUpdateButton() { this.updateUsernameInFirebase(); this.updateVehiculeInFirebase(); this.updateAdresseInFirebase(); this.updateTelInFirebase(); this.updateIdentiteInFirebase(); this.updateMatriculeInFirebase(); this.updateAnneeInFirebase(); }

    @OnClick(R.id.profile_activity_button_sign_out)
    public void onClickSignOutButton() { this.signOutUserFromFirebase(); }

    @OnClick(R.id.profile_activity_button_delete)
    public void onClickDeleteButton() {
        new AlertDialog.Builder(this)
            .setMessage(R.string.popup_message_confirmation_delete_account)
            .setPositiveButton(R.string.popup_message_choice_yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    deleteUserFromFirebase();
                }
            })
            .setNegativeButton(R.string.popup_message_choice_no, null)
            .show();
    }

    @OnClick(R.id.profile_activity_check_box_is_mentor)
    public void onClickCheckBoxIsMentor() { this.updateUserIsMentor(); }

    // --------------------
    // REST REQUESTS
    // --------------------

    private void signOutUserFromFirebase(){
        AuthUI.getInstance()
                .signOut(this)
                .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(SIGN_OUT_TASK));
    }

    private void deleteUserFromFirebase(){
        if (this.getCurrentUser() != null) {

            // delete user depuis firestore storage
            UserHelper.deleteUser(this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener());

            AuthUI.getInstance()
                    .delete(this)
                    .addOnSuccessListener(this, this.updateUIAfterRESTRequestsCompleted(DELETE_USER_TASK));
        }
    }

    //  Update User Username
    private void updateUsernameInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String username = this.textInputEditTextUsername.getText().toString();

        if (this.getCurrentUser() != null){
            if (!username.isEmpty() &&  !username.equals(getString(R.string.info_no_username_found))){
                UserHelper.updateUsername(username, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_USERNAME));
            }
        }
    }



    //  Update Vehicule
    private void updateVehiculeInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String vehicule = this.textInputEditTextVehicule.getText().toString();

        if (this.getCurrentUser() != null){
            if (!vehicule.isEmpty() &&  !vehicule.equals("")){
                UserHelper.updateVehicule(vehicule, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_VEHICULE));
            }
        }
    }

    //  Update matricule
    private void updateMatriculeInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String matricule = this.textInputEditTextMatricule.getText().toString();

        if (this.getCurrentUser() != null){
            if (!matricule.isEmpty() &&  !matricule.equals("")){
                UserHelper.updateMatricule(matricule, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_MATRICULE));
            }
        }
    }

    // Update annee de la voiture
    private void updateAnneeInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String annee = this.textInputEditTextAnnee.getText().toString();

        if (this.getCurrentUser() != null){
            if (!annee.isEmpty() &&  !annee.equals("")){
                UserHelper.updateAnnee(annee, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_ANNEE));
            }
        }
    }

    //  Update Tel
    private void updateTelInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String tel = this.textInputEditTextTel.getText().toString();

        if (this.getCurrentUser() != null){
            if (!tel.isEmpty() &&  !tel.equals("")){
                UserHelper.updateTel(tel, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_TEL));
            }
        }
    }

    //  Update IDENTITE
    private void updateIdentiteInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String identite = this.textInputEditTextIdentite.getText().toString();

        if (this.getCurrentUser() != null){
            if (!identite.isEmpty() &&  !identite.equals("")){
                UserHelper.updateIdentite(identite, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_IDENTITE));
            }
        }
    }

    //  Update Adresse
    private void updateAdresseInFirebase(){

        this.progressBar.setVisibility(View.VISIBLE);
        String adresse = this.textInputEditTextAdresse.getText().toString();

        if (this.getCurrentUser() != null){
            if (!adresse.isEmpty() &&  !adresse.equals("")){
                UserHelper.updateAdresse(adresse, this.getCurrentUser().getUid()).addOnFailureListener(this.onFailureListener()).addOnSuccessListener(this.updateUIAfterRESTRequestsCompleted(UPDATE_ADRESSE));
            }
        }
    }

    //  Update Mentor (oui ou non)
    private void updateUserIsMentor(){
        if (this.getCurrentUser() != null) {
            UserHelper.updateIsMentor(this.getCurrentUser().getUid(), this.checkBoxIsMentor.isChecked()).addOnFailureListener(this.onFailureListener());
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

            //Update views avec donnee
            this.textViewEmail.setText(email);

            //  - Get donnees supplimentaire depuis Firestore
            UserHelper.getUser(this.getCurrentUser().getUid()).addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    User currentUser = documentSnapshot.toObject(User.class);
                    String username = TextUtils.isEmpty(currentUser.getUsername()) ? getString(R.string.info_no_username_found) : currentUser.getUsername();
                    String vehicule = TextUtils.isEmpty(currentUser.getVehicule()) ? "" : currentUser.getVehicule();
                    String matricule = TextUtils.isEmpty(currentUser.getMatricule()) ? "" : currentUser.getMatricule();
                    String annee = TextUtils.isEmpty(currentUser.getAnnee()) ? "" : currentUser.getAnnee();
                    String tel = TextUtils.isEmpty(currentUser.getTel()) ? "" : currentUser.getTel();
                    String identite = TextUtils.isEmpty(currentUser.getIdentite()) ? "" : currentUser.getIdentite();
                    String adresse = TextUtils.isEmpty(currentUser.getAdresse()) ? "" : currentUser.getAdresse();
                    checkBoxIsMentor.setChecked(currentUser.getIsMentor());
                    textInputEditTextUsername.setText(username);
                    textInputEditTextVehicule.setText(vehicule);
                    textInputEditTextMatricule.setText(matricule);
                    textInputEditTextAnnee.setText(annee);
                    textInputEditTextTel.setText(tel);
                    textInputEditTextIdentite.setText(identite);
                    textInputEditTextAdresse.setText(adresse);
                }
            });
        }
    }

    private OnSuccessListener<Void> updateUIAfterRESTRequestsCompleted(final int origin){
        return new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                switch (origin){
                    case UPDATE_USERNAME:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case UPDATE_VEHICULE:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case UPDATE_MATRICULE:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case UPDATE_ANNEE:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case UPDATE_TEL:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case UPDATE_IDENTITE:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    case UPDATE_ADRESSE:
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
