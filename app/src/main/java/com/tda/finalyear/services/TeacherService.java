package com.tda.finalyear.services;

import com.tda.finalyear.models.Student;
import com.tda.finalyear.models.Teacher;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface TeacherService {
    @GET("/teacher")
    Call<ResponseBody> getTeachers();

    @POST("/teacher/signup")
    Call<ResponseBody> signUpTeacher(@Body Teacher teacher);

    @FormUrlEncoded
    @POST("/teacher/login")
    Call<ResponseBody> loginTeacher(@Field("email") String email, @Field("password") String password);

    @PUT("/teacher/{id}")
    Call<ResponseBody> updateTeacher(@Path("id") String id, @Body Teacher teacher);
}
