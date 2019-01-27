package com.std.redditarticles.adapters;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.std.redditarticles.R;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageSwypePagerAdapter extends PagerAdapter {

    private List<Pair<Integer, String>> images;
    private LayoutInflater inflater;
    private Context context;

    public ImageSwypePagerAdapter(Context context, List<Pair<Integer, String>> images) {
        this.context = context;
        this.images = images;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return images.size();
    }

    public List<Pair<Integer, String>> getImages() {
        return images;
    }

    @Override
    public Object instantiateItem(ViewGroup viewGroup, int position) {
        View view = inflater.inflate(R.layout.item_image_swype, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        Picasso.get().load(images.get(position).second).into(viewHolder.image);

        viewGroup.addView(view, 0);
        return view;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    public class ViewHolder {

        @BindView(R.id.image_swype)
        ImageView image;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
