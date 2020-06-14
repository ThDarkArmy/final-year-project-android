package com.tda.finalyear.activities.facility;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.holiday.HolidayListActivity;
import com.tda.finalyear.adapter.FacilityAdapter;
import com.tda.finalyear.adapter.HolidayAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.Facility;
import com.tda.finalyear.models.FacilityList;
import com.tda.finalyear.models.HolidayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacilityListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility_list);
        recyclerView = findViewById(R.id.facility_recycler);
        getFacilityList();
    }
    public void getFacilityList(){
        RetrofitClient.getInstance().getFacilityService().getFacilityList().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    if(response.isSuccessful()){
                        FacilityList facilityList = new GsonBuilder().create().fromJson(response.body().string(), FacilityList.class);
                        FacilityAdapter facilityAdapter = new FacilityAdapter(facilityList ,FacilityListActivity.this);

                        recyclerView.setLayoutManager(new LinearLayoutManager(FacilityListActivity.this));
                        recyclerView.setAdapter(facilityAdapter);
                    }else{
                        ErrorPojo errorPojo = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorPojo.class);
                        Log.i("facility", errorPojo.getMsg());
                        Toast.makeText(FacilityListActivity.this, errorPojo.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.i("facility", e.getMessage());
                    Toast.makeText(FacilityListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("facility", t.getMessage());
                Toast.makeText(FacilityListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}