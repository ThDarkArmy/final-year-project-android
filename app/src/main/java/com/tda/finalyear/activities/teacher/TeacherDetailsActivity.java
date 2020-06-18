package com.tda.finalyear.activities.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.tda.finalyear.R;
import com.tda.finalyear.models.Student;
import com.tda.finalyear.models.Teacher;

import java.util.Objects;

public class TeacherDetailsActivity extends AppCompatActivity {

    TextView name, email, mobile, std, teacherId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details);
        bind();
        Teacher teacher = (Teacher) Objects.requireNonNull(getIntent().getSerializableExtra("CLASS_TYPE"));
        name.setText(teacher.getName());
        mobile.setText(teacher.getMobile());
        std.setText(teacher.getClassTeacherOfClass());
        teacherId.setText(teacher.getId());
        email.setText(teacher.getEmail());
    }

    public void bind(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        std = findViewById(R.id.std);
        teacherId = findViewById(R.id.teacher_id);
    }
}