package com.gang.micro.gallery.remote;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gang.micro.R;
import com.gang.micro.api.MicroApi;
import com.gang.micro.api.MicroApiSpecification;
import com.gang.micro.gallery.common.item.GalleryItemFragment;
import com.gang.micro.image.analysis.Analysis;
import com.gang.micro.image.analysis.AnalysisResultListener;
import com.gang.micro.image.analysis.AnalysisType;
import com.gang.micro.utils.api.ErrorLoggingCallback;

import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Response;
import retrofit.Retrofit;

public class RemoteGalleryItemFragment extends GalleryItemFragment {

    RemoteGalleryItemFragmentListener listener;

    MicroApiSpecification microApi;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_remote_image_detail, container, false);
        ButterKnife.bind(this, rootView);

        microApi = new MicroApi(getActivity()).getApi();

        return initUI(rootView);
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
                Log.d("Analysis", "Finished analysis");
            }
        };

        microApi.analyseImage(image.getId(), new Analysis(AnalysisType.BLOOD__RED_CELL_COUNT))
                .enqueue(new ErrorLoggingCallback<Analysis>() {
                    @Override
                    public void onSuccessfulResponse(Response<Analysis> response, Retrofit retrofit) {
                        Context context = getContext();
                        if (context != null) {
                            Toast.makeText(context, response.body().getResult(), Toast.LENGTH_LONG).show();
                            analysisResultListener.setAnalysis(response.body());
                        }
                    }
                });
    }

}
