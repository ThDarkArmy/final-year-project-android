package com.tda.finalyear.activities.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.tda.finalyear.R;
import com.tda.finalyear.activities.student.EditStudentProfileActivity;
import com.tda.finalyear.activities.student.StudentProfileActivity;
import com.tda.finalyear.models.Admin;
import com.tda.finalyear.models.Student;

import java.util.Objects;

public class AdminProfileActivity extends AppCompatActivity {
    ImageView edit;
    TextView name, email, mobile, school, adminId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_profile);
        bind();
        Admin admin = (Admin) Objects.requireNonNull(getIntent().getSerializableExtra("CLASS_TYPE"));
        name.setText(admin.getName());
        mobile.setText(admin.getMobile());
        adminId.setText(admin.getId());
        email.setText(admin.getEmail());
        school.setText(admin.getSchool());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminProfileActivity.this, EditAdminProfileActivity.class);
                intent.putExtra("CLASS_TYPE", admin);
                startActivity(intent);
            }
        });
    }

    public void bind(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        school = findViewById(R.id.school);
        adminId = findViewById(R.id.admin_id);
        edit = findViewById(R.id.edit);
    }
}