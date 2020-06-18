package com.tda.finalyear.services;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
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

public interface SolutionService {
    @GET("/solution")
    Call<ResponseBody> getSolutions();

    @GET("/solution/std/{assignment}")
    Call<ResponseBody> getSolutionByAssignment(@Path("assignment") String assignment);

    @GET("/solution/{id}")
    Call<ResponseBody> getSolutionById(@Path("id") String id);

//    @GET("/{path}")
//    Call<ResponseBody> getFile(@Path("path") String path);

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

    @Multipart
    @POST("/solution")
    Call<ResponseBody> uploadSolution(@Part MultipartBody.Part assignmentFile, @Part("assignment") RequestBody assignment, @Part("student") RequestBody student, @Part("studentName") RequestBody name);

    @Multipart
    @PUT("/solution/{id}")
    Call<ResponseBody> updateSolution(@Path("id") String id, @Part MultipartBody.Part routine, @Part("title") RequestBody title, @Part("std") RequestBody std);

    @DELETE("/solution/{id}")
    Call<ResponseBody> deleteSolution(@Path("id") String id);
}
