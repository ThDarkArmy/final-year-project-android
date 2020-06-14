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
import com.tda.finalyear.activities.notice.EditNoticeActivity;
import com.tda.finalyear.activities.notice.NoticeActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.NoticeList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.NoticeViewHolder> {
    Context context;
    NoticeList notices;
    ConstraintLayout mainLayout;

    public NoticeAdapter(Context context, NoticeList notices) {
        this.context = context;
        this.notices = notices;
    }

    @NonNull
    @Override
    public NoticeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.notice_card_view, parent, false);
        return new NoticeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoticeViewHolder holder, int position) {
        holder.title.setText(notices.getNotices().get(position).getTitle());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditNoticeActivity.class);
                intent.putExtra("NOTICE_ID", notices.getNotices().get(position).getId());
                intent.putExtra("NOTICE_TITLE", notices.getNotices().get(position).getTitle());
                intent.putExtra("NOTICE_DESC", notices.getNotices().get(position).getDescription());
                intent.putExtra("NOTICE_DATE", notices.getNotices().get(position).getDate());
                context.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getNoticeService().deleteNotice(notices.getNotices().get(position).getId())
                        .enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                if (response.isSuccessful()) {
                                    notices.getNotices().remove(position);
                                    notifyDataSetChanged();
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Toast.makeText(context, "Delete Unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        });

        mainLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NoticeActivity.class);
                intent.putExtra("NOTICE_ID", notices.getNotices().get(position).getId());
                intent.putExtra("NOTICE_TITLE", notices.getNotices().get(position).getTitle());
                intent.putExtra("NOTICE_DESC", notices.getNotices().get(position).getDescription());
                intent.putExtra("NOTICE_DATE", notices.getNotices().get(position).getDate());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return notices.getNotices().size();
    }

    public class NoticeViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        Button edit, delete;

        public NoticeViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            delete = itemView.findViewById(R.id.delete_notice_card);
            edit = itemView.findViewById(R.id.edit_notice_card);
            mainLayout = itemView.findViewById(R.id.notice_card);

        }
    }
}
