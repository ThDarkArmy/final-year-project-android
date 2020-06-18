package com.tda.finalyear.activities.student;

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
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.StudentWithToken;

import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentLoginActivity extends AppCompatActivity {

    EditText email, password;
    Button btnLogin, btnSignup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_student);

        bind();
        email.setText("rajputanjali4421@gmail.com");
        password.setText("password");
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                studentLogin();
            }
        });

        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentLoginActivity.this, StudentSignupActivity.class));
            }
        });


    }


    private void studentLogin() {
        Call<ResponseBody> call = RetrofitClient.getInstance()
                .getStudentService()
                .loginStudent(email.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()) {
                        String str = response.body().string();
                        StudentWithToken studentWithToken = new GsonBuilder().create().fromJson(str,StudentWithToken.class);
                        Log.i("classSt", studentWithToken.getStudent().toString());
                        Log.i("login", studentWithToken.toString());
                        Intent intent = new Intent(StudentLoginActivity.this, StudentActivity.class);
                        intent.putExtra("CLASS_TYPE",studentWithToken.getStudent());
                        startActivity(intent);
                    }else{
                        Gson gson = new GsonBuilder().create();
                        ErrorPojo mError;
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ErrorPojo.class);
                            Toast.makeText(StudentLoginActivity.this, mError.getMsg(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Toast.makeText(StudentLoginActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                    Toast.makeText(StudentLoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(StudentLoginActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void bind(){
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignup = findViewById(R.id.btnSignup);

    }
}