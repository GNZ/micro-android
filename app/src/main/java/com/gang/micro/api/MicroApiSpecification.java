package com.gang.micro.api;

import com.gang.micro.image.Image;
import com.gang.micro.image.analysis.Analysis;

import java.util.List;
import java.util.UUID;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.DELETE;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.PUT;
import retrofit.http.Path;

public interface MicroApiSpecification {

    @GET("/images")
    Call<List<Image>> getImages();

    @POST("/images")
    Call<Image> captureImage();

    @GET("/images/{id}")
    Call<Image> getImage(@Path("id") UUID id);

    @PUT("/images/{id}")
    Call<Image> updateImage(@Path("id") UUID id, @Body Image image);

    @DELETE("/images/{id}")
    Call<Boolean> deleteImage(@Path("id") UUID id);

    @POST("/images/{id}/analyses")
    Call<Analysis> analyseImage(@Path("id") UUID id, @Body Analysis analysis);

}
