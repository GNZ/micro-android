package com.gang.micro.core.microscope;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.gang.micro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MicroscopesFragment extends Fragment {

    @Bind(R.id.microscope_list)
    ListView listView;

    public MicroscopesFragment() {
        // Required empty public constructor  
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Create adapter
        MicroscopeListAdapter microscopeListAdapter = new MicroscopeListAdapter(getActivity(), R.layout.microscope_list_item);

        // Set adapter
        listView.setAdapter(microscopeListAdapter);

        // Set item click listener
        listView.setOnItemClickListener(new MicroscopeListItemClickListener());
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
}  