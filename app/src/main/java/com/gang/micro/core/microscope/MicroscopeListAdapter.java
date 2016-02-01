package com.gang.micro.core.microscope;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.core.nsd.NSDAsyncDiscoveryTask;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MicroscopeListAdapter extends ArrayAdapter<Microscope> {
    private final Context context;
    private final int resource;

    public MicroscopeListAdapter(Context context, int resource) {
        super(context, resource, new ArrayList<Microscope>());
        this.context = context;

        this.resource = resource;

        loadMicroscopes();
    }

    private void loadMicroscopes() {

        NSDAsyncDiscoveryTask findMicroscopesTask = new NSDAsyncDiscoveryTask(this);

        findMicroscopesTask.execute();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        View view = convertView;

        if (view != null) {
            viewHolder = (ViewHolder) view.getTag();
        } else {
            LayoutInflater layoutInflater = ((Activity) getContext()).getLayoutInflater();

            view = layoutInflater.inflate(resource, parent, false);

            viewHolder = new ViewHolder(view);

            view.setTag(viewHolder);
        }

        Microscope microscope = getItem(position);

        viewHolder.titleTextView.setText(microscope.getName());
        viewHolder.subtitleTextView.setText(microscope.getIp());

        return view;
    }

    static class ViewHolder {
        @Bind(R.id.microscope_list_item_title)
        TextView titleTextView;

        @Bind(R.id.microscope_list_item_subtitle)
        TextView subtitleTextView;

        public ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
