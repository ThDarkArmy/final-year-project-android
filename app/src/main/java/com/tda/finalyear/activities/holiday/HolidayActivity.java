package com.tda.finalyear.activities.holiday;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Window;
import android.widget.TextView;

import com.tda.finalyear.R;

import java.util.Objects;

public class HolidayActivity extends AppCompatActivity {

    TextView title, description, date, duration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_holiday);
        bind();
        String holidayId = Objects.requireNonNull(getIntent().getExtras()).getString("HOLIDAY_ID");
        String holidayDescription = getIntent().getExtras().getString("HOLIDAY_DESCRIPTION");
        Integer holidayDuration = getIntent().getExtras().getInt("HOLIDAY_DURATION");
        String holidayTitle = getIntent().getExtras().getString("HOLIDAY_TITLE");
        String holidayStartDate  = getIntent().getExtras().getString("HOLIDAY_START_DATE");
        title.setText(holidayTitle);
        description.setText(holidayDescription);
        duration.setText(holidayDuration.toString());
        date.setText(holidayStartDate);

    }

    public void bind(){
        title = findViewById(R.id.holiday_title);
        description = findViewById(R.id.holiday_desc);
        duration = findViewById(R.id.holiday_duration);
        date = findViewById(R.id.holiday_date);
    }
}