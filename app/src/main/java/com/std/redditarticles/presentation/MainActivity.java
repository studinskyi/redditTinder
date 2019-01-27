package com.std.redditarticles.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.std.redditarticles.R;
import com.std.redditarticles.presentation.presenters.ManagerUsers;

public class MainActivity extends AppCompatActivity {

    Button btnOpenNews;
    EditText etRedditURL;
    Context context;
    private Menu mMenuMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;

        btnOpenNews = (Button) findViewById(R.id.btnOpenNews);
        etRedditURL = (EditText) findViewById(R.id.redditURL);
        etRedditURL.setText("https://www.reddit.com/r/aww.json");

        // скрытие видимости кнопки ТЕСТОВОГО открытия общего экрана статей реддит
        btnOpenNews.setVisibility(View.INVISIBLE);

        initUsersInDB();
    }

    public void clickOpenNews(View view) {
        //String feedUrl = etRedditURL.getText().toString();
        Intent intentArticlesList = new Intent(context, ArticlesActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        //intentArticlesList.putExtra(PUT_URL_REDDIT_RESOURCE, feedUrl);
        startActivity(intentArticlesList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.mMenuMain = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_activity, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miLoginApp:
                Intent intentLogin = new Intent(getBaseContext(), LoginActivity.class).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intentLogin);
                return true;
            case R.id.miLogoutApp:
                ManagerUsers.getInstance(context).setUserCurrent(null);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initUsersInDB() {
        // ДЛЯ ОТЛАДКИ - заполнения базы данных пользователей для тестирования
        //ManagerUsers.getInstance(context).deleteAllUsers();
        if (ManagerUsers.getInstance(context).getUsersCount() < 4) {
            //if (ManagerUsers.getInstance(context).getUsers().size() < 4) {
            ManagerUsers.getInstance(context).addUser("admin", "password", true, "firstName11", "surname11", "secondName11", "email11@gmail.com", "phone11");
            ManagerUsers.getInstance(context).addUser("login", "password", true, "firstName22", "surname22", "secondName22", "email22@gmail.com", "phone22");
            ManagerUsers.getInstance(context).addUser("user", "password", false, "firstName33", "surname33", "secondName33", "email33@gmail.com", "phone33");
        }
    }

}
