package com.wja.myapplication.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by hanke on 2018/2/9.
 */

public class ApiRetrofit {
    private static final String BASE="http://is.snssdk.com/";
    private ApiService apiService;
    private static ApiRetrofit apiRetrofit;


    public static ApiRetrofit newIntance(){
        if (apiRetrofit==null){
            synchronized (ApiRetrofit.class){
                if (apiRetrofit==null){
                    apiRetrofit=new ApiRetrofit();
                }
            }
        }
        return apiRetrofit;
    }

    private ApiRetrofit(){
        OkHttpClient client=new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(10,TimeUnit.SECONDS)
                .build();
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(BASE)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        apiService=retrofit.create(ApiService.class);
    }

    public ApiService getApiService(){
        return apiService;
    }
}
