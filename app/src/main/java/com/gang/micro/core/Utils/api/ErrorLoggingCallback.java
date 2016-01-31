package com.gang.micro.core.Utils.api;

import android.util.Log;

import retrofit.Response;
import retrofit.Retrofit;

public abstract class ErrorLoggingCallback<T> implements SuccessfulResponseCallback<T> {

    @Override
    public void onResponse(Response<T> response, Retrofit retrofit) {
        if (response.isSuccess()) {
            onSuccessfulResponse(response, retrofit);
            return;
        }

        Log.e("Retrofit", "Error. Code: " + response.code() + ". Message: " + response.message());
    }

    @Override
    public void onFailure(Throwable t) {
        Log.e("Retrofit", t.getMessage());
    }
}
