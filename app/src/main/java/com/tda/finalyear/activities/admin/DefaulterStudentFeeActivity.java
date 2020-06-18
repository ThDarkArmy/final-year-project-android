package com.tda.finalyear.activities.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.tda.finalyear.R;
import com.tda.finalyear.adapter.DefaulterStudentFeeAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.StudentList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DefaulterStudentFeeActivity extends AppCompatActivity {

    private DefaulterStudentFeeAdapter defaulterStudentFeeAdapter;
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defaulter_student_fee);
        recyclerView = findViewById(R.id.defaulterStudentRecyclerView);
        getDefaulterList();


    }

    private void getDefaulterList() {
        Call<ResponseBody> call = RetrofitClient.getInstance().getStudentService().getDefaulterList();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try {
                        JSONArray jsonArray = new JSONArray(response.body().string());
                        StudentList studentList = (StudentList) jsonArray.get(0);
                        System.out.println("^!@#^!(@#^!@(&#^!@(#^!("+studentList);
                        defaulterStudentFeeAdapter = new DefaulterStudentFeeAdapter(studentList.getStudents(),DefaulterStudentFeeActivity.this);
                        recyclerView.setLayoutManager( new LinearLayoutManager(DefaulterStudentFeeActivity.this));
                        recyclerView.setAdapter(defaulterStudentFeeAdapter);
                    } catch (IOException | JSONException e) {
                        e.printStackTrace();
                    }
                }else{
                    Toast.makeText(DefaulterStudentFeeActivity.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("TAG","DefaulterStudentFeeActivity :: "+t.getMessage());
                Toast.makeText(DefaulterStudentFeeActivity.this, "Unable to fetch data", Toast.LENGTH_SHORT).show();
            }
        });
    }
}