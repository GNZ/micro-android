package com.gang.micro.core.gallery.remote;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gang.micro.R;
import com.gang.micro.core.MicroApplication;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.microscope.Microscope;
import com.gang.micro.core.utils.io.ImageIO;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RemoteGalleryActivity extends AppCompatActivity implements RemoteGalleryItemFragmentListener {

    @Bind(R.id.remote_gallery_activity_toolbar)
    Toolbar toolbar;

    private boolean saving = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_gallery);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        Microscope microscope = ((MicroApplication) getApplication()).getCurrentMicroscope();

        setTitle("Imágenes en " + microscope.getName());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void saveImageInLocalGallery(final Image image, String url){
        Glide.with(this)
                .load(url)
                .asBitmap()
                .toBytes(Bitmap.CompressFormat.JPEG, 100)
                .into(new SimpleTarget<byte[]>() {
                    @Override
                    public void onResourceReady(final byte[] resource, GlideAnimation<? super byte[]> glideAnimation) {
                        new AsyncTask<Void, Void, Void>() {
                            @Override
                            protected void onPreExecute() {
                                saving = true;
                            }

                            @Override
                            protected Void doInBackground(Void... params) {

                                ImageIO.savePictureFromByteArray(resource, image);

                                ImageIO.saveImage(image);

                                return null;
                            }
                            @Override
                            protected void onPostExecute(Void aVoid) {
                                String msg = getResources().getString(R.string.image_save) + " " + image.getName();

                                Toast.makeText(RemoteGalleryActivity.this, msg, Toast.LENGTH_LONG).show();

                                ((MicroApplication) RemoteGalleryActivity.this.getApplication())
                                        .getLocalGalleryAdapter()
                                        .add(image);
                                saving = false;
                            }
                        }.execute();
                    }
                });
    }

    @Override
    public void onBackPressed() {
        if (saving){
            Toast.makeText(this, R.string.saving_image, Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
