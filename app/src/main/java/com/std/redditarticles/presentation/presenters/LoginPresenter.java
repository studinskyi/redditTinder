package com.std.redditarticles.presentation.presenters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.std.redditarticles.models.User;
import com.std.redditarticles.presentation.ArticlesActivity;
import com.std.redditarticles.presentation.views.ILoginView;
import com.std.redditarticles.utils.UtilsReddit;

public class LoginPresenter {

    private final ILoginView mILoginView;
    private final Context context;


    public LoginPresenter(@NonNull ILoginView ILoginView) {
        mILoginView = ILoginView;
        context = mILoginView.getContext();
    }

    public void tryLogIn(@NonNull String login, @NonNull String password) {
        if (TextUtils.isEmpty(login)) {
            mILoginView.showLoginError();
        } else if (TextUtils.isEmpty(password)) {
            mILoginView.showPasswordError();
        } else {
            // проверка логина и пароля, открытие доступа к скрытым страницам
            UtilsReddit.showToast(context, "проверка возможности логина пользователя login: " + login + " password: " + password);
            login(login, password);
        }
    }

    public User login(@NonNull String loginStr, @NonNull String passwStr) {
        User userFindByLogin = ManagerUsers.getInstance(context).getUserByLogin(loginStr);
        if (userFindByLogin != null) {
            if (userFindByLogin.getPassword().equals(passwStr)) {
                // установка текущей информации об активном пользователе
                ManagerUsers.getInstance(context).setUserCurrent(userFindByLogin);

                // запуск экрана со списком статей реддит
                Intent intentArticlesList = new Intent(context, ArticlesActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intentArticlesList);

                Log.d(UtilsReddit.logTAG, "Удачный вход login: " + loginStr + " password: " + passwStr);
                UtilsReddit.showToast(context, "Удачный вход login: " + loginStr + " password: " + passwStr);
            } else {
                // сброс всей информаци об активном пользователе (по причине неверного ввода пароля и логина)
                userFindByLogin = null; //
                ManagerUsers.getInstance(context).setUserCurrent(null);
                Log.d(UtilsReddit.logTAG, "Введен неверный пароль: " + loginStr + " password: " + passwStr);
                UtilsReddit.showToast(context, "Введен неверный пароль: " + loginStr + " password: " + passwStr);
            }
        } else {
            Log.d(UtilsReddit.logTAG, "Неудачная попытка входа под логином: " + loginStr + " (пользователя с таким логином не существует)");
            UtilsReddit.showToast(context, "Пользователь с логином " + loginStr + " не зарегистрирован!");
        }

        return userFindByLogin;
    }
}
