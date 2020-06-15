package com.tda.finalyear.services;

import com.tda.finalyear.models.Event;
import com.tda.finalyear.models.Fee;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FeeService {
    @GET("/fee")
    Call<ResponseBody> getFeeList();

    @GET("/fee/std/{std}")
    Call<ResponseBody> getFeeByClass(@Path("std") String std);

    @GET("/fee/{id}")
    Call<ResponseBody> getFeeById(@Path("id") String id);

    @POST("/fee")
    Call<ResponseBody> addFee(@Body Fee fee);

    @PUT("/fee/{id}")
    Call<ResponseBody> editFee(@Path("id") String id, @Body Fee fee);

    @DELETE("/fee/{id}")
    Call<ResponseBody> deleteFee(@Path("id") String id);
}
