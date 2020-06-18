package com.tda.finalyear.activities.fee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.student.StudentActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.FeeHistory;
import com.tda.finalyear.models.Student;

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
    private MaterialButton payButton;
    private FeeHistory feeHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fee_payment);

        student = (Student) Objects.requireNonNull(getIntent().getSerializableExtra("CLASS_TYPE"));
        bind();

        name.setText(student.getName());
        feeHistory = student.getFeeHistory().get(student.getFeeHistory().size() - 1);
        examFee.setText(feeHistory.getExamFee());
        tutionFee.setText(feeHistory.getTuitionFee());
        admissionFee.setText(feeHistory.getAdmissionFee());
        Integer tution_fee = Integer.parseInt(tutionFee.getText().toString());
        Integer exam_fee = Integer.parseInt(examFee.getText().toString());
        Integer admission_fee = Integer.parseInt(admissionFee.getText().toString());
        Integer total_fee = tution_fee + exam_fee + admission_fee;
        totalFee.setText(total_fee.toString());

        payButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Map<String, String> studentId = new HashMap<>();
                studentId.put("id", student.getId());
                payment(studentId);
            }
        });
    }

    private void payment(Map<String, String> studentId) {
        Call<ResponseBody> call = RetrofitClient.getInstance().getStudentService().feePayment(studentId);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(FeePaymentActivity.this, "Payment Successful", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(FeePaymentActivity.this, StudentActivity.class);
                    intent.putExtra("CLASS_TYPE", student);
                    startActivity(intent);
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