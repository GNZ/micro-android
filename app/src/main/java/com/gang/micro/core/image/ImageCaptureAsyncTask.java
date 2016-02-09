package com.gang.micro.core.image;

import android.content.Context;
import android.os.AsyncTask;
import android.widget.Toast;

import com.gang.micro.core.api.MicroApi;
import com.gang.micro.core.api.MicroApiSpecification;
import com.gang.micro.R;

import java.io.IOException;

public class ImageCaptureAsyncTask extends AsyncTask<Void, Void, Image> {

    private final Context context;
    private final ImageContainer imageContainer;

    public ImageCaptureAsyncTask(Context context, ImageContainer imageContainer) {
        this.context = context;
        this.imageContainer = imageContainer;
    }

    @Override
    protected Image doInBackground(Void... params) {

        MicroApiSpecification api = new MicroApi(context).getApi();

        Image imageResponse = null;

        try {
            imageResponse = api.captureImage().execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return imageResponse;
    }

    @Override
    protected void onPostExecute(Image image) {
        if (image != null)
            imageContainer.setImage(image);
        else Toast.makeText(context, R.string.error_taking_picture,Toast.LENGTH_LONG).show();
    }
}
