package com.gang.micro.core.mjpeg;

import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MjpegViewInitializer extends AsyncTask<String, Void, MjpegInputStream> {

    private MjpegView mjpegView;

    public MjpegViewInitializer(MjpegView mjpegView) {
        this.mjpegView = mjpegView;
    }

    @Override
    protected MjpegInputStream doInBackground(String... stringUrl) {

        InputStream inputStream = null;
        try {
            URL url = new URL(stringUrl[0]);

            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");

            inputStream = connection.getInputStream();

        } catch (IOException e) {
            return null;
        }

        return new MjpegInputStream(inputStream);
    }

    @Override
    protected void onPostExecute(MjpegInputStream mjpegInputStream) {

        if (mjpegInputStream == null) {
            Toast.makeText(mjpegView.getContext().getApplicationContext(),
                    "Error al conectar al microscopio",
                    Toast.LENGTH_SHORT)
                    .show();
            return;
        }

        mjpegView.setSource(mjpegInputStream);
        mjpegView.setDisplayMode(MjpegView.SIZE_BEST_FIT);
        mjpegView.showFps(true);
    }
}
