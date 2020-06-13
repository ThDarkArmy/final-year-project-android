package com.tda.finalyear.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.tda.finalyear.R;
import com.tda.finalyear.models.HolidayList;



public class HolidayAdapter extends RecyclerView.Adapter<HolidayAdapter.HolidayViewHolder> {
    Context context;
    private HolidayList holidays;

    public HolidayAdapter(Context context, HolidayList holidays) {
        this.context = (Context) context;
        this.holidays = holidays;
    }

    @NonNull
    @Override
    public HolidayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.holiday_card_view, parent, false);
        return new HolidayViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HolidayViewHolder holder, int position) {
        holder.title.setText(holidays.getHolidays().get(position).getTitle());
        holder.description.setText(holidays.getHolidays().get(position).getDescription());
        holder.date.setText(holidays.getHolidays().get(position).getStartDate());
        holder.duration.setText(holidays.getHolidays().get(position).getDuration().toString());
    }

    @Override
    public int getItemCount() {
        return holidays.getHolidays().size();
    }

    public class HolidayViewHolder extends RecyclerView.ViewHolder {
        TextView title, duration, description, date;
        public HolidayViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_holiday_title);
            description = itemView.findViewById(R.id.card_holiday_desc);
            date = itemView.findViewById(R.id.card_holiday_date);
            duration = itemView.findViewById(R.id.card_holiday_duration);
        }
    }
}
