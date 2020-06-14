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
import com.tda.finalyear.activities.facility.EditFacilityActivity;
import com.tda.finalyear.activities.facility.FacilityActivity;
import com.tda.finalyear.api.RetrofitClient;
import com.tda.finalyear.models.FacilityList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FacilityAdapter extends RecyclerView.Adapter<FacilityAdapter.FacilityViewHolder> {
    FacilityList faclities;
    Context context;
    ConstraintLayout mainLayout;

    public FacilityAdapter(FacilityList faclities, Context context) {
        this.faclities = faclities;
        this.context = context;
    }

    @NonNull
    @Override
    public FacilityViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.facility_card_view, parent, false);
        return new FacilityViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FacilityViewHolder holder, int position) {
        holder.name.setText(faclities.getFacilities().get(position).getName());
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditFacilityActivity.class);
                intent.putExtra("FACILITY_ID", faclities.getFacilities().get(position).getId());
                intent.putExtra("FACILITY_NAME", faclities.getFacilities().get(position).getName());
                intent.putExtra("FACILITY_DESC", faclities.getFacilities().get(position).getDescription());
                context.startActivity(intent);
            }
        });
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RetrofitClient.getInstance().getFacilityService().deleteFacility(faclities.getFacilities().get(position).getId()).enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.isSuccessful()) {
                            faclities.getFacilities().remove(position);
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
                Intent intent = new Intent(context, FacilityActivity.class);
                intent.putExtra("FACILITY_ID", faclities.getFacilities().get(position).getId());
                intent.putExtra("FACILITY_NAME", faclities.getFacilities().get(position).getName());
                intent.putExtra("FACILITY_DESC", faclities.getFacilities().get(position).getDescription());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return faclities.getFacilities().size();
    }

    public class FacilityViewHolder extends RecyclerView.ViewHolder{

        TextView name;
        Button edit, delete;
        public FacilityViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            edit = itemView.findViewById(R.id.edit_facility_card);
            delete = itemView.findViewById(R.id.delete_facility_card);
            mainLayout = itemView.findViewById(R.id.facility_card);
        }
    }
}
