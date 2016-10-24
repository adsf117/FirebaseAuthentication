package com.puzzlebench.loginfirebase.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.util.Log;
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
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.GoogleAuthProvider;
import com.puzzlebench.loginfirebase.R;
import com.puzzlebench.loginfirebase.mvp.presenter.LoginPresenter;
import com.puzzlebench.loginfirebase.mvp.presenter.LoginPresenterImpl;
import com.puzzlebench.loginfirebase.mvp.view.LoginView;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A login screen that offers login via email/password.
 */
public class LoginActivity extends LoginBaseActivity implements
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener, LoginView {

    private static final String TAG = "SignInActivity";

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
    private static final int RC_SIGN_IN = 9001;
    private GoogleApiClient mGoogleApiClient;

    private TwitterLoginButton twitterLoginButton;


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
                loginPresenter.handlefirebaseAuthWithFacebook(loginResult.getAccessToken());
                Toast.makeText(getApplicationContext(), "onSuccess facebook_login", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), "onCancel facebook_login", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), "onError facebook_login" + error.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        SignInButton signInButton = (SignInButton) findViewById(R.id.google_login_button);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setScopes(gso.getScopeArray());
        signInButton.setOnClickListener(this);

        twitterLoginButton = (TwitterLoginButton) findViewById(R.id.twitter_login_button);
        twitterLoginButton.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                // The TwitterSession is also available through:
                // Twitter.getInstance().core.getSessionManager().getActiveSession()
                TwitterSession session = result.data;
                loginPresenter.handlefirebaseAuthWithTwitter(session.getAuthToken().token,session.getAuthToken().secret);
            }
            @Override
            public void failure(TwitterException exception) {
                Log.d("TwitterKit", "Login with Twitter failure", exception);
            }
        });


    }

    @Override
    protected void onStop() {
        super.onStop();
        if (loginPresenter != null) {
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.google_login_button:
                signIn();
                break;
        }
    }

    // [START signIn]
    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    // [END sign

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {

                loginPresenter.handlefirebaseAuthWithGoogle(result.getSignInAccount());
            } else {

                Log.d(TAG, "handleSignInResult:" + result.isSuccess());
            }
        }
        else{
            twitterLoginButton.onActivityResult(requestCode, resultCode, data);
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void setEmailError(String error) {
        email_login_TextInputLayout.setError(error);
        email_loginEditText.requestFocus();
    }

    @Override
    public void setPasswordError(String error) {
        password_login_TextInputLayout.setError(error);
        password_login_TextView.requestFocus();
    }

    @Override
    public void faildRestorePassword() {
        mLogin_progress.setVisibility(View.GONE);
        login_email_form.setVisibility(View.VISIBLE);
    }

    @Override
    public void succefulRestorePassword() {
        mLogin_progress.setVisibility(View.GONE);
        login_email_form.setVisibility(View.VISIBLE);
    }

    @Override
    public void faildLogin() {
        mLogin_progress.setVisibility(View.GONE);
        login_email_form.setVisibility(View.VISIBLE);
    }

    @Override
    public void succefulLogin() {
        Toast.makeText(this, "succefulLogin", Toast.LENGTH_LONG).show();
    }

    @Override
    public void noInternetConection() {
        mLogin_progress.setVisibility(View.GONE);
        login_email_form.setVisibility(View.VISIBLE);
    }

    @Override
    public void showLoginForm() {
        mLogin_progress.setVisibility(View.VISIBLE);
        login_email_form.setVisibility(View.GONE);
    }

    public void createAccount(View v) {
        startActivity(new Intent(LoginActivity.this, CreateAccount.class));
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}

