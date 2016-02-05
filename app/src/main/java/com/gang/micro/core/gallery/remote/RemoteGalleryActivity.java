package com.gang.micro.core.gallery.remote;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

import com.gang.micro.R;

import butterknife.Bind;
import butterknife.ButterKnife;

public class RemoteGalleryActivity extends AppCompatActivity {

    @Bind(R.id.remote_gallery_activity_toolbar)
    Toolbar toolbar;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remote_gallery);

        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }
}
