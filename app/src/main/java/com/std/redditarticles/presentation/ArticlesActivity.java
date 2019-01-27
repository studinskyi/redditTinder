package com.std.redditarticles.presentation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.std.redditarticles.R;
import com.std.redditarticles.adapters.ArticleAdapterRV;
import com.std.redditarticles.loaders.ReadArticlesAsyncTask;
import com.std.redditarticles.models.RedditItem;
import com.std.redditarticles.presentation.presenters.ArticlesListPresenter;
import com.std.redditarticles.presentation.views.IArticlesView;

import java.util.ArrayList;

import static com.std.redditarticles.presentation.SliderActivity.ALL_MODE;
import static com.std.redditarticles.presentation.SliderActivity.DISLIKED_MODE;
import static com.std.redditarticles.presentation.SliderActivity.LIKED_MODE;
import static com.std.redditarticles.presentation.SliderActivity.SWYPE_MODE;

public class ArticlesActivity extends AppCompatActivity implements IArticlesView {

    public static final String PUT_URL_REDDIT_RESOURCE = "PUT_URL_REDDIT_RESOURCE";
    private RecyclerView rvArticlesList;
    private static ArticleAdapterRV mArticleAdapterRV;
    private static ArrayList<RedditItem> mListArticles = new ArrayList<>();
    private static String loginCurrent; // логин текущего активного пользователя
    private LinearLayoutManager mLinLayoutManager;

    private Context mContext;
    private Menu mMenuArticles;
    private int mCurrentPosition = 0;

    private ArticlesListPresenter articlesPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.articles_activity);
        mContext = this;

        rvArticlesList = (RecyclerView) findViewById(R.id.rvArticlesList);
        rvArticlesList.setHasFixedSize(true);
        mLinLayoutManager = new LinearLayoutManager(this);
        rvArticlesList.setLayoutManager(mLinLayoutManager);

        mArticleAdapterRV = new ArticleAdapterRV(mListArticles);
        rvArticlesList.setAdapter(mArticleAdapterRV);


        // определение объекта асинхронного выполнения
        ReadArticlesAsyncTask task = (ReadArticlesAsyncTask) getLastNonConfigurationInstance();
        if (task == null) {
            task = new ReadArticlesAsyncTask(this);
        } else {
            task.setContext(this);
        }
        // объявление и инициализация презентера
        articlesPresenter = new ArticlesListPresenter(this, mContext, task);
        articlesPresenter.loadRedditArticles();

        // определение текущей позиции отображаемой статьи при скролле
        rvArticlesList.setOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                mCurrentPosition = mLinLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mArticleAdapterRV.notifyDataSetChanged();
    }

    @Override
    public void loadRedditArticlesAsyncTask() {
        articlesPresenter.startAsyncTask_LoadArticles();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenuArticles = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main_list, mMenuArticles);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentSlider;
        switch (item.getItemId()) {
            case R.id.miSwypeImages:
                //выбран пункт - Swype (в списочном меню)
                openIntentSlyderActivity();
                return true;
            case R.id.miSwypePhoto:
                //выбран пункт - Swype (по элменту меню в виде иконки в апп баре)
                openIntentSlyderActivity();
                return true;
            case R.id.miArticleOpenSite:
                // выбран пункт - Liked images
                openSiteInBrouser();
                return true;
            case R.id.miLikeListImages:
                intentSlider = SliderActivity.newIntent(mContext, getCurrentPosition(), LIKED_MODE);
                startActivity(intentSlider);
                return true;
            case R.id.miDislikedListImages:
                intentSlider = SliderActivity.newIntent(mContext, getCurrentPosition(), DISLIKED_MODE);
                startActivity(intentSlider);
                return true;
            case R.id.miShowAllImages:
                intentSlider = SliderActivity.newIntent(mContext, getCurrentPosition(), ALL_MODE);
                startActivity(intentSlider);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Метод открытия интента оценки изображений через свайп {@link SliderActivity}
     */
    public void openIntentSlyderActivity() {
        Intent intentSlider = SliderActivity.newIntent(mContext, getCurrentPosition(), SWYPE_MODE);
        startActivity(intentSlider);
    }

    /**
     * Метод открытия интента по ссылке на статью реддит в браузере
     */
    public void openSiteInBrouser() {
        // открытие из контекстного меню страницы статьи по ссылке реддит
        String choiceURL = mListArticles.get(getCurrentPosition()).getLink();
        Intent intentURL = new Intent(Intent.ACTION_VIEW, Uri.parse(choiceURL));
        startActivity(intentURL);
    }

    public static ArrayList<RedditItem> getListArticles() {
        return mListArticles;
    }

    public static ArticleAdapterRV getArticleAdapterRV() {
        return mArticleAdapterRV;
    }

    public ArticlesListPresenter getArticlesPresenter() {
        return articlesPresenter;
    }

    public void setArticlesPresenter(ArticlesListPresenter articlesPresenter) {
        this.articlesPresenter = articlesPresenter;
    }

    public int getCurrentPosition() {
        return (mCurrentPosition <= 0 || mCurrentPosition >= mListArticles.size())
                ? 0 : mCurrentPosition;
    }
}
