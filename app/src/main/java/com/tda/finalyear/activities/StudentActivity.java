package com.tda.finalyear.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.tda.finalyear.R;
import com.tda.finalyear.adapter.StudentAdapter;
import com.tda.finalyear.apicalls.StudentApiCalls;
import com.tda.finalyear.models.Student;

import java.util.List;

public class StudentActivity extends AppCompatActivity {

    private RecyclerView studentRecyclerView;
    private List<Student> studentList;
    private StudentAdapter studentAdapter;
    private StudentApiCalls apiCalls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        apiCalls = new StudentApiCalls();
        studentList = apiCalls.getAllTheStudentOfTheParticularClass(10, "A");

        studentAdapter =  new StudentAdapter(studentList,StudentActivity.this);

        studentRecyclerView = findViewById(R.id.studentRecycler);
        studentRecyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        studentRecyclerView.setItemAnimator(new DefaultItemAnimator());
        studentRecyclerView.setAdapter(studentAdapter);

    }

}