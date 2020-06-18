package com.tda.finalyear.activities.teacher;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.adapter.TeacherAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.TeacherList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeachersListActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    Spinner spinner;
    Button submit;
    String std;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teachers_list);
        recyclerView = findViewById(R.id.teacher_recycler);
        spinner = findViewById(R.id.class_spinner);
        submit = findViewById(R.id.submit);
        showTeachers();

    }

    public void showTeachers(){
        RetrofitClient.getInstance().getTeacherService().getTeachers().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        try{
                            TeacherList teacherList = new GsonBuilder().create().fromJson(response.body().string(),TeacherList.class);
                            TeacherAdapter teacherAdapter = new TeacherAdapter(TeachersListActivity.this, teacherList);
                            recyclerView.setLayoutManager(new LinearLayoutManager(TeachersListActivity.this));
                            recyclerView.setAdapter(teacherAdapter);
                            Log.i("teacherLists", teacherList.toString());
                        }catch (Exception e){
                            Log.i("attEx",e.getMessage());
                        }
                    }else{
                        Log.i("attElse",response.errorBody().string());
                    }
                }catch (Exception e){
                    Log.i("attExx",e.getMessage());
                    Toast.makeText(TeachersListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TeachersListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("attF",t.getMessage());
            }
        });
    }

}