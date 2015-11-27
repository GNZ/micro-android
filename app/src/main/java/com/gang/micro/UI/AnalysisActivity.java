package com.gang.micro.UI;

import android.app.Activity;
import android.os.Bundle;

import com.gang.micro.R;

import butterknife.OnClick;

public class AnalysisActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_analysis);
    }

    @OnClick(R.id.ok_button) void backToPreview(){
        finish();
    }
}
