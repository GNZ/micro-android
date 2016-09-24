package com.gang.micro.utils.api;

import android.util.Log;

import retrofit2.Call;
import retrofit2.Response;


public abstract class ErrorLoggingCallback<T> implements SuccessfulResponseCallback<T> {

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccessfulResponse(call, response);
            return;
        }

        Log.e("Retrofit", "Error. Code: " + response.code() + ". Message: " + response.message());
    }

    @Override
    public void onFailure(Call<T> call, Throwable t) {
        Log.e("Retrofit", t.getMessage());
    }
}
