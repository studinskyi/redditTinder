package com.std.redditarticles.models.sqlite;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "toursdb";
    private static int DATABASE_VERSION = 1; // версия базы данных (наращивать модификации таблиц)
    private static final String NAME_TABLE_user = "user"; // название таблицы пользователей
    private static final String KEY_ID = "id";

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + NAME_TABLE_user + " (id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "login TEXT, " +
                "password TEXT , " +
                "isAdministrator NUMERIC, " +
                "firstName TEXT, " +
                "surname TEXT, " +
                "secondName TEXT," +
                " email TEXT, " +
                "phone TEXT);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + NAME_TABLE_user);
        onCreate(db);
    }

    public static int getDatabaseVersion() {
        return DATABASE_VERSION;
    }

    public static void setDatabaseVersion(int databaseVersion) {
        DATABASE_VERSION = databaseVersion;
    }

    public static String getNAME_TABLE_user() {
        return NAME_TABLE_user;
    }
}
