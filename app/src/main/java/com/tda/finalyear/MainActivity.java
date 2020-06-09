package com.tda.finalyear;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.tda.finalyear.activities.student.StudentSignupActivity;

public class MainActivity extends AppCompatActivity {

    Button signupButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        signupButton = findViewById(R.id.SignUpBtn);
        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, StudentSignupActivity.class);
                startActivity(intent);
            }
        });
    }

}