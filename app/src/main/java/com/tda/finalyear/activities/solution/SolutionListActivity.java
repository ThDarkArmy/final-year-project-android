package com.tda.finalyear.activities.solution;

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
import com.tda.finalyear.adapter.SolutionAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.AssignmentList;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.SolutionList;
import com.tda.finalyear.models.Student;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SolutionListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    String classType;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solution_list);
        recyclerView = findViewById(R.id.solution_recycler);
        //classType = Objects.requireNonNull(getIntent().getSerializableExtra("CLASS_TYPE").getClass().getName());
        getAssignmentList();
    }

    public void getAssignmentList(){
        RetrofitClient.getInstance().getSolutionService().getSolutions().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        SolutionList solutionList = new GsonBuilder().create().fromJson(response.body().string(), SolutionList.class);
                        SolutionAdapter solutionAdapter = new SolutionAdapter(SolutionListActivity.this, solutionList);

                        recyclerView.setLayoutManager(new LinearLayoutManager(SolutionListActivity.this));
                        recyclerView.setAdapter(solutionAdapter);
                    }catch(Exception e){
                        Log.i("assignmentList", e.getMessage());
                    }
                }else{
                    try{
                        ErrorPojo errorPojo = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorPojo.class);
                        Toast.makeText(SolutionListActivity.this, errorPojo.getMsg(), Toast.LENGTH_SHORT).show();
                    }catch (Exception e){
                        Toast.makeText(SolutionListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}