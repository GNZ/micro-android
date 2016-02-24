package com.gang.micro.gallery.common;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.gang.micro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public abstract class GalleryFragment extends Fragment {

    @Bind(R.id.gallery_list)
    protected RecyclerView recyclerView;

    @Bind(R.id.gallery_empty)
    protected TextView empty;

    @Bind(R.id.swipeRefreshLayout)
    SwipeRefreshLayout swipeRefreshLayout;

    protected GalleryAdapter galleryAdapter;

    protected RecyclerView.LayoutManager layoutManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);

        // Bind components
        ButterKnife.bind(this, view);

        // Disable animations
        DefaultItemAnimator animator = new DefaultItemAnimator() {
            @Override
            public boolean canReuseUpdatedViewHolder(RecyclerView.ViewHolder viewHolder) {
                return true;
            }
        };

        //initSwipeRefreshLayout();
        initSwipeRefreshLayout();

        recyclerView.setItemAnimator(animator);

        return view;
    }

    private void initSwipeRefreshLayout() {

        // Set swipe listener
        swipeRefreshLayout.setOnRefreshListener(new GalleryFragmentSwipeOnRefresh());

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
    public void onResume() {
        super.onResume();
        checkRecyclerViewEmpty();
    }

    public void updateUI() {
        stopRefreshing();
        checkRecyclerViewEmpty();
    }

    private void stopRefreshing() {
        swipeRefreshLayout.setRefreshing(false);
        swipeRefreshLayout.setEnabled(true);
    }

    private void checkRecyclerViewEmpty() {
        if (recyclerView.getAdapter().getItemCount() == 0)
            empty.setVisibility(View.VISIBLE);
        else
            empty.setVisibility(View.GONE);
    }

    private class GalleryFragmentSwipeOnRefresh implements SwipeRefreshLayout.OnRefreshListener {

        @Override
        public void onRefresh() {
            galleryAdapter.loadImages();
        }
    }

}
