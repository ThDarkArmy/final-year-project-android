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
import com.tda.finalyear.activities.teacher.TeacherDetailsActivity;
import com.tda.finalyear.models.StudentList;
import com.tda.finalyear.models.TeacherList;

public class TeacherAdapter extends RecyclerView.Adapter<TeacherAdapter.TeacherViewHolder> {
    Context context;
    TeacherList teachers;
    ConstraintLayout mainLayout;

    public TeacherAdapter(Context context, TeacherList teacherList) {
        this.context = context;
        this.teachers = teacherList;
    }

    @NonNull
    @Override
    public TeacherViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.teacher_card_view, parent, false);
        return new TeacherViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TeacherViewHolder holder, int position) {
        holder.name.setText(teachers.getTeachers().get(position).getName());
        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, TeacherDetailsActivity.class);
                intent.putExtra("CLASS_TYPE", teachers.getTeachers().get(position));
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return teachers.getTeachers().size();
    }


    public class TeacherViewHolder extends RecyclerView.ViewHolder{
        TextView name;
        public TeacherViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            mainLayout = itemView.findViewById(R.id.teacher_card);
        }
    }

}
