package com.tda.finalyear.activities.fee;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
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

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddFeeActivity extends AppCompatActivity {

    EditText std, admissionFee, tuitionFee, examFee;
    Button addFee, feeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_fee);
        bind();

        addFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    addFeeToDatabase();
                }

            }
        });

        feeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AddFeeActivity.this, FeeListActivity.class));
            }
        });

    }

    public void addFeeToDatabase(){
        Fee fee = new Fee(std.getText().toString(),Integer.parseInt(tuitionFee.getText().toString()),Integer.parseInt(examFee.getText().toString()),Integer.parseInt(admissionFee.getText().toString()));
        RetrofitClient.getInstance().getFeeService().addFee(fee).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        startActivity(new Intent(AddFeeActivity.this, FeeListActivity.class));
                    }else{
                        ErrorPojo errorPojo = new GsonBuilder().create().fromJson(response.errorBody().string(), ErrorPojo.class);
                        Log.i("fee", errorPojo.getMsg());
                        Toast.makeText(AddFeeActivity.this, errorPojo.getMsg(), Toast.LENGTH_SHORT).show();
                    }
                }catch (Exception e){
                    Log.i("fee", e.getMessage());
                    Toast.makeText(AddFeeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(AddFeeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        addFee = findViewById(R.id.add_fee);
        feeList = findViewById(R.id.view_fee_list);
    }
}