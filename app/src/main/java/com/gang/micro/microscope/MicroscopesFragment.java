package com.gang.micro.microscope;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.nsd.NSDAsyncDiscoveryTask;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MicroscopesFragment extends Fragment {

    @Bind(R.id.microscope_list)
    RecyclerView recyclerView;

    @Bind(R.id.microscopes_empty)
    TextView emptyTextView;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    private MicroscopeListAdapter microscopeListAdapter;

    public MicroscopesFragment() {
        // Required empty public constructor  
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create adapter
        microscopeListAdapter = new MicroscopeListAdapter(getActivity(),this);

        // Create layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        // Set the layout manager
        recyclerView.setLayoutManager(layoutManager);

        // Set adapter
        recyclerView.setAdapter(microscopeListAdapter);

        // Init swipe refresh
        initSwipeRefresh();

        // Set item click listener
        microscopeListAdapter.setOnItemClickListener(new MicroscopeListItemClickListener(getActivity(), microscopeListAdapter));
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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_microscopes, container, false);

        // Bind components
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onPause() {
        microscopeListAdapter.cancelMicroscopesSearch();

        super.onPause();
    }

    public void updateUI() {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(true);
        if (recyclerView.getAdapter().getItemCount() == 0)
            emptyTextView.setVisibility(View.VISIBLE);
    }

    private class MicroscopesFragmentSwipeOnRefresh implements SwipeRefreshLayout.OnRefreshListener{

        @Override
        public void onRefresh() {
            microscopeListAdapter.loadMicroscopes();
        }
    }
}  