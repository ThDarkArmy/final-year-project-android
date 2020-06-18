package com.tda.finalyear.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tda.finalyear.R;
import com.tda.finalyear.activities.student.StudentDetailsActivity;
import com.tda.finalyear.activities.student.StudentsListActivity;
import com.tda.finalyear.models.StudentList;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

    Context context;
    StudentList students;
    ConstraintLayout mainLayout;

    public StudentAdapter(Context context, StudentList students) {
        this.context = context;
        this.students = students;
    }

    @NonNull
    @Override
    public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.student_card_view, parent, false);
        return new StudentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
        holder.roll.setText(students.getStudents().get(position).getRoll().toString()+".");
        holder.name.setText(students.getStudents().get(position).getName());
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, StudentDetailsActivity.class);
                intent.putExtra("CLASS_TYPE", students.getStudents().get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return students.getStudents().size();
    }


    public class StudentViewHolder extends RecyclerView.ViewHolder{
        TextView roll, name;
        public StudentViewHolder(@NonNull View itemView) {
            super(itemView);
            roll = itemView.findViewById(R.id.roll);
            name = itemView.findViewById(R.id.name);
            mainLayout = itemView.findViewById(R.id.student_card);
        }
    }

}
