package com.tda.finalyear.activities.fee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.student.StudentActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.FeeHistory;
import com.tda.finalyear.models.Student;
import com.tda.finalyear.models.StudentList;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeePaymentActivity extends AppCompatActivity {

    private Student student;
    private TextView name, tutionFee, examFee, admissionFee, totalFee;
    private Button payButton;
    private FeeHistory feeHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_payment);

        student = (Student) Objects.requireNonNull(getIntent().getSerializableExtra("CLASS_TYPE"));
        bind();
        name.setText(student.getName());
        feeHistory = student.getFeeHistory().get(student.getFeeHistory().size() - 1);
        if(feeHistory.getIsPaid()){
            examFee.setText("0");
            tutionFee.setText("0");
            admissionFee.setText("0");
            totalFee.setText("0");
        }else{
            examFee.setText(feeHistory.getExamFee().toString());
            tutionFee.setText(feeHistory.getTuitionFee().toString());
            admissionFee.setText(feeHistory.getAdmissionFee().toString());
            Integer tution_fee = Integer.parseInt(tutionFee.getText().toString());
            Integer exam_fee = Integer.parseInt(examFee.getText().toString());
            Integer admission_fee = Integer.parseInt(admissionFee.getText().toString());
            Integer total_fee = tution_fee + exam_fee + admission_fee;
            totalFee.setText(total_fee.toString());
        }


        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> id = new HashMap<>();
                id.put("id", student.getId());
                System.out.println(id);
                payment(id);
            }
        });
    }

    private void payment(Map<String, String> id) {
        Call<ResponseBody> call = RetrofitClient.getInstance().getStudentService().feePayment(id);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FeePaymentActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FeePaymentActivity.this, StudentActivity.class);
                    intent.putExtra("CLASS_TYPE", student);
                    examFee.setText("0");
                    tutionFee.setText("0");
                    admissionFee.setText("0");
                    totalFee.setText("0");
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(FeePaymentActivity.this, "Payment Unsuccessful", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void bind() {
        name = findViewById(R.id.name);
        tutionFee = findViewById(R.id.tuition_fee);
        examFee = findViewById(R.id.exam_fee);
        admissionFee = findViewById(R.id.admission_fee);
        totalFee = findViewById(R.id.total_fee);
        payButton = findViewById(R.id.buttonpay);
    }
}