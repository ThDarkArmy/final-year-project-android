package com.tda.finalyear.activities.exam;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.event.EventListActivity;
import com.tda.finalyear.adapter.EventAdapter;
import com.tda.finalyear.adapter.ExamAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ExamList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamListActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_list);
        recyclerView = findViewById(R.id.exam_recycler);
        getExamList();
    }

    public void getExamList(){
        RetrofitClient.getInstance().getExamService().getExamList().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        try{
                            ExamList examList = new GsonBuilder().create().fromJson(response.body().string(),ExamList.class);
                            ExamAdapter examAdapter = new ExamAdapter(ExamListActivity.this, examList);

                            recyclerView.setLayoutManager(new LinearLayoutManager(ExamListActivity.this));
                            recyclerView.setAdapter(examAdapter);
                            Log.i("exam", examList.getExams().toString());
                        }catch (Exception e){
                            Log.i("exam", e.getMessage());
                        }
                    }else{
                        Log.i("exam", response.errorBody().string());
                        Toast.makeText(ExamListActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    }
                }catch(Exception e){
                    Toast.makeText(ExamListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(ExamListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}