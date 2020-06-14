package com.tda.finalyear.activities.event;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.holiday.HolidayListActivity;
import com.tda.finalyear.adapter.EventAdapter;
import com.tda.finalyear.adapter.HolidayAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.EventList;
import com.tda.finalyear.models.HolidayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_list);
        recyclerView = findViewById(R.id.event_recycler);
        getEventList();
    }

    public void getEventList(){
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getEventService()
                .getEventList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    try {
                        Gson gson = new GsonBuilder().create();
                        EventList eventList = gson.fromJson(response.body().string(), EventList.class);
                        EventAdapter eventAdapter = new EventAdapter(EventListActivity.this, eventList);

                        recyclerView.setLayoutManager(new LinearLayoutManager(EventListActivity.this));
                        recyclerView.setAdapter(eventAdapter);
                        Log.i("eventlist", eventList.getEvents().toString());
                    } catch (Exception e) {
                        Log.i("eventliste", e.getMessage());
                    }
                } catch (Exception e) {
                    Toast.makeText(EventListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                    Log.i("eventlistex", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}