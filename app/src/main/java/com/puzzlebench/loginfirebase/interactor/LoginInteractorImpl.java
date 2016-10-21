package com.puzzlebench.loginfirebase.interactor;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.puzzlebench.loginfirebase.Globals;
import com.puzzlebench.loginfirebase.R;
import com.puzzlebench.loginfirebase.Utils;
import com.puzzlebench.loginfirebase.UtilsValidations;
import com.puzzlebench.loginfirebase.models.User;
import com.puzzlebench.loginfirebase.ui.view.OnListenerLogin;


/**
 * Created  on 22/09/16.
 */
public class LoginInteractorImpl implements LoginInteractor {

    String LOG_TAG = LoginInteractorImpl.class.getName();

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Activity activity;
    private OnListenerLogin listenerLogin;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef_Usuario = database.getReference(Globals.REF_USER);

    private String mUuid = "";
    private User mUser = null;


    public LoginInteractorImpl(Activity activity, OnListenerLogin listener) {
        this.activity = activity;
        mAuth = FirebaseAuth.getInstance();
        listenerLogin = listener;
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    listenerLogin.succefulLogin();
                } else {
                    // User is signed out
                    listenerLogin.succefulLogin();
                }
                // ...
            }
        };
        mAuth.addAuthStateListener(mAuthListener);
    }



    @Override
    public void checkLogin() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            mUuid = user.getUid();
            listenerLogin.succefulLogin();
        } else {
            listenerLogin.showLoginForm();
        }
    }

    @Override
    public void resetPassword(String email) {

        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            listenerLogin.succefulRestorePassword();
                        } else {
                            listenerLogin.faildRestorePassword();
                        }
                    }
                });
    }

    @Override
    public void validarCampos(String email, String password) {

        if (!Utils.isConnected(activity.getApplicationContext())) {
            listenerLogin.noInternetConection();

        } else {
            if (TextUtils.isEmpty(email)) {
                listenerLogin.setEmailError(activity.getString(R.string.requiervalue));
            } else if (!email.contains("@")) {
                listenerLogin.setEmailError(activity.getString(R.string.error_invalid_email_format));

            } else if (TextUtils.isEmpty(password)) {
                listenerLogin.setPasswordError(activity.getString(R.string.requiervalue));
            } else if (password.length() < UtilsValidations.MAXLENGTHPASSWORD) {
                listenerLogin.setPasswordError(String.format(activity.getString(R.string.error_password_length), UtilsValidations.MAXLENGTHPASSWORD));
            } else {
                signIn(email, password);
            }
        }
    }

    @Override
    public void handleFacebookAccessToken(AccessToken token) {
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mUuid = task.getResult().getUser().getUid();
                    listenerLogin.succefulLogin();
                }
                else {
                    listenerLogin.faildLogin();
                }
            }
        });
    }

    @Override
    public void removeAuthStateListener() {
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn(String email, String password) {
        // [START sign_in_with_email]
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(activity, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    mUuid = task.getResult().getUser().getUid();
                    listenerLogin.succefulLogin();
                } else {
                    listenerLogin.faildLogin();
                }

            }
        });

    }

}
