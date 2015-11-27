package com.gang.micro.UI;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


import com.gang.micro.R;
public class PictureActivity extends Activity {

    @Bind(R.id.micro_picture) ImageView microPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        ButterKnife.bind(this);


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
