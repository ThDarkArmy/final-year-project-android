package com.tda.finalyear.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tda.finalyear.R;
import com.tda.finalyear.models.Student;

import java.util.Objects;

public class StudentDetailsActivity extends AppCompatActivity {

    TextView name, email, mobile, std, roll, studentId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_details);
        bind();
        Student student = (Student) Objects.requireNonNull(getIntent().getSerializableExtra("CLASS_TYPE"));
        name.setText(student.getName());
        mobile.setText(student.getMobile());
        std.setText(student.getStd());
        roll.setText(student.getRoll().toString());
        studentId.setText(student.getId());
        email.setText(student.getEmail());

    }

    public void bind(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        std = findViewById(R.id.std);
        roll = findViewById(R.id.roll);
        studentId = findViewById(R.id.student_id);
    }
}