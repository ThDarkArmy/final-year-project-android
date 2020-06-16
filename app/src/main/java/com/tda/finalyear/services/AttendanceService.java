package com.tda.finalyear.services;

import java.util.List;
import java.util.Map;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AttendanceService {
    @POST("/attendance/{std}")
    Call<ResponseBody> makeAttendance(@Path ("std") String std, @Body Map<String, List<String>> ids);
}
