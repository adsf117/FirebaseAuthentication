package com.puzzlebench.loginfirebase.mvp.presenter;

import com.facebook.AccessToken;
import com.puzzlebench.loginfirebase.interactor.LoginInteractorImpl;
import com.puzzlebench.loginfirebase.mvp.view.LoginView;
import com.puzzlebench.loginfirebase.ui.view.OnListenerLogin;

/**
 * Created by andresdavid on 22/09/16.
 */
public class LoginPresenterImpl implements LoginPresenter, OnListenerLogin {

    LoginView mView;
    LoginInteractorImpl mInteractor;

    public LoginPresenterImpl(LoginView view) {

        this.mView = view;
        this.mInteractor = new LoginInteractorImpl(mView.getViewActicity(),this);
    }


    @Override
    public void chenckLogin() {
        mInteractor.checkLogin();
    }

    @Override
    public void resetPassword(String email) {
     mInteractor.resetPassword(email);
    }

    @Override
    public void validarCampos(String email, String password) {
        if (mView != null)
            mInteractor.validarCampos(email,password);

    }

    @Override
    public void handleFacebookAccessToken(AccessToken token) {
        mInteractor.handleFacebookAccessToken(token);

    }

    @Override
    public void removeAuthStateListener() {
        mInteractor.removeAuthStateListener();
    }


    @Override
    public void setEmailError(String error) {
        if (mView != null)
            mView.setEmailError(error);
    }

    @Override
    public void setPasswordError(String error) {
        if (mView != null)
            mView.setPasswordError(error);
    }

    @Override
    public void faildRestorePassword() {
        if (mView != null)
            mView.faildRestorePassword();
    }

    @Override
    public void succefulRestorePassword() {
        if (mView != null)
            mView.faildRestorePassword();
    }

    @Override
    public void faildLogin() {
        if (mView != null)
            mView.faildLogin();
    }

    @Override
    public void succefulLogin() {
        if (mView != null)
            mView.succefulLogin();

    }

    @Override
    public void noInternetConection() {
        if (mView != null)
            mView.noInternetConection();
    }

    @Override
    public void showLoginForm() {
        if (mView != null)
            mView.showLoginForm();
    }

}
