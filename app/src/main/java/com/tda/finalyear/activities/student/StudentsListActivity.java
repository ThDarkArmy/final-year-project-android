package com.tda.finalyear.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.attendance.TakeAttendanceActivity;
import com.tda.finalyear.adapter.AttendanceAdapter;
import com.tda.finalyear.adapter.StudentAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.StudentList;
import com.tda.finalyear.models.Teacher;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentsListActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    Spinner spinner;
    Button submit;
    String std;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_list);

        recyclerView = findViewById(R.id.students_recycler);
        spinner = findViewById(R.id.class_spinner);
        submit = findViewById(R.id.submit);

        spinner.setOnItemSelectedListener(this);

        String[] cls = getResources().getStringArray(R.array.class_arrays);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cls);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Log.i("inSelect", "inSelect");
        if(parent.getId()==R.id.class_spinner){
            String vfs = parent.getItemAtPosition(position).toString();
            std = vfs.substring(6);
            RetrofitClient.getInstance().getStudentService().getStudentByClass(std).enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        if(response.isSuccessful()){
                            try{
                                StudentList studentList = new GsonBuilder().create().fromJson(response.body().string(),StudentList.class);
                                StudentAdapter studentAdapter = new StudentAdapter(StudentsListActivity.this, studentList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(StudentsListActivity.this));
                                recyclerView.setAdapter(studentAdapter);
                                //ids = studentAdapter.getIdList();
                                Log.i("studentList", studentList.toString());
                            }catch (Exception e){
                                Log.i("attEx",e.getMessage());
                            }
                        }else{
                            Log.i("attElse",response.errorBody().string());
                        }
                    }catch (Exception e){
                        Log.i("attExx",e.getMessage());
                        Toast.makeText(StudentsListActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(StudentsListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("attF",t.getMessage());
                }
            });

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Log.i("attN", parent.toString());
    }
}
