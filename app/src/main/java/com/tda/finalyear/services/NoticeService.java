package com.tda.finalyear.services;
import com.tda.finalyear.models.Notice;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface NoticeService {
    @GET("/notice")
    Call<ResponseBody> getNoticeList();

    @GET("/notice/{id}")
    Call<ResponseBody> getNoticeById(@Path("id") String id);

    @POST("/notice")
    Call<ResponseBody> addNotice(@Body Notice notice);

    @PUT("/notice/{id}")
    Call<ResponseBody> editNotice(@Path("id") String id, @Body Notice notice);

    @DELETE("/notice/{id}")
    Call<ResponseBody> deleteNotice(@Path("id") String id);
}
