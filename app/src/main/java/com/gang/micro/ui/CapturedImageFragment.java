package com.gang.micro.ui;


import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.gang.micro.R;
import com.gang.micro.core.api.MicroApi;
import com.gang.micro.core.api.MicroApiSpecification;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.image.ImageContainer;
import com.gang.micro.core.image.analysis.Analysis;
import com.gang.micro.core.image.analysis.AnalysisAsyncTask;
import com.gang.micro.core.image.analysis.AnalysisResultListener;
import com.gang.micro.core.utils.api.ErrorLoggingCallback;
import com.gang.micro.core.utils.image.ImageUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class CapturedImageFragment extends DialogFragment implements ImageContainer {

    MicroApi microApi;

    @Bind(R.id.fragment_captured_image_image_view)
    ImageView capturedImage;

    @Bind(R.id.fragment_captured_image_name_edit)
    TextView nameEdit;

    @Bind(R.id.fragment_captured_image_description_edit)
    TextView descriptionEdit;

    @Bind(R.id.analysis_item_progress_bar)
    ProgressBar progressBar;

    @Bind(R.id.analysis_item_value)
    TextView analysisResultTextView;

    private Context context;
    private Image image;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(STYLE_NO_FRAME, android.R.style.Theme_Holo_Light);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        context = getActivity().getApplicationContext();

        MicroApiSpecification microApi = new MicroApi(context).getApi();

        microApi.captureImage().enqueue(new ErrorLoggingCallback<Image>() {
            @Override
            public void onSuccessfulResponse(Response<Image> response, Retrofit retrofit) {
                setImage(response.body());
            }
        });
    }

    @Override
    public void setImage(Image image) {

        this.image = image;

        String imageUrl = new ImageUtils(context).getImageUrl(image);

        // Set bitmap asynchronously
        Glide.with(context)
                .load(imageUrl)
                .override(ImageUtils.WIDTH, ImageUtils.HEIGHT)
                .centerCrop()
                .into(capturedImage);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_captured_image, container, false);

        // Bind components
        ButterKnife.bind(this, view);

        progressBar.setIndeterminate(true);

        return view;
    }

    @OnClick(R.id.fragment_captured_image_save_button)
    public void saveImage() {

        if (image != null && capturedImage.getDrawable() != null) {

            image.setName(nameEdit.getText().toString());
            image.setDescription(descriptionEdit.getText().toString());

            MicroApiSpecification api = new MicroApi(context).getApi();

            Call<Image> updateCall = api.updateImage(image.getId(), image);

            updateCall.enqueue(new ErrorLoggingCallback<Image>() {
                @Override
                public void onSuccessfulResponse(Response<Image> response, Retrofit retrofit) {
                    Toast.makeText(context, R.string.success_taking_picture, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        } else Toast.makeText(context, R.string.error_taking_picture, Toast.LENGTH_LONG).show();

        dismiss();
    }

    @Override
    public void onCancel(DialogInterface dialog) {

        MicroApiSpecification api = new MicroApi(context).getApi();

        if (image != null) {

            Call<Boolean> deleteCall = api.deleteImage(image.getId());

            deleteCall.enqueue(new ErrorLoggingCallback<Boolean>() {
                @Override
                public void onSuccessfulResponse(Response<Boolean> response, Retrofit retrofit) {
                    Toast.makeText(context, R.string.discard_image, Toast.LENGTH_SHORT)
                            .show();
                }
            });
        } else Toast.makeText(context, R.string.error_taking_picture, Toast.LENGTH_LONG).show();
    }

    @OnClick(R.id.fragment_captured_image_analyse_button)
    public void analyseImage() {
        progressBar.setVisibility(View.VISIBLE);

        Log.d("Analysis", "Started analysis");

        AnalysisResultListener analysisResultListener = new AnalysisResultListener() {
            @Override
            public void setAnalysis(Analysis analysis) {
                analysisResultTextView.setText(analysis.getResult());

                Log.d("Analysis", "Finished analysis");

                progressBar.setVisibility(View.INVISIBLE);
            }
        };

        new AnalysisAsyncTask(context, analysisResultListener, image).execute();
    }
}
