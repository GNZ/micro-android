package com.gang.micro.ui;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


import com.gang.micro.R;
import com.gang.micro.core.MicroApplication;
import com.gang.micro.core.api.MicroApi;
import com.gang.micro.core.image.Image;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

public class PictureActivity extends Activity {

    private static final String TAG = "PictureActivity";
    static final String FOLDER = Environment.getExternalStorageDirectory()+File.separator+"micro";
    static final String FIX_URL = "/images/output/";
    @Bind(R.id.micro_picture)
    ImageView microPicture;
    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);

        loadImage();
    }

    private void loadImage() {
        Call<Image> call = MicroApi.getInstance().takePicture();

        call.enqueue(new Callback<Image>() {
            @Override
            public void onResponse(Response<Image> response, Retrofit retrofit) {

                id = response.body().getId().toString();
                String serverIP = ((MicroApplication)getApplication()).getServerIP();

                Picasso.with(getBaseContext())
                        .load("http://"+ serverIP + FIX_URL + id + ".jpg")
                        .into(microPicture);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    @OnClick(R.id.discard_button)
    void discardPicture() {
        //TODO do something before close the activity
        finish();
    }

    @OnClick(R.id.save_button)
    void savePicture() {
        File directory = createFolder();
        saveImage(id,directory);
        Intent analysisActivity = new Intent(PictureActivity.this, AnalysisActivity.class);
        //TODO put something in the intent?
        analysisActivity.putExtra("imageId",id);
        startActivity(analysisActivity);
        finish();
    }

    private File createFolder() {
        File exportDir = new File(FOLDER);
        if (!exportDir.exists()) {
            exportDir.mkdirs();
        }
        return exportDir;
    }

    private void saveImage(String name,File directory) {
        //Get the image from the ImageView
        microPicture.buildDrawingCache();
        Bitmap bm = microPicture.getDrawingCache();
        //Save the picture
        OutputStream fOut = null;
        Uri outputFileUri;
        try {
            File sdImageMainDirectory = new File(directory, name+".jpg");
            outputFileUri = Uri.fromFile(sdImageMainDirectory);
            fOut = new FileOutputStream(sdImageMainDirectory);
        } catch (Exception e) {
            Toast.makeText(this, "Error occured. Please try again later.",
                    Toast.LENGTH_SHORT).show();
        }

        try {
            bm.compress(Bitmap.CompressFormat.JPEG, 100, fOut);
            fOut.flush();
            fOut.close();
        } catch (Exception e) {
            Log.d(TAG,e.getMessage() );
        }
    }

}
