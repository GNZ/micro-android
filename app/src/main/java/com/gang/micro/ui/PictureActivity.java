package com.gang.micro.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.gang.micro.R;
import com.gang.micro.core.MicroApplication;
import com.gang.micro.core.api.MicroApi;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.FileManager;
import com.gang.micro.core.utils.api.ErrorLoggingCallback;
import com.squareup.picasso.Picasso;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class PictureActivity extends Activity {

    private static final String TAG = "PictureActivity";
    static final String FOLDER = Environment.getExternalStorageDirectory() +
            File.separator + "micro" + File.separator;

    static final String FIX_URL = "/images/output/";

    MicroApi microApi;
    // UI
    @Bind(R.id.micro_picture)
    ImageView microPicture;
    @Bind(R.id.loading_image)
    ProgressBar loadingImage;
    @Bind(R.id.discard_button)
    Button discardButton;
    @Bind(R.id.save_button)
    Button saveButton;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);
        FileManager.createFolder(FOLDER);
        microApi = new MicroApi(this);
        setButtonsClickeable(false);
        loadImage();
    }

    public void onBackPressed() {
        super.onBackPressed();
    }

    private void loadImage() {
        Call<Image> call = microApi.getApi().takePicture();

        call.enqueue(new ErrorLoggingCallback<Image>() {
            @Override
            public void onSuccessfulResponse(Response<Image> response, Retrofit retrofit) {

                loadingImage.setVisibility(View.GONE);
                setButtonsClickeable(true);

                id = response.body().getId().toString();
                String serverIP = ((MicroApplication) getApplication()).getServerIP();

                String imageUrl = "http://" + serverIP + FIX_URL + id + ".jpg";

                Picasso.with(getBaseContext())
                        .load(imageUrl)
                        .into(microPicture);
            }
        });

        // TODO download and save the analysis also
    }

    @OnClick(R.id.discard_button)
    void discardPicture() {

        finish();
    }

    private void startPreviewActivity() {
        Intent previewActivity = new Intent(PictureActivity.this, PreviewActivity.class);
        startActivity(previewActivity);
    }

    @OnClick(R.id.save_button)
    void savePicture() {
        if (!FileManager.saveJpegImageFromBitmap(FOLDER, id, microPicture.getDrawingCache()))
            Log.d(TAG, "Couldn't save the image");
        else {
            Intent analysisActivity = new Intent(PictureActivity.this, AnalysisActivity.class);
            //TODO put something in the intent?
            analysisActivity.putExtra("imageId", id);
            startActivity(analysisActivity);
            finish();
        }
    }

    private void setButtonsClickeable(boolean clickeable) {
        discardButton.setClickable(clickeable);
        saveButton.setClickable(clickeable);
    }

}
