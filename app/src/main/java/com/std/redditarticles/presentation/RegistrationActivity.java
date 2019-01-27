package com.std.redditarticles.presentation;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.std.redditarticles.R;
import com.std.redditarticles.presentation.presenters.ManagerUsers;
import com.std.redditarticles.utils.UtilsReddit;

public class RegistrationActivity extends AppCompatActivity implements View.OnClickListener {

    private Button btnConfirmRegistration;
    private EditText etLoginReg;
    private EditText etPasswordReg;
    private EditText etConfirmReg;
    private EditText etFirstNameRegistrationStudent;
    private EditText etSurnameRegistrationStudent;
    private EditText etSecondNameRegistrationStudent;

    private CheckBox chbAdministrator;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration_activity);
        context = this;
        etLoginReg = (EditText) findViewById(R.id.editLoginReg);
        etPasswordReg = (EditText) findViewById(R.id.editPasswordReg);
        etConfirmReg = (EditText) findViewById(R.id.editConfirmReg);
        etFirstNameRegistrationStudent = (EditText) findViewById(R.id.etFirstNameRegistration);
        etSurnameRegistrationStudent = (EditText) findViewById(R.id.etSurnameRegistration);
        etSecondNameRegistrationStudent = (EditText) findViewById(R.id.etSecondNameRegistration);

        chbAdministrator = (CheckBox) findViewById(R.id.chbAdministrator);
        chbAdministrator.setChecked(false);

        btnConfirmRegistration = (Button) findViewById(R.id.btnConfirmRegistration);
        btnConfirmRegistration.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        // по id определяем кнопку, вызвавшую этот обработчик
        switch (v.getId()) {
            case R.id.btnConfirmRegistration:
                String loginStr = etLoginReg.getText().toString();
                String passwStr = etPasswordReg.getText().toString();
                String confirmPasswStr = etConfirmReg.getText().toString();
                if (loginStr.length() < 6) {
                    UtilsReddit.showToast(context, "Логин не может быть менее 6 символов!");
                    break;
                }
                if (ManagerUsers.getInstance(context).getUserByLogin(loginStr) != null) {
                    UtilsReddit.showToast(context, "Пользователь с таким логином уже существует!");
                    break;
                }
                if (passwStr.length() < 10) {
                    UtilsReddit.showToast(context, "Пароль не может быть менее 10 символов!");
                    break;
                }
                if (!passwStr.equals(confirmPasswStr)) {
                    UtilsReddit.showToast(context, "Пароль и его подтверждение не совпадают!");
                    break;
                }
                Log.d(UtilsReddit.logTAG, "запуск регистрации по кнопке Registration confirm, "
                        + "для пользователя login: " + loginStr + " password: " + confirmPasswStr);

                startActivity(new Intent(context, LoginActivity.class));
                break;
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void setVisibilityOfElements() {
        if (chbAdministrator.isChecked()) {
            etFirstNameRegistrationStudent.setEnabled(false);
            etSurnameRegistrationStudent.setEnabled(false);
            etSecondNameRegistrationStudent.setEnabled(false);
            //etLoginReg.setVisibility(View.INVISIBLE);
        } else {
            etFirstNameRegistrationStudent.setEnabled(true);
            etSurnameRegistrationStudent.setEnabled(true);
            etSecondNameRegistrationStudent.setEnabled(true);
            //etLoginReg.setVisibility(View.INVISIBLE);
        }
    }

    public void onClick_chbAdministrator(View view) {
        setVisibilityOfElements();
    }
}
