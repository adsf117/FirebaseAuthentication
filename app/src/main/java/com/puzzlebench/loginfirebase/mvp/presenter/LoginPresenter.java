package com.puzzlebench.loginfirebase.mvp.presenter;

import com.facebook.AccessToken;

/**
 * Created by andresdavid on 22/09/16.
 */
public interface LoginPresenter {
    void chenckLogin();
    void resetPassword(String email);
    void validarCampos(String email, String password);
    void handleFacebookAccessToken(AccessToken token);
    void removeAuthStateListener();

}
