package com.gang.micro.microscope;

import android.content.Context;
import android.net.nsd.NsdManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.microscope.events.FoundMicroscopeEvent;
import com.gang.micro.nsd.NSDCoordinator;
import com.gang.micro.nsd.events.StartNSDDiscoveryEvent;
import com.gang.micro.nsd.events.StopNSDDiscoveryEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MicroscopeListAdapter extends RecyclerView.Adapter<MicroscopeListAdapter.MicroscopeViewHolder> {

    private ArrayList<Microscope> dataset;

    private static MicroscopeListItemClickListener clickListener;
    private Context context;
    private MicroscopesFragment fragment;
    private NSDCoordinator nsdCoordinator;

    public MicroscopeListAdapter(Context context, MicroscopesFragment fragment) {
        this.context = context;
        this.fragment = fragment;

        EventBus.getDefault().register(this);

        dataset = new ArrayList<>();

        // Get NsdManager reference
        NsdManager nsdManager = (NsdManager) getContext().getSystemService(Context.NSD_SERVICE);

        nsdCoordinator = new NSDCoordinator(nsdManager);
    }

    @Override
    protected void finalize() throws Throwable {

        EventBus.getDefault().unregister(this);

        super.finalize();
    }

    public void loadMicroscopes() {

        loadMockMicroscope();

        EventBus.getDefault().post(new StartNSDDiscoveryEvent());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void addMicroscope(FoundMicroscopeEvent foundMicroscopeEvent){
        addAll(foundMicroscopeEvent.getMicroscope());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void stopFindMicroscope(StopNSDDiscoveryEvent stopNSDDiscoveryEvent){
        fragment.updateUI();
    }

    private void loadMockMicroscope() {
        Microscope microscope = new Microscope("Test microscope", "microuns.herokuapp.com");
        microscope.setWebApplicationPort("80");

        if (!dataset.contains(microscope))
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
        if (!dataset.contains(microscope)) {
            dataset.add(microscope);
            notifyItemInserted(dataset.size() - 1);
        }
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

    public MicroscopesFragment getMicroscopeFragment() {
        return fragment;
    }

    class MicroscopeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
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
