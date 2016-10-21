package com.puzzlebench.loginfirebase.interactor;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.EditText;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.puzzlebench.loginfirebase.Globals;
import com.puzzlebench.loginfirebase.R;
import com.puzzlebench.loginfirebase.UtilsValidations;
import com.puzzlebench.loginfirebase.models.User;

/**
 * Created by andresdavid on 20/10/16.
 */
public class CreateAccountInteractorImp implements CreateAccountInteractor {

    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef_Usuario = database.getReference(Globals.REF_USER);
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();

    private CreateAccountInteractor.OnCreateAccountInteractorListener listener;
    private  Activity activity;

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
        else if (password1.getText().toString().length()< UtilsValidations.MAXLENGTHPASSWORD)
        {
            listener.setPasswordError(String.format(activity.getString(R.string.error_password_length),UtilsValidations.MAXLENGTHPASSWORD));
        }
        else if (!password1.getText().toString().equals(password2.getText().toString())) {
            listener.setPasswordConfirmError(activity.getString(R.string.error_noequals_passwords));
        }
        else {
            User user = new User();
            user.setEmail(email.getText().toString());
            user.setName(name.getText().toString());
            crearUsuario(user,password1.getText().toString());
        }
    }

    public void crearUsuario(final User user, String password) {

        firebaseAuth.createUserWithEmailAndPassword(user.getEmail(), password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //myRef_Usuario.child(task.getResult().getUser().getUid()).setValue(user);
                    listener.successfulCreateAccount();
                }
                else{
                    listener.failCreateAccount("Error Creando cueent");
                }
            }
        });
    }
}
