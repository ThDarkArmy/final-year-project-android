package com.tda.finalyear.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.tda.finalyear.R;
import com.tda.finalyear.activities.exam.EditExamActivity;
import com.tda.finalyear.activities.exam.ExamListActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.ExamList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamAdapter extends RecyclerView.Adapter<ExamAdapter.ExamViewHolder> {
    Context context;
    ExamList exams;
    ConstraintLayout mainLayout;

    public ExamAdapter(Context context, ExamList exams) {
        this.context = context;
        this.exams = exams;
    }

    @NonNull
    @Override
    public ExamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.exam_card_view, parent, false);
        return new ExamViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExamViewHolder holder, int position) {
        holder.title.setText(exams.getExams().get(position).getTitle());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditExamActivity.class);
                intent.putExtra("EXAM_ID", exams.getExams().get(position).getId());
                intent.putExtra("EXAM_STD", exams.getExams().get(position).getStd());
                intent.putExtra("EXAM_TITLE", exams.getExams().get(position).getTitle());
                intent.putExtra("EXAM_ROUTINE", exams.getExams().get(position).getRoutine());
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getExamService().deleteExam(exams.getExams().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            exams.getExams().remove(position);
                            notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ExamListActivity.class);
                intent.putExtra("EXAM_ID", exams.getExams().get(position).getId());
                intent.putExtra("EXAM_STD", exams.getExams().get(position).getStd());
                intent.putExtra("EXAM_TITLE", exams.getExams().get(position).getTitle());
                intent.putExtra("EXAM_ROUTINE", exams.getExams().get(position).getRoutine());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return exams.getExams().size();
    }

    public class ExamViewHolder extends RecyclerView.ViewHolder {

        TextView title;
        Button edit, delete;

        public ExamViewHolder(@NonNull View itemView) {
            super(itemView);
            edit = itemView.findViewById(R.id.edit_exam);
            delete = itemView.findViewById(R.id.delete_exam);
            title = itemView.findViewById(R.id.card_exam_title);
            mainLayout = itemView.findViewById(R.id.exam_card);
        }
    }

}
