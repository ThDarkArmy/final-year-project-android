package com.tda.finalyear.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.tda.finalyear.R;

public class StudentActivity extends AppCompatActivity {

    private RelativeLayout notesBtn, assignmentBtn, feeBtn, attendenceBtn, profileBtn;
    private TextView welcomename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        notesBtn = (RelativeLayout) findViewById(R.id.notes);
        assignmentBtn = (RelativeLayout) findViewById(R.id.assignment);
        feeBtn = (RelativeLayout) findViewById(R.id.fee);
        attendenceBtn = (RelativeLayout) findViewById(R.id.attendence);
        profileBtn = (RelativeLayout) findViewById(R.id.profile);

        welcomename = (TextView) findViewById(R.id.textView2);

        String user_email = getIntent().getStringExtra("user_email")==null ? "to your dashboard" : getIntent().getStringExtra("user_email").toString();
        welcomename.setText(user_email);

        notesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentActivity.this, "NOTES", Toast.LENGTH_SHORT).show();
            }
        });

        assignmentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentActivity.this, "ASSIGNMENT", Toast.LENGTH_SHORT).show();
            }
        });

        feeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentActivity.this, "FEE", Toast.LENGTH_SHORT).show();
            }
        });

        attendenceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentActivity.this, "ATTENDENCE", Toast.LENGTH_SHORT).show();
            }
        });

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(StudentActivity.this, "PROFILE", Toast.LENGTH_SHORT).show();
            }
        });
    }
}