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
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.Admin;
import com.tda.finalyear.models.ErrorPojo;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AdminSignupActivity extends AppCompatActivity {

    EditText name,email,password,confirmPassword, mobile,school;
    Button SignUpBtn,LoginBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_signup);
        bind();

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminSignupActivity.this, AdminLoginActivity.class));
            }
        });

        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signup();
            }
        });
    }

    public void signup(){
        Admin admin = new Admin(name.getText().toString(),email.getText().toString(),mobile.getText().toString(),school.getText().toString(), password.getText().toString());
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getAdminService()
                .signUpAdmin(admin);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        String str = response.body().string();
                        Log.i("adminsignup", str);
                        Intent intent = new Intent(AdminSignupActivity.this, AdminActivity.class);
                        startActivity(intent);
                    }else{
                        Gson gson = new GsonBuilder().create();
                        ErrorPojo mError;
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ErrorPojo.class);
                            Toast.makeText(AdminSignupActivity.this, mError.getMsg(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(AdminSignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }catch(Exception e){
                    e.printStackTrace();
                    Toast.makeText(AdminSignupActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AdminSignupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
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
        school = findViewById(R.id.school);
        password=findViewById(R.id.signupPassword);
        confirmPassword=findViewById(R.id.confirmPassword);
        SignUpBtn=findViewById(R.id.SignUpBtn);
        LoginBtn=findViewById(R.id.LoginBtn);
    }
}