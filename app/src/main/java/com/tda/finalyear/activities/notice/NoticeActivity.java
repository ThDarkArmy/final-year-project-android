package com.tda.finalyear.activities.notice;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import com.tda.finalyear.R;

import java.util.Objects;

public class NoticeActivity extends AppCompatActivity {
    TextView title, description, date;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice);
        bind();
        String noticeId = Objects.requireNonNull(getIntent().getExtras().getString("NOTICE_ID"));
        String noticeTitle = getIntent().getExtras().getString("NOTICE_TITLE");
        String noticeDesc = getIntent().getExtras().getString("NOTICE_DESC");
        String noticeDate = getIntent().getExtras().getString("NOTICE_DATE");
        title.setText(noticeTitle);
        description.setText(noticeDesc);
        date.setText(noticeDate);
    }


    public void bind(){
        title = findViewById(R.id.title);
        description = findViewById(R.id.description);
        date = findViewById(R.id.date);
    }
}

