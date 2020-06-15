package com.tda.finalyear.services;

import android.net.Uri;

import com.tda.finalyear.models.Exam;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ExamService {
    @GET("/exam")
    Call<ResponseBody> getExamList();

    @GET("/exam/{id}")
    Call<ResponseBody> getExamById(@Path("id") String id);

    @Multipart
    @POST("/exam")
    Call<ResponseBody> addExam(@Part MultipartBody.Part routine, @Part("title") RequestBody title, @Part("std") RequestBody std);

    @FormUrlEncoded
    @PUT("/exam/{id}")
    Call<ResponseBody> editExam(@Path("id") String id, @Field("title") String title, @Field("std") String std, @Field("routine") Uri routine);

    @DELETE("/exam/{id}")
    Call<ResponseBody> deleteExam(@Path("id") String id);

}
