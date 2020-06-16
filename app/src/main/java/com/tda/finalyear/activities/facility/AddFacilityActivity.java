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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFacilityActivity extends AppCompatActivity {

    EditText name, description;
    Button addFacility, facilityList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_facility);
        bind();
        addFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    addFacilityToDatabase();
                }

            }
        });

        facilityList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddFacilityActivity.this, FacilityListActivity.class));
            }
        });

    }

    public void addFacilityToDatabase(){
        Facility facility = new Facility(name.getText().toString(), description.getText().toString());
        RetrofitClient.getInstance().getFacilityService().addFacility(facility).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        Log.i("facility", response.body().string());
                        startActivity(new Intent(AddFacilityActivity.this, FacilityListActivity.class));
                    }else{
                        ErrorPojo errorPojo = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorPojo.class);
                        Log.i("facility",errorPojo.getMsg());
                        Toast.makeText(AddFacilityActivity.this, errorPojo.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Log.i("facility", e.getMessage());
                    Toast.makeText(AddFacilityActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddFacilityActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // form validation
    public boolean validation(){
        String vName = name.getText().toString();
        String vDesc = description.getText().toString();

        if(vName.isEmpty()){
            name.setError("Name field cannot be empty.");
            return false;
        }else if(vDesc.isEmpty()){
            description.setError("Enter a valid admission fee.");
            return false;
        }else{
            return true;
        }
    }

    public void bind(){
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        addFacility = findViewById(R.id.add_facility);
        facilityList = findViewById(R.id.view_facility_list);
    }
}