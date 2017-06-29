package com.sfeir.sfeircra.clientapi;

import com.sfeir.sfeircra.cra.Cra;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface CraClientApi {
    @FormUrlEncoded
    @POST("/api/login")
    Call<String> login(@Field("login") String login, @Field("password") String password);

    @GET("/api/cra")
    Call<Cra> get(@Query("token") String token);

    @GET("/api/cra/{month}-{year}")
    Call<Cra> getMonth(@Path("month") String month, @Path("month") String year, @Query("token") String token);

    @POST("/api/cra")
    Call<Void> update(@Body Cra cra);
}
