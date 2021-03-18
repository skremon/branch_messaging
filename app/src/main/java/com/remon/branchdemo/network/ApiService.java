package com.remon.branchdemo.network;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.remon.branchdemo.data.Message;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Header;

public interface ApiService {

    String AUTH_TOKEN = "HoCP26THsUA5dZxG89R09w";
    String BASE_URL = "https://android-messaging.branch.co";

    @GET("api/messages")
    Call<List<Message>> getAllMessages(@Header("X-Branch-Auth-Token") String authToken);

    static ApiService create() {
        Gson gson = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES).create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        return retrofit.create(ApiService.class);
    }
}
