package com.tda.finalyear.activities.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.tda.finalyear.R;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ErrorPojo;
import com.tda.finalyear.models.Notice;

import java.io.IOException;
import java.util.Objects;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EditNoticeActivity extends AppCompatActivity {

    EditText title, description, date;
    Button editNotice, noticeList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_notice);
        bind();
        String noticeId = Objects.requireNonNull(getIntent().getExtras().getString("NOTICE_ID"));
        String noticeTitle = getIntent().getExtras().getString("NOTICE_TITLE");
        String noticeDesc = getIntent().getExtras().getString("NOTICE_DESC");
        String noticeDate = getIntent().getExtras().getString("NOTICE_DATE");
        title.setText(noticeTitle);
        description.setText(noticeDesc);
        date.setText(noticeDate);
        editNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validation()){
                    updateNotice(noticeId);
                }

            }
        });

        noticeList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(EditNoticeActivity.this, NoticeListActivity.class));
            }
        });
    }

    public void updateNotice(String id){
        Notice notice = new Notice(title.getText().toString(), description.getText().toString(), date.getText().toString());
        RetrofitClient.getInstance().getNoticeService().editNotice(id, notice).enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if(response.isSuccessful()){
                        startActivity(new Intent(EditNoticeActivity.this, NoticeListActivity.class));
                    }else{
                        Gson gson = new GsonBuilder().create();
                        ErrorPojo mError;
                        try {
                            mError= gson.fromJson(response.errorBody().string(),ErrorPojo.class);
                            Log.i("notice", mError.getMsg());
                            Toast.makeText(EditNoticeActivity.this, mError.getMsg(), Toast.LENGTH_LONG).show();
                        } catch (IOException e) {
                            Log.i("notice", e.getMessage());
                            Toast.makeText(EditNoticeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                }catch(Exception e){
                    Toast.makeText(EditNoticeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(EditNoticeActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    // form validation
    public boolean validation(){
        String vTitle = title.getText().toString();
        String vDEsc = description.getText().toString();
        String vDate = date.getText().toString();

        if(vTitle.isEmpty()){
            title.setError("Title field cannot be empty.");
            return false;
        }else if(vDate.isEmpty()){
            date.setError("Enter a valid date.");
            return false;
        }else if(vDEsc.isEmpty()){
            description.setError("Enter a valid description.");
            return false;
        }else{
            return true;
        }

    }

    public void bind(){
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
        editNotice = findViewById(R.id.edit_notice);
        noticeList = findViewById(R.id.view_notice_list);
    }
}