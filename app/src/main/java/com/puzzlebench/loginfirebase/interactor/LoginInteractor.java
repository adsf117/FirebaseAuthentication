package com.puzzlebench.loginfirebase.interactor;


import com.facebook.AccessToken;

/**
 * Created  on 22/09/16.
 */
public interface LoginInteractor {
    void checkLogin();
    void resetPassword(String email);
    void validarCampos(String email, String password);
    void handleFacebookAccessToken(AccessToken token);
    void removeAuthStateListener();

}

