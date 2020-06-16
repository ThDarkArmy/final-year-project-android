package com.tda.finalyear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tda.finalyear.R;
import com.tda.finalyear.models.StudentList;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class AttendanceAdapter extends RecyclerView.Adapter<AttendanceAdapter.AttendanceViewHolder> {
    Context context;
    StudentList students;
    List<String> idList;

    public AttendanceAdapter(Context context, StudentList students) {
        this.context = context;
        this.students = students;
        idList =  new ArrayList<>();
    }

    public List<String> getIdList(){
        return idList;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.attendance_card_view, parent, false);
        return new AttendanceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AttendanceViewHolder holder, int position) {
        holder.roll.setText(students.getStudents().get(position).getRoll().toString()+".");
        holder.name.setText(students.getStudents().get(position).getName());
        if(holder.checkBox.isChecked()){
            idList.add(students.getStudents().get(position).getId());
        }
    }

    @Override
    public int getItemCount() {
        return students.getStudents().size();
    }

    public class AttendanceViewHolder extends RecyclerView.ViewHolder{
        TextView roll, name;
        CheckBox checkBox;
        public AttendanceViewHolder(@NonNull View itemView) {
            super(itemView);
            roll = itemView.findViewById(R.id.roll);
            name = itemView.findViewById(R.id.name);
            checkBox = itemView.findViewById(R.id.check_box);
        }
    }
}
