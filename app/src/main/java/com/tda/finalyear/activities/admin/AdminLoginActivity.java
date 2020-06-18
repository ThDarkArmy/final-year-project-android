package com.tda.finalyear.activities.admin;

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
import com.tda.finalyear.activities.teacher.TeacherActivity;
import com.tda.finalyear.activities.teacher.TeacherLoginActivity;
import com.tda.finalyear.activities.teacher.TeacherSignupActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.Admin;
import com.tda.finalyear.models.AdminResponse;
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
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        bind();
        email.setText("admin@gmail.com");
        password.setText("password");
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
                        AdminResponse adminResponse = new GsonBuilder().create().fromJson(str,AdminResponse.class);
                        Intent intent = new Intent(AdminLoginActivity.this, AdminActivity.class);
                        intent.putExtra("CLASS_TYPE", adminResponse.getAdmin());
                        startActivity(intent);
                    }else{
                        Gson gson = new GsonBuilder().create();
                        ErrorPojo mError;
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ErrorPojo.class);
                            Toast.makeText(AdminLoginActivity.this, mError.getMsg(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(AdminLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                            Log.i("loginadmin", e.getMessage());
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(AdminLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    Log.i("loginadmin", e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AdminLoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                Log.i("loginadmin", t.getMessage());
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