package com.gang.micro.core.microscope;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gang.micro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MicroscopesFragment extends Fragment {

    @Bind(R.id.microscope_list)
    RecyclerView recyclerView;

    @Bind(R.id.microscopes_loading_bar)
    ProgressBar loadingBar;

    @Bind(R.id.microscopes_empty)
    TextView emptyTextView;

    public MicroscopesFragment() {
        // Required empty public constructor  
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create adapter
        MicroscopeListAdapter microscopeListAdapter = new MicroscopeListAdapter(getActivity(),this);

        // Create layout manager
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());

        // Set the layout manager
        recyclerView.setLayoutManager(layoutManager);

        // Set adapter
        recyclerView.setAdapter(microscopeListAdapter);

        // Set item click listener
        microscopeListAdapter.setOnItemClickListener(new MicroscopeListItemClickListener(getActivity(), microscopeListAdapter));
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

    public void updateUI() {
        loadingBar.setVisibility(View.GONE);
        if (recyclerView.getAdapter().getItemCount() == 0)
            emptyTextView.setVisibility(View.VISIBLE);
    }
}  