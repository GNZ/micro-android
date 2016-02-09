package com.gang.micro.core.image.analysis;

import android.content.Context;
import android.os.AsyncTask;

import com.gang.micro.core.api.MicroApi;
import com.gang.micro.core.api.MicroApiSpecification;
import com.gang.micro.core.image.Image;

import java.io.IOException;

public class AnalysisAsyncTask extends AsyncTask<Void, Void, Analysis> {

    private final Context context;
    private final AnalysisResultListener analysisResultListener;
    private final Image image;

    public AnalysisAsyncTask(Context context, AnalysisResultListener analysisResultListener, Image image) {
        this.context = context;
        this.analysisResultListener = analysisResultListener;
        this.image = image;
    }

    @Override
    protected Analysis doInBackground(Void... params) {

        MicroApiSpecification api = new MicroApi(context).getApi();

        Analysis analysis = new Analysis(AnalysisType.BLOOD__RED_CELL_COUNT);

        Analysis result = null;

        try {
            result = api.analyseImage(image.getId(), analysis).execute().body();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @Override
    protected void onPostExecute(Analysis analysis) {
        analysisResultListener.setAnalysis(analysis);
    }
}
