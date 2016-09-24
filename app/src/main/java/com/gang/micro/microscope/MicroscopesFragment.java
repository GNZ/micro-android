package com.gang.micro.microscope;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.start.StartActivityComponent;
import com.gang.micro.dagger.BaseActivityComponent;
import com.gang.micro.dagger.BaseFragment;
import com.gang.micro.dagger.FragmentModule;
import com.gang.micro.nsd.events.StopNSDDiscoveryEvent;

import org.greenrobot.eventbus.EventBus;

import javax.inject.Inject;

import butterknife.Bind;

public class MicroscopesFragment extends BaseFragment {

    @Bind(R.id.microscope_list)
    RecyclerView recyclerView;

    @Bind(R.id.microscopes_empty)
    TextView emptyTextView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    @Inject
    MicroscopeListAdapter microscopeListAdapter;
    @Inject
    MicroscopeListItemClickListener microscopeListItemClickListener;

    public MicroscopesFragment() {
        // Required empty public constructor  
    }

    public static MicroscopesFragment newInstance() {
        return new MicroscopesFragment();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Bind adapter to click listener
        microscopeListItemClickListener.bindAdapter(microscopeListAdapter);

        // Create layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        // Set the layout manager
        recyclerView.setLayoutManager(layoutManager);

        // Set adapter
        recyclerView.setAdapter(microscopeListAdapter);

        // Init swipe refresh
        initSwipeRefresh();

        // Set item click listener
        microscopeListAdapter.setOnItemClickListener(microscopeListItemClickListener);

        // Load microscopes
        microscopeListAdapter.loadMicroscopes();
    }

    private void initSwipeRefresh() {

        // Set swipe listener
        swipeRefreshLayout.setOnRefreshListener(new MicroscopesFragmentSwipeOnRefresh());

        // After onMeasure start refreshing
        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {

                // Disable swipe refreshing
                swipeRefreshLayout.setEnabled(false);

                // Show refreshing
                swipeRefreshLayout.setRefreshing(true);
            }
        });
    }

    @Override
    protected void injectComponent(@NonNull BaseActivityComponent baseActivityComponent, @NonNull FragmentModule fragmentModule, @Nullable Bundle savedInstanceState) {
        final MicroscopesFragmentComponent component = DaggerMicroscopesFragmentComponent
                .builder()
                .startActivityComponent((StartActivityComponent) baseActivityComponent)
                .microscopesFragmentModule(new MicroscopesFragmentModule(this))
                .build();
        component.inject(this);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_microscopes, container, false);

        return view;
    }

    @Override
    public void onPause() {

        EventBus.getDefault().post(new StopNSDDiscoveryEvent());

        super.onPause();
    }

    public void updateUI() {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(true);
        if (recyclerView.getAdapter().getItemCount() == 0)
            emptyTextView.setVisibility(View.VISIBLE);
    }

    private class MicroscopesFragmentSwipeOnRefresh implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            microscopeListAdapter.loadMicroscopes();
        }
    }
}  