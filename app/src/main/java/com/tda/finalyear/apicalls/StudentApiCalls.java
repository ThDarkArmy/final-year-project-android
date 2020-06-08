package com.tda.finalyear.apicalls;

import android.util.Log;

import com.tda.finalyear.models.Student;

import java.util.ArrayList;
import java.util.List;

public class StudentApiCalls {
    //Todo Api Calls to server

    List<Student> studentList;

    public List<Student> getAllTheStudentOfTheParticularClass(Integer standard, String section){
        //After getting the response from server
        studentList =  new ArrayList<>();
        Student student =  new Student();
        Student student1 =  new Student();
        student.setStudentName("sahil");
        student.setStudentRollNumber(13000116068L);
        studentList.add(student);
        Log.d("TAG", "getAllTheStudentOfTheParticularClass: "+studentList);
        student1.setStudentName("Prashant");
        student1.setStudentRollNumber(13000116084L);
        studentList.add(student1);
        Log.d("TAG", "getAllTheStudentOfTheParticularClass: "+studentList);
        return studentList;
    }

}
