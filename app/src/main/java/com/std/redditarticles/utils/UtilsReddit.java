package com.std.redditarticles.utils;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.std.redditarticles.models.RedditItem;
import com.std.redditarticles.presentation.ArticlesActivity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class UtilsReddit {

    public static final String logTAG = "logsNewsReddit";

    public static void showToast(Context context, String textToast) {
        Toast toast = Toast.makeText(context, textToast, Toast.LENGTH_SHORT);
        TextView v = (TextView) toast.getView().findViewById(android.R.id.message);
        v.setTextColor(Color.BLACK);
        //toast.setGravity(Gravity.LEFT, 0, 20);
        toast.show();
    }

    public static String getDate(long date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy");
        return dateFormat.format(date);
    }

    public static String getTime(long time) {
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH.mm");
        return timeFormat.format(time);
    }

    public static String getFullDate(long date) {
        SimpleDateFormat fullDateFormat = new SimpleDateFormat("dd.MM.yy HH.mm");
        return fullDateFormat.format(date);
    }

    public static Calendar calend(int year, int month, int dayOfMonth) {
        return new GregorianCalendar(year, month, dayOfMonth);
    }

    public static Calendar calend(int year, int month, int dayOfMonth, int hours, int minutes) {
        return new GregorianCalendar(year, month, dayOfMonth, hours, minutes);
    }

    public static String getCurrentDateTimeString() {
        Date d = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");
        return formatDate.format(d);
    }

    public static String getCurrentDateTimeToNameFile() {
        Date d = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd_HH_mm_ss");
        return formatDate.format(d);
    }

    public static String getCurrentDateTimeString_forFirebase() {
        Date d = new Date();
        SimpleDateFormat formatDate = new SimpleDateFormat("yyyyMMdd_HHmmss");
        return formatDate.format(d);
    }

    public static boolean hasImage(@NonNull ImageView view) {
        Drawable drawable = view.getDrawable();
        boolean hasImage = (drawable != null);

        if (hasImage && (drawable instanceof BitmapDrawable)) {
            hasImage = ((BitmapDrawable) drawable).getBitmap() != null;
        }

        return hasImage;
    }

    public static boolean checkUrlImageExists(String urlImage) {
        Uri uriImage = null;
        try {
            uriImage = Uri.parse(urlImage);
        } catch (Exception e) {
            return false;
        }
        File imgFile = new File(uriImage.getPath());
        if (imgFile.exists()) {
            return true;
        }
        return false;
    }

    public static Uri checkUrlExists(Context context, String urlImage) {
        Uri uriImage = null;
        try {
            uriImage = Uri.parse(urlImage);
        } catch (Exception e) {
            return null;
        }
        return checkUriExists(context, uriImage);
    }

    public static Uri checkUriExists(Context context, Uri mUri) {
        Drawable d = null;
        if (mUri != null) {
            if ("content".equals(mUri.getScheme())) {
                try {
                    d = Drawable.createFromStream(
                            context.getContentResolver().openInputStream(mUri),
                            null);
                } catch (Exception e) {
                    String msgError = "Unable to open content: " + mUri + " " + e.getMessage();
                    Log.w(logTAG, msgError, e);
                    mUri = null;
                }
            } else {
                d = Drawable.createFromPath(mUri.toString());
            }

            if (d == null) {
                // Invalid uri
                mUri = null;
            }
        }

        return mUri;
    }

    /**
     * Получение позиции неоцененной позиции изображения (like/dislike)
     */
    public static int getNotRatedImage(int currentPosition) {
        int positionResult = -1;
        //        currentPosition = (currentPosition >= ArticlesActivity.getListArticles().size()) ?
        //                ArticlesActivity.getListArticles().size() - 1 : 0;
        //        if (ArticlesActivity.getListArticles().get(currentPosition).isNotRated()) {
        //            return currentPosition;
        //        }
        for (RedditItem redditItem : ArticlesActivity.getListArticles()) {
            if (redditItem.isNotRated()) {
                positionResult = redditItem.getPositionArticle();
                break;
            }
        }
        return positionResult;
    }
}
