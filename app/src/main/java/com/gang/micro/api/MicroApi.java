package com.gang.micro.api;

import android.content.Context;

import com.gang.micro.application.MicroApplication;
import com.gang.micro.constansts.ApplicationConsts;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;

public class MicroApi {

    private MicroApiSpecification api;

    public MicroApi(Context context) {
        MicroApplication app = ((MicroApplication) context.getApplicationContext());

        String ip = app.getServerIp();

        String baseUrl = app.getProtocol() + "://" + ip + ":" + app.getWebApplicationPort();

        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(ApplicationConsts.CLIENT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(ApplicationConsts.CLIENT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .build();

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
