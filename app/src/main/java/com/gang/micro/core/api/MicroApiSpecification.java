package com.gang.micro.core.api;

import com.gang.micro.core.image.Image;

import retrofit.Call;
import retrofit.http.POST;

public interface MicroApiSpecification {

    @POST("/images")
    Call<Image> takePicture();
}
