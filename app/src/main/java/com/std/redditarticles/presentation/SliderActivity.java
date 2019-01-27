package com.std.redditarticles.presentation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.util.Pair;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.std.redditarticles.R;
import com.std.redditarticles.models.RedditItem;
import com.std.redditarticles.presentation.views.ImagePreviewSwypeView;
import com.std.redditarticles.utils.UtilsReddit;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SliderActivity extends AppCompatActivity {

    //private static final String PUT_URL_ALL_IMAGES = "PUT_URL_MAP_ALL_IMAGES";
    public static final String PUT_IMAGE_ADAPTER_POSITION = "PUT_IMAGE_ADAPTER_POSITION";
    public static final String PUT_SWYPE_MODE = "PUT_SWYPE_MODE";
    public static final String SWYPE_MODE = "SWYPE";
    public static final String ALL_MODE = "ALL_MODE";
    public static final String LIKED_MODE = "LIKE";
    public static final String DISLIKED_MODE = "DISLIKE";

    @BindView(R.id.imageSliderView)
    ImagePreviewSwypeView imagePreviewSwypeView;
    private String mSwypeMode;
    private Menu mMenuArticles;
    private Context mContext;

    /**
     * Оформление интента {@link SliderActivity} для запуска просмотра изображений
     */
    public static Intent newIntent(@NonNull Context context, int positionImage, String swypeMode) {
        Intent intent = new Intent(context, SliderActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra(PUT_IMAGE_ADAPTER_POSITION, (positionImage <= 0 ||
                positionImage >= ArticlesActivity.getListArticles().size())
                ? 0 : positionImage);
        intent.putExtra(PUT_SWYPE_MODE, swypeMode);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slider);
        ButterKnife.bind(this);
        mContext = this;

        //        List<String> listUrlImagesToSwype = new ArrayList<>();
        //        // получены url-ов всех изображений реддит, полученных из адаптера
        //        listUrlImagesToSwype.add("стартовое изображение");
        //        for (RedditItem redditItem : ArticlesActivity.getListArticles()) {
        //            listUrlImagesToSwype.add(redditItem.getImg());
        //        }
        //        listUrlImagesToSwype.add("финишное изображение");
        //        int positionImage = getIntent().getIntExtra(PUT_IMAGE_ADAPTER_POSITION, 0);
        int positionImage = getIntent().getIntExtra(PUT_IMAGE_ADAPTER_POSITION, 0);
        mSwypeMode = getIntent().getStringExtra(PUT_SWYPE_MODE);

        // подготовка списка ссылок на изображения для открытия их в окне просмотра
        List<Pair<Integer, String>> listUrlImagesToSwype = new ArrayList<>();
        listUrlImagesToSwype.add(new Pair<>(-1, "стартовое изображение"));
        // получение url-ов всех изображений реддит, полученных из адаптера
        for (RedditItem redditItem : ArticlesActivity.getListArticles()) {
            Pair<Integer, String> pairImage = new Pair<>(redditItem.getPositionArticle(), redditItem.getImg());
            if (mSwypeMode.equals(LIKED_MODE) && !redditItem.isLike()
                    || mSwypeMode.equals(DISLIKED_MODE) && !redditItem.isDislike()) {
                // пропуск несоответствующих для режима просмотра избражений
                continue;
            }
            listUrlImagesToSwype.add(pairImage);
            //            listUrlImagesToSwype.add(new Pair<>(redditItem.getPositionArticle(),
            //                    redditItem.getImg()));
        }
        listUrlImagesToSwype.add(new Pair<>(-2, "финишное изображение"));
        //        // получены url-ов всех изображений реддит, полученных из адаптера
        //        listUrlImagesToSwype.add(new Pair<>(-1, "стартовое изображение"));
        //        for (int i = 0; i < ArticlesActivity.getListArticles().size(); i++) {
        //            listUrlImagesToSwype.add(new Pair<>(i, ArticlesActivity.getListArticles().get(i).getImg()));
        //        }
        //        listUrlImagesToSwype.add(new Pair<>(-2, "финишное изображение"));

        if (listUrlImagesToSwype.size() == 0) {
            UtilsReddit.showToast(this, "Cписок изображений для выбранного режима пуст!"
                    + System.getProperty("line.separator") + "Экран превью не будет открыт.");
        }
        positionImage = (positionImage <= 0 || positionImage >= ArticlesActivity.getListArticles().size())
                ? 0 : positionImage;

        // открытие превью окна для свайпа или просмотра оцененных пользователем изображений
        if (UtilsReddit.getNotRatedImage(positionImage) < 0 && mSwypeMode.equals(SWYPE_MODE)) {
            UtilsReddit.showToast(this, "Все изображения оценены!"
                    + System.getProperty("line.separator") + "Открыт просмотр всех фото, без возможности оценки.");
            imagePreviewSwypeView.setImageList(listUrlImagesToSwype, positionImage + 1, ALL_MODE);
        } else {
            UtilsReddit.showToast(this, "Список изображений открыт в режиме " + mSwypeMode);
            imagePreviewSwypeView.setImageList(listUrlImagesToSwype, positionImage + 1, mSwypeMode);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMenuArticles = menu;
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_preview_list, mMenuArticles);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intentSlider;
        switch (item.getItemId()) {
            case R.id.miPreviewToArticleList:
                // переход на экран всех статей
                this.finish();
                return true;
            case R.id.miPreviewToLikeList:
                this.finish();
                intentSlider = SliderActivity.newIntent(mContext, 0, LIKED_MODE);
                startActivity(intentSlider);
                return true;
            case R.id.miPreviewToDislikedList:
                this.finish();
                intentSlider = SliderActivity.newIntent(mContext, 0, DISLIKED_MODE);
                startActivity(intentSlider);
                return true;
            case R.id.miPreviewToShowAllImages:
                this.finish();
                intentSlider = SliderActivity.newIntent(mContext, 0, ALL_MODE);
                startActivity(intentSlider);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
