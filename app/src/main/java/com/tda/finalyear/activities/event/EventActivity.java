package com.tda.finalyear.activities.event;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.tda.finalyear.R;

import java.util.Objects;

public class EventActivity extends AppCompatActivity {
    TextView title, description, date, duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event);
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
    }

    public void bind(){
        title = findViewById(R.id.title_event);
        description = findViewById(R.id.description_event);
        date = findViewById(R.id.date_event);
        duration = findViewById(R.id.duration_event);
    }
}