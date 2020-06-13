package com.tda.finalyear.activities.holiday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.adapter.HolidayAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.HolidayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HolidayListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday_list);
        recyclerView = findViewById(R.id.holiday_recycler);
        getHolidayList();

    }

    public void getHolidayList() {
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getHolidayService()
                .getHolidayList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    try {
                        Gson gson = new GsonBuilder().create();
                        HolidayList holidayList = gson.fromJson(response.body().string(), HolidayList.class);
                        HolidayAdapter holidayAdapter = new HolidayAdapter(HolidayListActivity.this, holidayList);

                        recyclerView.setLayoutManager(new LinearLayoutManager(HolidayListActivity.this));
                        recyclerView.setAdapter(holidayAdapter);
                        Log.i("holidaylist", holidayList.getHolidays().toString());
                    } catch (Exception e) {
                        Log.i("holidayliste", e.getMessage());
                    }
                } catch (Exception e) {
                    Toast.makeText(HolidayListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    Log.i("holidaylistex", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(HolidayListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("holidaylistf", t.getMessage());
            }
        });
    }
}