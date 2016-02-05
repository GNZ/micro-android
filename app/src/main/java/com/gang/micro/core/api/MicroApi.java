package com.gang.micro.core.api;

import android.content.Context;

import com.gang.micro.core.MicroApplication;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

public class MicroApi {

    private MicroApiSpecification api;

    public MicroApi(Context context) {
        String ip = ((MicroApplication) context.getApplicationContext()).getServerIP();
        String baseUrl = "http://" + ip + ":5000";

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
