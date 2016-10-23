package com.puzzlebench.loginfirebase.interactor;


import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created  on 22/09/16.
 */
public interface LoginInteractor {
    void checkLogin();
    void resetPassword(String email);
    void validarCampos(String email, String password);
    void handlefirebaseAuthWithFacebook(AccessToken token);
    void handlefirebaseAuthWithGoogle(GoogleSignInAccount acct);
    void removeAuthStateListener();

}

