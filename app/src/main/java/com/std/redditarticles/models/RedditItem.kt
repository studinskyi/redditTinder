package com.std.redditarticles.models

import com.std.redditarticles.utils.UtilsReddit

import java.io.Serializable
import java.util.Date

class RedditItem(val id: String,
                 var title: String?,
                 var pubDate: Date?,
                 var link: String?,
                 val img: String,
                 var creator: String?,
                 val positionArticle: Int,
                 private var like: Boolean,
                 private var dislike: Boolean) : Serializable {

    var isLike: Boolean
        get() = like
        set(like) {
            this.like = like
            this.dislike = !like
        }

    var isDislike: Boolean
        get() = dislike
        set(dislike) {
            this.dislike = dislike
            this.like = !dislike
        }

    val isNotRated: Boolean
        get() = !isLike && !isDislike

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null || javaClass != obj.javaClass) return false
        val that = obj as RedditItem?
        return id == that!!.id && link == that.link
    }

    override fun hashCode(): Int {
        return 11 * id.hashCode() + 21 * link!!.hashCode()
    }

    override fun toString(): String {
        return (UtilsReddit.getFullDate(pubDate!!.time)
                + " " + creator
                + " ( id: " + id + ")")
    }

    companion object {

        val BASE_URL = "https://www.reddit.com"
        val URL_REDDIT_ARTICLES = "https://www.reddit.com/r/aww.json"
        val CREATED = "created_utc"
        val TITLE_ARTICLE = "title"
        val URL_ARTICLE = "url"
        val AUTHOR_ARTICLE = "author"
        val PARMA_LINK = "permalink"
    }
}
