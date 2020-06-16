package com.tda.finalyear.activities.holiday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.Holiday;
import com.tda.finalyear.models.HolidayList;

import java.io.IOException;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddHolidayActivity extends AppCompatActivity {

    EditText title, description, date, duration;
    Button addHoliday, holidayList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_holiday);
        bind();

        addHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    addHolidayToDatabase();
                }
            }
        });

        holidayList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddHolidayActivity.this, HolidayListActivity.class));
            }
        });

    }

    public void addHolidayToDatabase(){
        Holiday holiday = new Holiday(title.getText().toString(),date.getText().toString(),Integer.parseInt(duration.getText().toString()) ,description.getText().toString());
        Log.i("holidayadd", holiday.toString());
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getHolidayService()
                .addHoliday(holiday);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {

                try{
                    if(response.isSuccessful()){
                        startActivity(new Intent(AddHolidayActivity.this, HolidayListActivity.class));
                    }else{
                        Gson gson = new GsonBuilder().create();
                        ErrorPojo mError;
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ErrorPojo.class);
                            Log.i("holidayelse", mError.getMsg());
                            Toast.makeText(AddHolidayActivity.this, mError.getMsg(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Log.i("holidayelsee", e.getMessage());
                            Toast.makeText(AddHolidayActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }catch(Exception e){
                    Log.i("holidayex", e.getMessage());
                    Toast.makeText(AddHolidayActivity.this, e.getMessage(), Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddHolidayActivity.this, t.getMessage(), Toast.LENGTH_SHORT);
                Log.i("holidayf", t.getMessage());
            }
        });
    }

    // form validation
    public boolean validation(){
        String vTitle = title.getText().toString();
        String vDEsc = description.getText().toString();
        String vDate = date.getText().toString();
        String vDuration = duration.getText().toString();
        if(vTitle.isEmpty()){
            title.setError("Title field cannot be empty.");
            return false;

        }else if(vDate.isEmpty()){
            date.setError("Enter a valid date.");
            return false;
        }else if(vDuration.isEmpty() || !vDuration.matches("-?\\d+")){
            duration.setError("Enter a valid duration.");
            return false;
        }else if(vDEsc.isEmpty()){
            description.setError("Enter a valid description.");
            return false;
        }else{
            return true;
        }

    }

    public void bind(){
        title = findViewById(R.id.holidaytitle);
        description = findViewById(R.id.holiday_desc);
        date = findViewById(R.id.holidaydate);
        duration = findViewById(R.id.holidayduration);
        addHoliday = findViewById(R.id.addholiday);
        holidayList = findViewById(R.id.view_holiday_list);
    }
}