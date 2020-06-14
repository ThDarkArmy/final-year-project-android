package com.tda.finalyear.services;

import com.tda.finalyear.models.Facility;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface FacilityService {
    @GET("/facility")
    Call<ResponseBody> getFacilityList();

    @GET("/facility/{id}")
    Call<ResponseBody> getFacilityById(@Path("id") String id);

    @POST("/facility")
    Call<ResponseBody> addFacility(@Body Facility facility);

    @PUT("/facility/{id}")
    Call<ResponseBody> editFacility(@Path("id") String id, @Body Facility facility);

    @DELETE("/facility/{id}")
    Call<ResponseBody> deleteFacility(@Path("id") String id);
}
