package com.tda.finalyear.activities.event;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.Event;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditEventActivity extends AppCompatActivity {
    EditText title, description, date, duration;
    Button editEvent, eventList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        bind();
        String eventId = Objects.requireNonNull(getIntent().getExtras()).getString("EVENT_ID");
        String eventTitle = getIntent().getExtras().getString("EVENT_TITLE");
        String eventDesc = getIntent().getExtras().getString("EVENT_DESCRIPTION");
        Integer eventDuration = getIntent().getExtras().getInt("EVENT_DURATION");
        String eventDate = getIntent().getExtras().getString("EVENT_START_DATE");

        title.setText(eventTitle);
        description.setText(eventDesc);
        date.setText(eventDate);
        duration.setText(eventDuration.toString());
        editEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateEvent(eventId);
            }
        });
        eventList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditEventActivity.this, EventListActivity.class));
            }
        });

    }

    public void updateEvent(String eventId){
        Event event = new Event(title.getText().toString(), description.getText().toString(), date.getText().toString(), Integer.parseInt(duration.getText().toString()));
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getEventService()
                .editEvent(eventId,event);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        Log.i("eventedit", response.body().string());
                        startActivity(new Intent(EditEventActivity.this, EventListActivity.class));
                    }else{
                        Gson gson = new GsonBuilder().create();
                        ErrorPojo mError;
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ErrorPojo.class);
                            Log.i("evente", mError.getMsg());
                            Toast.makeText(EditEventActivity.this, mError.getMsg(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Log.i("eventelsee", e.getMessage());
                            Toast.makeText(EditEventActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }catch(Exception e){
                    Log.i("eventex", e.getMessage());
                    Toast.makeText(EditEventActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("eventf", t.getMessage());
                Toast.makeText(EditEventActivity.this, t.getMessage(), Toast.LENGTH_SHORT);
            }
        });
    }

    public void bind(){
        title = findViewById(R.id.edit_event_title);
        description = findViewById(R.id.edit_event_description);
        duration = findViewById(R.id.edit_event_duration);
        date = findViewById(R.id.edit_event_date);
        editEvent = findViewById(R.id.edit_event);
        eventList = findViewById(R.id.view_event_list);
    }
}