package com.tda.finalyear.services;

import com.tda.finalyear.models.Student;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface StudentService {
    @GET("/student")
    Call<ResponseBody> getStudent();

    @POST("/student/signup")
    Call<ResponseBody> signUpStudent(@Body Student student);

    @FormUrlEncoded
    @POST("/student/login")
    Call<ResponseBody> loginStudent(@Field("email") String email, @Field("password") String password);
}
