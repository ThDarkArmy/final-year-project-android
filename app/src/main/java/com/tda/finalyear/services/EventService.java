package com.tda.finalyear.services;

import com.tda.finalyear.models.Event;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface EventService {
    @GET("/event")
    Call<ResponseBody> getEventList();

    @GET("/event/{id}")
    Call<ResponseBody> getEventById(@Path("id") String id);

    @POST("/event")
    Call<ResponseBody> addEvent(@Body Event event);

    @PUT("/event/{id}")
    Call<ResponseBody> editEvent(@Path("id") String id, @Body Event event);

    @DELETE("/event/{id}")
    Call<ResponseBody> deleteEvent(@Path("id") String id);

}
