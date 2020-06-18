package com.tda.finalyear.activities.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.student.StudentSignupActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.Teacher;
import com.tda.finalyear.models.TeacherResponse;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherSignupActivity extends AppCompatActivity {
    EditText name,email,password,confirmPassword, mobile,classTeacherOfClass;
    Button SignUpBtn,LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_signup);
        bind();
        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherSignupActivity.this, TeacherLoginActivity.class));
            }
        });

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    // sign up
    private void signup() {
        if(validation()){
            Teacher teacher = new Teacher(name.getText().toString(),classTeacherOfClass.getText().toString(),email.getText().toString(),mobile.getText().toString(),password.getText().toString());
            Call<ResponseBody> call = RetrofitClient.getInstance()
                    .getTeacherService()
                    .signUpTeacher(teacher);
            call.enqueue(new Callback<ResponseBody>() {
                @Override
                public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                    try{
                        if(response.isSuccessful()){
                            String str = response.body().string();
                            TeacherResponse teacherResponse = new GsonBuilder().create().fromJson(str,TeacherResponse.class);
                            Log.i("loginTeacher", teacherResponse.toString());
                            Intent intent = new Intent(TeacherSignupActivity.this, TeacherActivity.class);
                            intent.putExtra("CLASS_TYPE",teacherResponse.getTeacher());
                            startActivity(intent);
                        }else{
                            Gson gson = new GsonBuilder().create();
                            ErrorPojo mError;
                            try {
                                mError= gson.fromJson(response.errorBody().string(),ErrorPojo.class);
                                Toast.makeText(TeacherSignupActivity.this, mError.getMsg(), Toast.LENGTH_LONG).show();
                            } catch (IOException e) {
                                Toast.makeText(TeacherSignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    }catch(Exception e){
                        e.printStackTrace();
                        Toast.makeText(TeacherSignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseBody> call, Throwable t) {
                    Toast.makeText(TeacherSignupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }else{
            Toast.makeText(TeacherSignupActivity.this,"Passwords doesn't match.",Toast.LENGTH_SHORT).show();
        }
    }

    // confirm password
    public boolean validation() {
        if(password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){
            return true;
        }
        return false;
    }

    // binding
    public void bind(){
        name=findViewById(R.id.signupName);
        email=findViewById(R.id.signupEmail);
        mobile = findViewById(R.id.mobile);
        classTeacherOfClass = findViewById(R.id.std);
        password=findViewById(R.id.signupPassword);
        confirmPassword=findViewById(R.id.confirmPassword);
        SignUpBtn=findViewById(R.id.SignUpBtn);
        LoginBtn=findViewById(R.id.LoginBtn);
    }
}