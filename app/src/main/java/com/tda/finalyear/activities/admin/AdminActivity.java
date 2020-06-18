package com.tda.finalyear.activities.admin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.widget.Toolbar;

import com.google.android.material.navigation.NavigationView;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.event.AddEventActivity;
import com.tda.finalyear.activities.exam.AddExamActivity;
import com.tda.finalyear.activities.facility.AddFacilityActivity;
import com.tda.finalyear.activities.fee.AddFeeActivity;
import com.tda.finalyear.activities.holiday.AddHolidayActivity;
import com.tda.finalyear.activities.notice.AddNoticeActivity;
import com.tda.finalyear.activities.school.AboutSchoolActivity;
import com.tda.finalyear.activities.student.StudentsListActivity;
import com.tda.finalyear.activities.teacher.TeachersListActivity;
import com.tda.finalyear.models.Admin;

import java.util.Objects;

public class AdminActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    RelativeLayout addHoliday, addEvent, addNotice, addFacility, addFee, addExam, adminProfile;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;
    TextView name, email;
    Admin admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);

        bind();
        admin = (Admin)Objects.requireNonNull(getIntent().getSerializableExtra("CLASS_TYPE"));
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        View headerView = navigationView.getHeaderView(0);
        name = headerView.findViewById(R.id.nav_header_name);
        email = headerView.findViewById(R.id.nav_header_email);
        name.setText(admin.getName());
        email.setText(admin.getEmail());

        addHoliday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddHolidayActivity.class));
            }
        });
        addExam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddExamActivity.class));
            }
        });
        addFee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddFeeActivity.class));
            }
        });
        addFacility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddFacilityActivity.class));
            }
        });
        addNotice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddNoticeActivity.class));
            }
        });
        addEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AdminActivity.this, AddEventActivity.class));
            }
        });
        adminProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, AdminProfileActivity.class);
                intent.putExtra("CLASS_TYPE", admin);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
            //drawerLayout.openDrawer(GravityCompat.START);
        }
    }

    public void bind(){
        addHoliday = findViewById(R.id.addholiday);
        addEvent = findViewById(R.id.addevent);
        addExam = findViewById(R.id.addexam);
        addFee = findViewById(R.id.addfee);
        addNotice = findViewById(R.id.add_notice);
        adminProfile = findViewById(R.id.profile);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        addFacility = findViewById(R.id.addfacility);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_home :
                startActivity(new Intent(AdminActivity.this, AdminActivity.class));
                break;
            case R.id.profile :
                Intent intent = new Intent(AdminActivity.this, AdminProfileActivity.class);
                intent.putExtra("CLASS_TYPE", admin);
                startActivity(intent);
                break;
            case R.id.teachers :
                startActivity(new Intent(AdminActivity.this, TeachersListActivity.class));
                break;
            case R.id.students :
                startActivity(new Intent(AdminActivity.this, StudentsListActivity.class));
                break;
            case R.id.about :
                startActivity(new Intent(AdminActivity.this, AboutSchoolActivity.class));
                break;
            case R.id.settings :
                startActivity(new Intent(AdminActivity.this, AdminSettingsActivity.class));
                break;
            case R.id.logout :
                startActivity(new Intent(AdminActivity.this, AdminLoginActivity.class));
                break;
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}