package com.gang.micro.core.gallery.remote;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.image.ImageUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RemoteGalleryAdapter extends RecyclerView.Adapter<RemoteGalleryAdapter.ImageHolder> {

    private ArrayList<Image> dataset;
    private Context context;

    public RemoteGalleryAdapter(Context context) {
        this.context = context;
        this.dataset = new ArrayList<>();

        loadRemoteImages();
    }

    private void loadRemoteImages() {
        RemoteImageLoadAsyncTask loadRemoteImages = new RemoteImageLoadAsyncTask(context, this);

        loadRemoteImages.execute();
    }

    @Override
    public ImageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.fragment_remote_gallery_list_item, parent, false);

        return new ImageHolder(view);
    }

    @Override
    public void onBindViewHolder(ImageHolder holder, int position) {
        String imageUrl = new ImageUtils(context).getImageUrl(dataset.get(position));

        holder.imageView.setImageDrawable(null);

        Picasso.with(context).load(imageUrl).into(holder.imageView);
        holder.textView.setText(dataset.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void add(Image image) {
        dataset.add(image);

        //new ImageIO().saveImage(image, image.getBitmap());

        notifyItemInserted(dataset.size() - 1);
    }

    static class ImageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.remote_gallery_list_item_title)
        TextView textView;

        @Bind(R.id.remote_gallery_list_item_image)
        ImageView imageView;

        public ImageHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
