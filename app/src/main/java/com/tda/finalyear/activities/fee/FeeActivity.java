package com.tda.finalyear.activities.fee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tda.finalyear.R;

import java.util.Objects;

public class FeeActivity extends AppCompatActivity {
    TextView std, admissionFee, tuitionFee, examFee;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee);
        bind();
        String feeId = Objects.requireNonNull(getIntent().getStringExtra("FEE_ID"));
        String stdf = getIntent().getStringExtra("FEE_STD");
        Integer tuition = getIntent().getExtras().getInt("TUITION_FEE");
        Integer admission = getIntent().getExtras().getInt("ADMISSION_FEE");
        Integer exam = getIntent().getExtras().getInt("EXAM_FEE");

        std.setText(stdf);
        tuitionFee.setText(tuition.toString());
        admissionFee.setText(admission.toString());
        examFee.setText(exam.toString());

    }

    public void bind(){
        std = findViewById(R.id.std);
        admissionFee = findViewById(R.id.admission_fee);
        tuitionFee = findViewById(R.id.tuition_fee);
        examFee = findViewById(R.id.exam_fee);
    }
}