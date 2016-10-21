package com.puzzlebench.loginfirebase.ui.view;

/**
 * Created by andresdavid on 22/09/16.
 */
public interface OnListenerLogin {
    void setEmailError(String error);
    void setPasswordError(String error);
    void faildRestorePassword();
    void succefulRestorePassword();
    void faildLogin();
    void succefulLogin();
    void noInternetConection();
    void showLoginForm();
}
