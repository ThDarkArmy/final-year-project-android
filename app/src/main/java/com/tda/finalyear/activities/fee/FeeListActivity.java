package com.tda.finalyear.activities.fee;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.adapter.FeeAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.FeeList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeeListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_list);
        recyclerView = findViewById(R.id.fee_recycler);
        getFeeList();
    }

    public void getFeeList(){
        RetrofitClient.getInstance().getFeeService().getFeeList().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        FeeList feeList = new GsonBuilder().create().fromJson(response.body().string(), FeeList.class);
                        FeeAdapter feeAdapter = new FeeAdapter(FeeListActivity.this, feeList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(FeeListActivity.this));
                        recyclerView.setAdapter(feeAdapter);
                    }else{
                        ErrorPojo errorPojo = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorPojo.class);
                        Log.i("fee", errorPojo.getMsg());
                        Toast.makeText(FeeListActivity.this, errorPojo.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Log.i("fee", e.getMessage());
                    Toast.makeText(FeeListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(FeeListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}