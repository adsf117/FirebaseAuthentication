package com.puzzlebench.loginfirebase.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.puzzlebench.loginfirebase.ui.view.LoginBaseView;

/**
 * Created  on 22/09/16.
 */
public class LoginBaseActivity extends AppCompatActivity implements LoginBaseView {

    @Override
    public Activity getViewActicity() {
        return this;
    }
}
