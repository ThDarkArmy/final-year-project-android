package com.tda.finalyear.activities.attendance;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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
import com.tda.finalyear.activities.event.EventListActivity;
import com.tda.finalyear.activities.teacher.TeacherActivity;
import com.tda.finalyear.adapter.AttendanceAdapter;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.AttendanceHistory;
import com.tda.finalyear.models.StudentList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TakeAttendanceActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    RecyclerView recyclerView;
    Spinner spinner;
    Button submit;
    List<String> ids = new ArrayList<>();
    String std;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_attendance);
        recyclerView = findViewById(R.id.attendance_recycler);
        spinner = findViewById(R.id.class_spinner);
        submit = findViewById(R.id.submit);

        spinner.setOnItemSelectedListener(this);

        String[] cls = getResources().getStringArray(R.array.class_arrays);
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, cls);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                submitAttendance(ids);
            }
        });
    }

    public void submitAttendance(List<String> ids){
        Log.i("ids",ids.toString());
        Map<String, List<String>> map = new HashMap<>();
        map.put("id",ids);
        Log.i("ids",map.toString());
        RetrofitClient.getInstance().getAttendanceService().makeAttendance(std, map).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        Log.i("responseAtt", response.body().string());
                        startActivity(new Intent(TakeAttendanceActivity.this, TeacherActivity.class));
                    }else{
                        Log.i("responseAttElse", response.errorBody().string());
                    }
                }catch(Exception e){
                    Log.i("responseAttEx", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TakeAttendanceActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
                                AttendanceAdapter attendanceAdapter = new AttendanceAdapter(TakeAttendanceActivity.this, studentList);
                                recyclerView.setLayoutManager(new LinearLayoutManager(TakeAttendanceActivity.this));
                                recyclerView.setAdapter(attendanceAdapter);
                                ids = attendanceAdapter.getIdList();
                                Log.i("attendanceStudent", studentList.toString());
                            }catch (Exception e){
                                Log.i("attEx",e.getMessage());
                            }
                        }else{
                            Log.i("attElse",response.errorBody().string());
                        }
                    }catch (Exception e){
                        Log.i("attExx",e.getMessage());
                        Toast.makeText(TakeAttendanceActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(TakeAttendanceActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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