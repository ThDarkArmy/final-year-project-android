package com.tda.finalyear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.recyclerview.widget.RecyclerView;

import com.tda.finalyear.R;
import com.tda.finalyear.models.Student;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@AllArgsConstructor
public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    private List<Student> studentList;
    private Context context;

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentViewHolder(LayoutInflater.from(context).inflate(R.layout.student_recycler_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        Student student = studentList.get(position);
        holder.studentNameRecyclerView.setText(student.getStudentName());
        holder.studentRollNumberRecyclerView.setText(String.format("Roll Number %s",student.getStudentRollNumber()));
        //Todo checkbox select logic
    }

    @Override
    public int getItemCount() {
        return studentList.size();
    }

    public class StudentViewHolder extends RecyclerView.ViewHolder {
        private TextView studentNameRecyclerView,studentRollNumberRecyclerView;
        private AppCompatCheckBox checkBoxRecyclerView;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            studentNameRecyclerView = itemView.findViewById(R.id.student_name_recycler_layout);
            studentRollNumberRecyclerView= itemView.findViewById(R.id.roll_number_recycler_layout);
            checkBoxRecyclerView = itemView.findViewById(R.id.checkbox_student_recycler_layout);
        }
    }
}
