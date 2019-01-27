package com.std.redditarticles.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.std.redditarticles.R;
import com.std.redditarticles.presentation.presenters.LoginPresenter;
import com.std.redditarticles.presentation.views.ILoginView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity implements ILoginView {

    @BindView(R.id.btnOk)
    Button btnOk;
    @BindView(R.id.btnRegistration)
    Button btnRegistration;
    @BindView(R.id.editLogin)
    EditText etLogin;
    @BindView(R.id.editPassword)
    EditText etPassword;

    @BindView(R.id.tiLoginInputLayout)
    TextInputLayout tiLoginInputLayout;
    @BindView(R.id.tiPasswordInputLayout)
    TextInputLayout tiPasswordInputLayout;

    private LoginPresenter loginPresenter;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        ButterKnife.bind(this);
        context = this;

        loginPresenter = new LoginPresenter(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    //@SuppressWarnings("unused")
    @OnClick({R.id.btnOk, R.id.btnRegistration})
    public void onClick(View v) {
        // по id определяем кнопку, вызвавшую этот обработчик
        switch (v.getId()) {
            case R.id.btnOk:
                String loginStr = etLogin.getText().toString();
                String passwStr = etPassword.getText().toString();
                loginPresenter.tryLogIn(loginStr, passwStr);
                break;
            case R.id.btnRegistration:
                startActivity(new Intent(context, RegistrationActivity.class));
                break;
        }

    }

    @Override
    public Context getContext() {
        return context;
    }

    @Override
    public void showLoginError() {
        tiLoginInputLayout.setError(getString(R.string.errorLogin));
    }

    @Override
    public void showPasswordError() {
        tiPasswordInputLayout.setError(getString(R.string.errorPassword));
    }
}
