package com.tda.finalyear.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.teacher.TeacherActivity;
import com.tda.finalyear.activities.teacher.TeacherLoginActivity;
import com.tda.finalyear.activities.teacher.TeacherSignupActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.Admin;
import com.tda.finalyear.models.ErrorPojo;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminLoginActivity extends AppCompatActivity {

    EditText email,password;
    Button btnLogin, btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        bind();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adminLogin();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminLoginActivity.this, AdminSignupActivity.class));
            }
        });
    }

    // admin login
    public void adminLogin(){
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getAdminService()
                .loginAdmin(email.getText().toString(),password.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        String str = response.body().string();
                        Log.i("loginadmin", str);
                        Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }else{
                        Gson gson = new GsonBuilder().create();
                        ErrorPojo mError;
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ErrorPojo.class);
                            Toast.makeText(AdminLoginActivity.this, mError.getMsg(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(AdminLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(AdminLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

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