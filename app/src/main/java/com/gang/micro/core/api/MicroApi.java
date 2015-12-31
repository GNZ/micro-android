package com.gang.micro.core.api;

import android.content.Context;

import com.gang.micro.core.MicroApplication;

import retrofit.JacksonConverterFactory;
import retrofit.Retrofit;

public class MicroApi {

    /*
    static MicroApiSpecification instance = initializeApi();

    public static MicroApiSpecification getInstance() {
        return instance;
    }

    static MicroApiSpecification initializeApi() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.0.116:5000")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        return retrofit.create(MicroApiSpecification.class);
    }
    */

    private MicroApiSpecification api;

    public MicroApi (Context context){
        String ip = ((MicroApplication)context.getApplicationContext()).getServerIP();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://"+ip+":5000")
                .addConverterFactory(JacksonConverterFactory.create())
                .build();

        api =  retrofit.create(MicroApiSpecification.class);
    }

    public MicroApiSpecification getApi (){
        return api;
    }
}
