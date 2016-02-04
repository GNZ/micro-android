package com.gang.micro.ui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.gang.micro.R;
import com.gang.micro.core.image.Image;
import com.gang.micro.core.utils.io.ImageIO;

import java.io.IOException;

import butterknife.Bind;
import butterknife.ButterKnife;

public class ViewAnalysisActivity extends AppCompatActivity {

    static final String TAG = "ViewAnalysisActivity";
    public static final String EXTRA_IMAGE_ID = "image_id";

    @Bind(R.id.imageView)
    ImageView imageView;

    @Bind(R.id.name_textView)
    TextView nameTextView;

    @Bind(R.id.date_textView)
    TextView dateTextView;

    @Bind(R.id.count_textView)
    TextView countTextView;

    Image image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_viewanalysis);
        ButterKnife.bind(this);

        String imageId = getIntent().getStringExtra(ViewAnalysisActivity.EXTRA_IMAGE_ID);

        ImageIO imageIO = new ImageIO();
        try {
            image = imageIO.readImage(ImageIO.FOLDER + imageId);

            imageView.setImageBitmap(image.getBitmap());
            nameTextView.setText(image.getId().toString().substring(0, 6));
            dateTextView.setText(image.getCreated_at().toString());
            countTextView.setText(image.getAnalyses().get(0).getResult());

        }catch (IOException e){
            Toast.makeText(ViewAnalysisActivity.this, R.string.error_loding_image, Toast.LENGTH_LONG).show();
        }
    }
}