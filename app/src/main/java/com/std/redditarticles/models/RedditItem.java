package com.std.redditarticles.models;

import com.std.redditarticles.utils.UtilsReddit;

import java.io.Serializable;
import java.util.Date;

public class RedditItem implements Serializable {

    public static final String BASE_URL = "https://www.reddit.com";
    public static final String URL_REDDIT_ARTICLES = "https://www.reddit.com/r/aww.json";
    public static final String CREATED = "created_utc";
    public static final String TITLE_ARTICLE = "title";
    public static final String URL_ARTICLE = "url";
    public static final String AUTHOR_ARTICLE = "author";
    public static final String PARMA_LINK = "permalink";

    private String id_post;
    private String title;
    private Date pubDate;
    private String link;
    private String img;
    private String creator;
    private int positionArticle;
    private boolean like;
    private boolean dislike;

    public RedditItem(String id,
                      String title,
                      Date pubDate,
                      String link,
                      String img,
                      String creator,
                      int positionArticle,
                      boolean like,
                      boolean dislike) {
        this.id_post = id;
        this.title = title;
        this.pubDate = pubDate;
        this.link = link;
        this.img = img;
        this.creator = creator;
        this.positionArticle = positionArticle;
        this.like = like;
        this.dislike = dislike;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        RedditItem that = (RedditItem) obj;
        return id_post.equals(that.id_post) && link.equals(that.link);
    }

    @Override
    public int hashCode() {
        return 11 * id_post.hashCode() + 21 * link.hashCode();
    }

    public String getId() {
        return id_post;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Date getPubDate() {
        return pubDate;
    }

    public void setPubDate(Date pubDate) {
        this.pubDate = pubDate;
    }

    public String getImg() {
        return img;
    }

    public String getCreator() {
        return creator;
    }

    public void setCreator(String creator) {
        this.creator = creator;
    }

    public int getPositionArticle() {
        return positionArticle;
    }

    public boolean isLike() {
        return like;
    }

    public void setLike(boolean like) {
        this.like = like;
        this.dislike = !like;
    }

    public boolean isDislike() {
        return dislike;
    }

    public void setDislike(boolean dislike) {
        this.dislike = dislike;
        this.like = !dislike;
    }

    public boolean isNotRated() {
        return !isLike() && !isDislike();
    }

    @Override
    public String toString() {
        return UtilsReddit.getFullDate(pubDate.getTime())
                + " " + creator
                + " ( id: " + id_post + ")";
    }
}
