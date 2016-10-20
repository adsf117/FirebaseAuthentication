package com.puzzlebench.loginfirebase.mvp.presenter;

import android.widget.EditText;

import com.puzzlebench.loginfirebase.interactor.CreateAccountInteractor;
import com.puzzlebench.loginfirebase.interactor.CreateAccountInteractorImp;
import com.puzzlebench.loginfirebase.mvp.view.CreateAccountView;

/**
 * Created by andresdavid on 20/10/16.
 */
public class CreateAccountPresenterImp implements CreateAccountPresenter, CreateAccountInteractor.OnCreateAccountInteractorListener {


    CreateAccountView view;
    CreateAccountInteractorImp interactor;

    public CreateAccountPresenterImp(CreateAccountView view) {
        this.view = view;
        this.interactor = new CreateAccountInteractorImp(view.getActivity(),this);
    }


    @Override
    public void validateFrom(EditText email, EditText name, EditText password1, EditText password2) {
        interactor.validateFrom(email,name,password1,password2);
    }


    @Override
    public void setEmailError(String error) {
        if(view!=null)
            view.setEmailError(error);

    }
    @Override
    public void setNameError(String error) {
        if(view!=null)
            view.setNameError(error);
    }

    @Override
    public void setPasswordError(String error) {
        if(view!=null)
            view.setPasswordError(error);
    }

    @Override
    public void setPasswordConfirmError(String error) {
        if(view!=null)
            view.setPasswordConfirmError(error);
    }

    @Override
    public void successfulCreateAccount() {
        if(view!=null)
            view.successfulCreateAccount();
    }

    @Override
    public void failCreateAccount(String error) {
        if(view!=null)
            view.failCreateAccount(error);
    }

}
