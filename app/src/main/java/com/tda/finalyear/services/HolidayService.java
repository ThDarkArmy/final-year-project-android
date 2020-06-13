package com.tda.finalyear.services;

import com.tda.finalyear.models.Holiday;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface HolidayService {
    @GET("/holiday")
    Call<ResponseBody> getHolidayList();

    @GET("/holiday/{id}")
    Call<ResponseBody> getHolidayById(@Path("id") String id);

    @POST("/holiday")
    Call<ResponseBody> addHoliday(@Body Holiday holiday);

    @PUT("/holiday/{id}")
    Call<ResponseBody> editHoliday(@Path("id") String id, @Body Holiday holiday);

    @DELETE("/holiday/{id}")
    Call<ResponseBody> deleteHoliday(@Path("id") String id);
}
