package com.gang.micro.core.gallery;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.core.image.Image;

import java.util.ArrayList;

public abstract class GalleryAdapter extends ArrayAdapter<Image> implements GalleryService {

    private final int resource;
    private GalleryFragment fragment;

    public GalleryAdapter(Context context, int resource, GalleryFragment fragment) {
        super(context, resource, new ArrayList<Image>());

        this.resource = resource;
        this.fragment = fragment;

        loadImages();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View row = convertView;
        ViewHolder holder;

        if (row == null) {
            LayoutInflater inflater = ((Activity) getContext()).getLayoutInflater();
            row = inflater.inflate(resource, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) row.findViewById(R.id.bitmap);
            holder.textView = (TextView) row.findViewById(R.id.text);
            row.setTag(holder);
        } else {
            holder = (ViewHolder) row.getTag();
        }

        Image image = this.getItem(position);

        holder.imageView.setImageBitmap(image.getBitmap());
        holder.textView.setText(image.getId().toString().substring(0, 6));

        return row;
    }

    static class ViewHolder {
        TextView textView;
        ImageView imageView;
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
        fragment.updateUI();
    }
}
