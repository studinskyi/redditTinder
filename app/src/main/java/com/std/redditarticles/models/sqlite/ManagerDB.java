package com.std.redditarticles.models.sqlite;

import android.content.Context;

import com.std.redditarticles.models.User;

import java.util.List;

public class ManagerDB {

    // добавление пользователя в базу данных
    public static void addUser(User user, Context context) {
        DBAdapter adapter = new DBAdapter(context);
        adapter.open();
        adapter.addUser(user);
        adapter.close();
    }

    // запрос списка пользователей из базы данных
    public static List<User> getUsers(Context context) {
        DBAdapter adapter = new DBAdapter(context);
        adapter.open();
        List<User> users = adapter.getUsers();
        adapter.close();
        return users;
    }

    // запрос на удаление всех пользователей из базы данных
    public static void deleteAllUsers(Context context) {
        DBAdapter adapter = new DBAdapter(context);
        adapter.open();
        adapter.deleteAllUsers();
        adapter.close();
    }

    public static int getUsersCount(Context context) {
        DBAdapter adapter = new DBAdapter(context);
        adapter.open();
        int countUsers = adapter.getUsersCount();
        adapter.close();

        return countUsers;
    }

    public static User getUserByLogin(String login, Context context) {
        User user = null;
        DBAdapter adapter = new DBAdapter(context);
        adapter.open();
        user = adapter.getUserByLogin(login);
        adapter.close();
        return user;
    }

}
