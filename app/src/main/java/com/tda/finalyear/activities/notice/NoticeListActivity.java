package com.tda.finalyear.activities.notice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.event.EventListActivity;
import com.tda.finalyear.adapter.NoticeAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.EventList;
import com.tda.finalyear.models.NoticeList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_list);
        recyclerView = findViewById(R.id.notice_recycler);
        getNoticeList();
    }

    public void getNoticeList(){
        RetrofitClient.getInstance().getNoticeService().getNoticeList().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    NoticeList noticeList = new GsonBuilder().create().fromJson(response.body().string(), NoticeList.class);
                    NoticeAdapter noticeAdapter = new NoticeAdapter(NoticeListActivity.this, noticeList);
                    recyclerView.setLayoutManager(new LinearLayoutManager(NoticeListActivity.this));
                    recyclerView.setAdapter(noticeAdapter);
                    Log.i("notice", noticeList.getNotices().toString());
                }catch(Exception e){
                    Toast.makeText(NoticeListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("notice", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(NoticeListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("notice", t.getMessage());
            }
        });
    }
}