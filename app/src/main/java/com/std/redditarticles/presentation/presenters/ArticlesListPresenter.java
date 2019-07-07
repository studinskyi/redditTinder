package com.std.redditarticles.presentation.presenters;

import android.content.Context;
import android.support.annotation.NonNull;

import com.std.redditarticles.loaders.ReadArticlesAsyncTask;
import com.std.redditarticles.models.RedditItem;
import com.std.redditarticles.presentation.ArticlesActivity;
import com.std.redditarticles.presentation.views.IArticlesView;

import java.util.ArrayList;

public class ArticlesListPresenter {

    private final IArticlesView mIArticlesView;
    private final Context mContext;
    private ReadArticlesAsyncTask mTask;
    private static ArrayList<RedditItem> listNews = new ArrayList<>();

    public ArticlesListPresenter(@NonNull IArticlesView articlesView,
                                 @NonNull Context context,
                                 @NonNull ReadArticlesAsyncTask task) {
        mIArticlesView = articlesView;
        mContext = context;
        mTask = task;
    }

    public void loadRedditArticles() {
        mIArticlesView.loadRedditArticlesAsyncTask();
    }

    public int startAsyncTask_LoadArticles() {
        //mTask = new ReadArticlesAsyncTask(mContext);
        mTask.setContext(mContext);
        mTask.execute(RedditItem.Companion.getURL_REDDIT_ARTICLES());
        return 0;
    }

    public static ArrayList<RedditItem> getListNews() {
        return listNews;
    }

    public static void setListNews(ArrayList<RedditItem> listNews) {
        ArticlesListPresenter.listNews = listNews;
        //ArticlesActivity.setListNews(listNews);
        ArticlesActivity.getListArticles().clear();
        ArticlesActivity.getListArticles().addAll(listNews);
        ArticlesActivity.getArticleAdapterRV().notifyDataSetChanged();
    }

    public void setTask(ReadArticlesAsyncTask mTask) {
        this.mTask = mTask;
    }
}