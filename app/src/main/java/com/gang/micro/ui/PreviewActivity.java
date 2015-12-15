package com.gang.micro.ui;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.RelativeLayout;
import android.widget.VideoView;

import com.gang.micro.R;
import com.gang.micro.core.NSD.NSDConnection;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewActivity extends AppCompatActivity implements ChooseMicroscopeDialogFragment.OnCompleteListener {

    private static final String TAG = "PreviewActivity";
    private static final long TIME_TO_WAIT = 5000;
    private static final String FIX_URL = "/Something";

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.micro_video) VideoView microVideo;
    NSDConnection nsdConnection;
    private MediaController microController;
    private SharedPreferences settings;
    private LinearLayout.LayoutParams paramsNotFullscreen;
    private SharedPreferences.Editor prefEditor;
    private ChooseMicroscopeDialogFragment chooseDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        nsdConnection = new NSDConnection(this);

        chooseDialog = new ChooseMicroscopeDialogFragment();

        nsdConnection.discover();
        /*
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultURL = getResources().getString(R.string.default_url);
        url = settings.getString("url","");
        if (url.equals("") || url==null){
            prefEditor = settings.edit();
            url = defaultURL;
            prefEditor.putString("url",url);
        }
        */
        microController = new MediaController(this);

        microVideo.setMediaController(microController);
        //microVideo.setVideoURI(Uri.parse(url));

        //microVideo.start();

        waitDialog();



    }

    protected void onRestart(){
        super.onRestart();
        //settings = PreferenceManager.getDefaultSharedPreferences(this);
        //String newUrl = settings.getString("url",url);
        //Log.d("PreviewActivity", newUrl);
        //TODO what does it do here?
        /*
        if (!newUrl.equals(url) && !newUrl.equals("")) {
            url = newUrl;
            microVideo.setVideoURI(Uri.parse(url));
            microVideo.start();
        } else
        if (newUrl.equals("")||newUrl == null) {
            url = getResources().getString(R.string.default_url);
            microVideo.setVideoURI(Uri.parse(url));
            microVideo.start();
        } else {
            microVideo.resume();
        }*/
    }

    @Override
    protected void onStart(){
        super.onStart();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);


        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) //To fullscreen
        {
            paramsNotFullscreen=(LinearLayout.LayoutParams) microVideo.getLayoutParams();
            LinearLayout.LayoutParams params= null;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                params = new LinearLayout.LayoutParams(paramsNotFullscreen);
            } else {
                params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
            }
            params.setMargins(0, 0, 0, 0);
            params.height= ViewGroup.LayoutParams.MATCH_PARENT;
            params.width=ViewGroup.LayoutParams.MATCH_PARENT;
            microVideo.setLayoutParams(params);
            getSupportActionBar().hide();
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        }
        else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            microVideo.setLayoutParams(paramsNotFullscreen);
            getSupportActionBar().show();
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }



    @OnClick(R.id.fab) void selectFrame() {
        String picture = takeAndAnalize();
        seeTakenPicture(picture);
    }

    private void seeTakenPicture(String picture) {
        Intent pictureActivity = new Intent(PreviewActivity.this,PictureActivity.class);
        pictureActivity.putExtra("picture",picture);
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
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void waitDialog(){
        final ProgressDialog waitDialog = ProgressDialog.show(this,getResources().getString(R.string.wait_dialog_title),
                getResources().getString(R.string.wait_dialog_text),true);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(TIME_TO_WAIT);
                }catch (Exception e){
                    Log.d(TAG,e.getMessage());
                }
                //TODO show microscopes dialog
                chooseDialog.setMicroscopes(nsdConnection.getMicroscopes());
                waitDialog.dismiss();
                chooseDialog.show(getFragmentManager(),"ChooseMicroscopeDialogFragment");
            }
        }).start();
    }

    @Override
    public void onComplete(String IP) {
        startVideo(IP+FIX_URL);//TODO do it together?
    }

    private void startVideo(String url){
        microVideo.setVideoURI(Uri.parse(url));
        microVideo.start();
    }
}
