package com.puzzlebench.loginfirebase.ui;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.puzzlebench.loginfirebase.R;
import com.puzzlebench.loginfirebase.models.User;
import com.puzzlebench.loginfirebase.mvp.presenter.CreateAccountPresenter;
import com.puzzlebench.loginfirebase.mvp.presenter.CreateAccountPresenterImp;
import com.puzzlebench.loginfirebase.mvp.view.CreateAccountView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CreateAccount extends CreateAccountBaseActivity implements CreateAccountView {


    @BindView(R.id.email_creatreaccount_EditText)
    EditText email_creatreaccount_EditText;
    @BindView(R.id.viewemail)
    TextInputLayout viewemailTextimput;

    @BindView(R.id.name_createaccount_EditText)
    EditText name_createaccount_EditText;
    @BindView(R.id.viewname)
    TextInputLayout viewnameTextimput;

    @BindView(R.id.password_createcount_EditText)
    EditText password_createcount_EditText;
    @BindView(R.id.viewpasswordcreatecount)
    TextInputLayout viewpasswordcreatecountTextimput;

    @BindView(R.id.password_confirm_createaccount_EditText)
    EditText password_confirm_createaccount_EditText;
    @BindView(R.id.viewpasswordconfirmcreatecount)
    TextInputLayout viewpasswordconfirmcreatecountTextimput;

    CreateAccountPresenter creatreAccountPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        ButterKnife.bind(this);
        creatreAccountPresenter = new CreateAccountPresenterImp(this);

    }

    @OnClick(R.id.registerbutton)
    public void validfields() {
        viewemailTextimput.setError(null);
        viewnameTextimput.setError(null);
        viewpasswordcreatecountTextimput.setError(null);
        viewpasswordconfirmcreatecountTextimput.setError(null);
        creatreAccountPresenter.validateFrom(email_creatreaccount_EditText, name_createaccount_EditText, password_createcount_EditText, password_confirm_createaccount_EditText);
    }


    @Override
    public void setEmailError(String error) {
        viewemailTextimput.setError(error);
        viewemailTextimput.requestFocus();

    }

    @Override
    public void setNameError(String error) {
        viewnameTextimput.setError(error);
        viewnameTextimput.requestFocus();
    }

    @Override
    public void setPasswordError(String error) {
        viewpasswordcreatecountTextimput.setError(error);
        viewpasswordcreatecountTextimput.requestFocus();
    }

    @Override
    public void setPasswordConfirmError(String error) {
        password_confirm_createaccount_EditText.setText("");
        password_createcount_EditText.setText("");
        viewpasswordconfirmcreatecountTextimput.setError(error);
        viewpasswordcreatecountTextimput.requestFocus();
    }

    @Override
    public void successfulCreateAccount() {

        Toast.makeText(this, "successfulCreateAccount", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void failCreateAccount(String error) {

        Toast.makeText(this, "FailCreateAccount", Toast.LENGTH_SHORT).show();
    }
}
