package com.tda.finalyear.activities.exam;

import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.tda.finalyear.R;

import java.util.Objects;

public class ExamActivity extends AppCompatActivity {
    TextView title, std;
    ImageView routineImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        bind();
        String examId = Objects.requireNonNull(getIntent().getStringExtra("EXAM_ID"));
        String ETitle = getIntent().getStringExtra("EXAM_TITLE");
        String EStd = getIntent().getStringExtra("EXAM_STD");
        Uri myUri=getIntent().getData();
        title.setText(ETitle);
        std.setText(EStd);
        Picasso.get().load(myUri).into(routineImage);
    }

    public void bind(){
        title = findViewById(R.id.title);
        std = findViewById(R.id.std);
        routineImage = findViewById(R.id.routine_image);
    }
}