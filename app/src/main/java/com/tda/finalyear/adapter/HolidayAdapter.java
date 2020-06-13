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
import androidx.recyclerview.widget.RecyclerView;

import com.tda.finalyear.R;
import com.tda.finalyear.activities.holiday.EditHolidayActivity;
import com.tda.finalyear.activities.holiday.HolidayActivity;
import com.tda.finalyear.activities.holiday.HolidayListActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.Holiday;
import com.tda.finalyear.models.HolidayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


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
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i= new Intent(context, EditHolidayActivity.class);
                i.putExtra("HOLIDAY_ID",holidays.getHolidays().get(position).getId());
                i.putExtra("HOLIDAY_DESCRIPTION",holidays.getHolidays().get(position).getDescription());
                i.putExtra("HOLIDAY_DURATION",holidays.getHolidays().get(position).getDuration());
                i.putExtra("HOLIDAY_TITLE",holidays.getHolidays().get(position).getTitle());
                i.putExtra("HOLIDAY_START_DATE",holidays.getHolidays().get(position).getStartDate());
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getHolidayService().deleteHoliday(holidays.getHolidays().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if(response.isSuccessful()){
                            holidays.getHolidays().remove(position);
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
    }

    @Override
    public int getItemCount() {
        return holidays.getHolidays().size();
    }

    public class HolidayViewHolder extends RecyclerView.ViewHolder {
        TextView title; Button edit,delete;
        public HolidayViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.card_holiday_title);
            edit = itemView.findViewById(R.id.editHoliday);
            delete = itemView.findViewById(R.id.deleteHoliday);
        }
    }
}
