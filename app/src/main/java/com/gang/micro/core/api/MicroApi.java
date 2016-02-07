package com.gang.micro.core.api;

import android.content.Context;

import com.gang.micro.core.MicroApplication;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

public class MicroApi {

    private MicroApiSpecification api;

    public MicroApi(Context context) {
        MicroApplication app = ((MicroApplication) context.getApplicationContext());

        String ip = app.getServerIp();

        String baseUrl = app.getProtocol() + "://" + ip + ":" + app.getWebApplicationPort();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        api = retrofit.create(MicroApiSpecification.class);
    }

    public MicroApiSpecification getApi() {
        return api;
    }
}
