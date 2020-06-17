package com.tda.finalyear.activities.student;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.tda.finalyear.R;
import com.tda.finalyear.activities.admin.AdminSettingsActivity;
import com.tda.finalyear.activities.assignment.AssignmentListActivity;
import com.tda.finalyear.activities.assignment.UploadAssignmentActivity;
import com.tda.finalyear.activities.attendance.TakeAttendanceActivity;
import com.tda.finalyear.activities.event.EventListActivity;
import com.tda.finalyear.activities.exam.ExamListActivity;
import com.tda.finalyear.activities.facility.FacilityListActivity;
import com.tda.finalyear.activities.fee.FeeListActivity;
import com.tda.finalyear.activities.holiday.HolidayListActivity;
import com.tda.finalyear.activities.notes.NotesActivity;
import com.tda.finalyear.activities.notes.NotesListActivity;
import com.tda.finalyear.activities.notes.UploadNotesActivity;
import com.tda.finalyear.activities.notice.NoticeListActivity;
import com.tda.finalyear.activities.school.AboutSchoolActivity;
import com.tda.finalyear.activities.teacher.TeacherActivity;
import com.tda.finalyear.activities.teacher.TeacherLoginActivity;
import com.tda.finalyear.activities.teacher.TeacherProfileActivity;
import com.tda.finalyear.activities.teacher.TeachersListActivity;
import com.tda.finalyear.models.AssignmentList;

public class StudentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener  {

    RelativeLayout holiday, event, notice, facility, exam, assignment, note, fees, studentProfile;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        bind();
        setSupportActionBar(toolbar);
        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        holiday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, HolidayListActivity.class));
            }
        });

        event.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, EventListActivity.class));
            }
        });

        notice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, NoticeListActivity.class));
            }
        });

        facility.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, FacilityListActivity.class));
            }
        });

        exam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, ExamListActivity.class));

            }
        });

        assignment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, AssignmentListActivity.class));
            }
        });

        note.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, NotesListActivity.class));
            }
        });

        fees.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, FeeListActivity.class));
            }
        });

        studentProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(StudentActivity.this, StudentProfileActivity.class));
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
        holiday = findViewById(R.id.holidays);
        event = findViewById(R.id.events);
        exam = findViewById(R.id.exams);
        fees = findViewById(R.id.fees);
        note = findViewById(R.id.notes);
        assignment = findViewById(R.id.assignment);
        notice = findViewById(R.id.notices);
        facility = findViewById(R.id.facilities);
        studentProfile = findViewById(R.id.profile);
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.nav_home :
                startActivity(new Intent(StudentActivity.this, TeacherActivity.class));
                break;
            case R.id.profile :
                startActivity(new Intent(StudentActivity.this, TeacherProfileActivity.class));
                break;
            case R.id.teachers :
                startActivity(new Intent(StudentActivity.this, TeachersListActivity.class));
                break;
            case R.id.students :
                startActivity(new Intent(StudentActivity.this, StudentsListActivity.class));
                break;
            case R.id.about :
                startActivity(new Intent(StudentActivity.this, AboutSchoolActivity.class));
                break;
            case R.id.settings :
                startActivity(new Intent(StudentActivity.this, AdminSettingsActivity.class));
                break;
            case R.id.logout :
                startActivity(new Intent(StudentActivity.this, TeacherLoginActivity.class));
                break;
        }
        //close navigation drawer
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}