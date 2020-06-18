package com.tda.finalyear.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.teacher.EditTeacherProfileActivity;
import com.tda.finalyear.activities.teacher.TeacherProfileActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.Student;
import com.tda.finalyear.models.Teacher;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditStudentProfileActivity extends AppCompatActivity {

    EditText name, email, std, mobile, roll;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);
        bind();
        Student student1 = (Student) Objects.requireNonNull(getIntent().getSerializableExtra("CLASS_TYPE"));
        name.setText(student1.getName());
        mobile.setText(student1.getMobile());
        std.setText(student1.getStd());
        email.setText(student1.getEmail());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(student1.getId());
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
                        Student student = new GsonBuilder().create().fromJson(response.body().string(),Student.class);
                        Intent intent = new Intent(EditStudentProfileActivity.this, StudentProfileActivity.class);
                        intent.putExtra("CLASS_TYPE", student);
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(EditStudentProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        Toast.makeText(EditStudentProfileActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(EditStudentProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        roll = findViewById(R.id.roll);
        edit = findViewById(R.id.edit);
    }
}