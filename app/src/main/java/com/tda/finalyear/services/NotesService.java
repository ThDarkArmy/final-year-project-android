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

public interface NotesService {
    @GET("/notes")
    Call<ResponseBody> getNotesList();

    @GET("/notes/std/{std}")
    Call<ResponseBody> getNotesByClass(@Path("std") String std);

    @GET("/notes/{id}")
    Call<ResponseBody> getNotesById(@Path("id") String id);

    @GET("/{path}")
    Call<ResponseBody> getFile(@Path("path") String path);

    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlSync(@Url String fileUrl);

    @Streaming
    @GET
    Call<ResponseBody> downloadFileWithDynamicUrlAsync(@Url String fileUrl);

    @Multipart
    @POST("/notes")
    Call<ResponseBody> uploadNotes(@Part MultipartBody.Part assignmentFile, @Part("title") RequestBody title, @Part("std") RequestBody std);

    @Multipart
    @PUT("/notes/{id}")
    Call<ResponseBody> updateNotes(@Path("id") String id, @Part MultipartBody.Part routine, @Part("title") RequestBody title, @Part("std") RequestBody std);

    @DELETE("/notes/{id}")
    Call<ResponseBody> deleteNotes(@Path("id") String id);
}
