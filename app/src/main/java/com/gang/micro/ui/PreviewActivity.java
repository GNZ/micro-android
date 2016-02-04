package com.gang.micro.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.gang.micro.R;
import com.gang.micro.core.MicroApplication;
import com.gang.micro.core.api.MicroApi;
import com.gang.micro.core.gallery.RemoteGalleryActivity;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.image.analysis.Analysis;
import com.gang.micro.core.image.analysis.AnalysisType;
import com.gang.micro.core.mjpeg.MjpegView;
import com.gang.micro.core.mjpeg.MjpegViewInitializer;
import com.gang.micro.core.utils.api.ErrorLoggingCallback;

import java.util.List;
import java.util.UUID;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Response;
import retrofit.Retrofit;

public class PreviewActivity extends AppCompatActivity {

    private static final String TAG = "PreviewActivity";
    public static final String EXTRA_MICROSCOPE_IP = "micoscope_ip";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.micro_video)
    MjpegView microVideo;

    private SharedPreferences settings;
    private RelativeLayout.LayoutParams paramsNotFullscreen;
    private SharedPreferences.Editor prefEditor;
    private String serviceName;
    private String protocol;
    private String port;
    private String folderName;
    private String videoURL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        // No changes were made on settings
        ((MicroApplication) getApplication()).setChanges(false);
        // Avoid screen lock
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        String microscopeIP = getIntent().getStringExtra(EXTRA_MICROSCOPE_IP);

        ((MicroApplication) getApplication()).setServerIP(microscopeIP);

        getParameters();

    }

    protected void onRestart() {
        super.onRestart();
    }

    public void onPause() {
        super.onPause();

        microVideo.stopPlayback();
    }

    @Override
    protected void onStart() {
        super.onStart();

        startVideo();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) //To fullscreen
        {
            paramsNotFullscreen = (RelativeLayout.LayoutParams) microVideo.getLayoutParams();
            RelativeLayout.LayoutParams params = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                params = new RelativeLayout.LayoutParams(paramsNotFullscreen);
            } else {
                params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                        RelativeLayout.LayoutParams.MATCH_PARENT);
            }
            params.setMargins(0, 0, 0, 0);
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            microVideo.setLayoutParams(params);
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {
            microVideo.setLayoutParams(paramsNotFullscreen);
            getSupportActionBar().show();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    private void getParameters() {
        settings = PreferenceManager.getDefaultSharedPreferences(this);

        serviceName = settings.getString("service_name", "micro");
        protocol = settings.getString("protocol", "http");
        port = settings.getString("port", "8080");
        folderName = settings.getString("folder", "action=stream");

        ((MicroApplication) getApplication()).setServiceName(serviceName);
        ((MicroApplication) getApplication()).setProtocol(protocol);
        ((MicroApplication) getApplication()).setPort(port);
        ((MicroApplication) getApplication()).setFolderName(folderName);
    }

    @OnClick(R.id.fab)
    void selectFrame() {
        //String picture = takeAndAnalize();
        //seeTakenPicture(picture);

        Intent intent = new Intent(PreviewActivity.this, RemoteGalleryActivity.class);

        startActivity(intent);
    }

    private void seeTakenPicture(String picture) {
        Intent pictureActivity = new Intent(PreviewActivity.this, PictureActivity.class);
        pictureActivity.putExtra("picture", picture);
        startActivity(pictureActivity);

    }

    private String takeAndAnalize() {

        final MicroApi microApi = new MicroApi(getBaseContext());

        Call<List<Image>> imagesCall = microApi.getApi().getImages();

        imagesCall.enqueue(new ErrorLoggingCallback<List<Image>>() {

            @Override
            public void onSuccessfulResponse(Response<List<Image>> response, Retrofit retrofit) {
                Log.d(TAG, response.body().toString());

                UUID imageId = response.body().get(0).getId();
                Analysis analysis = new Analysis();
                analysis.setType(AnalysisType.BLOOD__RED_CELL_COUNT);

                microApi.getApi().analyseImage(imageId, analysis).enqueue(new ErrorLoggingCallback<Analysis>() {

                    @Override
                    public void onSuccessfulResponse(Response<Analysis> response, Retrofit retrofit) {

                        Log.d(TAG, String.valueOf(response.code()));

                        if (response.isSuccess())
                            Log.d(TAG, response.body().toString());
                    }
                });
            }
        });

        //TODO action take and analize picture

        //TODO return the image url
        return null;
    }

    private void startVideo() {
        String serverIP = ((MicroApplication) getApplication()).getServerIP();
        String port = ((MicroApplication) getApplication()).getPort();

        videoURL = "http://" + serverIP + ":" + port + "/?" + folderName;

        Log.d(TAG, videoURL);

        new MjpegViewInitializer(microVideo).execute(videoURL);
    }
}