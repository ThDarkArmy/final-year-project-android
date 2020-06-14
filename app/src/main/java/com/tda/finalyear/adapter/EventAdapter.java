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
import com.tda.finalyear.activities.event.EditEventActivity;
import com.tda.finalyear.activities.event.EventActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.EventList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {
    Context context;
    private EventList events;
    ConstraintLayout mainLayout;

    public EventAdapter(Context context, EventList events) {
        this.context = context;
        this.events = events;
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_card_view, parent, false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        holder.title.setText(events.getEvents().get(position).getTitle());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EditEventActivity.class);
                i.putExtra("EVENT_ID", events.getEvents().get(position).getId());
                i.putExtra("EVENT_DESCRIPTION", events.getEvents().get(position).getDescription());
                i.putExtra("EVENT_DURATION", events.getEvents().get(position).getDuration());
                i.putExtra("EVENT_TITLE", events.getEvents().get(position).getTitle());
                i.putExtra("EVENT_START_DATE", events.getEvents().get(position).getStartDate());
                context.startActivity(i);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getEventService().deleteEvent(events.getEvents().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            events.getEvents().remove(position);
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
                Intent intent = new Intent(context, EventActivity.class);
                intent.putExtra("EVENT_ID", events.getEvents().get(position).getId());
                intent.putExtra("EVENT_DESCRIPTION", events.getEvents().get(position).getDescription());
                intent.putExtra("EVENT_DURATION", events.getEvents().get(position).getDuration());
                intent.putExtra("EVENT_TITLE", events.getEvents().get(position).getTitle());
                intent.putExtra("EVENT_START_DATE", events.getEvents().get(position).getStartDate());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return events.getEvents().size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder{
        TextView title;
        Button edit, delete;
        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_event_title);
            edit = itemView.findViewById(R.id.editEvent);
            delete = itemView.findViewById(R.id.deleteEvent);
            mainLayout = itemView.findViewById(R.id.event_card);
        }
    }
}
