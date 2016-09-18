package com.gang.micro.utils.api;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public interface SuccessfulResponseCallback<T> extends Callback<T> {

    void onSuccessfulResponse(Call<T> call, Response<T> response);
}
