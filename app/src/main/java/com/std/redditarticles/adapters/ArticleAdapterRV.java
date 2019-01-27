package com.std.redditarticles.adapters;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.ActionMode;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.std.redditarticles.R;
import com.std.redditarticles.models.RedditItem;
import com.std.redditarticles.presentation.SliderActivity;

import java.util.List;

public class ArticleAdapterRV extends RecyclerView.Adapter<ArticleAdapterRV.NewsHolder> {

    private List<RedditItem> mListArticles;
    private String mMsgError = "";
    private int mCurrentPosition = 0;

    public ArticleAdapterRV(List<RedditItem> listNews) {
        mListArticles = listNews;
    }

    @Override
    public NewsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.article_list_item, parent, false);
        return new NewsHolder(v);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(NewsHolder holder, int position) {
        RedditItem redditItem = mListArticles.get(position);
        holder.tvTitleNews.setText(redditItem.getTitle());
        holder.tvPubDate.setText(redditItem.toString());

        // загрузка каждого отдельно изображения в своем холдере при помощи Picasso
        Picasso.get().load(redditItem.getImg()).into(holder.ivArticleImage);

        // отображение иконки like/dislike согласно выбору пользователя
        holder.ivLikeImage.setVisibility(View.VISIBLE);
        if (redditItem.isLike()) {
            holder.ivLikeImage.setImageResource(R.drawable.ic_mood_black_24dp);
        } else if (redditItem.isDislike()) {
            holder.ivLikeImage.setImageResource(R.drawable.ic_mood_bad_black_24dp);
        } else {
            holder.ivLikeImage.setVisibility(View.GONE);
        }

        // фиксация текущей отображенной позиции в списке
        mCurrentPosition = position;
    }

    @Override
    public int getItemCount() {
        return mListArticles.size();
    }

    public String getMsgError() {
        return mMsgError;
    }

    public int getCurrentPosition() {
        return mCurrentPosition;
    }

    /**
     * Внутренний класс холдера статьи реддит {@link NewsHolder}
     */
    class NewsHolder extends RecyclerView.ViewHolder {
        ImageView ivArticleImage;
        ImageView ivLikeImage;
        TextView tvTitleNews;
        TextView tvPubDate;
        View cvArticleReddit;
        //View linArticleRedditLayout;

        private ActionMode mActionMode;

        /**
         * Публичный конструктор класса {@link NewsHolder}
         */
        public NewsHolder(final View itemView) {
            super(itemView);
            ivArticleImage = (ImageView) itemView.findViewById(R.id.ivNewsImage);
            ivLikeImage = (ImageView) itemView.findViewById(R.id.image_like_article);
            tvTitleNews = (TextView) itemView.findViewById(R.id.tvTitleNews);
            tvPubDate = (TextView) itemView.findViewById(R.id.tvPubDateAuthor);
            cvArticleReddit = (View) itemView.findViewById(R.id.article_reddit_card_view);
            //linArticleRedditLayout = (View) itemView.findViewById(R.id.article_reddit_layout);

            /**
             * создание объекта колбэка для вызова меню через режим контекстных действий
             */
            final ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
                @Override
                public boolean onCreateActionMode(ActionMode mode, Menu menu) {
                    MenuInflater inflater = mode.getMenuInflater();
                    inflater.inflate(R.menu.menu_articles_list_long_click, menu);
                    return true;
                }

                @Override
                public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                    return false;
                }

                @Override
                public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                    RedditItem redditItem = mListArticles.get(getAdapterPosition());
                    switch (item.getItemId()) {
                        case R.id.miOpenSite:
                            // открытие из контекстного меню страницы статьи по ссылке реддит
                            String choiceURL = redditItem.getLink();
                            Intent intentURL = new Intent(Intent.ACTION_VIEW, Uri.parse(choiceURL));
                            itemView.getContext().startActivity(intentURL);
                            mode.finish();
                            return true;
                        case R.id.miSwypeImages:
                            // открытие из контекстного меню экрана оценки изображений через свайп
                            openIntentSlyderActivity();
                            mode.finish();
                            return true;
                        default:
                            return false;
                    }
                }

                @Override
                public void onDestroyActionMode(ActionMode mode) {
                    mActionMode = null;
                }
            };

            /**
             * регистрация вызова контекстного меню при длительном нажатии на холдер с этой статьей
             */
            cvArticleReddit.setOnLongClickListener(new View.OnLongClickListener() {
                public boolean onLongClick(View view) {
                    if (mActionMode != null) {
                        return false;
                    }
                    mActionMode = ((Activity) itemView.getContext()).startActionMode(mActionModeCallback);
                    view.setSelected(true);
                    return true;
                }
            });

            /**
             * установка кликабельности картинки в холдере с информацией о статье
             */
            ivArticleImage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // открытие интента оценки изображение через свайп
                    openIntentSlyderActivity();
                }
            });
        }

        /**
         * Метод открытия интента оценки изображений {@link SliderActivity}
         */
        public void openIntentSlyderActivity() {
            Intent intentSlider = SliderActivity.newIntent(itemView.getContext(),
                    getAdapterPosition(), SliderActivity.SWYPE_MODE);
            itemView.getContext().startActivity(intentSlider);
        }


    }
}
