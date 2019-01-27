package com.std.redditarticles.presentation.presenters;

import android.content.Context;

import com.std.redditarticles.models.User;
import com.std.redditarticles.models.sqlite.ManagerDB;
import com.std.redditarticles.utils.UtilsReddit;

import java.util.List;

public class ManagerUsers {

    private User userCurrent; // текущий активный пользователь
    private Context context;

    public ManagerUsers() {
        userCurrent = null;
    }

    private static class ManagerUsersHolder {
        private final static ManagerUsers instance = new ManagerUsers();
    }

    public static ManagerUsers getInstance(Context context) {
        ManagerUsers instance = ManagerUsersHolder.instance;
        if (instance != null)
            if (instance.context == null)
                instance.context = context;

        return instance;
    }

    public boolean isUserAdministrator() {
        if (userCurrent != null)
            return userCurrent.isAdministrator();

        return false;
    }

    public User getUserCurrent() {
        return userCurrent;
    }

    public void setUserCurrent(User userCurrent) {
        this.userCurrent = userCurrent;
    }

    public void addUser(String login, String password, Boolean isAdministrator
            , String firstName, String surname, String secondName
            , String email, String phone) {

        ManagerDB.addUser(new User(login, password, isAdministrator
                , firstName, surname, secondName, email, phone), context);

        UtilsReddit.showToast(context, "зарегистрирован новый пользователь login: " + login + " password: " + password);
    }

    public List<User> getUsers() {
        List<User> users = ManagerDB.getUsers(context);
        return users;
    }

    public void deleteAllUsers() {
        ManagerDB.deleteAllUsers(context);
    }

    public int getUsersCount() {
        return ManagerDB.getUsersCount(context);
    }

    public User getUserByLogin(String login) {
        return ManagerDB.getUserByLogin(login, context);
    }

}