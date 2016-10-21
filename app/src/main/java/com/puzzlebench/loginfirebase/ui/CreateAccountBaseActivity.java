package com.puzzlebench.loginfirebase.ui;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;

import com.puzzlebench.loginfirebase.ui.view.CreateAccountBaseView;


/**
 * Created by andresdavid on 20/10/16.
 */
public class CreateAccountBaseActivity extends AppCompatActivity implements CreateAccountBaseView {


    @Override
    public Activity getActivity() {
        return this;
    }
}
