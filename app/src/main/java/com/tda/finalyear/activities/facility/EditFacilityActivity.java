package com.tda.finalyear.activities.facility;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.Facility;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EditFacilityActivity extends AppCompatActivity {
    EditText name, description;
    Button editFacility, facilityList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_facility);
        bind();
        String facilityId = Objects.requireNonNull(getIntent().getExtras().getString("FACILITY_ID"));
        String facilityName = getIntent().getExtras().getString("FACILITY_NAME");
        String facilityDesc = getIntent().getExtras().getString("FACILITY_DESC");

        name.setText(facilityName);
        description.setText(facilityDesc);
        editFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateFacility(facilityId);
            }
        });

        facilityList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditFacilityActivity.this, FacilityListActivity.class));
            }
        });
    }

    public void updateFacility(String id){
        Facility facility = new Facility(name.getText().toString(), description.getText().toString());
        RetrofitClient.getInstance().getFacilityService().editFacility(id, facility).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        startActivity(new Intent(EditFacilityActivity.this, FacilityListActivity.class));
                    }else{
                        ErrorPojo errorPojo = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorPojo.class);
                        Log.i("facility",errorPojo.getMsg());
                        Toast.makeText(EditFacilityActivity.this, errorPojo.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Log.i("facility", e.getMessage());
                    Toast.makeText(EditFacilityActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.i("facility", t.getMessage());
                Toast.makeText(EditFacilityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void bind(){
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        editFacility = findViewById(R.id.edit_facility);
        facilityList = findViewById(R.id.view_facility_list);
    }
}