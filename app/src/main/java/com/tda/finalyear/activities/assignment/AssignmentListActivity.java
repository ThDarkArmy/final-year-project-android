package com.tda.finalyear.activities.assignment;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.os.RecoverySystem;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.adapter.AssignmentAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.AssignmentList;
import com.tda.finalyear.models.ErrorPojo;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AssignmentListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_assignment_list);
        recyclerView = findViewById(R.id.assignment_recycler);
        getAssignmentList();
    }

    public void getAssignmentList(){
        RetrofitClient.getInstance().getAssignmentService().getAssignmentList().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        AssignmentList assignmentList = new GsonBuilder().create().fromJson(response.body().string(), AssignmentList.class);
                        AssignmentAdapter assignmentAdapter = new AssignmentAdapter(AssignmentListActivity.this, assignmentList);
                        recyclerView.setLayoutManager(new LinearLayoutManager(AssignmentListActivity.this));
                        recyclerView.setAdapter(assignmentAdapter);
                    }catch(Exception e){
                        Log.i("assignmentList", e.getMessage());
                    }
                }else{
                    try{
                        ErrorPojo errorPojo = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorPojo.class);
                        Toast.makeText(AssignmentListActivity.this, errorPojo.getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(AssignmentListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}