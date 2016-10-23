package com.puzzlebench.loginfirebase.mvp.presenter;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

/**
 * Created by andresdavid on 22/09/16.
 */
public interface LoginPresenter {
    void chenckLogin();
    void resetPassword(String email);
    void validarCampos(String email, String password);
    void handlefirebaseAuthWithFacebook(AccessToken token);
    void handlefirebaseAuthWithGoogle(GoogleSignInAccount acct);
    void removeAuthStateListener();

}
