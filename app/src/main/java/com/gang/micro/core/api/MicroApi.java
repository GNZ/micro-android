package com.gang.micro.core.api;

import android.content.Context;

import com.gang.micro.core.MicroApplication;
import com.squareup.okhttp.OkHttpClient;

import java.util.concurrent.TimeUnit;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

public class MicroApi {

    private MicroApiSpecification api;

    public MicroApi(Context context) {
        MicroApplication app = ((MicroApplication) context.getApplicationContext());

        String ip = app.getServerIp();

        String baseUrl = app.getProtocol() + "://" + ip + ":" + app.getWebApplicationPort();

        final OkHttpClient okHttpClient = new OkHttpClient();
        okHttpClient.setReadTimeout(60, TimeUnit.SECONDS);
        okHttpClient.setConnectTimeout(60, TimeUnit.SECONDS);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .client(okHttpClient)
                .build();

        api = retrofit.create(MicroApiSpecification.class);
    }

    public MicroApiSpecification getApi() {
        return api;
    }
}
