package com.gang.micro.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.gang.micro.R;
import com.gang.micro.core.MicroApplication;
import com.gang.micro.core.NSD.NSDConnection;
import com.gang.micro.core.mjpeg.MjpegInputStream;
import com.gang.micro.core.mjpeg.MjpegView;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.net.URI;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewActivity extends AppCompatActivity implements ChooseMicroscopeDialogFragment.OnCompleteListener {

    private static final String TAG = "PreviewActivity";
    private static final long TIME_TO_WAIT = 5000;

    @Bind(R.id.toolbar)
    Toolbar toolbar;
    @Bind(R.id.micro_video)
    MjpegView microVideo;
    NSDConnection nsdConnection;
    private SharedPreferences settings;
    private LinearLayout.LayoutParams paramsNotFullscreen;
    private SharedPreferences.Editor prefEditor;
    private ChooseMicroscopeDialogFragment chooseDialog;
    private String serviceName;
    private String protocol;
    private String port;
    private String folderName;
    private String videoURL;
    AsyncTask video;


    private int width = 640;
    private int height = 480;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        // TODO discover microscopes
        //nsdConnection = new NSDConnection(this, serviceName);
        //getParameters();
        //chooseDialog = new ChooseMicroscopeDialogFragment();
        //nsdConnection.discover();
        //waitDialog();

        videoURL = "http://88.96.248.198/mjpg/video.mjpg?camera=1";


        video =  new DoRead().execute(videoURL);

    }

    protected void onRestart() {
        super.onRestart();
        //microVideo.startPlayback();
        //video = new DoRead().execute(videoURL);

        //microVideo = (MjpegView) findViewById(R.id.micro_video);
        //new DoRead().execute(videoURL);
    }


    public void onPause() {
        super.onPause();

        microVideo.stopPlayback();
        //microVideo = null;
        //video.cancel(true);
        //video = null;
    }


    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) //To fullscreen
        {
            paramsNotFullscreen = (LinearLayout.LayoutParams) microVideo.getLayoutParams();
            LinearLayout.LayoutParams params = null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                params = new LinearLayout.LayoutParams(paramsNotFullscreen);
            } else {
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
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

        serviceName = settings.getString("service_name", "");
        Log.d(TAG, serviceName);
        protocol = settings.getString("protocol", "");
        port = settings.getString("port", "");
        folderName = settings.getString("folder", "");

        ((MicroApplication) getApplication()).setServiceName(serviceName);
        nsdConnection.setServiceName(serviceName);
        ((MicroApplication) getApplication()).setProtocol(protocol);
        ((MicroApplication) getApplication()).setPort(port);
        ((MicroApplication) getApplication()).setFolderName(folderName);


    }


    @OnClick(R.id.fab)
    void selectFrame() {
        String picture = takeAndAnalize();
        seeTakenPicture(picture);
        finish();
    }

    private void seeTakenPicture(String picture) {
        Intent pictureActivity = new Intent(PreviewActivity.this, PictureActivity.class);
        pictureActivity.putExtra("picture", picture);
        startActivity(pictureActivity);

    }


    private String takeAndAnalize() {
        //TODO action take and analize picture

        //TODO return the image url
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_preview, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent settingsActivity = new Intent(PreviewActivity.this, SettingsActivity.class);
            startActivity(settingsActivity);
            finish();
            return true;
        }
        if (id == R.id.action_refresh) {
            getParameters();
            //nsdConnection.setServiceName(serviceName);
            //nsdConnection.stopDiscover();
            //nsdConnection.discover();
            nsdConnection.stopDiscover();
            //nsdConnection.setServiceName(serviceName);
            //nsdConnection.discover();
            waitDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void waitDialog() {
        final ProgressDialog waitDialog = ProgressDialog.show(this, getResources().getString(R.string.wait_dialog_title),
                getResources().getString(R.string.wait_dialog_text), true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(TIME_TO_WAIT);
                } catch (Exception e) {
                    Log.d(TAG, e.getMessage());
                }
                //TODO show microscopes dialog
                chooseDialog.setMicroscopes(nsdConnection.getMicroscopes());
                waitDialog.dismiss();
                chooseDialog.show(getFragmentManager(), "ChooseMicroscopeDialogFragment");
            }
        }).start();
    }

    @Override
    public void onComplete(String serverIP) {
        ((MicroApplication) getApplication()).setServerIP(serverIP);
        startVideo();//TODO do it together?
    }

    private void startVideo() {
        /*
        String serverIP = ((MicroApplication) getApplication()).getServerIP();
        String port = ((MicroApplication) getApplication()).getPort();
        videoURL = protocol + serverIP + ":" + port + "/" + folderName;
        Log.d(TAG, videoURL);
        microVideo.setVideoURI(Uri.parse(videoURL));
        microVideo.start();
        */
    }

    public class DoRead extends AsyncTask<String, Void, MjpegInputStream> {

        HttpResponse res = null;
        MjpegInputStream inputStream;

        protected MjpegInputStream doInBackground(String... url) {
            //TODO: if camera has authentication deal with it and don't just not work
            DefaultHttpClient httpclient = new DefaultHttpClient();
            Log.d(TAG, "1. Sending http request");
            try {
                res = httpclient.execute(new HttpGet(URI.create(url[0])));
                Log.d(TAG, "2. Request finished, status = " + res.getStatusLine().getStatusCode());
                if(res.getStatusLine().getStatusCode()==401){
                    //You must turn off camera User Access Control before this will work
                    return null;
                }
                inputStream = new MjpegInputStream(res.getEntity().getContent());
                return inputStream;
            } catch (ClientProtocolException e) {
                e.printStackTrace();
                Log.d(TAG, "Request failed-ClientProtocolException", e);
                //Error connecting to camera
            } catch (IOException e) {
                e.printStackTrace();
                Log.d(TAG, "Request failed-IOException", e);
                //Error connecting to camera
            }

            return null;
        }

        protected void onPostExecute(MjpegInputStream result) {
            microVideo.setSource(result);
            microVideo.setDisplayMode(MjpegView.SIZE_BEST_FIT);
            microVideo.showFps(true);
        }
    }
}
