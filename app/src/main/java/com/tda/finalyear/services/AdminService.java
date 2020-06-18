package com.tda.finalyear.services;

import com.tda.finalyear.models.Admin;
import com.tda.finalyear.models.Student;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface AdminService {
    @GET("/admin")
    Call<ResponseBody> getAdmin();

    @POST("/admin/signup")
    Call<ResponseBody> signUpAdmin(@Body Admin admin);

    @FormUrlEncoded
    @POST("/admin/login")
    Call<ResponseBody> loginAdmin(@Field("email") String email, @Field("password") String password);

    @PUT("/admin/{id}")
    Call<ResponseBody> updateAdmin(@Path("id") String id, @Body Admin admin);
}
