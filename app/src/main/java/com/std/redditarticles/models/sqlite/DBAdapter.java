package com.std.redditarticles.models.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import com.std.redditarticles.models.User;

import java.util.ArrayList;
import java.util.List;

public class DBAdapter implements IDBAdapter {

    private DBHelper dbHelper;
    private SQLiteDatabase db;

    public DBAdapter(Context context) {
        dbHelper = new DBHelper(context.getApplicationContext());
    }

    public DBAdapter open() {
        try {
            db = dbHelper.getWritableDatabase();
        } catch (SQLiteException ex) {
            db = dbHelper.getReadableDatabase();
        }
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    private Cursor getAllEntries(String nameTableDB) {
        return db.query(nameTableDB, null, null, null, null, null, null);
    }

    public List<User> getUsers() {
        List<User> users = new ArrayList<>();
        Cursor cursor = getAllEntries(DBHelper.getNAME_TABLE_user());
        User user = null;
        if (cursor.moveToFirst()) {
            do {
                user = new User(cursor.getLong(cursor.getColumnIndex("id")),
                        cursor.getString(cursor.getColumnIndex("login")), cursor.getString(cursor.getColumnIndex("password")),
                        Boolean.valueOf(cursor.getString(cursor.getColumnIndex("isAdministrator"))),
                        cursor.getString(cursor.getColumnIndex("firstName")), cursor.getString(cursor.getColumnIndex("surname")),
                        cursor.getString(cursor.getColumnIndex("secondName")), cursor.getString(cursor.getColumnIndex("email")),
                        cursor.getString(cursor.getColumnIndex("phone")));

                users.add(user);
            }
            while (cursor.moveToNext());
        }
        cursor.close();
        return users;
    }

    @Override
    public void addUser(User user) {
        ContentValues values = new ContentValues();
        values.put("login", user.getLogin());
        values.put("password", user.getPassword());
        values.put("isAdministrator", user.isAdministrator());
        values.put("firstName", user.getFirstName());
        values.put("surname", user.getSurname());
        values.put("secondName", user.getSecondName());
        values.put("email", user.getEmail());
        values.put("phone", user.getPhone());
        db.insert(DBHelper.getNAME_TABLE_user(), null, values);
    }

    @Override
    public void deleteAllUsers() {
        db.delete(DBHelper.getNAME_TABLE_user(), null, null);

    }

    @Override
    public int getUsersCount() {
        String countQuery = "SELECT  * FROM " + DBHelper.getNAME_TABLE_user();
        Cursor cursor = db.rawQuery(countQuery, null);
        int countUsers = cursor.getCount();
        cursor.close();

        return countUsers;
    }

    @Override
    public User getUserByLogin(String login) {
        User user = null;
        String query = String.format("SELECT * FROM %s WHERE %s=?", DBHelper.getNAME_TABLE_user(), "login");
        Cursor cursor = db.rawQuery(query, new String[]{login});
        if (cursor.moveToFirst()) {
            user = new User(cursor.getLong(cursor.getColumnIndex("id")),
                    cursor.getString(cursor.getColumnIndex("login")), cursor.getString(cursor.getColumnIndex("password")),
                    Boolean.valueOf(cursor.getString(cursor.getColumnIndex("isAdministrator"))),
                    cursor.getString(cursor.getColumnIndex("firstName")), cursor.getString(cursor.getColumnIndex("surname")),
                    cursor.getString(cursor.getColumnIndex("secondName")), cursor.getString(cursor.getColumnIndex("email")),
                    cursor.getString(cursor.getColumnIndex("phone")));
        }
        cursor.close();
        return user;
    }
}
