package com.tda.finalyear.activities.holiday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tda.finalyear.R;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.Holiday;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditHolidayActivity extends AppCompatActivity {

    private EditText holidayTitleEditText,holidayDescriptionEditText,holidayStartDateEditText,holidayDurationEditText;
    Button editHolidayButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_holiday);
        String holidayId = Objects.requireNonNull(getIntent().getExtras()).getString("HOLIDAY_ID");
        String holidayDescription = getIntent().getExtras().getString("HOLIDAY_DESCRIPTION");
        Integer holidayDuration = getIntent().getExtras().getInt("HOLIDAY_DURATION");
        String holidayTitle = getIntent().getExtras().getString("HOLIDAY_TITLE");
        String holidayStartDate  = getIntent().getExtras().getString("HOLIDAY_START_DATE");
        System.out.println(holidayDescription+" "+holidayDuration+" "+holidayTitle);
        holidayTitleEditText = findViewById(R.id.holidaytitle);
        holidayDescriptionEditText = findViewById(R.id.holiday_desc);
        holidayStartDateEditText = findViewById(R.id.holidaydate);
        holidayDurationEditText = findViewById(R.id.holidayduration);
        editHolidayButton = findViewById(R.id.updateholiday);

        holidayDurationEditText.setText(holidayDuration.toString());
        holidayDescriptionEditText.setText(holidayDescription);
        holidayStartDateEditText.setText(holidayStartDate);
        holidayTitleEditText.setText(holidayTitle);

        editHolidayButton.setOnClickListener(new View.OnClickListener() {
            Holiday holiday =  new Holiday(holidayTitleEditText.getText().toString(),holidayStartDateEditText.getText().toString(),Integer.parseInt(holidayDurationEditText.getText().toString()),holidayDescriptionEditText.getText().toString());
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getHolidayService().editHoliday(holidayId,holiday).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        startActivity(new Intent(getApplicationContext(),HolidayListActivity.class));
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(EditHolidayActivity.this, "Update Failed", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }
}