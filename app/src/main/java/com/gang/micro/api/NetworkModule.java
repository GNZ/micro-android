package com.gang.micro.api;

import com.gang.micro.BuildConfig;
import com.gang.micro.application.MicroApplication;
import com.gang.micro.constansts.ApplicationConsts;

import java.util.concurrent.TimeUnit;

import javax.inject.Named;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

@Module
public class NetworkModule {


    @Provides
    @Singleton
    public HttpLoggingInterceptor provideHttpLoggingInterceptor() {
        final HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BuildConfig.DEBUG ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE);
        return logging;
    }

    @Provides
    @Singleton
    OkHttpClient provideOkHttpClient(HttpLoggingInterceptor loggingInterceptor) {
        return new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .connectTimeout(ApplicationConsts.CLIENT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .readTimeout(ApplicationConsts.CLIENT_TIME_OUT_SECONDS, TimeUnit.SECONDS)
                .build();
    }

    @Provides
    @Singleton
    @Named("base_url")
    String getBaseUrl(MicroApplication microApplication) {
        return microApplication.getProtocol() + "://" +
                microApplication.getServerIp() + ":" +
                microApplication.getWebApplicationPort();
    }

    @Provides
    @Singleton
    MicroApiSpecification provideGbfsApiService(OkHttpClient client, @Named("base_url") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(JacksonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build()
                .create(MicroApiSpecification.class);
    }


}
