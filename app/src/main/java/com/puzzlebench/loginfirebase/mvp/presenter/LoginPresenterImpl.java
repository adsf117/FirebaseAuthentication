package com.puzzlebench.loginfirebase.mvp.presenter;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.puzzlebench.loginfirebase.interactor.LoginInteractorImpl;
import com.puzzlebench.loginfirebase.mvp.view.LoginView;
import com.puzzlebench.loginfirebase.ui.view.OnListenerLogin;

/**
 * Created by andresdavid on 22/09/16.
 */
public class LoginPresenterImpl implements LoginPresenter, OnListenerLogin {

    LoginView view;
    LoginInteractorImpl interactor;

    public LoginPresenterImpl(LoginView view) {

        this.view = view;
        this.interactor = new LoginInteractorImpl(this.view.getViewActicity(),this);
    }


    @Override
    public void chenckLogin() {
        interactor.checkLogin();
    }

    @Override
    public void resetPassword(String email) {
     interactor.resetPassword(email);
    }

    @Override
    public void validarCampos(String email, String password) {
        if (view != null)
            interactor.validarCampos(email,password);

    }

    @Override
    public void handlefirebaseAuthWithFacebook(AccessToken token) {
        interactor.handlefirebaseAuthWithFacebook(token);

    }

    @Override
    public void handlefirebaseAuthWithGoogle(GoogleSignInAccount acct) {
        interactor.handlefirebaseAuthWithGoogle(acct);
    }

    @Override
    public void removeAuthStateListener() {
        interactor.removeAuthStateListener();
    }


    @Override
    public void setEmailError(String error) {
        if (view != null)
            view.setEmailError(error);
    }

    @Override
    public void setPasswordError(String error) {
        if (view != null)
            view.setPasswordError(error);
    }

    @Override
    public void faildRestorePassword() {
        if (view != null)
            view.faildRestorePassword();
    }

    @Override
    public void succefulRestorePassword() {
        if (view != null)
            view.faildRestorePassword();
    }

    @Override
    public void faildLogin() {
        if (view != null)
            view.faildLogin();
    }

    @Override
    public void succefulLogin() {
        if (view != null)
            view.succefulLogin();

    }

    @Override
    public void noInternetConection() {
        if (view != null)
            view.noInternetConection();
    }

    @Override
    public void showLoginForm() {
        if (view != null)
            view.showLoginForm();
    }

}
