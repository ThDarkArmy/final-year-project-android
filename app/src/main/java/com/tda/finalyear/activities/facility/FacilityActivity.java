package com.tda.finalyear.activities.facility;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.tda.finalyear.R;

import java.util.Objects;

public class FacilityActivity extends AppCompatActivity {
    TextView name, description;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facility);
        bind();
        String facilityId = Objects.requireNonNull(getIntent().getExtras().getString("FACILITY_ID"));
        String facilityName = getIntent().getExtras().getString("FACILITY_NAME");
        String facilityDesc = getIntent().getExtras().getString("FACILITY_DESC");

        name.setText(facilityName);
        description.setText(facilityDesc);
    }

    public void bind(){
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
    }
}