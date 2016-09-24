package com.gang.micro.gallery.remote;

import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.gang.micro.application.MicroApplication;
import com.gang.micro.R;
import com.gang.micro.dagger.BaseActivity;
import com.gang.micro.dagger.BaseActivityComponent;
import com.gang.micro.image.Image;
import com.gang.micro.microscope.Microscope;
import com.gang.micro.microscope.MicroscopeProvider;
import com.gang.micro.utils.io.ImageIO;

import javax.inject.Inject;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RemoteGalleryActivity extends BaseActivity implements RemoteGalleryItemFragmentListener {

    @Bind(R.id.remote_gallery_activity_toolbar)
    Toolbar toolbar;

    private boolean saving = false;

    @Inject
    MicroscopeProvider microscopeProvider;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_gallery);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);

        setTitle("Imágenes en " + microscopeProvider.getMicroscope().getName());
    }

    @NonNull
    @Override
    protected BaseActivityComponent createActivityComponent() {
        final RemoteGalleryActivityComponent remoteGalleryActivityComponent =
                DaggerRemoteGalleryActivityComponent
                .builder()
                .applicationComponent(MicroApplication.getAppComponent(getApplication()))
                .build();
        remoteGalleryActivityComponent.inject(this);
        return remoteGalleryActivityComponent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void saveImageInLocalGallery(final Image image, String url) {
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
        if (saving) {
            Toast.makeText(this, R.string.saving_image, Toast.LENGTH_SHORT).show();
        } else {
            super.onBackPressed();
        }
    }
}
