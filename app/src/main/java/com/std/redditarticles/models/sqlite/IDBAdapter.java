package com.std.redditarticles.models.sqlite;

import com.std.redditarticles.models.User;

public interface IDBAdapter {

    IDBAdapter open();

    void close();

    void deleteAllUsers();

    void addUser(User user);

    int getUsersCount();

    User getUserByLogin(String login);

}
