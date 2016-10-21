package com.puzzlebench.loginfirebase.mvp.presenter;

/**
 * Created by andresdavid on 22/09/16.
 */
public interface LoginPresenter {
    void chenckLogin();
    void resetPassword(String email);
    void validarCampos(String email, String password);

}
