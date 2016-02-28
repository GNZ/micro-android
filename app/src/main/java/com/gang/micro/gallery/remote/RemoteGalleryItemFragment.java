package com.gang.micro.gallery.remote;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gang.micro.R;
import com.gang.micro.api.MicroApi;
import com.gang.micro.api.MicroApiSpecification;
import com.gang.micro.gallery.common.item.GalleryItemFragment;
import com.gang.micro.image.analysis.Analysis;
import com.gang.micro.image.analysis.AnalysisResultListener;
import com.gang.micro.image.analysis.AnalysisType;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public class RemoteGalleryItemFragment extends GalleryItemFragment {

    RemoteGalleryItemFragmentListener listener;

    MicroApiSpecification microApi;

    @Bind(R.id.analysis_item_value)
    TextView analysisResult;

    @Bind(R.id.analysis_item_progress_bar)
    ProgressBar analysisProgressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_remote_image_detail, container, false);
        ButterKnife.bind(this, rootView);

        microApi = new MicroApi(getActivity()).getApi();

        return initUI(rootView);
    }

    @Override
    protected View initUI(View rootView) {
        View view = super.initUI(rootView);

        // Add analysis to view if already made
        if (image.getAnalyses() != null && image.getAnalyses().size() > 0) {
            // TODO: Delete hardcoded position when there's more than one anlysis
            analysisResult.setText(image.getAnalyses().get(0).getResult());
        }

        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        listener = (RemoteGalleryItemFragmentListener) activity;
        super.onAttach(activity);
    }

    @OnClick(R.id.view_analysis_save_fab)
    void saveInLocalGallery() {
        listener.saveImageInLocalGallery(image, caller.getUrl());
    }

    @OnClick(R.id.fragment_captured_image_analyse_button)
    void analyseImage() {

        final AnalysisResultListener analysisResultListener = new AnalysisResultListener() {
            @Override
            public void setAnalysis(Analysis analysis) {
                analysisResult.setText(analysis.getResult());

                // TODO: This should be made on each refresh
                image.getAnalyses().clear();
                image.getAnalyses().add(analysis);

                analysisProgressBar.setVisibility(View.INVISIBLE);

                Log.d("Analysis", "Finished analysis");
            }
        };

        analysisProgressBar.setVisibility(View.VISIBLE);

        microApi.analyseImage(image.getId(), new Analysis(AnalysisType.BLOOD__RED_CELL_COUNT))
                .enqueue(new Callback<Analysis>() {
                    @Override
                    public void onResponse(Response<Analysis> response, Retrofit retrofit) {
                        Context context = getContext();
                        if (context != null) {
                            analysisResultListener.setAnalysis(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Throwable t) {
                        analysisProgressBar.setVisibility(View.INVISIBLE);
                    }
                });
    }

}
