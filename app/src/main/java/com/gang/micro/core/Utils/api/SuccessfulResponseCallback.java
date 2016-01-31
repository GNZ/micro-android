package com.gang.micro.core.Utils.api;

import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

public interface SuccessfulResponseCallback<T> extends Callback<T> {

    void onSuccessfulResponse(Response<T> response, Retrofit retrofit);
}
