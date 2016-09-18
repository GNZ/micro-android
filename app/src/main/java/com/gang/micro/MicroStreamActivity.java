package com.gang.micro;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.RelativeLayout;

import com.gang.micro.application.MicroApplication;
import com.gang.micro.gallery.remote.RemoteGalleryActivity;
import com.gang.micro.mjpeg.MjpegView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MicroStreamActivity extends AppCompatActivity {

    private static final String TAG = "MicroStreamActivity";

    @Bind(R.id.toolbar)
    Toolbar toolbar;

    @Bind(R.id.micro_video)
    MjpegView microVideo;

    private RelativeLayout.LayoutParams paramsNotFullscreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_micro_stream);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        // Avoid screen lock
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
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

    @OnClick(R.id.micro_stream_take_picture_button)
    void selectFrame() {
        seeTakenPicture();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_microscope_stream_activity, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_open_remote_gallery) {
            Intent remoteGalleryIntent = new Intent(MicroStreamActivity.this, RemoteGalleryActivity.class);
            startActivity(remoteGalleryIntent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void seeTakenPicture() {
        CapturedImageFragment capturedImageFragment = new CapturedImageFragment();

        capturedImageFragment.show(getFragmentManager(), "capturedImageFragment");
    }

    private void startVideo() {
        MicroApplication application = ((MicroApplication) getApplication());

        String serverIP = application.getServerIp();
        String port = application.getStreamingPort();
        String folderName = application.getFolderName();

        String videoURL = "http://" + serverIP + ":" + port + "/?" + folderName;

        Log.d(TAG, videoURL);

        microVideo.setDisplayMode(MjpegView.SIZE_BEST_FIT);
        microVideo.setOverlayBackgroundColor(Color.BLACK);
        microVideo.setOverlayTextColor(Color.WHITE);
        microVideo.setOverlayPosition(MjpegView.POSITION_LOWER_RIGHT);
        microVideo.setOverlayPaint(new Paint());
        microVideo.setSource(videoURL);
    }
}