package com.tda.finalyear.activities.admin;

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
import com.tda.finalyear.activities.admin.AdminProfileActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.Admin;
import com.tda.finalyear.models.Teacher;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditAdminProfileActivity extends AppCompatActivity {

    EditText name, email, school, mobile;
    Button edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_teacher_activity);
        bind();
        Admin admin = (Admin) Objects.requireNonNull(getIntent().getSerializableExtra("CLASS_TYPE"));
        name.setText(admin.getName());
        mobile.setText(admin.getMobile());
        school.setText(admin.getSchool());
        email.setText(admin.getEmail());
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update(admin.getId());
            }
        });
    }

    public void update(String id){
        Admin admin = new Admin(name.getText().toString(),email.getText().toString(),mobile.getText().toString(),school.getText().toString());

        RetrofitClient.getInstance().getAdminService().updateAdmin(id, admin).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.isSuccessful()){
                    try{
                        Teacher teacher1 = new GsonBuilder().create().fromJson(response.body().string(),Teacher.class);
                        Intent intent = new Intent(EditAdminProfileActivity.this, AdminProfileActivity.class);
                        intent.putExtra("CLASS_TYPE", teacher1);
                        startActivity(intent);
                    }catch (Exception e){
                        Toast.makeText(EditAdminProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }else{
                    try {
                        Toast.makeText(EditAdminProfileActivity.this, response.errorBody().string(), Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(EditAdminProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
        school = findViewById(R.id.school);
        mobile = findViewById(R.id.mobile);
        edit = findViewById(R.id.edit);
    }
}