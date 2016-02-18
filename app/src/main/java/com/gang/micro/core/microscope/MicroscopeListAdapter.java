package com.gang.micro.core.microscope;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.core.nsd.NSDAsyncDiscoveryTask;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MicroscopeListAdapter extends RecyclerView.Adapter<MicroscopeListAdapter.MicroscopeViewHolder> {

    private ArrayList<Microscope> dataset;

    private static MicroscopeListItemClickListener clickListener;
    private Context context;
    private MicroscopesFragment fragment;
    private NSDAsyncDiscoveryTask findMicroscopesTask;

    public MicroscopeListAdapter(Context context, MicroscopesFragment fragment) {
        this.context = context;
        this.fragment = fragment;
        this.dataset = new ArrayList<>();

        loadMicroscopes();
    }

    private void loadMicroscopes() {

        loadMockMicroscope();

        findMicroscopesTask = new NSDAsyncDiscoveryTask(this);

        findMicroscopesTask.execute();
    }

    private void loadMockMicroscope() {
        Microscope microscope = new Microscope("Test microscope", "microuns.herokuapp.com");
        microscope.setWebApplicationPort("80");

        dataset.add(microscope);
    }

    @Override
    public MicroscopeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.microscope_list_item, parent, false);

        return new MicroscopeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MicroscopeViewHolder holder, int position) {
        holder.titleTextView.setText(dataset.get(position).getName());
        holder.subtitleTextView.setText(dataset.get(position).getServerIp());
    }

    @Override
    public int getItemCount() {
        return dataset.size();
    }

    public void addAll(Microscope microscope) {
        dataset.add(microscope);
        notifyItemInserted(dataset.size() - 1);
    }

    public void setOnItemClickListener(MicroscopeListItemClickListener microscopeListItemClickListener) {
        clickListener = microscopeListItemClickListener;
    }

    public Microscope getItemAtPosition(int position) {
        return dataset.get(position);
    }

    public Context getContext() {
        return context;
    }

    public MicroscopesFragment getMicroscopeFragment(){
        return fragment;
    }

    public void stopDiscovery() {
        findMicroscopesTask.cancel(true);
    }

    static class MicroscopeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.microscope_list_item_title)
        TextView titleTextView;

        @Bind(R.id.microscope_list_item_subtitle)
        TextView subtitleTextView;

        public MicroscopeViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            clickListener.onItemClick(getAdapterPosition(), view);
        }
    }
}
