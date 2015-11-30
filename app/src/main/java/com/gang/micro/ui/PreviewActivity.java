package com.gang.micro.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.MediaController;
import android.widget.VideoView;

import com.gang.micro.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PreviewActivity extends AppCompatActivity {

    @Bind(R.id.toolbar) Toolbar toolbar;
    @Bind(R.id.micro_video) VideoView microVideo;
    private MediaController microController;
    private SharedPreferences settings;
    private SharedPreferences.Editor prefEditor;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        //
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        String defaultURL = getResources().getString(R.string.default_url);
        url = settings.getString("url","");
        if (url.equals("") || url==null){
            prefEditor = settings.edit();
            url = defaultURL;
            prefEditor.putString("url",url);
        }

        microController = new MediaController(this);

        microVideo.setMediaController(microController);
        microVideo.setVideoURI(Uri.parse(url));

        microVideo.start();



    }

    protected void onRestart(){
        super.onRestart();
        settings = PreferenceManager.getDefaultSharedPreferences(this);
        String newUrl = settings.getString("url",url);
        Log.d("PreviewActivity", newUrl);

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
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
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
}
