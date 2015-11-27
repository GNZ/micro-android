package com.gang.micro.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;


import com.gang.micro.R;
import com.gang.micro.core.api.MicroApi;
import com.gang.micro.core.image.Image;
import com.squareup.picasso.Picasso;

public class PictureActivity extends Activity {

    @Bind(R.id.micro_picture) ImageView microPicture;

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

                String id = response.body().getId().toString();

                Picasso.with(getBaseContext())
                        .load("http://192.168.0.116/images/output/" + id + ".jpg")
                        .into(microPicture);
            }

            @Override
            public void onFailure(Throwable t) {
            }
        });
    }

    @OnClick(R.id.discard_button) void discardPicture() {
        //TODO do something before close the activity
        finish();
    }

    @OnClick(R.id.save_button) void savePicture () {
        Intent analysisActivity = new Intent(PictureActivity.this,AnalysisActivity.class);
        //TODO put something in the intent?
        startActivity(analysisActivity);
        finish();
    }
}
