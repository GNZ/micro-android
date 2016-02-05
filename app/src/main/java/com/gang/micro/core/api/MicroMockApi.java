package com.gang.micro.core.api;

import android.content.Context;

import com.gang.micro.core.MicroApplication;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

public class MicroMockApi {

    private MicroApiSpecification api;

    public MicroMockApi(Context context) {
        String ip = ((MicroApplication) context.getApplicationContext()).getServerIP();
        String baseUrl = "http://:5000";

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
