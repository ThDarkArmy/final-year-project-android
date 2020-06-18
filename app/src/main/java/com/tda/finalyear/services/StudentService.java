package com.tda.finalyear.services;

import com.tda.finalyear.models.Student;
import com.tda.finalyear.models.Teacher;

import java.util.HashMap;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface StudentService {
    @GET("/student")
    Call<ResponseBody> getStudent();

    @GET("/student/std/{std}")
    Call<ResponseBody> getStudentByClass(@Path("std") String std);

    @POST("/student/signup")
    Call<ResponseBody> signUpStudent(@Body Student student);

    @FormUrlEncoded
    @POST("/student/login")
    Call<ResponseBody> loginStudent(@Field("email") String email, @Field("password") String password);

    @PUT("/student/{id}")
    Call<ResponseBody> updateStudent(@Path("id") String id, @Body Student student);

    @POST("/feepayment")
    Call<ResponseBody> feePayment (Map<String, String> studentId);

    @GET("/student/defaulter")
    Call<ResponseBody> getDefaulterList ();

}
