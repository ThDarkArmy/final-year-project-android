package com.tda.finalyear.api;

import com.tda.finalyear.services.AdminService;
import com.tda.finalyear.services.EventService;
import com.tda.finalyear.services.ExamService;
import com.tda.finalyear.services.FacilityService;
import com.tda.finalyear.services.FeeService;
import com.tda.finalyear.services.HolidayService;
import com.tda.finalyear.services.NoticeService;
import com.tda.finalyear.services.StudentService;
import com.tda.finalyear.services.TeacherService;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {
    private static final String BASE_URL = "http://192.168.43.172:5000";
    private static RetrofitClient retrofitClient;
    private Retrofit retrofit;

    private RetrofitClient(){
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static synchronized RetrofitClient getInstance(){
        if(retrofitClient==null){
            retrofitClient = new RetrofitClient();
        }
        return retrofitClient;
    }

    public StudentService getStudentService(){
        return retrofit.create(StudentService.class);
    }

    public TeacherService getTeacherService() {
        return retrofit.create(TeacherService.class);
    }

    public AdminService getAdminService(){
        return retrofit.create(AdminService.class);
    }

    public HolidayService getHolidayService(){ return retrofit.create(HolidayService.class);}

    public EventService getEventService(){ return retrofit.create(EventService.class); }

    public NoticeService getNoticeService(){ return retrofit.create(NoticeService.class); }

    public FacilityService getFacilityService(){ return retrofit.create(FacilityService.class); }
    public FeeService getFeeService(){ return retrofit.create(FeeService.class); }
    public ExamService getExamService(){ return retrofit.create(ExamService.class); }
}
