package com.tda.finalyear.services;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

public interface AssignmentService {
    @GET("/assignment")
    Call<ResponseBody> getAssignmentList();

    @GET("/assignment/std/{std}")
    Call<ResponseBody> getAssignmentByClass(@Path("std") String std);

    @GET("/assignment/{id}")
    Call<ResponseBody> getAssignmentById(@Path("id") String id);

    @GET("/{path}")
    Call<ResponseBody> getFile(@Path("path") String path);

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

    @Multipart
    @POST("/assignment")
    Call<ResponseBody> uploadAssignment(@Part MultipartBody.Part assignmentFile, @Part("title") RequestBody title, @Part("std") RequestBody std);

    @Multipart
    @PUT("/assignment/{id}")
    Call<ResponseBody> updateAssignment(@Path("id") String id, @Part MultipartBody.Part routine, @Part("title") RequestBody title, @Part("std") RequestBody std);

    @DELETE("/assignment/{id}")
    Call<ResponseBody> deleteAssignment(@Path("id") String id);
}
