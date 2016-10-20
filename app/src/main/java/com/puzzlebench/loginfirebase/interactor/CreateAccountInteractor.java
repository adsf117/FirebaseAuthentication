package com.puzzlebench.loginfirebase.interactor;

import android.widget.EditText;

import com.puzzlebench.loginfirebase.models.User;

/**
 * Created by andresdavid on 20/10/16.
 */
public interface CreateAccountInteractor {
    interface OnCreateAccountInteractorListener {
        void setEmailError(String error);
        void setNameError(String error);
        void setPasswordError(String error);
        void setPasswordConfirmError(String error);
        void successfulCreateAccount();
        void failCreateAccount(String error);

    }
    void validateFrom(EditText email,EditText name,EditText password1,EditText password2);}
