package com.puzzlebench.loginfirebase.mvp.presenter;

import android.widget.EditText;


/**
 * Created by andresdavid on 20/10/16.
 */
public interface CreateAccountPresenter {

    void validateFrom(EditText email,EditText name,EditText password1,EditText password2);
}
