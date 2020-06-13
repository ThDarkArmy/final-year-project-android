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
import com.tda.finalyear.activities.student.StudentLoginActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TeacherLoginActivity extends AppCompatActivity {

    EditText email,password;
    Button btnLogin, btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);
        bind();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                teacherLogin();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TeacherLoginActivity.this, TeacherSignupActivity.class));
            }
        });
    }


    // teacher login
    public void teacherLogin(){
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getTeacherService()
                .loginTeacher(email.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        String str = response.body().string();
                        Log.i("loginteacher", str);
                        Intent intent = new Intent(TeacherLoginActivity.this, TeacherActivity.class);
                        startActivity(intent);
                    }else{
                        Gson gson = new GsonBuilder().create();
                        ErrorPojo mError;
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ErrorPojo.class);
                            Toast.makeText(TeacherLoginActivity.this, mError.getMsg(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(TeacherLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }

                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(TeacherLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(TeacherLoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // bind
    private void bind(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);
    }
}