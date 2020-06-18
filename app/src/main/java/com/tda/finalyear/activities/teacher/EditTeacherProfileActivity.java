package com.tda.finalyear.activities.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.google.gson.internal.GsonBuildConfig;
import com.tda.finalyear.R;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.Teacher;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditTeacherProfileActivity extends AppCompatActivity {

    EditText name, email, std, mobile;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teacher_activity);
        bind();
        Teacher teacher = (Teacher) Objects.requireNonNull(getIntent().getSerializableExtra("CLASS_TYPE"));
        name.setText(teacher.getName());
        mobile.setText(teacher.getMobile());
        std.setText(teacher.getClassTeacherOfClass());
        email.setText(teacher.getEmail());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(teacher.getId());
            }
        });
    }

    public void update(String id){
        Teacher teacher = new Teacher(name.getText().toString(),std.getText().toString(),email.getText().toString(),mobile.getText().toString());

        RetrofitClient.getInstance().getTeacherService().updateTeacher(id, teacher).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        Teacher teacher1 = new GsonBuilder().create().fromJson(response.body().string(),Teacher.class);
                        Intent intent = new Intent(EditTeacherProfileActivity.this, TeacherProfileActivity.class);
                        intent.putExtra("CLASS_TYPE", teacher1);
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(EditTeacherProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        Toast.makeText(EditTeacherProfileActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(EditTeacherProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void bind(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        std = findViewById(R.id.std);
        mobile = findViewById(R.id.mobile);
        edit = findViewById(R.id.edit);
    }
}