package com.tda.finalyear.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.Student;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class StudentSignupActivity extends AppCompatActivity {
    EditText name,email,password,confirmPassword,roll, mobile,std;
    Button SignUpBtn,LoginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_signup);
        bind();

        LoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentSignupActivity.this, StudentLoginActivity.class));
            }
        });
        SignUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SignUp();
            }
        });
    }
    public boolean validation() {
        if(password.getText().toString().trim().equals(confirmPassword.getText().toString().trim())){
            return true;
        }
        return false;
    }

    public void SignUp(){

        if(validation()){
            Student student = new Student(name.getText().toString(),Integer.parseInt(roll.getText().toString()),email.getText().toString(), mobile.getText().toString(), std.getText().toString(),password.getText().toString());
                Call<ResponseBody> call = RetrofitClient.getInstance()
                        .getStudentService()
                        .signUpStudent(student);

                call.enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        try{
                            if(response.isSuccessful()) {
                                String str = response.body().string();
                                Log.i("signup", str);
                                Intent intent = new Intent(StudentSignupActivity.this, StudentActivity.class);
                                startActivity(intent);
                            }else{
                                Gson gson = new GsonBuilder().create();
                                ErrorPojo mError;
                                try {
                                    mError= gson.fromJson(response.errorBody().string(),ErrorPojo.class);
                                    Toast.makeText(StudentSignupActivity.this, mError.getMsg(), Toast.LENGTH_LONG).show();
                                } catch (IOException e) {
                                    Toast.makeText(StudentSignupActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                                }
                            }
                        }catch (Exception e){
                            e.printStackTrace();
                            Toast.makeText(StudentSignupActivity.this, "Signing up unsuccessfull, try again.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(StudentSignupActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
        }else{
            Toast.makeText(this.getApplicationContext(),"Password are not matching.",Toast.LENGTH_SHORT).show();
        }



    }

    public void bind(){
        name=findViewById(R.id.signupName);
        email=findViewById(R.id.signupEmail);
        roll = findViewById(R.id.roll);
        mobile = findViewById(R.id.mobile);
        std = findViewById(R.id.std);
        password=findViewById(R.id.signupPassword);
        confirmPassword=findViewById(R.id.confirmPassword);
        SignUpBtn=findViewById(R.id.SignUpBtn);
        LoginBtn=findViewById(R.id.LoginBtn);
    }
}
