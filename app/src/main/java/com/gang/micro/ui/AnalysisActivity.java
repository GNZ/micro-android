package com.gang.micro.ui;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.widget.ImageView;

import com.gang.micro.R;

import java.io.File;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AnalysisActivity extends Activity {

    private String imageId;
    @Bind(R.id.imageView) ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
        ButterKnife.bind(this);
        imageId = getIntent().getStringExtra("imageId");
        //String path = PictureActivity.FOLDER +  File.separator + imageId + ".jpg";
        //setPic(path,image);

    }

    @OnClick(R.id.ok_button) void backToPreview(){
        finish();
    }

    private void setPic(String pathToImage, ImageView view) {
        // Get the dimensions of the View
        int targetW = view.getWidth();
        int targetH = view.getHeight();

        // Get the dimensions of the bitmap
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathToImage, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        // Determine how much to scale down the image
        int scaleFactor = Math.min(photoW/targetW, photoH/targetH);

        // Decode the image file into a Bitmap sized to fill the View
        bmOptions.inJustDecodeBounds = false;
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inPurgeable = true;

        Bitmap bitmap = BitmapFactory.decodeFile(pathToImage, bmOptions);
        view.setImageBitmap(bitmap);
    }
}
