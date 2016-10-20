package com.puzzlebench.loginfirebase.interactor;

import android.app.Activity;
import android.text.TextUtils;
import android.widget.EditText;

import com.puzzlebench.loginfirebase.R;
import com.puzzlebench.loginfirebase.models.User;

/**
 * Created by andresdavid on 20/10/16.
 */
public class CreateAccountInteractorImp implements CreateAccountInteractor {

    CreateAccountInteractor.OnCreateAccountInteractorListener listener;
    Activity activity;

    public CreateAccountInteractorImp(Activity activity, OnCreateAccountInteractorListener listener) {
        this.activity = activity;
        this.listener = listener;
    }


    @Override
    public void validateFrom(EditText email, EditText name, EditText password1, EditText password2) {
        if(TextUtils.isEmpty(email.getText().toString()))
        {
            listener.setEmailError(activity.getString(R.string.requiervalue));
        }
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email.getText().toString()).matches()) {
            listener.setEmailError(activity.getString(R.string.error_invalid_email_format));
        }
        else if (TextUtils.isEmpty(name.getText().toString()))
        {
            listener.setNameError(activity.getString(R.string.requiervalue));
        }
        else if (TextUtils.isEmpty(password1.getText().toString()))
        {
            listener.setPasswordError(activity.getString(R.string.requiervalue));
        }
        else if (!password1.getText().toString().equals(password2.getText().toString())) {
            listener.setPasswordConfirmError(activity.getString(R.string.error_noequals_passwords));
        }
        else {
            listener.successfulCreateAccount();
        }
    }
}
