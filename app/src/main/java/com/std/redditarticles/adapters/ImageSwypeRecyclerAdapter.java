package com.std.redditarticles.adapters;

import android.content.Context;
import android.support.v4.util.Pair;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;
import com.std.redditarticles.R;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImageSwypeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //private List<View> selectedViews = new ArrayList<>();
    private View selectedView = null;
    //private List<View> allViews = new ArrayList<>();

    private List<Pair<Integer, String>> productImages;
    private OnItemClickListener onItemClickListener;
    private Context context;

    public ImageSwypeRecyclerAdapter(Context context,
                                     List<Pair<Integer, String>> productImages) {
        this.context = context;
        this.productImages = productImages;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).
                inflate(R.layout.item_horizontal_image_thumbs, parent, false);
        return new ReceiveViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        ReceiveViewHolder viewHolder = (ReceiveViewHolder) holder;
        Picasso.get().load(productImages.get(position).second).into(viewHolder.image);
        //allViews.add(viewHolder.itemView);
    }

    @Override
    public int getItemCount() {
        return productImages.size();
    }

    //    /**
    //     * Установка текущей позиции в горизонтальном ресайкле превью,
    //     * согласно просматриваемому на свайпе изображению
    //     */
    //    public void setCurrentItemActive(int position) {
    //        //        for (int i = 0; i < selectedViews.size(); i++) {
    //        //            selectedViews.remove(i).setSelected(false);
    //        //        }
    //        //        position = (position >= allViews.size()) ? 0 : position;
    //        //        selectedViews.add(allViews.get(position));
    //        //allViews.get(position).setSelected(true);
    //
    //        //        if (selectedView != null) {
    //        //            selectedView.setSelected(false);
    //        //        }
    //        //
    //        //        if (position >= 0 && position < allViews.size()) {
    //        //            selectedView = allViews.get(position);
    //        //        }
    //        //        if (selectedView != null) {
    //        //            selectedView.setSelected(true);
    //        //        }
    //    }

    public class ReceiveViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.image_swype)
        ImageView image;

        public ReceiveViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(view -> {
                //                for (int i = 0; i < selectedViews.size(); i++) {
                //                    selectedViews.remove(i).setSelected(false);
                //                }
                //                selectedViews.add(itemView);
                //                itemView.setSelected(true);
                int positionItem = getAdapterPosition();
                if (positionItem == 0) {
                    positionItem = 1;
                }
                if (positionItem == productImages.size()) {
                    positionItem = productImages.size() - 1;
                }

                if (selectedView != null) {
                    selectedView.setSelected(false);
                }
                selectedView = itemView;
                selectedView.setSelected(true);
                onItemClickListener.onItemClick(productImages.get(positionItem).second
                        , getAdapterPosition());


            });
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(String imagePath, int position);
    }

    public List<Pair<Integer, String>> getProductImages() {
        return productImages;
    }
}
