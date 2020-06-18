package com.tda.finalyear.activities.teacher;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.tda.finalyear.R;
import com.tda.finalyear.activities.student.EditStudentProfileActivity;
import com.tda.finalyear.activities.student.StudentProfileActivity;
import com.tda.finalyear.models.Student;
import com.tda.finalyear.models.Teacher;

import java.util.Objects;

public class TeacherProfileActivity extends AppCompatActivity {

    ImageView edit;
    TextView name, email, mobile, std, teacherId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_profile);
        bind();
        Teacher teacher = (Teacher) Objects.requireNonNull(getIntent().getSerializableExtra("CLASS_TYPE"));
        name.setText(teacher.getName());
        mobile.setText(teacher.getMobile());
        std.setText(teacher.getClassTeacherOfClass());
        teacherId.setText(teacher.getId());
        email.setText(teacher.getEmail());

        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TeacherProfileActivity.this, EditTeacherProfileActivity.class);
                intent.putExtra("CLASS_TYPE", teacher);
                startActivity(intent);
            }
        });
    }

    public void bind(){
        name = findViewById(R.id.name);
        email = findViewById(R.id.email);
        mobile = findViewById(R.id.mobile);
        std = findViewById(R.id.std);
        teacherId = findViewById(R.id.teacher_id);
        edit = findViewById(R.id.edit);
    }
}