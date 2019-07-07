package com.std.redditarticles.loaders;

import android.content.Context;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.std.redditarticles.models.RedditItem;
import com.std.redditarticles.presentation.presenters.ArticlesListPresenter;
import com.std.redditarticles.utils.UtilsReddit;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class ReadArticlesAsyncTask extends AsyncTask<String, Integer, ArrayList<RedditItem>> {

    private String mUrlReddit; // для тестирования etRedditURL.setText("https://www.reddit.com/r/aww.json");
    private Context mContext;
    private String mMsgError = "";

    public ReadArticlesAsyncTask(Context context) {
        mContext = context;
    }

    @Override
    protected ArrayList<RedditItem> doInBackground(String... params) {
        mUrlReddit = params[0];
        ArrayList<RedditItem> redditItems = new ArrayList<>();

        try {
            URL url = null;
            try {
                url = new URL(mUrlReddit);
            } catch (MalformedURLException e) {
                mMsgError = "Ошибка формирования URL объекта - ReadArticlesAsyncTask ; " + e.getMessage();
                Log.d(UtilsReddit.logTAG, mMsgError);
                UtilsReddit.showToast(mContext, mMsgError);
            }

            HttpURLConnection httpConnect = null;
            try {
                httpConnect = (HttpURLConnection) url.openConnection();
                httpConnect.setRequestMethod("GET");
            } catch (IOException e) {
                mMsgError = "Ошибка открытия соединения с Reddit сервером - ReadArticlesAsyncTask ; " + e.getMessage();
                Log.d(UtilsReddit.logTAG, mMsgError);
                UtilsReddit.showToast(mContext, mMsgError);
            }

            if (httpConnect.getResponseCode() == HttpURLConnection.HTTP_OK) {

                String jsonText = readContentsFromHttp(httpConnect);
                Log.i("json_reddit_data: ", jsonText);

                try {
                    JSONObject data = new JSONObject(jsonText).getJSONObject("data");
                    JSONArray jsonArray = data.getJSONArray("children");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject currentObject = jsonArray.getJSONObject(i).getJSONObject("data");
                        String imgUrl = ((JSONObject) currentObject.getJSONObject("preview").
                                getJSONArray("images").get(0)).
                                getJSONObject("source").
                                get(RedditItem.Companion.getURL_ARTICLE()).toString();

                        RedditItem newRssArticle = new RedditItem(
                                currentObject.getString("id"),
                                currentObject.getString(RedditItem.Companion.getTITLE_ARTICLE()),
                                getDateCreatedNews(currentObject, TimeUnit.SECONDS),
                                RedditItem.Companion.getBASE_URL() + currentObject.getString(RedditItem.Companion.getPARMA_LINK()),
                                imgUrl,
                                currentObject.getString(RedditItem.Companion.getAUTHOR_ARTICLE()),
                                i,
                                false,
                                false);

                        // добавлять статья в список отображения если url изображения не пустой
                        if (!TextUtils.isEmpty(newRssArticle.getImg())) {
                            redditItems.add(newRssArticle);
                        }

                        //                        // ограничение загрузки статей до 3 (для тестовых прогонов при разработке)
                        //                        if (redditItems.size() >= 5) {
                        //                            break;
                        //                        }
                    }
                } catch (JSONException e) {
                    mMsgError = "Ошибка парсинга json-файла Reddit - ReadArticlesAsyncTask ; " + e.getMessage();
                    Log.d(UtilsReddit.logTAG, mMsgError);
                    UtilsReddit.showToast(mContext, mMsgError);
                }
            }
        } catch (IOException e) {
            mMsgError = "Ошибка IOException при работе с Reddit сервером - ReadArticlesAsyncTask ; " + e.getMessage();
            Log.d(UtilsReddit.logTAG, mMsgError);
            UtilsReddit.showToast(mContext, mMsgError);
        }

        return redditItems;
    }

    public static String readContentsFromHttp(HttpURLConnection httpConnect) {
        StringBuffer sb = null;
        if (httpConnect == null) {
            return null;
        }

        try {
            sb = new StringBuffer(8192);
            String tmp = "";
            BufferedReader br = new BufferedReader(
                    new InputStreamReader(httpConnect.getInputStream())
            );
            while ((tmp = br.readLine()) != null) {
                sb.append(tmp).append("\n");
            }
            br.close();

        } catch (IOException e) {
            e.printStackTrace();

        }

        return sb.toString();
    }

    @Override
    protected void onPostExecute(ArrayList<RedditItem> habrItems) {
        super.onPostExecute(habrItems);
        // возвращение результирующего списка в адаптер списка статей реддит
        ArticlesListPresenter.setListNews(habrItems);
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        if (mContext != null) {
            UtilsReddit.showToast(mContext, "load articles: " + values[0]);
        }
    }

    public String getMsgError() {
        return mMsgError;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    @Nullable
    public static Date getDateCreatedNews(JSONObject currentObject, TimeUnit sourceUnit) {
        Date value = null;
        String msgError = "";
        try {
            long created = currentObject.getLong(RedditItem.Companion.getCREATED());
            if (created > 0L) {
                created = TimeUnit.MILLISECONDS.convert(created, sourceUnit);
                value = new Date(created);
            }
        } catch (IllegalStateException e) {
            msgError = "Ошибка преобразования секунд из long в дату Date ; " + e.getMessage();
            Log.d(UtilsReddit.logTAG, msgError);
        } catch (JSONException e) {
            msgError = "Ошибка парсинга времен создания новости в виде long из json-файла ; " + e.getMessage();
            Log.d(UtilsReddit.logTAG, msgError);
        }
        return value;
    }
}