package com.puzzlebench.loginfirebase.mvp.view;

import android.widget.EditText;

import com.puzzlebench.loginfirebase.ui.CreateAccountBaseView;

/**
 * Created by andresdavid on 20/10/16.
 */
public interface CreateAccountView extends CreateAccountBaseView {
    void setEmailError(String error);
    void setNameError(String error);
    void setPasswordError(String error);
    void setPasswordConfirmError(String error);
    void successfulCreateAccount();
    void failCreateAccount(String error);

}
