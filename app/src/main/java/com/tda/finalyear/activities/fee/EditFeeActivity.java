package com.tda.finalyear.activities.fee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.Fee;

import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditFeeActivity extends AppCompatActivity {
    EditText std, admissionFee, tuitionFee, examFee;
    Button editFee, feeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_fee);
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

        editFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    updateFee(feeId);
                }
            }
        });

        feeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditFeeActivity.this, FeeListActivity.class));
            }
        });
    }

    public void updateFee(String feeId){
        Fee fee = new Fee(std.getText().toString(),Integer.parseInt(tuitionFee.getText().toString()),Integer.parseInt(examFee.getText().toString()),Integer.parseInt(admissionFee.getText().toString()));
        RetrofitClient.getInstance().getFeeService().editFee(feeId, fee).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        startActivity(new Intent(EditFeeActivity.this, FeeListActivity.class));
                    }else{
                        ErrorPojo errorPojo = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorPojo.class);
                        Log.i("fee", errorPojo.getMsg());
                        Toast.makeText(EditFeeActivity.this, errorPojo.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.i("fee", e.getMessage());
                    Toast.makeText(EditFeeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditFeeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // form validation
    public boolean validation(){
        String vStd = std.getText().toString();
        String vAdmFee = admissionFee.getText().toString();
        String vEX = examFee.getText().toString();
        String vTF = tuitionFee.getText().toString();
        if(vStd.isEmpty()){
            std.setError("Class field cannot be empty.");
            return false;
        }else if(vAdmFee.isEmpty() || !vAdmFee.matches("-?\\d+")){
            admissionFee.setError("Enter a valid admission fee.");
            return false;
        }else if(vTF.isEmpty() || !vTF.matches("-?\\d+")){
            examFee.setError("Enter a valid tuition fee.");
            return false;
        }else if(vEX.isEmpty() || !vEX.matches("-?\\d+")){
            examFee.setError("Enter a valid exam fee.");
            return false;
        }else{
            return true;
        }

    }

    public void bind(){
        std = findViewById(R.id.std);
        admissionFee = findViewById(R.id.admission_fee);
        tuitionFee = findViewById(R.id.tuition_fee);
        examFee = findViewById(R.id.exam_fee);
        editFee = findViewById(R.id.edit_fee);
        feeList = findViewById(R.id.view_fee_list);
    }
}