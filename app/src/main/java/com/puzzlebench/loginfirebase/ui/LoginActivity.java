package com.puzzlebench.loginfirebase.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.puzzlebench.loginfirebase.R;
import com.puzzlebench.loginfirebase.mvp.presenter.LoginPresenter;
import com.puzzlebench.loginfirebase.mvp.presenter.LoginPresenterImpl;
import com.puzzlebench.loginfirebase.mvp.view.LoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends LoginBaseActivity implements LoginView {

    @BindView(R.id.login_progress)
    ProgressBar mLogin_progress;

    @BindView(R.id.email_loginEditText)
    EditText email_loginEditText;
    @BindView(R.id.email_login_TextInputLayout)
    TextInputLayout email_login_TextInputLayout;

    @BindView(R.id.password_login_TextView)
    EditText password_login_TextView;
    @BindView(R.id.password_login_TextInputLayout)
    TextInputLayout password_login_TextInputLayout;

    @BindView(R.id.login_email_form)
    LinearLayout login_email_form;

    LoginPresenter loginPresenter;


    @BindView(R.id.facebook_login_button)
    LoginButton facebook_login_button;

    CallbackManager callbackManager;


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode,resultCode,data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        loginPresenter = new LoginPresenterImpl(this);

        callbackManager = CallbackManager.Factory.create();
        //LoginButton facebook_login_button = (LoginButton) findViewById(R.id.facebook_login_button);
        facebook_login_button.setReadPermissions("email", "public_profile");
        facebook_login_button.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                loginPresenter.handleFacebookAccessToken(loginResult.getAccessToken());
                Toast.makeText(getApplicationContext(),"onSuccess facebook_login",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(),"onCancel facebook_login",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(),"onError facebook_login"+error.getMessage(),Toast.LENGTH_LONG).show();
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (loginPresenter!=null)
        {
            loginPresenter.removeAuthStateListener();
        }
    }


    @OnClick(R.id.email_sign_in_button)
    public void login() {
        email_login_TextInputLayout.setError(null);
        password_login_TextInputLayout.setError(null);

        loginPresenter.validarCampos(email_loginEditText.getText().toString(), password_login_TextView.getText().toString());

    }

    @OnClick(R.id.restore_password)
    public void showFormRestorePassword() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setTitle("Restablecer Contraseña");
        alertDialog.setMessage("Ingrese el email");

        final EditText input = new EditText(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        input.setLayoutParams(lp);
        alertDialog.setView(input);
        alertDialog.setPositiveButton("Enviar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        loginPresenter.resetPassword(input.getText().toString());
                    }
                });

        alertDialog.setNegativeButton("Cancelar",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        alertDialog.show();
    }




@Override
public void setEmailError(String error){
        email_login_TextInputLayout.setError(error);
        email_loginEditText.requestFocus();
        }

@Override
public void setPasswordError(String error){
        password_login_TextInputLayout.setError(error);
        password_login_TextView.requestFocus();
        }

@Override
public void faildRestorePassword(){
        mLogin_progress.setVisibility(View.GONE);
        login_email_form.setVisibility(View.VISIBLE);
        }

@Override
public void succefulRestorePassword(){
        mLogin_progress.setVisibility(View.GONE);
        login_email_form.setVisibility(View.VISIBLE);
        }

@Override
public void faildLogin(){
        mLogin_progress.setVisibility(View.GONE);
        login_email_form.setVisibility(View.VISIBLE);
        }

@Override
public void succefulLogin(){
        Toast.makeText(this,"succefulLogin",Toast.LENGTH_LONG).show();
        }

@Override
public void noInternetConection(){
        mLogin_progress.setVisibility(View.GONE);
        login_email_form.setVisibility(View.VISIBLE);
        }

@Override
public void showLoginForm(){
        mLogin_progress.setVisibility(View.VISIBLE);
        login_email_form.setVisibility(View.GONE);
        }

public void createAccount(View v){
        startActivity(new Intent(LoginActivity.this,CreateAccount.class));
        }
        }

