package com.std.redditarticles.presentation.views;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.util.Pair;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.widget.LinearLayout;

import com.std.redditarticles.R;
import com.std.redditarticles.adapters.ImageSwypePagerAdapter;
import com.std.redditarticles.adapters.ImageSwypeRecyclerAdapter;
import com.std.redditarticles.presentation.ArticlesActivity;
import com.std.redditarticles.presentation.SliderActivity;
import com.std.redditarticles.utils.UtilsReddit;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.std.redditarticles.presentation.SliderActivity.SWYPE_MODE;

public class ImagePreviewSwypeView extends LinearLayout implements ImageSwypeRecyclerAdapter.OnItemClickListener,
        ViewPager.OnPageChangeListener {

    @BindView(R.id.pager)
    ViewPager mViewPager;
    @BindView(R.id.imagesRecyclerView)
    RecyclerView mImagesRecyclerView;

    private Context context;
    private ImageSwypeRecyclerAdapter mRecyclerImageAdapter;
    private ImageSwypePagerAdapter mAdapterViewPager;
    private String mSwypeMode = "";

    private int mPreviousPosition = -100;
    private boolean mDisabledLiked = false;

    public ImagePreviewSwypeView(Context context) {
        super(context);
        init(context);
    }

    public ImagePreviewSwypeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public ImagePreviewSwypeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.context = context;
        inflate(context, R.layout.view_image_preview_with_swype, this);
        ButterKnife.bind(this);
    }

    /**
     * Инициализация списка изображений для просмотра и свайпа
     */
    public void setImageList(List<Pair<Integer, String>> imageList, int positionImage, String swypeMode) {
        if (imageList != null && imageList.size() > 0) {
            mSwypeMode = swypeMode;
            mAdapterViewPager = new ImageSwypePagerAdapter(context, imageList);
            mViewPager.setAdapter(mAdapterViewPager);
            mViewPager.addOnPageChangeListener(this);


            mRecyclerImageAdapter = new ImageSwypeRecyclerAdapter(context, imageList);
            mRecyclerImageAdapter.setOnItemClickListener(this);

            mImagesRecyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
            mImagesRecyclerView.setAdapter(mRecyclerImageAdapter);

            // установка текущей позиции просматриваемого изображения
            mViewPager.setCurrentItem(positionImage);
            //mImagesRecyclerView.scrollToPosition(positionImage);
        }
    }

    /**
     * Установка просматриваемого изображения кликом из горизонтального списка превью ресайкла
     */
    @Override
    public void onItemClick(String catalogModel, int position) {
        mDisabledLiked = true;
        mViewPager.setCurrentItem(position);
    }

    /**
     * обработка действий при свайпе изображения,
     */
    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        if (!mDisabledLiked && mSwypeMode.equals(SWYPE_MODE)) {
            if ((position - mPreviousPosition) == 1) {
                // установка флага Dislike для статьи
                int positionDislike = mAdapterViewPager.getImages().get(mPreviousPosition).first;
                if (positionDislike >= 0) {
                    ArticlesActivity.getListArticles().get(positionDislike).setDislike(true);
                }
                UtilsReddit.showToast(context, "Dislike");

                //mRecyclerImageAdapter.getProductImages().remove(mPreviousPosition);
                //mAdapterViewPager.getImages().remove(mPreviousPosition);
                //                if (mPreviousPosition != 0 && mPreviousPosition != mViewPager.getAdapter().getCount()) {
                //                    //mViewPager.removeViewAt(mPreviousPosition);
                //                    mAdapterViewPager.getImages().remove(mPreviousPosition);
                //                }
                //Toast.makeText(context, "Dislike " + mAdapterViewPager.getCount() + " " + mViewPager.getAdapter().getCount(), Toast.LENGTH_SHORT).show();
                //mRecyclerImageAdapter.notifyDataSetChanged();
                //mAdapterViewPager.notifyDataSetChanged();
                //position = mPreviousPosition;
                //mViewPager.setCurrentItem(position);
            } else if ((position - mPreviousPosition) == -1) {
                // установка флага Like для статьи
                int positionLike = mAdapterViewPager.getImages().get(mPreviousPosition).first;
                if (positionLike >= 0) {
                    ArticlesActivity.getListArticles().get(positionLike).setLike(true);
                }
                UtilsReddit.showToast(context, "Like");

                //                //mRecyclerImageAdapter.getProductImages().remove(mPreviousPosition);
                //                if (mPreviousPosition != 0 && mPreviousPosition != mViewPager.getAdapter().getCount()) {
                //                    //mViewPager.removeViewAt(mPreviousPosition);
                //                    mAdapterViewPager.getImages().remove(mPreviousPosition);
                //                }
                //                //mAdapterViewPager.getImages().remove(mPreviousPosition);
                //                Toast.makeText(context, "Like " + mAdapterViewPager.getCount() + " " + mViewPager.getAdapter().getCount(), Toast.LENGTH_SHORT).show();
                //mRecyclerImageAdapter.notifyDataSetChanged();
                //mAdapterViewPager.notifyDataSetChanged();

                //position = mPreviousPosition;
                //mViewPager.setCurrentItem(position);
                //ArticlesActivity.getListArticles().get(mPreviousPosition - 1).setLike(true);
            }
        }

        if (position >= mAdapterViewPager.getCount() - 1) {
            //position = mAdapterViewPager.getImages().size() - 1;
            position = 1;
            mViewPager.setCurrentItem(position);
            //mAdapterViewPager.notifyDataSetChanged();
            mDisabledLiked = true;
        }
        if (position <= 0) {
            //if (mViewPager.getCurrentItem() == 0) {
            //position = 1;
            position = mAdapterViewPager.getCount() - 1;
            mViewPager.setCurrentItem(position);
            mDisabledLiked = true;
            //mAdapterViewPager.notifyDataSetChanged();
        }
        // для режима оценки изображений следует подобрать без флага (like/dislike)
        if (mSwypeMode.equals(SWYPE_MODE)) {
            mPreviousPosition = position;
            position = UtilsReddit.getNotRatedImage(position - 1) + 1;
            if (position == 0) {
                // закрыть окно оценки изображений из-за отсутствия неоцененных
                ((SliderActivity) context).finish();
                position = -100; // выход из просмотра изображений
            }
            if (position != mPreviousPosition) {
                mViewPager.setCurrentItem(position);
                mDisabledLiked = true;
            }
        }

        // сохранение номера текущей позиции изображения во вью пейджере
        mPreviousPosition = position;
        mImagesRecyclerView.scrollToPosition(position);
    }

    /**
     * обработка действий при выборе страницы с изображением,
     * указание активной позиции в ресайкле ленты превью
     */
    @Override
    public void onPageSelected(int position) {
        //mImagesRecyclerView.scrollToPosition(position);
        //mRecyclerImageAdapter.setCurrentItemActive(position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        // сбрасывание флага внешней смены фокуса во вью педжере
        mDisabledLiked = false;
    }
}
