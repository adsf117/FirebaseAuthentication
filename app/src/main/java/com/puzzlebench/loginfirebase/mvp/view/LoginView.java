package com.puzzlebench.loginfirebase.mvp.view;


import com.puzzlebench.loginfirebase.ui.view.LoginBaseView;

/**
 * Created on 22/09/16.
 */
public interface LoginView extends LoginBaseView {
    void setEmailError(String error);
    void setPasswordError(String error);
    void faildRestorePassword();
    void succefulRestorePassword();
    void faildLogin();
    void succefulLogin();
    void noInternetConection();
    void showLoginForm();
}
