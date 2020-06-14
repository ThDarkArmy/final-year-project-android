package com.tda.finalyear.activities.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.holiday.AddHolidayActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.Event;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddEventActivity extends AppCompatActivity {
    EditText title, description, date, duration;
    Button addEvent, eventList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        bind();
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addEventToDatabase();
            }
        });

        eventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddEventActivity.this, EventListActivity.class));
            }
        });

    }

    public void addEventToDatabase(){
        Event event = new Event(title.getText().toString(), description.getText().toString(), date.getText().toString(), Integer.parseInt(duration.getText().toString()));
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getEventService()
                .addEvent(event);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        String str = response.body().string();

                        Toast.makeText(AddEventActivity.this, str, Toast.LENGTH_LONG);
                        Log.i("eventadd", str);
                        startActivity(new Intent(AddEventActivity.this, EventListActivity.class));

                    }else{
                        Gson gson = new GsonBuilder().create();
                        ErrorPojo mError;
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ErrorPojo.class);
                            Log.i("evente", mError.getMsg());
                            Toast.makeText(AddEventActivity.this, mError.getMsg(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Log.i("eventelsee", e.getMessage());
                            Toast.makeText(AddEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }catch(Exception e){
                    Log.i("eventex", e.getMessage());
                    Toast.makeText(AddEventActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("eventf", t.getMessage());
                Toast.makeText(AddEventActivity.this, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    public void bind(){
        title = findViewById(R.id.add_event_title);
        description = findViewById(R.id.add_event_description);
        duration = findViewById(R.id.add_event_duration);
        date = findViewById(R.id.add_event_date);
        addEvent = findViewById(R.id.add_event);
        eventList = findViewById(R.id.view_event_list);
    }
}